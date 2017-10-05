package ie.dcu.evalpir.measures;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelevanceFile;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.User;
import ie.dcu.evalpir.extractor.InputReaderImpl;


public class TestCaseMeasuresImpl {
	
	private ArrayList<User>  rel; 
	private ArrayList<User>  result;
	private MeasureImpl m;
	/**
	 * 
	 */
	public TestCaseMeasuresImpl() {
		super();
		InputReaderImpl reader = new InputReaderImpl();    
		rel = reader.extract("src/main/resources/qrels.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv", true);
	    result = reader.extract("src/main/resources/result.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv", false);
	    m = new MeasureImpl();	
	}

	
	@Test
	public void TestCaseAp() {
		DocumentRelevanceFile d1 = new DocumentRelevanceFile("1", 3);
		DocumentRelevanceFile d2 = new DocumentRelevanceFile("2", 3);
		DocumentRelevanceFile d3 = new DocumentRelevanceFile("3", 3);
		DocumentRelevanceFile d4 = new DocumentRelevanceFile("4", 3);
		DocumentRelevanceFile d5 = new DocumentRelevanceFile("5", 3);
		DocumentRelevanceFile d6 = new DocumentRelevanceFile("6", 1);
		DocumentRelevanceFile d7 = new DocumentRelevanceFile("7", 1);
		DocumentRelevanceFile d8 = new DocumentRelevanceFile("8", 1);
		DocumentRelevanceFile d9 = new DocumentRelevanceFile("9", 1);
		DocumentRelevanceFile d10 = new DocumentRelevanceFile("10", 1);
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
		Query qRel = new Query("queryRel", docs);
		
		
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
		Query qOut = new Query("queryOut", docs1);
		
		
		assertEquals(0.783, m.ap(qRel, qOut, false), 0.001);
		assertEquals(0.712121212, m.ap(qRel, qOut, true), 0.001);
		
		
	}
	
	
	
	
	@Test
	public void testFMeasure() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		double recall;
		double precision;
		
		//user0
		query_result = result.get(0).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(0);
		recall = m.recall(query_rel, query_result);
		precision = m.precision(query_rel, query_result);
		assertEquals(0.181818182, m.fMeasure(precision, recall, 0.5),delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(1);
		recall = m.recall(query_rel, query_result);
		precision = m.precision(query_rel, query_result);
		assertEquals(0.181818182, m.fMeasure(precision, recall, 0.5),delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(1);
		recall = m.recall(query_rel, query_result);
		precision = m.precision(query_rel, query_result);
		assertEquals(0.666666667, m.fMeasure(precision, recall, 0.5),delta);
		
	}
	

	@Test
	public void testPrecision() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(0);
		//user0 - P
		query_result = result.get(0).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(0);
		assertEquals(0.1, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(1);
		assertEquals(0.1, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(2);
		assertEquals(0.1, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(0);
		assertEquals(0.1, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(1);
		assertEquals(0.5, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(2);
		assertEquals(0.4, m.precision(query_rel, query_result) ,delta);
		

		//user1 - P
		query_result = result.get(1).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(0);
		assertEquals(0.4, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(1);
		assertEquals(0.1, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(2);
		assertEquals(0.3, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(0);
		assertEquals(0.2, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(1);
		assertEquals(0.2, m.precision(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(2);
		assertEquals(0.4, m.precision(query_rel, query_result) ,delta);
		
		
	}
	
	@Test
	public void testRecall() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		
		//user0 - R
		query_result = result.get(0).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(0);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(1);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(2);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(0);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(1);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(2);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		

		//user1 - R
		query_result = result.get(1).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(0);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(1);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(2);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(0);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(1);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(2);
		assertEquals(1.0, m.recall(query_rel, query_result) ,delta);
	}
	
	
	@Test
	public void testCalculateNDCG() {
		Double delta = 0.000001;
		Query query_result;
		Query query_rel;
		
		//user0 
		query_result = result.get(0).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(0);	
		assertEquals(0.441060599, m.calculateNDCG(query_rel, query_result, 3), delta);
	
		query_result = result.get(0).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(1);
		assertEquals(0.809953117, m.calculateNDCG(query_rel, query_result, 3), delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(2);
		assertEquals(0.904976558, m.calculateNDCG(query_rel, query_result, 3), delta);
		
				
		//user1 - PK
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(0);
		assertEquals(0.55353405, m.calculateNDCG(query_rel, query_result, 3), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(1);
		assertEquals(0.61830695, m.calculateNDCG(query_rel, query_result, 3), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(2);
		assertEquals(0.544276169, m.calculateNDCG(query_rel, query_result, 3), delta);
				
		
		
	}


	@Test
	public void testCalculatePKRK() {
		Double delta = 0.0000000001;
		Query query_result;
		Query query_rel;
		
		//user0 - PK
		query_result = result.get(0).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(0);
		
		assertEquals(0.0, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(1);
		assertEquals(0.0, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(2);
		assertEquals(0.2, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(0);
		assertEquals(0.2, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(1);
		assertEquals(0.40, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(2);
		assertEquals(0.40, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		//user0 - RK
		query_result = result.get(0).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(0);
		assertEquals(0.0, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(1);
		assertEquals(0.0, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(0).getQueries().get(2);
		assertEquals(1.0, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(0);
		assertEquals(1.0, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(1);
		assertEquals(0.40, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(0).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(0).getTopics().get(1).getQueries().get(2);
		assertEquals(0.50, m.calculatePKRK(query_rel, query_result, 5, true), delta);
	
		
		//user1 - PK
		query_result = result.get(1).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(0);
		assertEquals(0.60, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(1);
		assertEquals(0.2, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(2);
		assertEquals(0.2, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(0);
		assertEquals(0.2, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(1);
		assertEquals(0.0, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(2);
		assertEquals(0.2, m.calculatePKRK(query_rel, query_result, 5, false), delta);
		
		//user1 - RK
		query_result = result.get(1).getTopics().get(0).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(0);
		assertEquals(0.75, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(1);
		assertEquals(1.0, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(1).getTopics().get(0).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(0).getQueries().get(2);
		assertEquals(0.3333333333, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(0);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(0);
		assertEquals(0.5, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(1);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(1);
		assertEquals(0.0, m.calculatePKRK(query_rel, query_result, 5, true), delta);
		
		query_result = result.get(1).getTopics().get(1).getQueries().get(2);
		query_rel = rel.get(1).getTopics().get(1).getQueries().get(2);
		assertEquals(0.25, m.calculatePKRK(query_rel, query_result, 5, true), delta);
	}



	
	



}
