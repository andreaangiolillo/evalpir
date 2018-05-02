package ie.dcu.evalpir.output.chart;

/**
 * @author Andrea Angiolillo
 * 
 *         It creates the lineCharts
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ie.dcu.evalpir.elements.AbstractMeasure;
import ie.dcu.evalpir.elements.Measure;
import ie.dcu.evalpir.elements.MeasureCompound;
import ie.dcu.evalpir.elements.Pair;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;

public class LineChart {

	public static void CreateLineChartPerTopic(String path, ArrayList<Query> topic, Measure m) {
		initUIPerTopic(path, topic, m);
	}

	/**
	 * It creates the linechart
	 * 
	 * @param path
	 * @param topic
	 * @param m
	 */
	private static void initUIPerTopic(String path, ArrayList<Query> topic, Measure m) {
		XYSeriesCollection dataset = createDatasetPerTopic(topic, m);
		String user = topic.get(0).getUser();
		String nameTopic = topic.get(0).getTopic();
		JFreeChart chart = createChart(dataset, user, nameTopic, m.getName(), topic);
		StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
		theme.setPlotBackgroundPaint(Color.LIGHT_GRAY);
		ChartFactory.setChartTheme(theme);
		try {
			if (m.getName().contains("Precision@")) {
				ChartUtilities.saveChartAsPNG(
						new File(path + "/Precision@/" + "User_" + user + "_Topic_" + nameTopic + "_" + m.getName()
								+ ".png"),
						chart, BarChart.getWidthScreen(), BarChart.getHeightScreen(), null, false, 9);
			} else if (m.getName().contains("Recall@")) {
				ChartUtilities.saveChartAsPNG(
						new File(path + "/Recall@/" + "User_" + user + "_Topic_" + nameTopic + "_" + m.getName()
								+ ".png"),
						chart, BarChart.getWidthScreen(), BarChart.getHeightScreen(), null, false, 9);
			} else {
				ChartUtilities.saveChartAsPNG(
						new File(path + "/" + m.getName() + "/" + "User_" + user + "_Topic_" + nameTopic + "_"
								+ m.getName() + ".png"),
						chart, BarChart.getWidthScreen(), BarChart.getHeightScreen(), null, false, 9);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * It creates the dataset that contains all the points to show on the lineChart
	 * 
	 * @param topic
	 * @param m
	 * @return
	 */
	private static XYSeriesCollection createDatasetPerTopic(ArrayList<Query> topic, Measure m) {
		int nQuery = topic.size();
		int nPIR = m.getPIRvalue().size();
		m.sortbyKey();
		XYSeries[] series = new XYSeries[nPIR];
		Measure measure;
		Double value = 0.0;
		for (int i = 0; i < nQuery; i++) {

			for (int j = 0; j < nPIR; j++) {
				if (i == 0) {
					// System.out.println("Series name: " +m.getPIR(j).getKey());
					series[j] = new XYSeries(m.getPIR(j).getKey());
				}

				measure = (Measure) ((QueryRelFile) topic.get(i)).searchMeasure(m.getName());
				if (measure != null) {
					measure.sortbyKey();
					value = (Double) measure.getPIR(j).getValue();
				}

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

	/**
	 * It creates the chart
	 * 
	 * @param dataset
	 * @param user
	 * @param topicName
	 * @param measure
	 * @param topic
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static JFreeChart createChart(final XYSeriesCollection dataset, final String user, final String topicName,
			final String measure, ArrayList<Query> topic) {
		JFreeChart chart = ChartFactory.createXYLineChart("User: " + user + " Topic: " + topicName, "Query", measure,
				dataset, PlotOrientation.VERTICAL, true, true, false);

		setXYAxis(chart, measure, topic);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
		r.setAutoPopulateSeriesShape(true);
		r.setStroke(new BasicStroke(3));
		r.setBaseShapesVisible(true);
		return chart;
	}

	/**
	 * 
	 * @param chart
	 * @param measure
	 * @param topic
	 */
	private static void setXYAxis(JFreeChart chart, String measure, ArrayList<Query> topic) {
		// Setting the xAxis
		String[] name = new String[topic.size()];
		for (int j = 0; j < topic.size(); j++) {
			name[j] = topic.get(j).getId();
		}

		SymbolAxis rangeAxis = new SymbolAxis("Query", name);
		rangeAxis.setTickUnit(new NumberTickUnit(1));
		rangeAxis.setGridBandsVisible(false);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainAxis(rangeAxis);
		ValueAxis yAxis = plot.getRangeAxis();
		if (!measure.equalsIgnoreCase("Precision") && !measure.equalsIgnoreCase("F-Measure")) {
			yAxis.setRange(0, 1);
		} else {
			yAxis.setLowerBound(0);
		}
		yAxis.setLowerMargin(5);

	}

	/**
	 * 
	 * @param path
	 * @param topic
	 * @param measureName
	 */
	public static void CreateLineChartPerQuery(String path, ArrayList<Query> topic, String measureName) {
		initUIPerQuery(path, topic, measureName);
	}

	/**
	 * It creates the lineChart for the PrecisionRecallCurve
	 * 
	 * @param path
	 * @param topic
	 * @param measureName
	 */
	private static void initUIPerQuery(String path, ArrayList<Query> topic, String measureName) {
		StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
		theme.setPlotBackgroundPaint(Color.LIGHT_GRAY);
		ChartFactory.setChartTheme(theme);
		AbstractMeasure measure;
		for (int i = 0; i < topic.size(); i++) {
			measure = ((QueryRelFile) topic.get(i)).searchMeasure(measureName);
			if (((MeasureCompound) measure).getPIRvalue() != null
					&& ((QueryRelFile) topic.get(i)).getNRelevantDoc() > 0) {
				XYSeriesCollection dataset = createDataset((MeasureCompound) measure);
				String user = topic.get(i).getUser();
				String nameTopic = topic.get(i).getTopic();
				JFreeChart chart = createChart(dataset, user, nameTopic, topic.get(i).getId(), measureName);
				XYPlot plot = (XYPlot) chart.getPlot();
				ValueAxis xAxis = plot.getDomainAxis();
				xAxis.setRange(0, 105);
				xAxis.setLowerMargin(0);
				ValueAxis yAxis = plot.getRangeAxis();
				yAxis.setRange(0, 1.05);
				try {
					ChartUtilities.saveChartAsPNG(
							new File(path + "/" + measureName + "/" + "Query" + topic.get(i).getId() + "_" + measureName
									+ ".png"),
							chart, BarChart.getWidthScreen(), BarChart.getHeightScreen(), null, false, 9);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * It creates the chart object for PrecisionRecallCurve
	 * 
	 * @param dataset
	 * @param user
	 * @param topicName
	 * @param query
	 * @param measure
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static JFreeChart createChart(final XYSeriesCollection dataset, final String user, final String topicName,
			final String query, final String measure) {
		JFreeChart chart = ChartFactory.createXYLineChart("User: " + user + " Topic: " + topicName + " Query: " + query,
				"%Recall", "Precision", dataset, PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
		r.setAutoPopulateSeriesShape(true);
		r.setStroke(new BasicStroke(3));
		return chart;
	}

	/**
	 * It creates the Dataset
	 * 
	 * @param m
	 * @return
	 */
	private static XYSeriesCollection createDataset(final MeasureCompound m) {
		Iterator<Entry<String, ArrayList<Pair<Integer, Double>>>> it = m.getPIRvalueSortedByKey().entrySet().iterator();
		XYSeries[] series = new XYSeries[m.getPIRvalue().size()];
		ArrayList<Pair<Integer, Double>> value;
		int i = 0;
		while (it.hasNext()) {
			Entry<String, ArrayList<Pair<Integer, Double>>> measurePerPir = it.next();
			series[i] = new XYSeries(measurePerPir.getKey());
			value = measurePerPir.getValue();
			if (value != null) {
				for (int j = 0; j < value.size(); j++) {
					series[i].add(value.get(j).getKey(), value.get(j).getValue());
				}
			}

			i++;
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeries s : series) {
			// System.out.println("LIST" + s.getItems());
			dataset.addSeries(s);
		}

		return dataset;
	}

	/**
	 * It creates the lineChart for the session measure
	 * 
	 * @param path
	 * @param user
	 * @param topic
	 * @param measure
	 */
	public static void createLineChartForSessionMeasure(String path, String user, String topic,
			MeasureCompound measure) {
		StandardChartTheme theme = new StandardChartTheme("JFree/Shadow");
		theme.setPlotBackgroundPaint(Color.LIGHT_GRAY);
		ChartFactory.setChartTheme(theme);
		XYSeriesCollection dataset = createDataset((MeasureCompound) measure);
		JFreeChart chart = createChart(dataset, user, topic, "", measure.getName());
		XYPlot plot = (XYPlot) chart.getPlot();
		ValueAxis xAxis = plot.getDomainAxis();
		xAxis.setRange(0, 100);
		xAxis.setLowerMargin(0);
		ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setRange(0, 1.0);

		try {
			ChartUtilities.saveChartAsPNG(
					new File(path + "/" + measure.getName() + "/" + "User_" + user + "_Topic_" + topic + "_"
							+ measure.getName() + ".png"),
					chart, BarChart.getWidthScreen(), BarChart.getHeightScreen(), null, false, 9);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}