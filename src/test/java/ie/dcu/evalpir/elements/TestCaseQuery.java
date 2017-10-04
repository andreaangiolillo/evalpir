package ie.dcu.evalpir.elements;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ie.dcu.evalpir.extractor.InputReaderImpl;


public class TestCaseQuery {

	private ArrayList<User>  rel; 
	private ArrayList<User>  result;
	
	/**
	 * 
	 */
	public TestCaseQuery() {
		super();
		InputReaderImpl reader = new InputReaderImpl();    
		rel = reader.extract("src/main/resources/qrels.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv", true);
	    result = reader.extract("src/main/resources/result.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv", false);
	   
	}

	@Test
	public void testFindDoc() {
		Query qRel = rel.get(0).getTopics().get(0).getQueries().get(0);
		assertTrue(qRel.findDoc("clueweb12-0100tw-1").getId().equalsIgnoreCase("clueweb12-0100tw-1"));
	}

	@Test
	public void testNRelevantDoc() {
		int relevantDOC; 
		int mustBezero; 
		mustBezero = result.get(0).getTopics().get(0).getQueries().get(0).nRelevantDoc();
		relevantDOC = rel.get(0).getTopics().get(0).getQueries().get(0).nRelevantDoc();
		assertTrue("Error this element must be zero", mustBezero == 0);
		assertTrue(relevantDOC == 1);
		
		mustBezero = result.get(0).getTopics().get(0).getQueries().get(1).nRelevantDoc();
		relevantDOC = rel.get(0).getTopics().get(0).getQueries().get(1).nRelevantDoc();
		assertTrue("Error this element must be zero", mustBezero == 0);
		assertTrue(relevantDOC == 1);
		
		mustBezero = result.get(0).getTopics().get(1).getQueries().get(1).nRelevantDoc();
		relevantDOC = rel.get(0).getTopics().get(1).getQueries().get(1).nRelevantDoc();
		assertTrue("Error this element must be zero", mustBezero == 0);
		assertTrue(relevantDOC == 5);
		
		
		
		
		
	}

}
