package ie.dcu.evalpir.elements;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import ie.dcu.evalpir.extractor.InputReaderImpl;


public class TestCaseQuery {
	
	static final String RELEVANCE_FILE_PATH =  "src/main/resources/qrels.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv";
	
	
	private ArrayList<User>  rel; 
	private ArrayList<PIR>  result;
	
	/**
	 * 
	 */
	public TestCaseQuery() {
		super();
		InputReaderImpl reader = new InputReaderImpl();  
		File relevanceFile = new File(RELEVANCE_FILE_PATH);
		rel = reader.extractRelevanceFile(relevanceFile);
		
		File outputPIR = new File("src/main/resources/result.test.nUser.2.nTopic.2.Tue Oct 03 12:55:33 IST 2017.csv");
	    result = reader.extractOutputPIR(outputPIR);
	   
	}

	@Test
	public void testFindDoc() {
		QueryRelFile qRel = (QueryRelFile) rel.get(0).getTopics().get(0).getQueries().get(0);
		assertTrue(qRel.findDoc("clueweb12-0100tw-1").getId().equalsIgnoreCase("clueweb12-0100tw-1"));
	}

	@Test
	public void testNRelevantDoc() {
		int relevantDOC; 
		
		relevantDOC = ((QueryRelFile)rel.get(0).getTopics().get(0).getQueries().get(0)).getNRelevantDoc();
		assertTrue(relevantDOC == 1);
		
		relevantDOC = ((QueryRelFile) rel.get(0).getTopics().get(0).getQueries().get(1)).getNRelevantDoc();
		assertTrue(relevantDOC == 1);
		
		relevantDOC = ((QueryRelFile) rel.get(0).getTopics().get(1).getQueries().get(1)).getNRelevantDoc();
		assertTrue(relevantDOC == 5);	
		
	}

}
