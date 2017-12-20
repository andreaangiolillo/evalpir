package ie.dcu.evalpir.output.chart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import ie.dcu.evalpir.EvalEpir;
import ie.dcu.evalpir.elements.AbstractMeasure;
import ie.dcu.evalpir.elements.Log;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.MeasureCompound;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.exceptions.QueryNotInTheLogFileException;
import ie.dcu.evalpir.output.table.ConsolePrinter;



public class CreatorChart {

	final static String[] MEASURES_LINECHART = {"Recall", "Precision", "AveragePrecision", "NDCG@05", "NDCG@10", 
										"NDCG@15", "NDCG@20", "Precision@", "Recall@", "fMeasure0.5", "PrecisionRecallCurve" , "Session_PrecisionRecallCurve"};
	
	
	final static String[] MEASURES_BARCHART = { "Recall", "Precision", "AveragePrecision", "NDCG@05", "NDCG@10", 
			"NDCG@15", "NDCG@20", "Precision@", "Recall@", "fMeasure0.5"};
	
	final static String[] MEASURES_STACKEDBAR = {"Precision", "AveragePrecision"};

	/**
	 * It Creates the folder for the diagrams
	 * @param nameFolder
	 * @return
	 */
	 public static String createFolder(String folderName){
	    	String path;
			try {
				path = new File(".").getCanonicalPath() + "/Charts/" + folderName + "/";
				File dir = new File(path);
		        dir.mkdirs();
		     
				File subdir;
				String[] measures = null;
				//String[] measures = (folderName.equalsIgnoreCase("LineChart")) ? MEASURES_LINECHART : MEASURES_BARCHART;
				switch(folderName) {
					case "LineChart":
						measures = MEASURES_LINECHART;
						break;
					case "BarChart":
						measures = MEASURES_BARCHART;
						break;
					case "StackedBar":
						measures = MEASURES_STACKEDBAR;
						break;
					default:
						throw new IllegalArgumentException("Invalid name of the folder: " + folderName);
				}
				
		        for(int i = 0; i < measures.length; i++) {
		        	subdir = new File(path + measures[i]); 
		        	subdir.mkdir();
		        }
		        
				return (path);	
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			return "";
	 }
	
	 /**
	  * 
	  * @param queries
	  */
	 public static void createChart(final Map<String,Query> queries) {
		ConsolePrinter.startTask("Creating Charts");
		String pathL = createFolder("LineChart");
		String pathB = createFolder("BarChart");
		String pathS = createFolder("StackedBar");

		Map<String, ArrayList<Query>> topics = setTopic(queries);
		Iterator<Entry<String, ArrayList<Query>>> it = topics.entrySet().iterator();
		AbstractMeasure[] measures;
		ArrayList<Query> topic;
		while(it.hasNext()) {
			topic = it.next().getValue();
			measures = getNameMeasures(topic);
			for (AbstractMeasure measure : measures) {
				if(measure.mustBeDrawn()) {	
					if(measure instanceof Measure) {
//						LineChart.CreateLineChartPerTopic(pathL, topic, (Measure)measure);
//						BarChart.CreateBarChartPerTopic(pathB, topic, (Measure)measure);
						if(measure.isStackedBar()) {
							System.out.println("Stack da creare");
							StackedBar.CreateStackedBar(pathS, topic, (Measure)measure);
						}
					}else if(measure instanceof MeasureCompound) {
//						LineChart.CreateLineChartPerQuery(pathL, topic, measure.getName());
					}
				}
			}
		}
		ConsolePrinter.endTask("Creating Charts");
		
	 }
	 
	 public static void createChartSession(final Map<String, Topic> topics) {
		 String path = createFolder("LineChart");
		 Iterator<Entry<String, Topic>> itTopic = topics.entrySet().iterator();
		 Topic topic;
		 MeasureCompound measure;
		 while(itTopic.hasNext()) {
			 topic = itTopic.next().getValue();
			 measure = (MeasureCompound)topic.searchMeasure("Session_PrecisionRecallCurve");
			 if(measure != null) {
				 LineChart.createLineChartForSessionMeasure(path, topic.getUserId(), topic.getTopicId(), measure);
			 }
			 
		 }
		
	 }
	 
	 /**
	  * 
	  * @param queries
	  * @return
	  */
	public static Map<String, ArrayList<Query>> setTopic(final Map<String,Query> queries){
		Map<String, ArrayList<Query>> topicUser = new HashMap<String, ArrayList<Query>>(); 
		Map<String, Session> logs = EvalEpir.getLOGS();
		Iterator<Entry<String, Session>> itLogs = logs.entrySet().iterator();
		Entry<String, Session> entryLog;
		ArrayList<Log> querySorted ;
		Query query;
		while(itLogs.hasNext()) {
			entryLog = itLogs.next();
			querySorted = entryLog.getValue().getQuery();
			ArrayList<Query> topic = new ArrayList<Query>();
			for (int i = 0; i < querySorted.size(); i++) {
				query = queries.get(querySorted.get(i).getQuery().toLowerCase().trim());
				if(query == null) {
					throw new QueryNotInTheLogFileException("QueryID: " + querySorted.get(i).getQuery().toLowerCase().trim());
				}
				
				topic.add(query);
			}
			
			topicUser.put(entryLog.getKey(), topic);
		}
		
		return topicUser;
		
//		Iterator<Entry<String, Query>> it = queries.entrySet().iterator();
//		Query q;
//		String key ="";
//		Map<String, ArrayList<Query>> topicUser = new HashMap<String, ArrayList<Query>>();
//		ArrayList<Log> queryOrder;
//		while(it.hasNext()) {
//			q = it.next().getValue();	
//			if(((QueryRelFile)q).isToConsiderForChart()) {
//				key = q.getUser().toLowerCase() +  "," + q.getTopic().toLowerCase();
//				queryOrder = EvalEpir.LOGS.get(key).getQuery();
//				if(!topicUser.containsKey(key)) {
//					ArrayList<Query> topic = new ArrayList<Query>();
//					topic.add(q);
//					topicUser.put(key, topic);
//				}else {
//					topicUser.get(key).add(q);
//				}
//			}
//			
//		}
//		
//		return topicUser;
	}
	
	/**
	 * We calculated the k (for the metric precision@k, etc..) dynamically, consequently we have a 
	 * different number of measures for each query performed in a topic. As result of this we need to know the min number of measures 
	 * calculated for a query in the topic to create the chart.
	 * This method returns the name of the measures of the query with the min number of measures calculated in the topic.
	 * 
	 * @input  Topic
	 * @return Measures
	 *
	 * **/	
	public static AbstractMeasure[] getNameMeasures(final ArrayList<Query> topic) {
		AbstractMeasure [] nameMeasures = null;
		if (topic.get(0) instanceof QueryRelFile) {
			Map<String, AbstractMeasure> minMeasures = ((QueryRelFile)topic.get(0)).getMeasures();
			for (Query q : topic) {
				Map<String, AbstractMeasure> measure = ((QueryRelFile)q).getMeasures();
				if( measure.size() < minMeasures.size()) {
					minMeasures = measure;
				}
			}
			
		Collection<AbstractMeasure> value = minMeasures.values();
		nameMeasures = value.toArray(new AbstractMeasure[value.size()]);
		}
		
		return nameMeasures;
	}
}
