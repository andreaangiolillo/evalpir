package ie.dcu.evalpir.measures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import ie.dcu.evalpir.EvalEpir;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.MeasureCompound;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.output.table.ConsolePrinter;
import ie.dcu.evalpir.utilities.Pair;
import ie.dcu.evalpir.utilities.Utilities;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * It computes the measures 
 * **/
public class CalculateMeasure{
	
	/**
	 * Precision (P) is defined as the proportion of retrieved documents that
	 * are relevant; it measures the ability of the system to retrieve only the
	 * relevant documents.
	 * 
	 * @input queryRel
	 * @input queryOutputPIR
	 * @return 
	 * @Complexity O(n)
	 * **/
	public static double precision(Query queryRel, Query queryOutputPIR){
		return calculatePKRK(queryRel, queryOutputPIR, queryOutputPIR.getDocs().size(), false);
	}
	
	/**
	 * Recall (R) is the the proportion of relevant documents that are retrieved; 
	 * it measures the ability of the system to retrieve all the relevant
	 * documents from the archive.
	 * 
	 * @input queryRel
	 * @input queryOutputPIR
	 * @return 
	 * @Complexity O(n)
	 * **/
	public static double recall(Query queryRel, Query queryOutputPIR){
		return calculatePKRK(queryRel, queryOutputPIR, queryOutputPIR.getDocs().size(), true);
	}
	
	/**
	 * F-measure (F) is computed as the weighted harmonic 
	 * mean of precision and recall:
	 * 
	 * @input precision
	 * @input recall
	 * @input alpha must be in (0, 1]
	 * @return f_meausure
	 * @Complexity O(1) 
	 * **/
	public static double fMeasure(double precision, double recall, double alpha) {
		return (alpha > 0.0 && alpha <= 1.0 ) ? (1/((alpha * 1/precision) + (1 - alpha) * 1/recall)) : 0;
	}
	
	
	/**
	 * This function is used to calculate the Precision/Recall Curve
	 * @input queryRel
	 * @input queryOutputPIR
	 * @assumption If a relevant document never gets retrieved, we assume the precision corresponding to that relevant doc is 0 
	 * @return listPair
	 * @Complexity O(n^2)
	 * **/
	public static ArrayList<Pair<Integer, Double>> precisionRecallCurve(Query queryRel, Query queryOutputPIR) {		
		int nRelevantDoc = ((QueryRelFile) queryRel).getNRelevantDoc();
		if(nRelevantDoc != 0) {
			int nRelDocFind = 0;
			DocumentRelFile docRel;
			DocumentOutputPIR docOut;
			ArrayList<Pair<Integer, Double>> listPair = new ArrayList<Pair<Integer, Double>>();
			for(Map.Entry<String, Document> entryDocPIR : queryOutputPIR.getDocs().entrySet()) {
				docOut = (DocumentOutputPIR)entryDocPIR.getValue();
				docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
				if(docRel != null && docRel.getIsRelevance()) {
					nRelDocFind ++;
					listPair.add(new Pair<Integer, Double>(docOut.getRank(), calculatePKRK(queryRel, queryOutputPIR , docOut.getRank(), false)));					
				}
			}
			
			Collections.sort(listPair);
			for(int i = 0; i < listPair.size(); i++) { // setting the key (the recall point) where the doc was found
				listPair.get(i).setKey(((i + 1) * 100)/ nRelevantDoc);
			}
			
			if(nRelevantDoc > nRelDocFind){// If a relevant document never gets retrieved, we assume the precision corresponding to that relevant doc is 0 
				while(nRelevantDoc > nRelDocFind) {
					nRelDocFind ++;
					listPair.add(new Pair<Integer, Double>((nRelDocFind * 100)/nRelevantDoc, 0.0));	
				}
			}
			
			return computeInterpolation(listPair);	
		}
	
		return null;	
	}
	
	/***
	 * Average Precision (AP) is the average precision at k values
	 * computed after each relevant document is retrieved for a given topic,
	 *  
	 * If the query doesn't have at least one relevant document it returns 0
	 * @param queryRel
	 * @param queryOutputPIR
	 * @return
	 */
	public static double calculateAP(Query queryRel, Query queryOutputPIR) {
		ArrayList<Pair<Integer, Double>> ap = precisionRecallCurve(queryRel, queryOutputPIR);
		if(ap != null) {
			double averagePrecision = 0.0;
			int size = ap.size();
			for(int i = 0; i < size; i++) {
				averagePrecision += ap.get(i).getValue();
			}
						
			return averagePrecision / size;		
		}
		
		return 0.0;
	}
	
	/**
	 *  It implements an interpolated precision that takes the maximum precision 
	 *  over all recalls greater than r.
	 * 
	 * @input r
	 * @input listPair
	 * @return interpolated precision
	 * **/
	public static double interpolation(int r, ArrayList<Pair<Integer, Double>> listPair) {
		double max = 0.0;
		for (Pair<Integer, Double> p : listPair) {
			if(p.getKey() >= r) {
				max = (max >= p.getValue()) ? max : p.getValue();	
			}	
		}
		
		return max;
	}
	
	/**
	 * In computes the interpolation
	 * @param listPair
	 * @return
	 */
	public static ArrayList<Pair<Integer, Double>> computeInterpolation(ArrayList<Pair<Integer, Double>> listPair) {
		int i = 0;
		ArrayList<Pair<Integer, Double>> listPairInterpolated = new ArrayList<Pair<Integer, Double>>();
		while(i<=100) {
			listPairInterpolated.add(new Pair<Integer, Double>(i, interpolation(i, listPair)));
			i+=10;
		}
		
		return listPairInterpolated;
	}
	
	/**
	 * Discounted Cumulative Gain (DCG) is a metric that is designed for experiments where documents are judged
	 * using a non-binary relevance scale. It assigns higher scores for more
	 * relevant documents being ranked higher in the ranked results list.
	 * @param queryRel
	 * @param queryOutputPIR
	 * @param p
	 * @return
	 */
	public static double DCG(Query queryRel, Query queryOutputPIR, int p) {
		double dcg = 0.0;
		int value_relevance = 0;
		DocumentRelFile docRel;
		DocumentOutputPIR docOut;
		for(Map.Entry<String, Document> entryDocPIR : queryOutputPIR.getDocs().entrySet() ) {
			docOut = (DocumentOutputPIR)entryDocPIR.getValue();
			docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
			value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
			if(docOut.getRank() <= p) {	
				docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
				value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
				dcg += (docOut.getRank() == 1) ? value_relevance : value_relevance / (Utilities.log(docOut.getRank() + 1, 2));	
			}
		}
		
		return dcg;
	}
		
	/**
	 * Ideal DCG
	 * 
	 *@input queryRel: query with DocumentRelFile
	 *@input p : the number of the documents in the ranked list to consider
	 *@return the IDCG for the given data
	 *
	 *To create the IDCG, it considers only the relevant document in the queryRel
	 */
	public static double IDCG(Query queryRel, int p) {
		ArrayList<Integer> idcg = new ArrayList<Integer>();
		DocumentRelFile docRel;
		for(Map.Entry<String, Document> entry: queryRel.getDocs().entrySet()) {
			docRel = (DocumentRelFile)entry.getValue();
			idcg.add(docRel.getRelevance());
		}
		
		Collections.sort(idcg, new Comparator<Integer>() {
		    public int compare(Integer o1, Integer o2) {
		        return o2.compareTo(o1);
		    }
		});

		double idcgValue = idcg.get(0);
		int rank = 2;
		p = (p < idcg.size()) ? p : idcg.size(); // if p > idcg.size the value of the documents after size is 0 
		for (int i = 1; i < p; i++){	
			idcgValue += idcg.get(i) / (Utilities.log(rank + 1, 2));
			rank ++;	
		}
		
		return idcgValue;
	}
//	public static double IDCG(Query queryRel, Query queryOutputPIR, int p) {
//		ArrayList<Integer> idcg = new ArrayList<Integer>();
//		int value_relevance = 0;
//		Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
//		DocumentRelFile docRel;
//		DocumentOutputPIR docOut;
//		while (itDocOutputPIR.hasNext()) {
//			Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
//			docOut = (DocumentOutputPIR)pairDocOUT.getValue();
//			docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
//			value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
//			idcg.add(value_relevance);	
//		}
//		
//		Collections.sort(idcg, new Comparator<Integer>() {
//		    public int compare(Integer o1, Integer o2) {
//		        return o2.compareTo(o1);
//		    }
//		});
//		
//		double idcgValue = idcg.get(0);
//		int rank = 2;
//		for (int i = 1; i < p; i++){	
//			idcgValue += idcg.get(i) / (log(rank + 1, 2));
//			rank ++;	
//		}
//		
//		return idcgValue;
//	}
	
	/**
	 * Normalized Discounted Cumulative Gain (NDCG) is a precision
	 * metric that is designed for experiments where documents are judged
	 * using a non-binary relevance scale. It assigns higher scores for more
	 * relevant documents being ranked higher in the ranked results list.
	 * 
	 *@input realData
	 *@input predictionData
	 *@input p
	 *@return the NDCG for the given data
	 *@Complexity O(n)
	 */
	public static double calculateNDCG(Query queryRel, Query queryOutputPIR, int p) {
		return DCG(queryRel, queryOutputPIR, p) / IDCG(queryRel, p);
	}	

	/**
	 * Precision at k (P@k) measures the fraction of retrieved relevant
	 * documents within the top k retrieved documents. It applies a cut-off
	 * at the k-th result in the list and takes the top k documents as a set.
	 * 
	 * Recall at k (R@k) measures the fraction of retrieved relevant documents 
	 * within the top k documents over the total number of relevant
	 * documents in the document collection.
	 * 
	 * It computes the precision@k and the recall@k
	 * If the query doesn't have at least one relevant document it returns 0.
	 * 
	 * @param queryRel
	 * @param queryResult
	 * @param k
	 * @param recall : if it is true computes the recall@k otherwise precision@k
	 * @return (relDoc / denominator)
	 * @Complexity Complexity: O(n)
	 */
	public static double calculatePKRK(Query queryRel, Query queryOutputPIR , int k, boolean recall) {
		double relDocFounded = 0; // number of relevant docs found
		double nRelevantDoc = ((QueryRelFile) queryRel).getNRelevantDoc(); // number of relevant docs in queryRel
		if(nRelevantDoc != 0) {
			DocumentRelFile docRel;
			DocumentOutputPIR docOut;	
			for(Map.Entry<String, Document> entryDocPIR:  queryOutputPIR.getDocs().entrySet()) {
				docOut = (DocumentOutputPIR)entryDocPIR.getValue();
				docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
				if((docRel != null) && (docOut.getRank() <= k) && (docRel.getIsRelevance() == true)) {
					relDocFounded ++;
				}
			}
			
			double denominator = (recall == true) ? nRelevantDoc : k; 
			return (relDocFounded / denominator);
		}
		
		return 0;
	}	
		
	/***
	 * 
	 * @param pirs
	 */
	public static void calculateMeasures() {
		ArrayList<PIR> pirs = EvalEpir.MODELS;
		ConsolePrinter.startTask("Calculating Measures");
		Query queryPIR;
		QueryRelFile queryRel;
		double qPrecisionK = 0.0;
		double qRecallK = 0.0;
		double qNDCG5, qNDCG10, qNDCG15, qNDCG20 = 0.0;
//		double qNDCG5NewInfo, qNDCG10NewInfo, qNDCG15NewInfo, qNDCG20NewInfo = 0.0;
		double precision, precisionNewInfo = 0.0;
		double recall = 0.0;
		double fMeasure = 0.0;
		double ap, apNewInfo = 0.0;
		int k = 0;
		String zeroToSort = ""; // this variable adds 0 in case of k <= 9 (so we have 01, 02, 03, etc.. instead of 1, 2 ..) to allow to sort the measures by name
		
		for(Map.Entry<String, Query> entry :  EvalEpir.QUERYREL.entrySet()) {
			queryRel = (QueryRelFile) entry.getValue();
			for(PIR pir : pirs) {
				queryPIR = pir.getQuery(queryRel.getId());
				if(queryPIR != null) {
					queryRel.setToConsiderForChart(true); // not all the queries in the collection are used to create charts
					k =  queryRel.getNRelevantDoc();
					for (; k != 0; k --) {
						qPrecisionK = calculatePKRK(queryRel, queryPIR, k, false);
						qRecallK = calculatePKRK(queryRel, queryPIR, k, true);
						zeroToSort = (k > 9) ? "" : "0";
						((Measure) queryRel.searchAddMeasure("Precision@"+ zeroToSort+ k, false, true, EvalEpir.MEASURES_FOR_CHART.contains("Precision@"))).addPIR(pir.getName(), qPrecisionK);
						((Measure) queryRel.searchAddMeasure("Recall@"+ zeroToSort + k, false, true, EvalEpir.MEASURES_FOR_CHART.contains("Recall@"))).addPIR(pir.getName(), qRecallK);
						
					}
					
					qNDCG5 = calculateNDCG(queryRel, queryPIR, 5);
					qNDCG10 = calculateNDCG(queryRel, queryPIR, 10);
					qNDCG15 = calculateNDCG(queryRel, queryPIR, 15);
					qNDCG20 = calculateNDCG(queryRel, queryPIR, 20);
					precision = precision(queryRel, queryPIR);
					recall = recall(queryRel, queryPIR);
					fMeasure = fMeasure(precision, recall, 0.5);
					ap = calculateAP(queryRel, queryPIR);
	
					((Measure) queryRel.searchAddMeasure("NDCG@05", false, true, EvalEpir.MEASURES_FOR_CHART.contains("NDCG@05"))).addPIR(pir.getName(), qNDCG5);
					((Measure) queryRel.searchAddMeasure("NDCG@10",false, true, EvalEpir.MEASURES_FOR_CHART.contains("NDCG@10"))).addPIR(pir.getName(), qNDCG10);
					((Measure) queryRel.searchAddMeasure("NDCG@15", false, true, EvalEpir.MEASURES_FOR_CHART.contains("NDCG@15"))).addPIR(pir.getName(), qNDCG15);
					((Measure) queryRel.searchAddMeasure("NDCG@20", false, true, EvalEpir.MEASURES_FOR_CHART.contains("NDCG@20") )).addPIR(pir.getName(), qNDCG20);
					((Measure) queryRel.searchAddMeasure("Precision", false, true, EvalEpir.MEASURES_FOR_CHART.contains("Precision"))).addPIR(pir.getName(), precision);
					((Measure) queryRel.searchAddMeasure("Recall", false, true, EvalEpir.MEASURES_FOR_CHART.contains("Recall"))).addPIR(pir.getName(), recall);
					((Measure) queryRel.searchAddMeasure("fMeasure0.5", false, true, EvalEpir.MEASURES_FOR_CHART.contains("fMeasure0.5"))).addPIR(pir.getName(), fMeasure);
					((Measure) queryRel.searchAddMeasure("AveragePrecision", false, true, EvalEpir.MEASURES_FOR_CHART.contains("AveragePrecision"))).addPIR(pir.getName(), ap);
					
					//Compound measure
					((MeasureCompound)queryRel.searchAddMeasure("PrecisionRecallCurve", true, false, EvalEpir.MEASURES_FOR_CHART.contains("PrecisionRecallCurve"))).addPIR(pir.getName(), precisionRecallCurve(queryRel, queryPIR));
				
					
					precisionNewInfo = CalculateSessionMeasure.precisionConsideringNewInformation(queryRel, queryPIR);
					apNewInfo = CalculateSessionMeasure.calculateAPConsideringNewInformation(queryRel, queryPIR);
					if(precision != precisionNewInfo) {
						//considering only the relevant docs not in the previous queries	
						//System.out.println("TopicID: " + queryRel.getTopic() + " Query: " + queryRel.getId() );
						//System.out.println("Precision " + precision  + " PrecisionNewInfo: " + precisionNewInfo + " diff: " + (precision - precisionNewInfo));
						((Measure) queryRel.searchAddMeasure("Precision-NewInfo", false, false, false)).addPIR(pir.getName(), precisionNewInfo);
						((Measure) queryRel.searchAddMeasure("AveragePrecision-NewInfo", false, false, false)).addPIR(pir.getName(), apNewInfo);
						//System.out.println("QUII LA SETTIAMO:" + queryRel.getMeasures().get("precision").getName() + " PRIMA: " + queryRel.getMeasures().get("precision").isStackedBar());
						queryRel.getMeasures().get("precision").setStackedBar(true);
						//System.out.println("QUII LA SETTIAMO:" + queryRel.getMeasures().get("precision").getName() + " PRIMA: " + queryRel.getMeasures().get("precision").isStackedBar());
						
						queryRel.getMeasures().get("averageprecision").setStackedBar(true);	
					}
				
				}
				
			}
		}
		
		ConsolePrinter.endTask("Calculating Measures");
	}
}


