package ie.dcu.evalpir.measures;

import java.io.File;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import static org.junit.Assert.*;

import ie.dcu.evalpir.EvalEpir;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.Log;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Pair;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.extractor.InputReader;

public class TestCaseSessionMeasure {
	static final String RELEVANCE_FILE_PATH =  "src/main/resources/relFile.csv";
	static final String LOGS_FILE_PATH = "src/main/resources/logSFile.csv";
	static final String PIRS_FILE = "src/main/resources/ar.csv";
	
	
	private Map<String, Query>  rel;
	private ArrayList<PIR>  result;
	private Map<String ,Session> logsfile;
	/** example variables**/
	private Map<String,Query> qPIR = new HashMap<String, Query>();
	private Map<String,Query> qRel = new HashMap<String, Query>();
	private Session s;
	
	public TestCaseSessionMeasure() {
		String[] args = {"", "src/main/resources/relFile.csv", "src/main/resources/logSFile.csv", "src/main/resources/model1.csv","src/main/resources/model2.csv","src/main/resources/model3.csv"};
		rel = InputReader.extractRelevanceFile(new File(args[1]));
		result = EvalEpir.extractingModels(args);
		logsfile = InputReader.extracLogFile(new File(args[2]));
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
		qRel.put(query.getId().toLowerCase(), query);
		
		dr1 = new DocumentRelFile("12", 4);
		dr2 = new DocumentRelFile("22", 3);
		dr3 = new DocumentRelFile("32", 1);
		dr4 = new DocumentRelFile("42", 4);
		dr5 = new DocumentRelFile("52", 3);
		dr6 = new DocumentRelFile("62", 4);
		docs = new HashMap<String, Document>();
		docs.put("12", dr1);
		docs.put("22", dr2);
		docs.put("32", dr3);
		docs.put("42", dr4);
		docs.put("52", dr5);
		docs.put("62", dr6);

		query = new QueryRelFile("user", "topic", "query2", docs);
		qRel.put(query.getId().toLowerCase(), query);
		
		dr1 = new DocumentRelFile("122", 3);
		dr2 = new DocumentRelFile("222", 2);
		dr3 = new DocumentRelFile("32", 3);
		dr4 = new DocumentRelFile("42", 1);
		dr5 = new DocumentRelFile("55", 1);
		dr6 = new DocumentRelFile("62", 4);
		docs = new HashMap<String, Document>();
		docs.put("122", dr1);
		docs.put("222", dr2);
		docs.put("32", dr3);
		docs.put("42", dr4);
		docs.put("52", dr5);
		docs.put("62", dr6);

		query = new QueryRelFile("user", "topic", "query3", docs);
		qRel.put(query.getId().toLowerCase(), query);
		
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
		d3 = new DocumentOutputPIR("3", 3,0);
		d4 = new DocumentOutputPIR("42", 4,0);
		d5 = new DocumentOutputPIR("52", 5,0);
		d6 = new DocumentOutputPIR("6", 6,0);
		
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
		Log l6 = new Log("QUERY_SUBMISSION", "2018-03-15 20:00:11.957", "6", "query3", "4");
		
		s.addLog(l);
		s.addLog(l2);
		s.addLog(l4);
		s.addLog(l3);
		s.addLog(l5);
		s.addLog(l6);


	}
	
	@Test
	public void testCreatingQueryConsideringPreoviousOnes() {
		EvalEpir.setQUERYREL(qRel);
		Iterator<Entry<String, Query>> itq = qRel.entrySet().iterator();
		Entry<String, Query> entryQ;
		Query qNew = null;
		int positionQuery  = -1;
		while(itq.hasNext()) {
			entryQ = itq.next();
			if(entryQ.getKey().equalsIgnoreCase("query3")) {
				System.out.println(entryQ.getValue().getDocs());
				positionQuery = CalculateSessionMeasure.getPositionQuery((QueryRelFile)entryQ.getValue(), s.getQuery());
				assertEquals(2, positionQuery);
				qNew = CalculateSessionMeasure.creatingQueryConsideringPreoviousOnes((QueryRelFile)entryQ.getValue(), 1, s.getQuery(), true);
			}
		}
		
		assertEquals(2, ((QueryRelFile)qNew).getNRelevantDoc());
		System.out.println(qNew.getDocs());
	}
	
	@Test
	public void TestGetPath() {
		Map<String, Integer> path1 = s.getPath();
		Map<String, Integer> path = s.getPath(qPIR);
		System.out.println("PATH1: " + path1);
		System.out.println("PATH: " + path);
		
	}
	
	@Test
	public void TestPrecisionk() {
		Map<String, Document> docsMergedRel = CalculateSessionMeasure.mergeRelevanceDocs(qRel);
		/* TEST MergeReleanceDocs*/
		assertEquals(18, docsMergedRel.size());
		Map<String, Document> docsMergedPIR = CalculateSessionMeasure.mergeRankedList(qPIR, s, s.getPath());
		
		/* TEST MergeRankedList*/
		assertEquals(14, docsMergedPIR.size());
		
//		/* TEST Precision**/
		assertEquals(1.0, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 1), 0.01);
		assertEquals(0.75, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 4), 0.01);
		assertEquals(0.66, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 6), 0.01);
		assertEquals(0.55, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 9), 0.01);
		assertEquals(0.583, CalculateSessionMeasure.precisionK(docsMergedRel, docsMergedPIR, 12), 0.01);
	}

	@Test
	public void TestPrecisionRecallCurve(){

		ArrayList<Pair<Integer, Double>> curve = CalculateSessionMeasure.precisionRecallCurve(qRel, qPIR , s, s.getPath());
		System.out.println(curve);
		/**TEST CURVE*/
		assertEquals(11, curve.size());
		
	}
	
	@Test
	public void TestRelevanceCount() {
		DocumentRelFile dr1 = new DocumentRelFile("1", 1);
		DocumentRelFile dr2 = new DocumentRelFile("2", 1);
		DocumentRelFile dr3 = new DocumentRelFile("3", 4);
		DocumentRelFile dr4 = new DocumentRelFile("4", 1);
		DocumentRelFile dr5 = new DocumentRelFile("5", 1);
		DocumentRelFile dr6 = new DocumentRelFile("6", 4);
		DocumentRelFile dr7 = new DocumentRelFile("7", 4);
		Map<String,Document> docs = new HashMap<String, Document>();
		docs.put("1", dr1);
		docs.put("2", dr2);
		docs.put("3", dr3);
		docs.put("4", dr4);
		docs.put("5", dr5);
		docs.put("6", dr6);
		docs.put("7", dr7);
		Query queryRel = new QueryRelFile("user", "topic", "query1", docs);
		
		
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
		
		Query queryPIR = new QueryRelFile("user", "topic", "query1", docs);
	
		assertEquals(2, CalculateSessionMeasure.relevanceCount(queryRel, queryPIR, 6));
		assertEquals(1, CalculateSessionMeasure.relevanceCount(queryRel, queryPIR, 5));
	}
	
	@Test
	public void TestrR() {
		assertEquals(7,CalculateSessionMeasure.rR(qRel, qPIR, s.getPath()));
		
	}
	
	@Test
	public void TestrRC() {
		assertEquals(0.7,CalculateSessionMeasure.rRC(qRel, qPIR, s.getPath()), 0.01);
	}
	
	@Test
	public void TestrPC() {
		assertEquals(0.46,CalculateSessionMeasure.rPC(qRel, qPIR, s.getPath()), 0.01);
	}

	@Test
	public void TestrMAP() {
		
	}
	
	@Test
	public void TestrNsdcg() {
		
	}


}
