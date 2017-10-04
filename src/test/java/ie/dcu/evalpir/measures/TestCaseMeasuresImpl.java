package ie.dcu.evalpir.measures;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
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
