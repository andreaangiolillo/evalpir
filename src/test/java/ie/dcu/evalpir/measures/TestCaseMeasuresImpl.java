package ie.dcu.evalpir.measures;

import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;
import ie.dcu.evalpir.extractor.InputReaderImpl;


public class TestCaseMeasuresImpl {
	
	static final String RELEVANCE_FILE_PATH =  "src/main/resources/qrels.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv";
	static final String LOGS_FILE_PATH = "src/main/resources/logSFile.csv";
	
	private Map<String, Query>  rel; 
	private ArrayList<PIR>  result;
	private Map<String ,Session> logsfile;
	private CalculateMeasureImpl m;
	/**
	 * 
	 */
	public TestCaseMeasuresImpl() {
		super();

		InputReaderImpl reader = new InputReaderImpl();  
		File relevanceFile = new File(RELEVANCE_FILE_PATH);
		rel = reader.extractRelevanceFile(relevanceFile);
		
		File outputPIR = new File("src/main/resources/result.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv");
	    result = reader.extractOutputPIR(outputPIR);
	  
	    m = new CalculateMeasureImpl(rel);	
	}

	
	@Test
	public void TestCaseAp() {
		DocumentRelFile d1 = new DocumentRelFile("1", 3);
		DocumentRelFile d2 = new DocumentRelFile("2", 3);
		DocumentRelFile d3 = new DocumentRelFile("3", 3);
		DocumentRelFile d4 = new DocumentRelFile("4", 3);
		DocumentRelFile d5 = new DocumentRelFile("5", 3);
		DocumentRelFile d6 = new DocumentRelFile("6", 1);
		DocumentRelFile d7 = new DocumentRelFile("7", 1);
		DocumentRelFile d8 = new DocumentRelFile("8", 1);
		DocumentRelFile d9 = new DocumentRelFile("9", 1);
		DocumentRelFile d10 = new DocumentRelFile("10", 1);
		Map<String,Document> docs = new HashMap<String, Document>();
		docs.put("1", d1);
		docs.put("2", d2);
		docs.put("3", d3);
		docs.put("4", d4);
		docs.put("5", d5);
		docs.put("6", d6);
		docs.put("7", d7);
		docs.put("8", d8);
		docs.put("9", d9);
		docs.put("10", d10);
		Query qRel = new QueryRelFile("user", "topic", "queryRel", docs);
		
		
		DocumentOutputPIR o1 = new DocumentOutputPIR("1", 1, 0.95);
		DocumentOutputPIR o2 = new DocumentOutputPIR("5", 2, 0.95);
		DocumentOutputPIR o3 = new DocumentOutputPIR("10", 3, 0.95);
		DocumentOutputPIR o4 = new DocumentOutputPIR("4", 4, 0.95);
		DocumentOutputPIR o5 = new DocumentOutputPIR("9", 5, 0.95);
		DocumentOutputPIR o6 = new DocumentOutputPIR("3", 6, 0.95);
		DocumentOutputPIR o7 = new DocumentOutputPIR("8", 7, 0.95);
		DocumentOutputPIR o8 = new DocumentOutputPIR("7", 8, 0.95);
		DocumentOutputPIR o9 = new DocumentOutputPIR("6", 9, 0.95);
		DocumentOutputPIR o10 = new DocumentOutputPIR("2", 10, 0.95);
		Map<String,Document> docs1 = new HashMap<String, Document>();
		docs1.put("1", o1);
		docs1.put("5", o2);
		docs1.put("10", o3);
		docs1.put("4", o4);
		docs1.put("9", o5);
		docs1.put("3", o6);
		docs1.put("8", o7);
		docs1.put("7", o8);
		docs1.put("6", o9);
		docs1.put("2", o10);
		Query qOut = new Query("user", "topic", "queryOut", docs1);
		
		
		assertEquals(0.783, CalculateMeasureImpl.calculateAP(qRel, qOut), 0.001);
		
	}
	
	
	
	
	@Test
	public void testFMeasure() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		double recall;
		double precision;
		
		//user0
		query_result = result.get(0).getQuery("1");
		query_rel = rel.get("1");
		recall = CalculateMeasureImpl.recall(query_rel, query_result);
		precision = CalculateMeasureImpl.precision(query_rel, query_result);
		assertEquals(0.181818182, CalculateMeasureImpl.fMeasure(precision, recall, 0.5),delta);
		
		query_result = result.get(0).getQuery("2");
		query_rel = rel.get("2");
		recall = CalculateMeasureImpl.recall(query_rel, query_result);
		precision = CalculateMeasureImpl.precision(query_rel, query_result);
		assertEquals(0.181818182, CalculateMeasureImpl.fMeasure(precision, recall, 0.5),delta);
		
		query_result = result.get(0).getQuery("5");
		query_rel = rel.get("5");
		recall = CalculateMeasureImpl.recall(query_rel, query_result);
		precision = CalculateMeasureImpl.precision(query_rel, query_result);
		assertEquals(0.666666667, CalculateMeasureImpl.fMeasure(precision, recall, 0.5),delta);
		
	}
	

	@Test
	public void testPrecision() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		
		//user0 - P
		query_result = result.get(0).getQuery("1");
		query_rel = rel.get("1");
		assertEquals(0.1, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("2");
		query_rel = rel.get("2");
		assertEquals(0.1, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("3");
		query_rel = rel.get("3");
		assertEquals(0.1, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("4");
		query_rel = rel.get("4");
		assertEquals(0.1, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("5");
		query_rel = rel.get("5");
		assertEquals(0.5, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("6");
		query_rel = rel.get("6");
		assertEquals(0.4, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		

		//user1 - P
		query_result = result.get(0).getQuery("7");
		query_rel = rel.get("7");
		assertEquals(0.4, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("8");
		query_rel = rel.get("8");
		assertEquals(0.1, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("9");
		query_rel = rel.get("9");
		assertEquals(0.3, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("10");
		query_rel = rel.get("10");
		assertEquals(0.2, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("11");
		query_rel = rel.get("11");
		assertEquals(0.2, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("12");
		query_rel = rel.get("12");
		assertEquals(0.4, CalculateMeasureImpl.precision(query_rel, query_result) ,delta);
		
		
	}
	
	@Test
	public void testRecall() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		
		//user0 - R
		query_result = result.get(0).getQuery("1");
		query_rel = rel.get("1");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("2");
		query_rel = rel.get("2");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("3");
		query_rel = rel.get("3");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("4");
		query_rel = rel.get("4");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
			
		query_result = result.get(0).getQuery("5");
		query_rel = rel.get("5");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("6");
		query_rel = rel.get("6");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		

		//user1 - R
		query_result = result.get(0).getQuery("7");
		query_rel = rel.get("7");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
			
		query_result = result.get(0).getQuery("8");
		query_rel = rel.get("8");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("9");
		query_rel = rel.get("9");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("10");
		query_rel = rel.get("10");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("11");
		query_rel = rel.get("11");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getQuery("12");
		query_rel = rel.get("12");
		assertEquals(1.0, CalculateMeasureImpl.recall(query_rel, query_result) ,delta);
		
	}
	
	
	@Test
	public void testCalculateNDCG() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		
		//user0 
		query_result = result.get(0).getQuery("1");
		query_rel = rel.get("1");
		assertEquals(0.441060599, CalculateMeasureImpl.calculateNDCG(query_rel, query_result, 3), delta);
	
		query_result = result.get(0).getQuery("2");
		query_rel = rel.get("2");
		assertEquals(0.809953117, CalculateMeasureImpl.calculateNDCG(query_rel, query_result, 3), delta);
		
		query_result = result.get(0).getQuery("3");
		query_rel = rel.get("3");
		assertEquals(0.904976558, CalculateMeasureImpl.calculateNDCG(query_rel, query_result, 3), delta);
		
				
		//user1 - PK
		
		query_result = result.get(0).getQuery("10");
		query_rel = rel.get("10");
		assertEquals(0.55353405, CalculateMeasureImpl.calculateNDCG(query_rel, query_result, 3), delta);
		
		query_result = result.get(0).getQuery("11");
		query_rel = rel.get("11");
		assertEquals(0.61830695, CalculateMeasureImpl.calculateNDCG(query_rel, query_result, 3), delta);
		
		query_result = result.get(0).getQuery("12");
		query_rel = rel.get("12");
		assertEquals(0.544276169, CalculateMeasureImpl.calculateNDCG(query_rel, query_result, 3), delta);
				
		
		
	}


	@Test
	public void testCalculatePKRK() {
		Double delta = 0.0000000001;
		Query query_result;
		Query query_rel;
		
		//user0 - PK
		query_result = result.get(0).getQuery("1");
		query_rel = rel.get("1");
		
		assertEquals(0.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("2");
		query_rel = rel.get("2");
		assertEquals(0.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("3");
		query_rel = rel.get("3");
		assertEquals(0.2, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("4");
		query_rel = rel.get("4");
		assertEquals(0.2, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("5");
		query_rel = rel.get("5");
		assertEquals(0.40, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("6");
		query_rel = rel.get("6");
		assertEquals(0.40, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		//user0 - RK
		query_result = result.get(0).getQuery("1");
		query_rel = rel.get("1");
		assertEquals(0.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("2");
		query_rel = rel.get("2");
		assertEquals(0.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("3");
		query_rel = rel.get("3");
		assertEquals(1.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("4");
		query_rel = rel.get("4");
		assertEquals(1.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("5");
		query_rel = rel.get("5");
		assertEquals(0.40, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("6");
		query_rel = rel.get("6");
		assertEquals(0.50, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
	
		
		//user1 - PK
		query_result = result.get(0).getQuery("7");
		query_rel = rel.get("7");
		assertEquals(0.60, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("8");
		query_rel = rel.get("8");
		assertEquals(0.2, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("9");
		query_rel = rel.get("9");
		assertEquals(0.2, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("10");
		query_rel = rel.get("10");
		assertEquals(0.2, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("11");
		query_rel = rel.get("11");
		assertEquals(0.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getQuery("12");
		query_rel = rel.get("12");
		assertEquals(0.2, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		//user1 - RK
		query_result = result.get(0).getQuery("7");
		query_rel = rel.get("7");
		assertEquals(0.75, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("8");
		query_rel = rel.get("8");
		assertEquals(1.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("9");
		query_rel = rel.get("9");
		assertEquals(0.3333333333, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("10");
		query_rel = rel.get("10");
		assertEquals(0.5, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("11");
		query_rel = rel.get("11");
		assertEquals(0.0, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getQuery("12");
		query_rel = rel.get("12");
		assertEquals(0.25, CalculateMeasureImpl.calculatePKRK(query_rel, query_result, 5, true), delta);
	}



	
	



}
