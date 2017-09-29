package ie.dcu.evalpir.measures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelevanceFile;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.elements.User;

public class MeasureImpl implements Measure{

	private Map<String, String> measures;
	
	
	
	/**
	 * @param measures
	 */
	public MeasureImpl() {
		this.measures = new HashMap<String,String>();
	}
	



	/**
	 * Compute the normalized discounted cumulative gain (NDCG) of a list of ranked items.
	 *@input realData
	 *@input predictionData
	 *@return the NDCG for the given data
	 */
	public Double calculateNDCG(List<String> realData, List<String> predictionData) {
		return null;
	}



	/**
	 * @param queryRel
	 * @param queryResult
	 * @param k
	 * @return (relDoc / k)
	 * @Complexity Average Complexity: O(nDoc)
	 * @Complexity Worst Complexity: O(nDoc^2), It depends on HashTable
	 */
	public Double calculatePK(Query queryRel, Query queryOutputPIR , int k) {
		double relDoc = 0; // number of relevant docs
		Iterator<Entry<String, Document>> itDocOutputPIR = queryOutputPIR.getDocs().entrySet().iterator();
		DocumentRelevanceFile docRel;
		DocumentOutputPIR docOut;
		
		while (itDocOutputPIR.hasNext()) {
			Map.Entry pairDocOUT = (Map.Entry)itDocOutputPIR.next();
			docOut = (DocumentOutputPIR)pairDocOUT.getValue();
			docRel = (DocumentRelevanceFile)queryRel.findDoc(docOut.getId());
			
			if((docRel != null) && (docOut.getRank() <= k) && (docRel.getIsRelevance() == true)) {
				relDoc ++;
			}
		}
		//System.out.println("relDoc: " + relDoc);
		return (relDoc / k);
	}

	/**
	 * @param relevanceDoc
	 * @param outputPIR
	 * @param k
	 * @Complexity !CRITICAL! O(nUser * nTopic * nQuery * measures) 
	 */
	public void meanAverageMeasure(ArrayList<User> relevanceDoc, ArrayList<User> outputPIR, int k){
		
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
		
		
		while(itUserRel.hasNext() && itUserPIR.hasNext()) {
			userRel = (User) itUserRel.next();
			userPIR = (User) itUserPIR.next();
			itTopicRel = userRel.getTopics().iterator();
			itTopicPIR = userPIR.getTopics().iterator();
		
			
			while(itTopicRel.hasNext() && itTopicPIR.hasNext()) {
				topicRel = 	(Topic) itTopicRel.next();
				topicPIR = 	(Topic) itTopicPIR.next();
				itQueryRel = topicRel.getQueries().iterator();
				itQueryPIR = topicPIR.getQueries().iterator();
				
				while(itQueryRel.hasNext() && itQueryPIR.hasNext()) {
					queryRel = (Query)itQueryRel.next();
					queryPIR = (Query)itQueryPIR.next();
					qPrecisionK = calculatePK(queryRel, queryPIR, k);
					qPK = "UserID" + userRel.getId() + " TopicID: "+ topicRel.getId() + " QueryID: " + queryRel.getId() + 
							" Query-Precision@" + k + ": " + String.valueOf(qPrecisionK) + "\n";
					
					measures.put(userRel.getId() + topicRel.getId() + queryRel.getId(), qPK);
					//System.out.println(qPK);
					qPrecisionKMean += qPrecisionK;
				}
				
				tPrecisionK = qPrecisionKMean / topicRel.getQueries().size();
				tPK = "UserID" + userRel.getId() + " TopicID: "+ topicRel.getId() + 
						" Topic-Precision@" + k + ": " + String.valueOf(tPrecisionK) + "\n";
				
				
				measures.put(userRel.getId() + topicRel.getId(), tPK);
				tPrecisionKMean += tPrecisionK;		
				
				qPrecisionKMean = 0.0;
			}
			
			uPrecisionK = tPrecisionKMean / userRel.getTopics().size();
			uPK = "UserID" + userRel.getId() + 
					" User-Precision@" + k + ": " + String.valueOf(uPrecisionK) + "\n";
			measures.put(userRel.getId(), uPK);
			uPrecisionKMean += uPrecisionK;
			
			tPrecisionKMean = 0.0;
		}
		
		measures.put("TOT - Precision@" + k , String.valueOf(uPrecisionKMean / relevanceDoc.size()));
		
		
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
		Iterator it = measures.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			stringMeasures += (String)pair.getValue() + "\n";
		}
		System.out.println(measures.size());
		return "Measures: \n" + stringMeasures;
	}
	

	
	
	
	
	
	
	
	
	
	
	
}


