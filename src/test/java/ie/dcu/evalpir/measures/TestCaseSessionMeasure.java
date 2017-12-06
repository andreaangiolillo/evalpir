package ie.dcu.evalpir.measures;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.Log;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Pair;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.extractor.InputReaderImpl;

public class TestCaseSessionMeasure {
	static final String RELEVANCE_FILE_PATH =  "src/main/resources/relFile.csv";
	static final String LOGS_FILE_PATH = "src/main/resources/logSFile.csv";
	static final String PIRS_FILE = "src/main/resources/ar.csv";
	
	private Map<String, Query>  rel; 
	private ArrayList<PIR>  result;
	private Map<String ,Session> logsfile;
	private CalculateSessionMeasure m;
	private Map<String,Query> qPIR;
	private Map<String,Query> qrel;
	private Session s;
	
	public TestCaseSessionMeasure() {
		super();

		InputReaderImpl reader = new InputReaderImpl();  
		File relevanceFile = new File(RELEVANCE_FILE_PATH);
		rel = reader.extractRelevanceFile(relevanceFile);
		
		File outputPIR = new File(PIRS_FILE);
	    result = reader.extractOutputPIR(outputPIR);
	    
	    File log = new File(LOGS_FILE_PATH);
	    logsfile = reader.extracLogFile(log);
	  
	    m = new CalculateSessionMeasure(rel,logsfile);	
	    qPIR = new HashMap<String, Query>();
	    qrel = new HashMap<String, Query>();
	    setInstance();
	}
	
	
	
	public void setInstance() {
		DocumentRelFile dr1 = new DocumentRelFile("1", 1);
		DocumentRelFile dr2 = new DocumentRelFile("2", 1);
		DocumentRelFile dr3 = new DocumentRelFile("3", 4);
		DocumentRelFile dr4 = new DocumentRelFile("4", 1);
		DocumentRelFile dr5 = new DocumentRelFile("5", 1);
		DocumentRelFile dr6 = new DocumentRelFile("6", 4);
		Map<String,Document> docs = new HashMap<String, Document>();
		docs.put("1", dr1);
		docs.put("2", dr2);
		docs.put("3", dr3);
		docs.put("4", dr4);
		docs.put("5", dr5);
		docs.put("6", dr6);
		Query query = new QueryRelFile("user", "topic", "query1", docs);
		qrel.put(query.getId().toLowerCase(), query);
		
		dr1 = new DocumentRelFile("12", 1);
		dr2 = new DocumentRelFile("22", 3);
		dr3 = new DocumentRelFile("32", 1);
		dr4 = new DocumentRelFile("42", 4);
		dr5 = new DocumentRelFile("52", 3);
		dr6 = new DocumentRelFile("62", 1);
		docs = new HashMap<String, Document>();
		docs.put("12", dr1);
		docs.put("22", dr2);
		docs.put("32", dr3);
		docs.put("42", dr4);
		docs.put("52", dr5);
		docs.put("62", dr6);

		query = new QueryRelFile("user", "topic", "query2", docs);
		qrel.put(query.getId().toLowerCase(), query);
		
		dr1 = new DocumentRelFile("122", 3);
		dr2 = new DocumentRelFile("222", 2);
		dr3 = new DocumentRelFile("32", 3);
		dr4 = new DocumentRelFile("42", 1);
		dr5 = new DocumentRelFile("55", 1);
		dr6 = new DocumentRelFile("66", 4);
		docs = new HashMap<String, Document>();
		docs.put("122", dr1);
		docs.put("222", dr2);
		docs.put("32", dr3);
		docs.put("42", dr4);
		docs.put("52", dr5);
		docs.put("62", dr6);

		query = new QueryRelFile("user", "topic", "query3", docs);
		qrel.put(query.getId().toLowerCase(), query);
		
		DocumentOutputPIR d1 = new DocumentOutputPIR("1", 1, 0);
		DocumentOutputPIR d2 = new DocumentOutputPIR("2", 2, 0);
		DocumentOutputPIR d3 = new DocumentOutputPIR("3", 3, 0);
		DocumentOutputPIR d4 = new DocumentOutputPIR("4", 4, 0);
		DocumentOutputPIR d5 = new DocumentOutputPIR("5", 5, 0);
		DocumentOutputPIR d6 = new DocumentOutputPIR("6", 6, 0);
		docs = new HashMap<String, Document>();
		docs.put("1", d1);
		docs.put("2", d2);
		docs.put("3", d3);
		docs.put("4", d4);
		docs.put("5", d5);
		docs.put("6", d6);
		
		query = new QueryRelFile("user", "topic", "query1", docs);
		qPIR.put(query.getId().toLowerCase(), query);
		
		d1 = new DocumentOutputPIR("12", 1,0);
		d2 = new DocumentOutputPIR("22", 2,0);
		d3 = new DocumentOutputPIR("32", 3,0);
		d4 = new DocumentOutputPIR("42", 4,0);
		d5 = new DocumentOutputPIR("52", 5,0);
		d6 = new DocumentOutputPIR("62", 6,0);
		
		docs = new HashMap<String, Document>();
		docs.put("12", d1);
		docs.put("22", d2);
		docs.put("32", d3);
		docs.put("42", d4);
		docs.put("52", d5);
		docs.put("62", d6);
		
		query = new QueryRelFile("user", "topic", "query2", docs);
		qPIR.put(query.getId().toLowerCase(), query);
		
		d1 = new DocumentOutputPIR("122", 1,0);
		d2 = new DocumentOutputPIR("222", 2,0);
		d3 = new DocumentOutputPIR("32", 3,0);
		d4 = new DocumentOutputPIR("42", 4,0);
		d5 = new DocumentOutputPIR("52", 5,0);
		d6 = new DocumentOutputPIR("62", 6,0);
		
		docs = new HashMap<String, Document>();
		docs.put("122", d1);
		docs.put("222", d2);
		docs.put("32", d3);
		docs.put("42", d4);
		docs.put("52", d5);
		docs.put("62", d6);
		
		query = new QueryRelFile("user", "topic", "query3", docs);
		qPIR.put(query.getId().toLowerCase(), query);
		
		
		s = new Session("id", "user", "topic");
		Log l = new Log("OPEN_DOCUMENT", "2017-03-15 20:00:11.957", "6", "query1", "6");
		Log l2 = new Log("OPEN_DOCUMENT", "2016-03-15 20:00:11.957", "6", "query2", "4");
		Log l5 = new Log("OPEN_DOCUMENT", "2016-03-15 20:00:11.957", "6", "query3", "2");
		Log l3 = new Log("QUERY_SUBMISSION", "2016-03-15 20:00:11.957", "6", "query2", "4");
		Log l4 = new Log("QUERY_SUBMISSION", "2017-03-15 20:00:11.957", "6", "query1", "4");
		Log l6 = new Log("QUERY_SUBMISSION", "2011-03-15 20:00:11.957", "6", "query3", "4");
		
		s.addLog(l);
		s.addLog(l2);
		s.addLog(l4);
		s.addLog(l3);
		s.addLog(l5);
		s.addLog(l6);


	}
	
	@Test
	public void TestPrecisionk() {
		Map<String, Document> docsMergedRel = CalculateSessionMeasure.mergeRelevanceDocs(qrel);
		/* TEST MergeReleanceDocs*/
		assertEquals(18, docsMergedRel.size());
		Map<String, Document> docsMergedPIR = CalculateSessionMeasure.mergeRankedList(qPIR, s, s.getPath());
		
		/* TEST MergeRankedList*/
		assertEquals(12, docsMergedPIR.size());
		
		/* TEST Precision**/
		assertEquals(1.0, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 1), 0.01);
		assertEquals(0.5, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 4), 0.01);
		assertEquals(0.5, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 6), 0.01);
		assertEquals(0.44, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 9), 0.01);
		assertEquals(0.41, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 12), 0.01);
	}

	@Test
	public void TestPrecisionRecallCurve(){				
		ArrayList<Pair<Integer, Double>> curve = CalculateSessionMeasure.precisionRecallCurve( qrel, qPIR , s, s.getPath());
		System.out.print(curve);
		/**TEST CURVE*/
		assertEquals(8, curve.size());
		
	}
	
	@Test
	public void TestRelevanceCount() {
		
	}
	
	@Test
	public void TestrR() {
		
	}
	
	@Test
	public void TestrRC() {
		
	}
	
	@Test
	public void TestrPC() {
		
	}


}