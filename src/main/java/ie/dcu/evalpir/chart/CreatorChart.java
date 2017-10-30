package ie.dcu.evalpir.chart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Pair;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryOutputPIR;
import ie.dcu.evalpir.elements.Topic;
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
	
	public static void createChart(final ArrayList<PIR> pirs) {
		ProgressBar pb = new ProgressBar("Creating Charts", 100).start(); // progressbar
		String path = createFolder("Charts");
		String[] namePirs = new String[pirs.size()];
		String[] nameMeasures;
		ArrayList<Topic> topics = new ArrayList<Topic>();
		LineChart chart;
		int numberPIRS = pirs.size();
		int numberUsers = pirs.get(0).getUsers().size();
		int numberTopics; 
		String userId, topicId;
		pb.maxHint(numberUsers*5*20); // progressbar
		for (int k = 0; k < numberPIRS; k++) {
			namePirs[k] = pirs.get(k).getName();
		}
		
		for (int i = 0; i < numberUsers ; i++) {
			numberTopics = pirs.get(0).getUsers().get(0).getTopics().size();
			for(int j = 0; j < numberTopics; j++) {
				nameMeasures = getNameMeasures(pirs.get(0).getUsers().get(i).getTopics().get(j));
				userId = pirs.get(0).getUsers().get(i).getId();
				topicId = pirs.get(0).getUsers().get(i).getTopics().get(j).getId();
				for (PIR p : pirs) {
					topics.add(p.getUsers().get(i).getTopics().get(j));
				}
				for (String measure : nameMeasures) {
					chart = new LineChart(path + "/userId:" + userId + "_Topic:" + topicId , namePirs, topics, userId, measure);
					pb.step();// progressbar
				}
				
				topics = new ArrayList<Topic>();
			}
		}
		pb.stepTo(numberUsers*5*20);// progressbar
		pb.stop();// progressbar
	}
	
	/**
	 * We calculated the k (for the metric precision@k, etc..) dynamically, consequently we have a 
	 * different number of measures for each query performed in a topic. As result of this we need to know the max number of measures 
	 * calculated for a query in the topic to create the chart.
	 * This method returns the name of the measures of the query with the max number of measures calculated in the topic.
	 * 
	 * @return nameMeasures
	 *
	 * **/	
	public static String[] getNameMeasures(final Topic topic) {
		String [] nameMeasures = null;
		ArrayList<Query> queries = topic.getQueries();
		if (queries.get(0) instanceof QueryOutputPIR) {
			ArrayList<Pair<String, Double>> maxMeasures = ((QueryOutputPIR)queries.get(0)).getMeasures();
			for (Query q : queries) {
				ArrayList<Pair<String, Double>> measure = ((QueryOutputPIR)q).getMeasures();
				if( measure.size() > maxMeasures.size()) {
					maxMeasures = measure;
				}
			}
			
			nameMeasures = new String[maxMeasures.size()];
			for (int i = 0; i < maxMeasures.size(); i++) {
				nameMeasures[i] = maxMeasures.get(i).getKey();
			}	
		}
		
		return nameMeasures;
	}
}
