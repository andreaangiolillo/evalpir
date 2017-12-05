package ie.dcu.evalpir.measures;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.PIR;
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
	/**
	 * 
	 */
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
	}
	
	@Test
	public void TestMergeRelevanceDocs(){
		
		Map<String,Query> qrel = new HashMap<String, Query>();
		
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
		Query query = new QueryRelFile("user", "topic", "queryRel1", docs);
		qrel.put(query.getId().toLowerCase(), query);
		
		d1 = new DocumentRelFile("1", 3);
		d2 = new DocumentRelFile("2", 3);
		d3 = new DocumentRelFile("3", 3);
		d4 = new DocumentRelFile("4", 3);
		d5 = new DocumentRelFile("5", 3);
		d6 = new DocumentRelFile("6", 1);
		d7 = new DocumentRelFile("7", 1);
		d8 = new DocumentRelFile("8", 1);
		d9 = new DocumentRelFile("9", 1);
		d10 = new DocumentRelFile("100000", 1);
		docs = new HashMap<String, Document>();
		docs.put("12", d1);
		docs.put("22", d2);
		docs.put("32", d3);
		docs.put("42", d4);
		docs.put("52", d5);
		docs.put("62", d6);
		docs.put("72", d7);
		docs.put("82", d8);
		docs.put("92", d9);
		docs.put("120", d10);
		query = new QueryRelFile("user", "topic", "queryRel2", docs);
		qrel.put(query.getId().toLowerCase(), query);
		
		System.out.println(qrel.size());
		
//		ArrayList<Document> docsMerged = CalculateSessionMeasure.mergeRelevanceDocs(qrel);
		
//		assertEquals(20, docsMerged.size());
		
//		System.out.println(docsMerged.toString());
		
	}


}
