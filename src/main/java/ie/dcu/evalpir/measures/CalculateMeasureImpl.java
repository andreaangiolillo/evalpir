package ie.dcu.evalpir.measures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ie.dcu.evalpir.elements.ConsolePrinter;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.Log;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.MeasureCompound;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Pair;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.exceptions.DifferentQueryException;
import ie.dcu.evalpir.exceptions.DifferentSizeException;
import ie.dcu.evalpir.exceptions.QueryNotInTheLogFileException;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * **/
public class CalculateMeasureImpl{

	//private Map<String, String> measures;
	private Map<String, Query> relevanceFile;
	private Map<String, Session> logsFile;
	
	
	/**
	 * Constructor
	 * @param 
	 */
	public CalculateMeasureImpl(Map<String, Query> relevanceFile) {
		this.relevanceFile = relevanceFile;  // key = queryID, value = Query
	}
	
	
	
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
			Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
			DocumentRelFile docRel;
			DocumentOutputPIR docOut;
			ArrayList<Pair<Integer, Double>> listPair = new ArrayList<Pair<Integer, Double>>();
			while (itDocOutputPIR.hasNext()) {
				Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
				docOut = (DocumentOutputPIR)pairDocOUT.getValue();
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
			
			return listPair;	
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
		Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
		DocumentRelFile docRel;
		DocumentOutputPIR docOut;
		while (itDocOutputPIR.hasNext()) {
			Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
			docOut = (DocumentOutputPIR)pairDocOUT.getValue();
			docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
			value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
			if(docOut.getRank() <= p) {	
				docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
				value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
				dcg += (docOut.getRank() == 1) ? value_relevance : value_relevance / (log(docOut.getRank() + 1, 2));	
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
		Iterator<Entry<String, Document>> itDocRel = queryRel.getDocs().entrySet().iterator();
		DocumentRelFile docRel;
		Map.Entry<?,?> pairDoc;
		while (itDocRel.hasNext()) {
			pairDoc = (Map.Entry<?,?>)itDocRel.next();
			docRel = (DocumentRelFile)pairDoc.getValue();
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
			idcgValue += idcg.get(i) / (log(rank + 1, 2));
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
		return DCG(queryRel, queryOutputPIR, p) / IDCG( queryRel, p);
	}
	
	/**
	 * Changing of base
	 * 
	 * @input value
	 * @input base
	 * */
	public static double log(int value, int base) {
		return Math.log(value) / Math.log(base);
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
			Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
			DocumentRelFile docRel;
			DocumentOutputPIR docOut;
			while (itDocOutputPIR.hasNext()) {
				docOut = (DocumentOutputPIR)itDocOutputPIR.next().getValue();
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
	
	public static ArrayList<ArrayList<Integer>>  partition(int n, int k, int j, ArrayList<Integer> prefix, ArrayList<ArrayList<Integer>> memo) {
		if(prefix.size()> j || (n == 0 && prefix.size() < j)) {
	        	return memo;
		} 
		  
		if (n == 0) {
			System.out.println(prefix);
	        memo.add(prefix);
	        return memo;
		}
	        
		for (int i = n; i >= 1; i--) {
			
			if (i <=k) {
				ArrayList<Integer> prefix_ = new ArrayList<Integer>(prefix);
				prefix_.add(i);
				//System.out.println(i);
				
				partition(n-i, k, j, prefix_, memo);
			}
			
		}
		
		return memo;
	    
	}
	  
	  
	
	
	
	/**
	 * @param userID
	 * @param topicId
	 * @param queryId
	 * @param k
	 * @param measure
	 * @return the text with the measure's info
	 */
	public static String printMeasure(String userID, String topicId, String queryId, int k, Double measure) {
		return "UserID: " + userID + (topicId.equalsIgnoreCase("") ? "" : " TopicID: " + topicId)
				+ (queryId.equalsIgnoreCase("") ? "" : " QueryID: " + queryId) + " Precision@" + k + ": " + String.valueOf(measure) + "\n";  
		
	}
	
	/***
	 * 
	 * @param pirs
	 */
	public void calculateMeasures(ArrayList<PIR> pirs) {
		ConsolePrinter.startTask("Calculating Measures");
		Query queryPIR;
		QueryRelFile queryRel;
		/*Measures variables*/
		double qPrecisionK = 0.0;
		double qRecallK = 0.0;
		double qNDCG5, qNDCG10, qNDCG15, qNDCG20 = 0.0;
		double precision = 0.0;
		double recall = 0.0;
		double fMeasure = 0.0;
		double ap = 0.0;
		
		int k = 0;
		//int nQuery = getRelevanceFile().size();
		Iterator<Entry<String, Query>> it = getRelevanceFile().entrySet().iterator();
		while (it.hasNext()) {
			queryRel = (QueryRelFile) it.next().getValue();
			//queryRel = createMeasure(queryRel);
			for(PIR pir : pirs) {
				queryPIR = pir.getQuery(queryRel.getId());
				if(queryPIR != null) {
					queryRel.setToConsiderForChart(true);
					k =  queryRel.getNRelevantDoc();
					for (; k != 0; k --) {
						qPrecisionK = calculatePKRK(queryRel, queryPIR, k, false);
						qRecallK = calculatePKRK(queryRel, queryPIR, k, true);
						
						((Measure) queryRel.searchAddMeasure("Precision@"+k, false)).addPIR(pir.getName(), qPrecisionK);
						((Measure) queryRel.searchAddMeasure("Recall@"+k, false)).addPIR(pir.getName(), qRecallK);
						
					}
					
					qNDCG5 = calculateNDCG(queryRel, queryPIR, 5);
					qNDCG10 = calculateNDCG(queryRel, queryPIR, 10);
					qNDCG15 = calculateNDCG(queryRel, queryPIR, 15);
					qNDCG20 = calculateNDCG(queryRel, queryPIR, 20);
					precision = precision(queryRel, queryPIR);
					recall = recall(queryRel, queryPIR);
					fMeasure = fMeasure(precision, recall, 0.5);
					ap = calculateAP(queryRel, queryPIR);
						
					((Measure) queryRel.searchAddMeasure("NDCG@5", false)).addPIR(pir.getName(), qNDCG5);
					((Measure) queryRel.searchAddMeasure("NDCG@10",false)).addPIR(pir.getName(), qNDCG10);
					((Measure) queryRel.searchAddMeasure("NDCG@15", false)).addPIR(pir.getName(), qNDCG15);
					((Measure) queryRel.searchAddMeasure("NDCG@20", false )).addPIR(pir.getName(), qNDCG20);
					((Measure) queryRel.searchAddMeasure("Precision", false)).addPIR(pir.getName(), precision);
					((Measure) queryRel.searchAddMeasure("Recall", false)).addPIR(pir.getName(), recall);
					((Measure) queryRel.searchAddMeasure("fMeasure0.5", false)).addPIR(pir.getName(), fMeasure);
					((Measure) queryRel.searchAddMeasure("Average Precision", false)).addPIR(pir.getName(), ap);
					((MeasureCompound)queryRel.searchAddMeasure("PrecisionRecallCurve", true)).addPIR(pir.getName(), precisionRecallCurve(queryRel, queryPIR));
				
				}
				
			}
		}
		
		ConsolePrinter.endTask("Calculating Measures");
	}
	
	/**
	 * @return the relevanceDoc
	 */
	public Map<String, Query> getRelevanceFile() {
		return relevanceFile;
	}

	/**
	 * @return the logsFile
	 */
	public Map<String, Session> getLogsFile() {
		return logsFile;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Session getSession(String key){
		return getLogsFile().get(key);
		
	}
	
	
	
	
	
	
}


