package ie.dcu.evalpir.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryOutputPIR;
import ie.dcu.evalpir.elements.Topic;

public class LineChart{
	
	private String path;
	
	public LineChart(String path, String[] namePirs, ArrayList<Topic> topics, String user, String measure) {
		this.path = path;
		initUI(namePirs, topics, user, measure);
	}
	


	private void initUI(String[] namePirs, ArrayList<Topic> topics, String user, String measure) {
	
		StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
		theme.setPlotBackgroundPaint(Color.WHITE);
		
		ChartFactory.setChartTheme(theme);
		
		XYSeriesCollection dataset = createDataset(namePirs, topics, measure);
		JFreeChart chart = createChart(dataset, user, topics.get(0).getId(), measure);
		chart.setBackgroundPaint(Color.WHITE);
		
		
		
        try {
			ChartUtilities.saveChartAsPNG(new File(getPath() + "_" + measure +"_line_chart.png"), chart, 750, 500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private XYSeriesCollection createDataset(String[] namePirs, ArrayList<Topic> topics, String measure) {
		XYSeries[] series = new XYSeries[topics.size()];
		ArrayList<Query> queries;
		//final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < topics.size(); i++) {
			System.out.println("Nunmber topic: " + topics.size() + " number of pirs name: " + namePirs.length);
			
			series[i] = new XYSeries(namePirs[i]);
			queries = topics.get(i).getQueries();
			
			for (int j = 0; j < queries.size(); j++) {
				System.out.println("Number queries: " + topics.get(i).getQueries().size());
				System.out.println("j: " + j + " y: " + (Double) ((QueryOutputPIR)queries.get(j)).getMeasure(measure));
				//dataset.addValue((Double) ((QueryOutputPIR)queries.get(j)).getMeasure(measure), namePirs[i], Integer.toString(j));
				
				series[i].add(j, (Double) ((QueryOutputPIR)queries.get(j)).getMeasure(measure));
			}
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (XYSeries s : series) {
			dataset.addSeries(s);
			System.out.println(s.getMaxX());
		}
		
		return dataset;
		
	}
	
	 private JFreeChart createChart(final XYSeriesCollection dataset, final String user, final String topic, final String measure) {

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
	 
	 private void setXYAxis(JFreeChart chart, String measure) {
		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setTickUnit(new NumberTickUnit(1));
		
		
		
		boolean isNormaliseMeasure = false;
		String[] normaliseMeasure = new String[] {"precision@", "recall@", "ndcn"};
		
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
			yAxis.setUpperMargin(0.5);
		}
		
		
		
	 }
	

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	
}
