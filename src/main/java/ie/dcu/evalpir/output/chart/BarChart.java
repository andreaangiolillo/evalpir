package ie.dcu.evalpir.output.chart;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart {

	public static void CreateBarChartPerTopic(String path, ArrayList<Query> topic, Measure m) {
		initUIPerTopic(path, topic, m);
	}
	
	private static void initUIPerTopic(String path, ArrayList<Query> topic, Measure m) {
		CategoryDataset dataset = createDataset(topic, m);
		String user = topic.get(0).getUser();
		String nameTopic = topic.get(0).getTopic();
		JFreeChart chart = createChart(dataset, user, nameTopic, m.getName(), topic);
		StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
		theme.setPlotBackgroundPaint(Color.LIGHT_GRAY);
		ChartFactory.setChartTheme(theme);
        try {
			if(m.getName().contains("Precision@")) {
				ChartUtilities.saveChartAsPNG(new File(path + "/Precision@/"  + "User:" + user + "Topic:" + nameTopic + "_" + m.getName() +".png"), chart, 1366, 768);

			}else if (m.getName().contains("Recall@")) {
				ChartUtilities.saveChartAsPNG(new File(path + "/Recall@/" + "User:" + user + "Topic:" + nameTopic + "_" + m.getName() +".png"), chart, 1366, 768);

			}else {
				ChartUtilities.saveChartAsPNG(new File(path  + "/" + m.getName() +"/" +"User:" + user + "Topic:" + nameTopic + "_" + m.getName() +".png"), chart, 1366, 768);

			}
        	
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 private static JFreeChart createChart(final CategoryDataset dataset, final String user, final String topicName, final String measure , ArrayList<Query> topic) {
	        JFreeChart chart = ChartFactory.createBarChart(
	                "User: " + user + " Topic: " + topicName, 
	                "Systems", 
	                measure, 
	                dataset, 
	                PlotOrientation.VERTICAL,
	                true, 
	                true, 
	                false
	        );
	        
	        chart.getCategoryPlot().getRangeAxis().setLowerBound(0.0);
	        chart.getCategoryPlot().getRangeAxis().setUpperBound(1.0);
	        return chart;
	        
	 }
	
	
	public static CategoryDataset createDataset(ArrayList<Query> topic, Measure m) {
		int nQuery = topic.size();
		int nPIR = m.getPIRvalue().size();
		m.sortbyKey();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Measure measure;
		Double value = 0.0;
		for (int i = 0; i < nQuery; i++) {			
			for (int j = 0; j < nPIR; j++) { 
				measure = (Measure)((QueryRelFile)topic.get(i)).searchMeasure(m.getName());
				if(measure != null) {
					measure.sortbyKey();
					value = (Double)measure.getPIR(j).getValue();	
				}
				
				//System.out.println("Number of PIR: " + nPIR +"\n j: " + j);
				dataset.addValue(value, m.getPIR(j).getKey(), topic.get(i).getId());
				value = 0.0;
			}
		}
		
		
		return dataset;
	}
}
