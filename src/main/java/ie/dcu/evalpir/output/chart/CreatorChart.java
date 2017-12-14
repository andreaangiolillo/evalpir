package ie.dcu.evalpir.output.chart;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ie.dcu.evalpir.elements.AbstractMeasure;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.MeasureCompound;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.output.table.ConsolePrinter;



public class CreatorChart {

	final static String[] MEASURES_LINECHART = {"Recall", "Precision", "Average Precision", "NDCG@05", "NDCG@10", 
										"NDCG@15", "NDCG@20", "Precision@", "Recall@", "fMeasure0.5", "PrecisionRecallCurve" , "Session_PrecisionRecallCurve"};
	
	
	final static String[] MEASURES_BARCHART = { "Recall", "Precision", "Average Precision", "NDCG@05", "NDCG@10", 
			"NDCG@15", "NDCG@20", "Precision@", "Recall@", "fMeasure0.5"};

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
				String[] measures = (folderName.equalsIgnoreCase("LineChart")) ? MEASURES_LINECHART : MEASURES_BARCHART;
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

		Map<String, ArrayList<Query>> topics = setTopic(queries);
		Iterator<Entry<String, ArrayList<Query>>> it = topics.entrySet().iterator();
		AbstractMeasure[] measures;
		ArrayList<Query> topic;
		while(it.hasNext()) {
			topic = it.next().getValue();
			measures = getNameMeasures(topic);
			for (AbstractMeasure measure : measures) {
				if(measure instanceof Measure) {
					LineChart.CreateLineChartPerTopic(pathL, topic, (Measure)measure);
					BarChart.CreateBarChartPerTopic(pathB, topic, (Measure)measure);
					
					
				}else if(measure instanceof MeasureCompound) {
					LineChart.CreateLineChartPerQuery(pathL, topic, measure.getName());
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
//			 System.out.println("SIZE: " + topics.size());
//			 System.out.println(topic.getUserId() + " " +topic.getTopicId());
//			// System.out.println(topic.printMeasures());
//			 System.out.println(measure.getPIRvalue().size());
			 if(measure != null) {
				 LineChart.createLineChartForSessionMeasure(path, topic.getUserId(), topic.getTopicId(), measure);
			 }
			 
		 }
		 
		 
		 
//			ConsolePrinter.startTask("Creating Charts");
//			String path = createFolder("Charts");
//			Map<String, ArrayList<Query>> topics = setTopic(queries);
//			Iterator<Entry<String, ArrayList<Query>>> it = topics.entrySet().iterator();
//			AbstractMeasure[] measures;
//			ArrayList<Query> topic;
//			while(it.hasNext()) {
//				topic = it.next().getValue();
//				measures = getNameMeasures(topic);
//				for (AbstractMeasure measure : measures) {
//					if(measure instanceof Measure) {
//						LineChart.CreateLineChartPerTopic(path, topic, (Measure)measure);
//					}else if(measure instanceof MeasureCompound) {
//						LineChart.CreateLineChartPerQuery(path, topic, measure.getName());
//					}
//				}
//			}
//			ConsolePrinter.endTask("Creating Charts");
//			
		 }
	 /**
	  * 
	  * @param queries
	  * @return
	  */
	public static Map<String, ArrayList<Query>> setTopic(final Map<String,Query> queries){
		Iterator<Entry<String, Query>> it = queries.entrySet().iterator();
		Query q;
		String key ="";
		Map<String, ArrayList<Query>> topicUser = new HashMap<String, ArrayList<Query>>();
		while(it.hasNext()) {
			q = it.next().getValue();	
			if(((QueryRelFile)q).isToConsiderForChart()) {
				key = q.getTopic() +  "," + q.getUser();
				if(!topicUser.containsKey(key)) {
					ArrayList<Query> topic = new ArrayList<Query>();
					topic.add(q);
					topicUser.put(key, topic);
				}else {
					topicUser.get(key).add(q);
				}
			}
			
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
		
		return nameMeasures;
	}
}