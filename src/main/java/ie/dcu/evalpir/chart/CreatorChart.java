package ie.dcu.evalpir.chart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import me.tongfei.progressbar.ProgressBar;


public class CreatorChart {

	/**
	 * It Creates the folder for the diagrams
	 * @param nameFolder
	 * @return
	 */
	 public static String createFolder(String nameFolder){
	    	String path;
			try {
				path = new File(".").getCanonicalPath() + "/" + nameFolder;
				File dir = new File(path);
		        dir.mkdir();
				return (path);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return "";	
	 }
	
	 /**
	  * 
	  * @param queries
	  */
	 public static void createChart(final Map<String,Query> queries) {
		ProgressBar pb = new ProgressBar("Creating Charts", 100).start(); // progressbar
		pb.maxHint(queries.size() * 40); // progressbar
		String path = createFolder("Charts");
		Map<String, ArrayList<Query>> topics = setTopic(queries);
		Iterator<Entry<String, ArrayList<Query>>> it = topics.entrySet().iterator();
		Measure[] measures;
		ArrayList<Query> topic;
		while(it.hasNext()) {
			topic = it.next().getValue();
			measures = getNameMeasures(topic);
			for (Measure measure : measures) {
				LineChart.CreateLineChart(path, topic, measure);
				pb.step();
			}
		}
		
		pb.stepTo(queries.size() * 40);// progressbar
		pb.stop();// progressbar
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
				if(!topicUser.containsKey(q.getTopic() +  "," + q.getUser())) {
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
	public static Measure[] getNameMeasures(final ArrayList<Query> topic) {
		Measure [] nameMeasures = null;
		if (topic.get(0) instanceof QueryRelFile) {
			Map<String, Measure> minMeasures = ((QueryRelFile)topic.get(0)).getMeasures();
			for (Query q : topic) {
				Map<String, Measure> measure = ((QueryRelFile)q).getMeasures();
				if( measure.size() < minMeasures.size()) {
					minMeasures = measure;
				}
			}
			
		Collection<Measure> value = minMeasures.values();
		nameMeasures = value.toArray(new Measure[value.size()]);
		}
		
		return nameMeasures;
	}
}
