package ie.dcu.evalpir.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;


public class LineChart{
	
	public static void CreateLineChart(String path, ArrayList<Query> topic, Measure m) {
		initUI(path, topic, m);
	}
	
	private static void initUI(String path, ArrayList<Query> topic, Measure m) {
	
		StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
		theme.setPlotBackgroundPaint(Color.WHITE);
		ChartFactory.setChartTheme(theme);
		XYSeriesCollection dataset = createDataset(topic, m);
		String user = topic.get(0).getUser();
		String nameTopic = topic.get(0).getTopic();
		JFreeChart chart = createChart(dataset, user, nameTopic, m.getName());
		chart.setBackgroundPaint(Color.WHITE);
		
		System.out.println("MEASURE NAME: " + m.getName());
        try {
			ChartUtilities.saveChartAsPNG(new File(path + "/" + "User:" + user + "Topic:" + nameTopic + "_" + m.getName() +".png"), chart, 750, 500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static XYSeriesCollection createDataset(ArrayList<Query> topic, Measure m) {
		int nQuery = topic.size();
		int nPIR = m.getPIRvalue().size();
		XYSeries[] series = new XYSeries[nPIR];
		Measure measure;
		Double value = 0.0;
		for (int i = 0; i < nQuery; i++) {			
			for (int j = 0; j < nPIR; j++) { 
				if(i == 0) {
					series[j] = new XYSeries(m.getPIR(j).getKey());
				}
				
				measure = ((QueryRelFile)topic.get(i)).searchMeasure(m.getName());
				if(measure != null) {
					value = (Double)measure.getPIR(j).getValue();
					
				}
				System.out.println("Number of PIR: " + nPIR +"\n j: " + j);
				series[j].add(i, value);
				value = 0.0;
			}
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (XYSeries s : series) {
			dataset.addSeries(s);
		}
		
		return dataset;
		
	}
	
	 private static JFreeChart createChart(final XYSeriesCollection dataset, final String user, final String topic, final String measure) {
	        JFreeChart chart = ChartFactory.createXYLineChart(
	                "User: " + user + " Topic: " + topic, 
	                "Query", 
	                measure, 
	                dataset, 
	                PlotOrientation.VERTICAL,
	                true, 
	                true, 
	                false
	        );
	        setXYAxis(chart, measure);
	        XYPlot plot = (XYPlot) chart.getPlot();
	        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
			r.setAutoPopulateSeriesShape(true);
			for (int i = 0; i < dataset.getSeries().size(); i++){			
				r.setSeriesShapesVisible (i, true);
				r.setSeriesStroke(i, new BasicStroke(2));
			}
			
	        return chart;
	        
	 }
	 
	 private static void setXYAxis(JFreeChart chart, String measure) {
		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setTickUnit(new NumberTickUnit(1));
		boolean isNormaliseMeasure = false;
		String[] normaliseMeasure = new String[] {"precision@", "recall@", "ndcn", "precision", "recall", "fmeasure"};
		int i = 0;
		while(i < normaliseMeasure.length && !isNormaliseMeasure) {
			if(measure.toLowerCase().contains(normaliseMeasure[i])){
				isNormaliseMeasure = true;
			}
			
			i++;
		}
		
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainAxis(xAxis);
		if (isNormaliseMeasure) {
			ValueAxis yAxis = plot.getRangeAxis();
			yAxis.setRange(0, 1.0);
		}		
	 }
	
	
	
}
