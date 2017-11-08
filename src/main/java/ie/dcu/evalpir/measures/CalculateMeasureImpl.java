package ie.dcu.evalpir.measures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.spi.TransactionalWriter;

import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.Log;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Pair;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.exceptions.DifferentSizeException;
import me.tongfei.progressbar.ProgressBar;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * **/
public class CalculateMeasureImpl{

	//private Map<String, String> measures;
	private ArrayList<Query> relevanceFile;
	private Map<String, Session> logsFile;
	
	
	/**
	 * Constructor
	 * @param 
	 */
	public CalculateMeasureImpl(ArrayList<Query> relevanceFile, Map<String, Session> logsfile) {
		//this.measures = new LinkedHashMap<String,String>();
		this.relevanceFile = relevanceFile;
		this.logsFile = logsfile;
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
	 * Average Precision (AP) is the average precision at k values
	 * computed after each relevant document is retrieved for a given topic,
	 *  
	 * If the query doesn't have at least one relevant document it returns 0.
	 * 
	 * @input queryRel
	 * @input queryOutputPIR
	 * @input interpolation if it is true calculates the Interpolation Average Precision on 11 level (0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100).
	 * @return AP
	 * @Complexity O(n^2)
	 * **/
	public static double ap(Query queryRel, Query queryOutputPIR, boolean interpolation) {
		int nRelevantDoc = (((QueryRelFile) queryRel).getNRelevantDoc() == 0) ? -1 : ((QueryRelFile) queryRel).getNRelevantDoc();
		double aveP = 0.0;
		if(nRelevantDoc != -1) {
			Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
			DocumentRelFile docRel;
			DocumentOutputPIR docOut;
			ArrayList<Pair<Integer, Double>> listPair = new ArrayList<Pair<Integer, Double>>();
			while (itDocOutputPIR.hasNext()) {
				Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
				docOut = (DocumentOutputPIR)pairDocOUT.getValue();
				docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
				if(docRel != null & docRel.getIsRelevance()) {
					listPair.add(new Pair<Integer, Double>(docOut.getRank(), calculatePKRK(queryRel, queryOutputPIR , docOut.getRank(), false)));
				}
			}
			
			if(interpolation) {
				Collections.sort(listPair);
				for (int i = 0; i < 11; i++) {
					aveP += interpolation(i, listPair);
				}
				
			}else {
				for (Pair<Integer, Double> p : listPair) {
					aveP += (Double)p.getValue();
				}
				
			}
			aveP = (interpolation) ? aveP/11 : aveP/nRelevantDoc;
		}else {
			return 0; // return 0 if there isn't a relevant document
			
		}
		
		return aveP;
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
	 *@input realData
	 *@input predictionData
	 *@input p
	 *@return the NDCG for the given data
	 */
	public static double IDCG(Query queryRel, Query queryOutputPIR, int p) {
		ArrayList<Integer> idcg = new ArrayList<Integer>();
		int value_relevance = 0;
		Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
		DocumentRelFile docRel;
		DocumentOutputPIR docOut;
		while (itDocOutputPIR.hasNext()) {
			Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
			docOut = (DocumentOutputPIR)pairDocOUT.getValue();
			docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
			value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
			idcg.add(value_relevance);	
		}
		
		Collections.sort(idcg, new Comparator<Integer>() {
		    public int compare(Integer o1, Integer o2) {
		        return o2.compareTo(o1);
		    }
		});
		
		double idcgValue = idcg.get(0);
		int rank = 2;
		for (int i = 1; i < p; i++){	
			idcgValue += idcg.get(i) / (log(rank + 1, 2));
			rank ++;	
		}
		
		return idcgValue;
	}
	
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
		return DCG(queryRel, queryOutputPIR, p) / IDCG( queryRel, queryOutputPIR, p);
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
		double relDoc = 0; // number of relevant docs found
		double nRelevantDoc = ((QueryRelFile) queryRel).getNRelevantDoc(); // number of relevant docs in queryRel
		if(nRelevantDoc != -1) {
			Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
			DocumentRelFile docRel;
			DocumentOutputPIR docOut;
			while (itDocOutputPIR.hasNext()) {
				Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
				docOut = (DocumentOutputPIR)pairDocOUT.getValue();
				docRel = (DocumentRelFile)queryRel.findDoc(docOut.getId());
				if((docRel != null) && (docOut.getRank() <= k) && (docRel.getIsRelevance() == true)) {
					relDoc ++;
				}
			}
			
			double denominator = (recall == true) ? nRelevantDoc : k; 
			return (relDoc / denominator);
		}
		
		return 0;
	}	
	
	/** Session measures **/
	
	/**
	 * @param query
	 * @return
	 */
	public int setMaxK(ArrayList<Query> query) {
		String key = query.get(0).getUser() + query.get(0).getTopic();
		Session session = getSession(key);
		ArrayList<Log> logs = session.getnDocOpened();
		int k = 0;
		int rank = 0;
		
		for (Log l : logs) {
			rank = Integer.parseInt(l.getRank());
			if (rank > k){
				k = rank;
			}
		}
		
		return k;
	}
	
	/**
	 * @param query
	 * @return
	 */
	public int setAverageK(ArrayList<Query> query) {
		String key = query.get(0).getUser() + query.get(0).getTopic();
		Session session = getSession(key);
		ArrayList<Log> logs = session.getnDocOpened();
		int k = 0;
		int rank = 0;
		for (Log l : logs) {
			rank = Integer.parseInt(l.getRank());
			k += rank;
			
		}
		
		return k/logs.size();
	}
	
	
	/**
	 * sDCG(q) = (1 + log_bq q)-1 * DCG
	 * where bq ∈ R is the logarithm base for the query discount; 1 < bq < 1000
	 * q is the position of the query.
	 * @param queryRel
	 * @param queryOutputPIR
	 * @param logbase
	 * @param ideal
	 * @return
	 */
	public static double sDCG(ArrayList<Query> queryRel, ArrayList<Query> queryOutputPIR, int k,  int logbase, boolean ideal) {
		double sDCG = 0.0;
		if (queryRel.size() != queryOutputPIR.size()) {
			throw new DifferentSizeException();
		}
		
		for (int i = 0; i < queryRel.size(); i++) {
			sDCG += ideal ? Math.pow(1 + log(i + 1, logbase), -1) * DCG(queryRel.get(i), queryOutputPIR.get(i), k) 
						  :	Math.pow(1 + log(i + 1, logbase), -1) * IDCG(queryRel.get(i), queryOutputPIR.get(i), k);
		}
		
		return sDCG;
	}

	
	/**
	 * Normalized session discounted cumulative gain
	 * @param queryRel
	 * @param queryOutputPIR
	 * @param logbase
	 * @param ideal
	 * @return
	 */
	public static double NSDCG(ArrayList<Query> queryRel, ArrayList<Query> queryOutputPIR, int k, int logbase, boolean ideal) {
		return sDCG(queryRel, queryOutputPIR, k, logbase, false) / sDCG(queryRel, queryOutputPIR, k, logbase, true);
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
	
	/**
	 * @param q
	 * @return
	 */
	public QueryRelFile createMeasure(QueryRelFile q) {
		int k = q.getNRelevantDoc();
		for (; k != 0; k --) {
			q.addMeasure(new Measure("Precision@"+k));
			q.addMeasure(new Measure("Recall@"+k));
		}
		
		q.addMeasure(new Measure("Precision"));
		q.addMeasure(new Measure("Recall"));
		q.addMeasure(new Measure("fMeasure0.5"));
		q.addMeasure(new Measure("NDCG@10"));
		
		return q;
	}
	
	public void calculateMeasures(ArrayList<PIR> pirs) {
		
		ProgressBar pb = new ProgressBar("Measures Calculation ", 100).start(); // progressbar
		pb.maxHint(pirs.size()* pirs.get(0).getQueries().size()); // progressbar
		Query queryPIR;
		QueryRelFile queryRel;
		/*Measures variables*/
		Double qPrecisionK = 0.0;
		Double qRecallK = 0.0;
		Double qNDCG = 0.0;
		Double precision = 0.0;
		Double recall = 0.0;
		Double fMeasure = 0.0;
		int k = 0;
		int nQuery = getRelevanceFile().size();
		for (int i = 0; i< nQuery; i ++) {
			queryRel =(QueryRelFile) getRelevanceFile().get(i);
			queryRel = createMeasure(queryRel);
			for(PIR pir : pirs) {
				pb.step();
				queryPIR = pir.getQuery(i);
				k =  queryRel.getNRelevantDoc();
				for (; k != 0; k --) {
					qPrecisionK = calculatePKRK(queryRel, queryPIR, k, false);
					qRecallK = calculatePKRK(queryRel, queryPIR, k, true);
					
					queryRel.searchMeasure("Precision@"+k).addPIR(pir.getName(), qPrecisionK);
					queryRel.searchMeasure("Recall@"+k).addPIR(pir.getName(), qRecallK);
					
				}
				
				qNDCG = calculateNDCG(queryRel, queryPIR, 10);
				precision = precision(queryRel, queryPIR);
				recall = recall(queryRel, queryPIR);
				fMeasure = fMeasure(precision, recall, 0.5);
					
				queryRel.searchMeasure("NDCG@10").addPIR(pir.getName(), qNDCG);
				queryRel.searchMeasure("Precision").addPIR(pir.getName(), precision);
				queryRel.searchMeasure("Recall").addPIR(pir.getName(), recall);
				queryRel.searchMeasure("fMeasure0.5").addPIR(pir.getName(), fMeasure);
				
			}
			pb.stepTo(pirs.size()* pirs.get(0).getQueries().size());// progressbar
			pb.stop();//progressbar
		}	
	}
	
	/**
	 * @return the relevanceDoc
	 */
	public ArrayList<Query> getRelevanceFile() {
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


