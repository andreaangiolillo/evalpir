package ie.dcu.evalpir.measures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelevanceFile;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.elements.User;

public class MeasureImpl{

	private Map<String, String> measures;
	
	
	
	/**
	 * Constructor
	 * @param measures
	 */
	public MeasureImpl() {
		this.measures = new LinkedHashMap<String,String>();
	}
	
	
	
	/**
	 * Precision (P) is defined as the proportion of retrieved documents that
	 * are relevant; it measures the ability of the system to retrieve only the
	 * relevant documents.
	 * 
	 * @input queryRel
	 * @input queryOutputPIR
	 * @return 
	 * **/
	public double precision(Query queryRel, Query queryOutputPIR){
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
	 * **/
	public double recall(Query queryRel, Query queryOutputPIR){
		return calculatePKRK(queryRel, queryOutputPIR, queryOutputPIR.getDocs().size(), true);
	}
	
	/**
	 * F-measure (F) is computed as the weighted harmonic 
	 * mean of precision and recall:
	 * 
	 * @input precision
	 * @input recall
	 * @return f_meausure 
	 * **/
	public double fMeasure(double precision, double recall, double alpha) {
		return (alpha > 0.0 && alpha <= 1.0 ) ? (1/((alpha * 1/precision) + (1 - alpha) * 1/recall)) : 0;
	}
	
	
	/**
	 * Mean Average Precision (MAP) is the average precision at k values
	 * computed after each relevant document is retrieved for a given topic,
	 * where the mean of all these averages is calculated across all the test
	 * topics.
	 * 
	 * **/
	public double map(Query queryRel, Query queryOutputPIR) {
		int nRelevantDoc = queryRel.nRelevanteDoc();
		if(nRelevantDoc != 0) {
			
		}
		return 0.0;
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
	 */
	public double calculateNDCG(Query queryRel, Query queryOutputPIR, int p) {
		ArrayList<Integer> idcg = new ArrayList<Integer>();
		double dcg = 0.0;
		int value_relevance = 0;
		Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
		DocumentRelevanceFile docRel;
		DocumentOutputPIR docOut;
		while (itDocOutputPIR.hasNext()) {
			Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
			docOut = (DocumentOutputPIR)pairDocOUT.getValue();
			docRel = (DocumentRelevanceFile)queryRel.findDoc(docOut.getId());
			value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
			idcg.add(value_relevance);
			if(docOut.getRank() <= p) {	
				docRel = (DocumentRelevanceFile)queryRel.findDoc(docOut.getId());
				value_relevance = (docRel == null) ? 0 : docRel.getRelevance();
				dcg += (docOut.getRank() == 1) ? value_relevance : value_relevance / (log(docOut.getRank() + 1, 2));	
			}
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
		
//		System.out.println("IDCG: " + idcgValue);
//		System.out.println("DCG: " + dcg);
		return dcg / idcgValue;
	}
	
	/**Changing of base
	 * 
	 * @input value
	 * @input base
	 * */
	public double log(int value, int base) {
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
	 * @param queryRel
	 * @param queryResult
	 * @param k
	 * @param recall : if it is true computes the recall@k otherwise precision@k
	 * @return (relDoc / denominator)
	 * @Complexity Average Complexity: O(numberDocs)
	 * @Complexity Worst Complexity: O(numberDocs^2), It depends on HashTable
	 */
	public double calculatePKRK(Query queryRel, Query queryOutputPIR , int k, boolean recall) {
		double relDoc = 0; // number of relevant docs
		Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
		DocumentRelevanceFile docRel;
		DocumentOutputPIR docOut;
		while (itDocOutputPIR.hasNext()) {
			Map.Entry<?,?> pairDocOUT = (Map.Entry<?,?>)itDocOutputPIR.next();
			docOut = (DocumentOutputPIR)pairDocOUT.getValue();
			docRel = (DocumentRelevanceFile)queryRel.findDoc(docOut.getId());
			if((docRel != null) && (docOut.getRank() <= k) && (docRel.getIsRelevance() == true)) {
				relDoc ++;
			}
		}
		
		double denominator = (recall == true) ? queryRel.nRelevanteDoc() : k; 
		return (relDoc / denominator);
	}

	/**
	 * @param relevanceDoc
	 * @param outputPIR
	 * @param k
	 * @Complexity !CRITICAL! O(nUser * nTopic * nQuery * measures) 
	 */
	public void evaluationProcess(ArrayList<User> relevanceDoc, ArrayList<User> outputPIR, int k){
		
		Iterator<?> itUserRel, itUserPIR, itTopicRel, itTopicPIR, itQueryRel, itQueryPIR;
		User userRel, userPIR;
		Topic topicRel, topicPIR;
		Query queryRel, queryPIR;
		/*Precision@K variables*/
		Double uPrecisionKMean, uPrecisionK ,tPrecisionKMean, tPrecisionK, qPrecisionKMean, qPrecisionK;
		uPrecisionKMean = uPrecisionK = tPrecisionKMean = tPrecisionK = qPrecisionKMean = qPrecisionK = 0.0;
		String qPK, tPK, uPK;
		qPK = tPK = uPK = "";
		itUserRel = relevanceDoc.iterator();
		itUserPIR = outputPIR.iterator();
		while(itUserRel.hasNext() && itUserPIR.hasNext()) { // per-user
			userRel = (User) itUserRel.next();
			userPIR = (User) itUserPIR.next();
			itTopicRel = userRel.getTopics().iterator();
			itTopicPIR = userPIR.getTopics().iterator();
			while(itTopicRel.hasNext() && itTopicPIR.hasNext()) { // per-topic
				topicRel = 	(Topic) itTopicRel.next();
				topicPIR = 	(Topic) itTopicPIR.next();
				itQueryRel = topicRel.getQueries().iterator();
				itQueryPIR = topicPIR.getQueries().iterator();
				while(itQueryRel.hasNext() && itQueryPIR.hasNext()) { // per-query
					queryRel = (Query)itQueryRel.next();
					queryPIR = (Query)itQueryPIR.next();
					qPrecisionK = calculatePKRK(queryRel, queryPIR, k, false);
					qPK += printMeasure(userRel.getId(), topicRel.getId(), queryRel.getId(), k, qPrecisionK);
					qPrecisionKMean += qPrecisionK;
				}
				
				measures.put("p"+k+"query", qPK);
				tPrecisionK = qPrecisionKMean / topicRel.getQueries().size();				
				tPK += printMeasure(userRel.getId(), topicRel.getId(), "", k, tPrecisionK);
				tPrecisionKMean += tPrecisionK;		
				qPrecisionKMean = 0.0;
			}
			
			measures.put("p"+k+"topic", tPK);
			uPrecisionK = tPrecisionKMean / userRel.getTopics().size();
			uPK += printMeasure(userRel.getId(), "", "", k, uPrecisionK);
			uPrecisionKMean += uPrecisionK;
			tPrecisionKMean = 0.0;
		}
		
		measures.put("p"+k+"user", uPK);
		measures.put("TOT - Precision@" + k , "TOT - Precision@" + k + ": " + String.valueOf(uPrecisionKMean / relevanceDoc.size()));
	}
	
	
	/**
	 * @param userID
	 * @param topicId
	 * @param queryId
	 * @param k
	 * @param measure
	 * @return the text with the measure's info
	 */
	public String printMeasure(String userID, String topicId, String queryId, int k, Double measure) {
		return "UserID: " + userID + (topicId.equalsIgnoreCase("") ? "" : " TopicID: " + topicId)
				+ (queryId.equalsIgnoreCase("") ? "" : " QueryID: " + queryId) + " Precision@" + k + ": " + String.valueOf(measure) + "\n";  
		
	}
	
	
	
	/**
	 * @return the measures
	 */
	public Map<String, String> getMeasures() {
		return measures;
	}


	/**
	 * @param measures the measures to set
	 */
	public void setMeasures(Map<String, String> measures) {
		this.measures = measures;
	}



	@Override
	public String toString() {
		String stringMeasures = "";
		Iterator<?> it = measures.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			stringMeasures += (String)pair.getValue() + "\n";
		}
		return "Measures: \n" + stringMeasures;
	}
	
}


