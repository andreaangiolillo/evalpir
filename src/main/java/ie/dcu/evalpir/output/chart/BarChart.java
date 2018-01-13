package ie.dcu.evalpir.output.chart;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class BarChart {

	public static void CreateBarChartPerTopic(String path, ArrayList<Query> topic, Measure m, boolean newRelDoc) {
		initUIPerTopic(path, topic, m, newRelDoc);
	}
	
	private static void initUIPerTopic(String path, ArrayList<Query> topic, Measure m, boolean newRelDoc) {
		CategoryDataset dataset = (newRelDoc)? createDatasetNewInfo(topic, m):createDataset(topic, m) ;	
		String user = topic.get(0).getUser();
		String nameTopic = topic.get(0).getTopic();
		JFreeChart chart = createChart(dataset, user, nameTopic, m.getName(), topic);
		StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
		theme.setPlotBackgroundPaint(Color.LIGHT_GRAY);
		ChartFactory.setChartTheme(theme);
        try {
        	int heightChart = getHeightScreen();
        	int widthChart = getWidthScreen();
			if(m.getName().contains("Precision@")) {
				ChartUtilities.saveChartAsPNG(new File(path + "/Precision@/"  + "User:" + user + "Topic:" + nameTopic + "_" + m.getName() +".png"), chart, widthChart, heightChart, null, false, 9);
			}else if (m.getName().contains("Recall@")) {
				ChartUtilities.saveChartAsPNG(new File(path + "/Recall@/" + "User:" + user + "Topic:" + nameTopic + "_" + m.getName() +".png"), chart, widthChart, heightChart, null, false, 9);

			}else {
				ChartUtilities.saveChartAsPNG(new File(path  + "/" + m.getName() +"/" +"User:" + user + "Topic:" + nameTopic + "_" + m.getName() +".png"), chart, widthChart, heightChart, null, false, 9);
				
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
	        CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot(); 
	        BarRenderer renderer = new BarRenderer(); 
	        renderer.setShadowVisible(false); 
	        categoryPlot.setRenderer(renderer); 
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
	
	
	/***BarChart with considering only new relevant docs**/
		
	public static CategoryDataset createDatasetNewInfo(ArrayList<Query> topic, Measure m) {
		int nQuery = topic.size();
		int nPIR = m.getPIRvalue().size();
		m.sortbyKey();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Measure measure;
		String pirName = "";
		double value;
		for (int i = 0; i < nQuery; i++) {			
			for (int j = 0; j < nPIR; j++) { 
				value = 0;
				measure = (Measure)((QueryRelFile)topic.get(i)).searchMeasure(m.getName());
				if(measure != null) {
					measure.sortbyKey();
					value = (Double)measure.getPIR(j).getValue();
					pirName = m.getPIR(j).getKey();
				}
				
				//System.out.println("Number of PIR: " + nPIR +"\n j: " + j);
				dataset.addValue(value, m.getPIR(j).getKey(), topic.get(i).getId());
				
				value = 0;
				measure = (Measure)((QueryRelFile)topic.get(i)).searchMeasure(m.getName()+"-NewRelDoc");
				if(measure != null) {
					value = measure.getPIRValue(pirName);
				}
				
				//System.out.println("Number of PIR: " + nPIR +"\n j: " + j);
				dataset.addValue(value, m.getPIR(j).getKey()+"-NewRelDoc", topic.get(i).getId());
				
				value = 0;
				measure = (Measure)((QueryRelFile)topic.get(i)).searchMeasure(m.getName()+"-OldRelDoc");
				if(measure != null) {
					value = measure.getPIRValue(pirName);
				}
				
				//System.out.println("Number of PIR: " + nPIR +"\n j: " + j);
				dataset.addValue(value, m.getPIR(j).getKey()+"-OldRelDoc", topic.get(i).getId());
			}
		}

		return dataset;
		
	}
	
	
	/**
	 * Return the width of the screen 
	 * @return
	 */
	public static int getWidthScreen() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return gd.getDisplayMode().getWidth();
	}
	
	/**
	 * Return the height of the screen 
	 * @return
	 */
	public static int getHeightScreen() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		return gd.getDisplayMode().getHeight();
	}
	
	
}
