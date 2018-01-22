package ie.dcu.evalpir.output.chart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
										"NDCG@15", "NDCG@20", "Precision@", "Recall@", "fMeasure0.5", "PrecisionRecallCurve" , "Session_PrecisionRecallCurve",
										"Session_PrecisionRecallCurve_UntilNotRelDocFound", "Session_PrecisionRecallCurve_Moffat&ZobelDistribution"};
	
	
	final static String[] MEASURES_BARCHART = { "Recall", "Precision", "AveragePrecision", "NDCG@05", "NDCG@10", 
			"NDCG@15", "NDCG@20", "Precision@", "Recall@", "fMeasure0.5"};
	
	final static String[] MEASURES_INDEPTH = {"AveragePrecision", "Precision@01", "Precision@02","Precision@03", "Precision@04","Precision@05", "Precision@06",
			"Precision@07", "Precision@08","Precision@09", "Precision@10"};

	/**
	 * It creates the folder for the diagrams
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
					case "In-depth analysis":
						measures = MEASURES_INDEPTH;
						break;
					default:
						throw new IllegalArgumentException("Invalid name of the folder: " + folderName);
				}
				
		        for(int i = 0; i < measures.length; i++) {
		        	if(EvalEpir.MEASURES_FOR_CHART.contains(measures[i])) {
			        	subdir = new File(path + measures[i]); 
			        	subdir.mkdir();
		        	}
		        }
		        
				return (path);	
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			return "";
	 }
	
	 /**
	  * It computes the charts for the measures
	  *
	  * @param queries
	  */
	 public static void createChart(final Map<String,Query> queries) {
		ConsolePrinter.startTask("Creating Charts");
		String pathL = createFolder("LineChart");
		String pathB = createFolder("BarChart");
		String pathS = createFolder("In-depth analysis");

		Map<String, ArrayList<Query>> topics = setTopic(queries);
		AbstractMeasure[] measures;
		ArrayList<Query> topic;
		for(Map.Entry<String, ArrayList<Query>> entry : topics.entrySet()) {
			topic = entry.getValue();
			measures = getNameMeasures(topic);
			for (AbstractMeasure measure : measures) {
				if(measure.mustBeDrawn()) {	
					//System.out.println("MEASURE: " + measure.getName());
					if(measure instanceof Measure) {
						LineChart.CreateLineChartPerTopic(pathL, topic, (Measure)measure);
						BarChart.CreateBarChartPerTopic(pathB, topic, (Measure)measure, false);
						if(measure.isStackedBar()) {
							BarChart.CreateBarChartPerTopic(pathS, topic, (Measure)measure, true);
						}
					}else if(measure instanceof MeasureCompound) {
						LineChart.CreateLineChartPerQuery(pathL, topic, measure.getName());
					}
				}
			}
		}
		
		ConsolePrinter.endTask("Creating Charts");
		
	 }
	 
	 /**
	  * In computes the charts for the sessions measures
	  * @param topics
	  */
	 public static void createChartSession(final Map<String, Topic> topics) {
		 String path = createFolder("LineChart");
		 MeasureCompound measureDf;
		 MeasureCompound measureNewInfo;
		 for(Map.Entry<String, Topic> topic: topics.entrySet()) {
			 if(EvalEpir.SESSION_METHOD_1) {
				 measureDf = (MeasureCompound)topic.getValue().searchMeasure("Session_PrecisionRecallCurve");
				 if(measureDf != null) {
					 LineChart.createLineChartForSessionMeasure(path, topic.getValue().getUserId(), topic.getValue().getTopicId(), measureDf);
				 }
			 }
			 
			 if(EvalEpir.SESSION_METHOD_2) {
				 measureNewInfo = (MeasureCompound)topic.getValue().searchMeasure("Session_PrecisionRecallCurve_UntilNotRelDocFound");
				 if(measureNewInfo != null) {
					 LineChart.createLineChartForSessionMeasure(path, topic.getValue().getUserId(), topic.getValue().getTopicId(), measureNewInfo);
				 }
			 }
			 
			 if(EvalEpir.SESSION_METHOD_3) {
				 measureNewInfo = (MeasureCompound)topic.getValue().searchMeasure("Session_PrecisionRecallCurve_Moffat&ZobelDistribution");
				 if(measureNewInfo != null) {
					 LineChart.createLineChartForSessionMeasure(path, topic.getValue().getUserId(), topic.getValue().getTopicId(), measureNewInfo);
				 }
			 }
			 
		 }
	 }
	 
	 /**
	  * Ii creates a map of <TopicId, queries in the topic>
	  * @param queries
	  * @return
	  */
	public static Map<String, ArrayList<Query>> setTopic(final Map<String,Query> queries){
		Map<String, ArrayList<Query>> topicUser = new HashMap<String, ArrayList<Query>>(); 
		Map<String, Session> logs = EvalEpir.getLOGS();
		ArrayList<Log> querySorted ;
		Query query;
		for(Map.Entry<String, Session> entryLog: logs.entrySet()) {
			querySorted = entryLog.getValue().getQuery();
			ArrayList<Query> topicQueries = new ArrayList<Query>();
			for (int i = 0; i < querySorted.size(); i++) {
				query = queries.get(querySorted.get(i).getQuery().toLowerCase().trim());
				if(query == null) {
					throw new QueryNotInTheLogFileException("QueryID: " + querySorted.get(i).getQuery().toLowerCase().trim());
				}
				
				topicQueries.add(query);
			}
			
			topicUser.put(entryLog.getKey(), topicQueries);
		}
		
		return topicUser;
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
		
		return setStackedBar(nameMeasures, topic);
	}
	
	
	/***
	 * It searches measures with isStackedBar == true in the topic, and, in that case, set the measures  true
	 * @param measures
	 * @param topic
	 * @return
	 */
	public static AbstractMeasure[] setStackedBar(AbstractMeasure[] measures, final ArrayList<Query> topic) {
		Map<String, Boolean> dictionaryMeasure = new HashMap<String, Boolean>();
		for(String measureStackedBar : MEASURES_INDEPTH) {
			dictionaryMeasure.put(measureStackedBar.trim().toLowerCase(), false);
		}
		
		Map<String, AbstractMeasure> measuresTopic;
		AbstractMeasure measureTopic;
		for (Query query : topic) {
			measuresTopic = ((QueryRelFile)query).getMeasures();
			for(String measureStackedBar : MEASURES_INDEPTH) {
				measureTopic = measuresTopic.get(measureStackedBar.trim().toLowerCase());
				if(measureTopic != null && measureTopic.isStackedBar()) {
					dictionaryMeasure.put(measureStackedBar.trim().toLowerCase(), true);
				}
			}
		}
		
		for (AbstractMeasure measure : measures) {
			if(dictionaryMeasure.containsKey(measure.getName().trim().toLowerCase())
					&&  dictionaryMeasure.get(measure.getName().trim().toLowerCase())){
					measure.setStackedBar(true);
			}
		}
		
		return measures;
	}
}
