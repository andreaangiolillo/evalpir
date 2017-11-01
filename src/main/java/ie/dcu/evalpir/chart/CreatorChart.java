package ie.dcu.evalpir.chart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
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
	
	 /***
	  * 
	  * @param 
	  */
	public static void createChart(final ArrayList<Query> queries) {	
		ProgressBar pb = new ProgressBar("Creating Charts", 100).start(); // progressbar
		pb.maxHint(queries.size() * 40); // progressbar
		String path = createFolder("Charts");
		ArrayList<Query> topic = new ArrayList<Query>();
		Measure[] measures;
		int nQuery = queries.size();
		topic.add(queries.get(0));
		for (int i = 1; i < nQuery; i++) {
			if(queries.get(i).getTopic().equalsIgnoreCase(queries.get(i - 1).getTopic())) {
				topic.add(queries.get(i));
			}else {
				measures = getNameMeasures(topic);
				for (Measure measure : measures) {
					System.out.println("Topic " + topic.size());
					LineChart.CreateLineChart(path, topic, measure);
					pb.step();
				}
				
				topic = new ArrayList<Query>();
				topic.add(queries.get(i));
			}	
		}
		
		pb.stepTo(queries.size() * 40);// progressbar
		pb.stop();// progressbar
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
