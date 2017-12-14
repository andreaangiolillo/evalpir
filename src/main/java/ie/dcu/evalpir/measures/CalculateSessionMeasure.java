package ie.dcu.evalpir.measures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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

public class CalculateSessionMeasure {

	//private Map<String, String> measures;
		private Map<String, Query> relevanceFile; // key = queryID, value = Query
		private Map<String, Session> logsFile; //key =  USERID.toLowerCase() + "," + TOPICID.toLowerCase(), value = Session
		
		
		/**
		 * Constructor
		 * @param 
		 */
		public CalculateSessionMeasure(Map<String, Query> relevanceFile, Map<String, Session> logsfile) {
			this.relevanceFile = relevanceFile;
			this.logsFile = logsfile;
		}
		
		
		/** Session measures **/
		
		
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
				if(queryRel.get(i).getId().equalsIgnoreCase(queryOutputPIR.get(i).getId())) {
					sDCG += !ideal ? Math.pow(1 + CalculateMeasureImpl.log(i + 1, logbase), -1) * CalculateMeasureImpl.DCG(queryRel.get(i), queryOutputPIR.get(i), k) 
							  :	Math.pow(1 + CalculateMeasureImpl.log(i + 1, logbase), -1) * CalculateMeasureImpl.IDCG(queryRel.get(i), k);
				}else {
					throw new DifferentQueryException("Different queries: \nQueryRelID: " + queryRel.get(i).getId() + ", QueryOutputPIR: " + queryOutputPIR.get(i).getId());
				}
				
			}
			
			return sDCG;
		}

		
		/**
		 * Normalized session discounted cumulative gain
		 * @param queryRel
		 * @param queryOutputPIR
		 * @param logbase
		 * @return
		 */
		public static double NsDCG(Map<String, Query> queryRel, Map<String, Query> queryOutputPIR, int k, int logbase, Map<String, Session> sessions) {
			ArrayList<Query> queryRelSortedbyTime = sortByTimestamp(queryRel, sessions);
			ArrayList<Query> queryPIRSortedbyTime = sortByTimestamp(queryOutputPIR, sessions);
			return sDCG(queryRelSortedbyTime, queryPIRSortedbyTime, k, logbase, false) / sDCG(queryRelSortedbyTime, queryPIRSortedbyTime, k, logbase, true);
		}
		
		/**
		 * 
		 * @param queries
		 * @param sessions
		 * @return
		 */
		public static ArrayList<Query> sortByTimestamp(Map<String, Query> queries, Map<String, Session> sessions){
			Iterator<Entry<String, Query>> itQ = queries.entrySet().iterator();
			ArrayList<Log> session;
			ArrayList<Query> queriesSorted = new ArrayList<Query>();
			if(queries.size() != 1) {
				Query query =  itQ.next().getValue();
				//System.out.println(sessions.toString());
				if(sessions.get((query.getUser() + "," + query.getTopic()).toLowerCase()) == null) {
					throw new QueryNotInTheLogFileException("The queries in User:" + query.getUser() +" Topic: " + query.getTopic() + " are not in the logfile" );
				}
				
				session = sessions.get((query.getUser() + "," + query.getTopic()).toLowerCase()).getQuery();			
				for(Log log : session) {
					if(queries.containsKey(log.getQuery())){
						queriesSorted.add(queries.get(log.getQuery()));
					}else {
						throw new QueryNotInTheLogFileException("The querie: " + log.getQuery() + " is not in the logfile" );
					}	
				}
				
			}else {
				queriesSorted.add(itQ.next().getValue());
			}
			
			return queriesSorted;
			
		}
		
		/**
		 * 
		 * @param queryRel
		 * @param queryOutputPIR
		 * @return
		 */
		public static double calculateMAP(Map<String, Query> queryRel, Map<String, Query> queryOutputPIR) {
			double map = 0.0;
			int size = queryRel.size();
			if (size != queryOutputPIR.size()) {
				throw new DifferentSizeException();
			}
			
			Iterator <Entry<String, Query>> itRel = queryRel.entrySet().iterator();
			Query qRel;
			while(itRel.hasNext()) {
				qRel = itRel.next().getValue();
				if(!queryOutputPIR.containsKey(qRel.getId())) {
					throw new DifferentQueryException();
				}
				map += CalculateMeasureImpl.calculateAP(qRel, queryOutputPIR.get(qRel.getId()));
				
			}

			return map/size;		
		}
		
		/**
		 * 
		 * @param pirs
		 */
		public Map<String, Topic> calculateSessionMeasure(ArrayList<PIR> pirs) {
			Map<String, Topic> topicsRel = setTopic(getRelevanceFile());
			Map<String, Topic> topicsPir = null;
			Iterator<Entry<String, Topic>> itRel;
			Entry<String, Topic> topicRel;
			Session session;
			
			double nsDCGMax = 0.0;
			double nsDCGAverage = 0.0;
			double man = 0.0;
			double rPC = 0.0;
			double rRC = 0.0;
			for(PIR pir : pirs) {
				topicsPir = setTopic(pir.getQueries());
				itRel = topicsRel.entrySet().iterator();
				while(itRel.hasNext()) {
					topicRel = itRel.next();
					if(topicsPir.containsKey(topicRel.getKey())) {
						session = getLogsFile().get(topicRel.getKey());
						nsDCGAverage = NsDCG(topicRel.getValue().getQueries(), topicsPir.get(topicRel.getKey()).getQueries(), session.getAverageK(), 4, getLogsFile());
						nsDCGMax = NsDCG(topicRel.getValue().getQueries(), topicsPir.get(topicRel.getKey()).getQueries(), session.getMaxK(), 4, getLogsFile());
						man = calculateMAP(topicRel.getValue().getQueries(), topicsPir.get(topicRel.getKey()).getQueries());
						rPC = rPC(topicRel.getValue().getQueries(), topicsPir.get(topicRel.getKey()).getQueries(), session.getPath());
						rRC = rRC(topicRel.getValue().getQueries(), topicsPir.get(topicRel.getKey()).getQueries(), session.getPath());
						((Measure)topicRel.getValue().searchAddMeasure("nSDCG_AverageK@" + session.getAverageK(), false)).addPIR(pir.getName(), nsDCGAverage);
						((Measure)topicRel.getValue().searchAddMeasure("nSDCG_MaxK@" +  session.getMaxK(), false)).addPIR(pir.getName(), nsDCGMax);	
						((Measure)topicRel.getValue().searchAddMeasure("MeanAveragePrecision", false)).addPIR(pir.getName(), man);
						((Measure)topicRel.getValue().searchAddMeasure("Session_Precision", false)).addPIR(pir.getName(), rPC);
						((Measure)topicRel.getValue().searchAddMeasure("Session_Recall", false)).addPIR(pir.getName(), rRC);
						((MeasureCompound)topicRel.getValue().searchAddMeasure("Session_PrecisionRecallCurve", true)).addPIR(pir.getName(), precisionRecallCurve(topicRel.getValue().getQueries(), topicsPir.get(topicRel.getKey()).getQueries(), session, session.getPath()));
					}
					
				}
				//System.out.println("NAME PIR: " + pir.getName());
				
			}
			
			return topicsRel;
		}
		
		 /**
		  * 
		  * @param queries
		  * @return
		  */
		public static Map<String, Topic> setTopic(final Map<String,Query> queries){
			Iterator<Entry<String, Query>> itQueries = queries.entrySet().iterator();
			Query query;
			String key ="";
			Map<String, Topic> topicUser = new HashMap<String, Topic>();
			while(itQueries.hasNext()) {
				query = itQueries.next().getValue();	
				key = query.getUser().toLowerCase() +  "," +  query.getTopic().toLowerCase() ;
				if(!topicUser.containsKey(key)) {
					Map<String, Query> quertTopic = new HashMap<String, Query>();
					quertTopic.put(query.getId(), query);
					Topic topic = new Topic(query.getUser(), query.getTopic(), quertTopic);
					topicUser.put(key, topic);
				}else {
					topicUser.get(key).addQuery(query);
				}	
			}
			
			return topicUser;
		}


		public Map<String, Query> getRelevanceFile() {
			return relevanceFile;
		}


		public Map<String, Session> getLogsFile() {
			return logsFile;
		}

		/**
		 * @param measures must be normalized
		 * @return
		 */
		public static double[] extendSingleQueryMeasure(Double[] measure) {
			double[] session = new double[measure.length];
			for (int i = 0; i < measure.length; i++) {
				session[i] += (i == 0) ? measure[i] * Math.exp(-(1 + measure[i]))
									: session[i - 1] + measure[i] * Math.exp(-1 + (measure[i] - measure[i - 1]));
			}
			
			return session;
		}
		
		/**
		 * This method computes the rR as defined in http://ir.cis.udel.edu/~carteret/papers/sigir11b.pdf
		 * It return the number of relevant document in each ranked lists of the session, 
		 * where the stop point for each ranked list is in path
		 * 
		 * 
		 * @assumption at least the one document, the first one, is views in each ranked lists
		 * @param queriesRel
		 * @param logsfile
		 */
		public static int rR(Map<String, Query> queriesRel, Map<String, Query> queriesPIR, Map<String, Integer> path){
			int nRelevantDocFinded = 0;
			Iterator<Entry<String, Query>> itQuery = queriesPIR.entrySet().iterator();
			Query queryPIR = null;
			int rankDoc = 0;
			while(itQuery.hasNext()) {
				queryPIR = itQuery.next().getValue();
				rankDoc = (path.get(queryPIR.getId().toLowerCase()) == null) ? 1 : path.get(queryPIR.getId().toLowerCase()); 
				if(queriesRel.get(queryPIR.getId().toLowerCase()) != null) {
					nRelevantDocFinded += relevanceCount(queriesRel.get(queryPIR.getId().toLowerCase()), queryPIR, rankDoc);
				}else {
					throw new DifferentQueryException();
				}	
			}
			
			return nRelevantDocFinded; 
		}
		
		/**
		 * It computes the number of relevant document in the ranked list in input
		 * @param queryRel
		 * @param queryPIR
		 * @param k
		 * @see http://ir.cis.udel.edu/~carteret/papers/sigir11b.pdf (rR@j,k)
		 * @return
		 */
		public static int relevanceCount(Query queryRel, Query queryPIR, int k) {
			Iterator<Entry<String, Document>> itDocPIR = queryPIR.getDocs().entrySet().iterator();
			Map<String, Document> relevanceDocs = queryRel.getDocs();
			DocumentRelFile docRel = null;
			DocumentOutputPIR docPIR = null;
			int nRelevantDocFinded = 0;
			while(itDocPIR.hasNext()) {
				docPIR = (DocumentOutputPIR)itDocPIR.next().getValue();
				if(docPIR.getRank() <= k) {
					docRel = (DocumentRelFile)relevanceDocs.get(docPIR.getId().toLowerCase());
					if((docRel != null) && docRel.getIsRelevance()) {
						nRelevantDocFinded += 1;
					}
				}
			}
			return nRelevantDocFinded;
		}
		
		/**
		 * number of relevant documents views in the ranked lists / total number of document viewed in the ranked lists
		 * @param queriesRel
		 * @param queriesPIR
		 * @param path
		 * @see http://ir.cis.udel.edu/~carteret/papers/sigir11b.pdf (rPC)
		 * @return
		 */
		public static double rPC(Map<String, Query> queriesRel, Map<String, Query> queriesPIR, Map<String, Integer> path){
			return (double)rR(queriesRel, queriesPIR, path)/(double)totDocumentViewSession(path);
		}
		
		/**
		 * It computes the number of documents viewed in the session
		 * @param path
		 * @return
		 */
		public static int totDocumentViewSession(Map<String, Integer> path) {
			int k = 0;
			Iterator<Entry<String,Integer>> itPath = path.entrySet().iterator();
			while(itPath.hasNext()) {
				k += itPath.next().getValue(); 
			}
			
			return k;
		}
		
		/**
		 * number of relevant documents views in the ranked lists / total number of relevant documents in the ranked list
		 * @param queryRel
		 * @param queryOutputPIR
		 * @param path
		 * @see http://ir.cis.udel.edu/~carteret/papers/sigir11b.pdf (rRC)
		 * @return
		 */
		public static double rRC(Map<String, Query> queryRel, Map<String, Query> queryOutputPIR, Map<String, Integer> path){
			return (double)rR(queryRel, queryOutputPIR, path)/(double)totRelevantDocSession(queryRel);	
		}
		
		/**
		 * It computes the number of relevant documents in the session
		 * @param queryRel
		 * @return
		 */
		public static int totRelevantDocSession(Map<String, Query> queryRel) {
			int relevantDoc = 0;
			Iterator<Entry<String,Query>> itRelDocs = queryRel.entrySet().iterator();
			QueryRelFile query = null;
			while(itRelDocs.hasNext()) {
				query = (QueryRelFile)itRelDocs.next().getValue();
				relevantDoc += query.getNRelevantDoc();
			}
			
			return relevantDoc;
		}
		
		/**
		 * 
		 * @param docsRel
		 * @param docsPIR
		 * @param k
		 * @return
		 */
		public static double precisionK(Map<String,Document> docsRel, Map<String,Document> docsPIR, int k){
			double relDocFounded = 0;
			Iterator<Entry<String, Document>> itDocsPIR = docsPIR.entrySet().iterator();
			DocumentRelFile docRel;
			DocumentOutputPIR docPIR;
			Entry<String, Document> entryPIR;
			while (itDocsPIR.hasNext()) {
				entryPIR = itDocsPIR.next();
				docPIR = (DocumentOutputPIR)entryPIR.getValue();
				docRel = (DocumentRelFile)docsRel.get(entryPIR.getKey());			
				if((docRel != null) && (docPIR.getRank() <= k) && (docRel.getIsRelevance() == true)) {
					relDocFounded ++;
				}
			}
			
			return (relDocFounded / k);
		}
		
		
		/**
		 * 
		 * @return
		 */
		public static ArrayList<Pair<Integer, Double>> precisionRecallCurve(Map<String, Query> queriesRel, Map<String, Query> queriesOutputPIR,  Session session, Map<String, Integer> path) {
			ArrayList<Pair<Integer, Double>> listPair = new ArrayList<Pair<Integer, Double>>();
			Map<String,Document> docsRel = mergeRelevanceDocs(queriesRel);
			Map<String,Document> docsPIR = mergeRankedList(queriesOutputPIR, session, path);
			Iterator<Entry<String, Document>>itPIR = docsPIR.entrySet().iterator();
			DocumentOutputPIR docPIR;
			DocumentRelFile docRel;
			String keyDocPIR = "";
			Entry<String, Document> entryPIR;
			int nRelDocFind = 0;
			while(itPIR.hasNext()) {
				entryPIR = itPIR.next();
				docPIR = (DocumentOutputPIR)entryPIR.getValue();
				keyDocPIR = entryPIR.getKey();
				docRel = (DocumentRelFile)docsRel.get(keyDocPIR);
				if(docRel != null && docRel.getIsRelevance()) {
					listPair.add(new Pair<Integer, Double>(docPIR.getRank(), precisionK(docsRel, docsPIR, docPIR.getRank())));
					nRelDocFind++;
				}	
				
			}
			
			int nRelevantDoc = totRelevantDocSession(queriesRel);
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
		
		
		
		/***
		 * 
		 * @param queryRel
		 * @param queryPIR
		 * @param k
		 * @return
		 */
		public static int nRelevantDocumentFound(QueryRelFile queryRel, Query queryPIR, int k) {
			int nRelevantDocumentFounded = 0;
			DocumentOutputPIR doc;
			DocumentRelFile docRel;
			Iterator<Entry<String, Document>> itDocPIR = queryPIR.getDocs().entrySet().iterator();
			while(itDocPIR.hasNext()) {
				doc = (DocumentOutputPIR)itDocPIR.next().getValue();
				docRel = (DocumentRelFile)queryRel.findDoc(doc.getId().toLowerCase());
				if(docRel != null && docRel.getIsRelevance() && doc.getRank() <=  k) {
					nRelevantDocumentFounded ++;
				}
			}
			
			return nRelevantDocumentFounded;
		}
		
		
		/**
		 * 
		 * @param queries
		 * @return
		 */
		public static Map<String, Document> mergeRankedList(final Map<String, Query> queries, final Session session, final Map<String, Integer> path){
			ArrayList<Log> queryLog = session.getQuery();
			Query query = null;
			Map<String, Document> documentToMerge = new HashMap<String, Document>();
			DocumentOutputPIR doc;
			int rank = 0;
			int k = 0;
			Iterator<Entry<String, Document>> itDocs;
			for(int i = 0 ; i < queryLog.size(); i++) {
				query = queries.get(queryLog.get(i).getQuery().toLowerCase());
				if(query == null) {
					throw new DifferentQueryException("QueryID: " + queryLog.get(i).getQuery());
				}
				
				k = path.get(query.getId().toLowerCase()); // to find the query's stopping point
				itDocs = query.getDocs().entrySet().iterator();
				while(itDocs.hasNext()) {
					doc = ((DocumentOutputPIR)itDocs.next().getValue());
					if((doc.getRank() <= k)) {
						documentToMerge.put(query.getId() + "," + doc.getId() , new DocumentOutputPIR(doc.getId(), doc.getRank() + rank, doc.getSimilarity()));
					}		
				}
			
				rank = documentToMerge.size();	
			}
			
			return documentToMerge;
		}
		
		/**
		 * 
		 * @param queries
		 * @return
		 */
		public static Map<String, Document> mergeRelevanceDocs(final Map<String, Query> queries){
			Map<String, Document> documentMerged = new HashMap<String, Document>();
			Iterator<Entry<String, Query>> itQuery = queries.entrySet().iterator();
			Iterator<Entry<String, Document>> itDocs;
			Query query;
			Document doc;
			while(itQuery.hasNext()) {
				query = itQuery.next().getValue();
				itDocs = query.getDocs().entrySet().iterator();
				while(itDocs.hasNext()) {
					doc = itDocs.next().getValue();
					documentMerged.put(query.getId() + "," + doc.getId(), doc);
				}
			}
			
			return documentMerged;
		}
		
		
		
//		public void modelFreeSession(ArrayList<Query> queryRel, Session s) {
//			
//			int j = s.getQuery().size();
//			int k = 10;
//			ArrayList<Integer> path;
//			ArrayList<Path> paths = new ArrayList<Path>();
//			Map <String, ArrayList<Path>> pathsession = new HashMap<String, ArrayList<Path>>();
//			for(int i = 0; i < j; i++) {
//				for(int z = 0; z < k; z++){
//					if(i == 0) {
//						path = new ArrayList<Integer>();
//						path.add(z + 1);
//						paths = new ArrayList<Path>();
//						paths.add(new Path(path));
//						pathsession.put(k + "," + i, paths);
//					}else {
//						paths = pathsession.get(k + "," + (i - 1));
//						for(Path p : paths) {
//							p.addElement(z + 1);
//						}
//						
//						pathsession.put(k + "," + i, paths);
//					}
//					
//					
//					
//					
//				}
//			}
//		}
		
		
		
		
		
		
}
