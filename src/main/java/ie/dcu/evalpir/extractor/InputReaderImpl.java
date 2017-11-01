package ie.dcu.evalpir.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import me.tongfei.progressbar.ProgressBar;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * **/
public class InputReaderImpl /*implements InputReader*/{

	public InputReaderImpl() {
		super();
	}
	
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @return Arraylist<User>
	 * 
	 */
	public ArrayList<Query> extractRelevanceFile(File file) {
	    BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ","; 
		Map<String,Document> docs = new HashMap<String,Document>();
		ArrayList<Query> queries = new ArrayList<Query>();
		Document doc;
		QueryRelFile query;
		
		try {
			String[] text;
			String userKey, topicKey, queryKey, docKey, docValue1, docValue2;
			userKey = topicKey = queryKey = docKey = docValue1 = docValue2 = "";
		    br = new BufferedReader(new FileReader(file.getPath()));
		    br.readLine();//removing first row
		   
		    while ((line= br.readLine()) != null) {	
		    	text = (line  != null) ? line.split(cvsSplitBy) : null;
		    	
		    	try {//the value (relevance, similarity, rank) should be a number
		    		if(!userKey.equalsIgnoreCase("")) {
		    			doc = new DocumentRelFile(docKey, Integer.parseInt(docValue1));
			    		docs.put(doc.getId(), doc);
		    			if (!queryKey.equalsIgnoreCase(text[2].replaceAll("\\s+",""))){
		    				query = new QueryRelFile(userKey, topicKey, queryKey, docs);												
				    		queries.add(query);
				    		docs =  new HashMap<String,Document>();
		    			}
		    		}
		    			
			    	userKey = text[0].replaceAll("\\s+","");
			    	topicKey = text[1].replaceAll("\\s+","");
			    	queryKey = text[2].replaceAll("\\s+","");
			    	docKey = text[3].replaceAll("\\s+","");
			    	docValue1 = text[4].replaceAll("\\s+","");
		    	}catch (NumberFormatException e) {
					e.printStackTrace();
				}
		    	
		    }
		    
		    doc = new DocumentRelFile(docKey, Integer.parseInt(docValue1));
		    docs.put(doc.getId(), doc);
		    query = new QueryRelFile(userKey, topicKey, queryKey, docs);
		    queries.add(query);	    
		}catch (NumberFormatException e) {
					e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		return queries;
	
	}
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @return ArrayList<PIR>
	 * 
	 */
	public ArrayList<PIR> extractOutputPIR(File file) {
	    BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ","; 
		Map<String,Document> docs = new HashMap<String,Document>();
		ArrayList<Query> queries = new ArrayList<Query>();
		ArrayList<PIR> pirs = new ArrayList<PIR>();
		Document doc;
		Query query;
		PIR pir;
		
		try {
			String[] text;
			String modelKey, userKey, topicKey, queryKey, docKey, docValue1, docValue2;
			modelKey = userKey = topicKey = queryKey = docKey = docValue1 = docValue2 = "";
		    br = new BufferedReader(new FileReader(file.getPath()));
		    br.readLine();//removing first row
		   
		    while ((line= br.readLine()) != null) {	
		    	text = (line  != null) ? line.split(cvsSplitBy) : null;
		    	
		    	try {//the value (relevance, similarity, rank) should be a number
		    		if(!userKey.equalsIgnoreCase("")) {
		    			
		    			doc = new DocumentOutputPIR(docKey, Integer.parseInt(docValue1), Double.parseDouble(docValue2));
			    		docs.put(doc.getId(), doc);
		    		
		    			if (!queryKey.equalsIgnoreCase(text[2].replaceAll("\\s+",""))){
		    				query = new Query(userKey, topicKey, queryKey, docs);												
				    		queries.add(query);
				    		docs =  new HashMap<String,Document>();
		    			}
				    	
				    	if(!modelKey.equalsIgnoreCase(text[6].replaceAll("\\s+",""))){
				    		pir = new PIR(modelKey, queries);
				    		pirs.add(pir);
				    		queries = new ArrayList<Query>();
				    	}
				    
		    		}
		    			
			    	userKey = text[0].replaceAll("\\s+","");
			    	modelKey = text[6].replaceAll("\\s+","");
			    	topicKey = text[1].replaceAll("\\s+","");
			    	queryKey = text[2].replaceAll("\\s+","");
			    	docKey = text[3].replaceAll("\\s+","");
			    	docValue1 = text[4].replaceAll("\\s+","");
			    	docValue2 = text[5].replaceAll("\\s+","");
		    	}catch (NumberFormatException e) {
					e.printStackTrace();
				}
		    	
		    }
		    
		    doc = new DocumentOutputPIR(docKey, Integer.parseInt(docValue1), Double.parseDouble(docValue2));
		    docs.put(doc.getId(), doc);
		    query = new Query(userKey, topicKey,queryKey, docs);
		    queries.add(query);
		    pir = new PIR(modelKey, queries);
		    pirs.add(pir);
		    
		    
		}catch (NumberFormatException e) {
					e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
		return pirs;
	
	}
	
	
}
