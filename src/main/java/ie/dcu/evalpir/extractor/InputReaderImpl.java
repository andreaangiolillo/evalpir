package ie.dcu.evalpir.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ie.dcu.evalpir.elements.ConsolePrinter;
import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelFile;
import ie.dcu.evalpir.elements.Log;
import ie.dcu.evalpir.elements.PIR;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.QueryRelFile;
import ie.dcu.evalpir.elements.Session;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * **/
public class InputReaderImpl /*implements InputReader*/{

	
	/**
	 * @input logsfile
	 * 
	 **/	
	public static Map<String ,Session> extracLogFile(File logsFile){
		ConsolePrinter.startTask("Extracting " + logsFile.getName());
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ","; 
		Map<String ,Session> sessions = new HashMap<String, Session>();		
		try {
			String[] row;
			br = new BufferedReader(new FileReader(logsFile.getPath()));
			Log log;
			Session session;
			String key ;
			br.readLine();//removing first row
			while ((line= br.readLine()) != null) {
				
				row = (line  != null) ? line.split(cvsSplitBy) : null;
				log = new Log(row[5], row[4], row[1], row[7], row[3]);
				key = row[6].toLowerCase() + "," + row[10].toLowerCase(); // user + "," + topic
				if(sessions.containsKey(key)) {
					if(!sessions.get(key).getId().contains(row[8].toLowerCase())) {
						sessions.get(key).addId(row[8].toLowerCase());
					}
					sessions.get(key).addLog(log);
				}else {
					session = new Session(row[8].toLowerCase(), row[9].toLowerCase(), row[10].toLowerCase());
					session.addLog(log);
					sessions.put(key , session);
				}			
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ConsolePrinter.endTask("Extracting " + logsFile.getName());
		return sessions;
	}
	
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @return Arraylist<User>
	 * 
	 */
	public static Map<String, Query> extractRelevanceFile(File file) {
		ConsolePrinter.startTask("Extracting " + file.getName());
	    BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ","; 
		Map<String,Document> docs = new HashMap<String,Document>();
		Map<String, Document> storeDocs;
		Map<String, Query> queries = new HashMap<String, Query>();
		
		
		Document doc;
		QueryRelFile query;
		
		try {
			String[] text;
			String userKey, topicKey, queryKey, docKey, docValue1;
			userKey = topicKey = queryKey = docKey = docValue1 = "";
		    br = new BufferedReader(new FileReader(file.getPath()));
		    br.readLine();//removing first row
		    while ((line= br.readLine()) != null) {	
		    	text = (line  != null) ? line.split(cvsSplitBy) : null;
	    		if(!userKey.equalsIgnoreCase("")) {
	    			doc = new DocumentRelFile(docKey, Integer.parseInt(docValue1));
		    		docs.put(doc.getId(), doc);
		    		
	    			if (!queryKey.equalsIgnoreCase(text[2].replaceAll("\\s+",""))){
	    				if(!queries.containsKey(queryKey)) {
		    				query = new QueryRelFile(userKey, topicKey, queryKey, docs);												
				    		queries.put(query.getId(),query);
	    				}else {
	    					storeDocs = queries.get(queryKey).getDocs();
	    					storeDocs.putAll(docs);
	    				}
	    				
	    				docs =  new HashMap<String,Document>();
	    			}
	    		}
	    			
		    	userKey = text[0].replaceAll("\\s+","");
		    	topicKey = text[1].replaceAll("\\s+","");
		    	queryKey = text[2].replaceAll("\\s+","");
		    	docKey = text[3].replaceAll("\\s+","");
		    	docValue1 = text[4].replaceAll("\\s+","");
		    }
		    
		    doc = new DocumentRelFile(docKey, Integer.parseInt(docValue1));
		    docs.put(doc.getId(), doc);
		    query = new QueryRelFile(userKey, topicKey, queryKey, docs);
		    queries.put(query.getId(), query);	    
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
		ConsolePrinter.endTask("Extracting " + file.getName());
		return queries;
	
	}
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @return ArrayList<PIR>
	 * 
	 */
	public static ArrayList<PIR> extractOutputPIR(File file) {
		ConsolePrinter.startTask("Extracting " + file.getName());
	    BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ","; 
		Map<String,Document> docs = new HashMap<String,Document>();
		Map<String, Query> queries = new HashMap<String, Query>();
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
	    		if(!userKey.equalsIgnoreCase("")) {
	    			
	    			doc = new DocumentOutputPIR(docKey, Integer.parseInt(docValue1), Double.parseDouble(docValue2));
		    		docs.put(doc.getId(), doc);
	    		
	    			if (!queryKey.equalsIgnoreCase(text[2].replaceAll("\\s+",""))){
	    				query = new Query(userKey, topicKey, queryKey, docs);												
			    		queries.put(query.getId(), query);
			    		docs =  new HashMap<String,Document>();
	    			}
			    	
			    	if(!modelKey.equalsIgnoreCase(text[6].replaceAll("\\s+",""))){
			    		pir = new PIR(modelKey, queries);
			    		pirs.add(pir);
			    		queries = new HashMap<String, Query>();
			    	}
			    
	    		}
	    			
		    	userKey = text[0].replaceAll("\\s+","");
		    	modelKey = text[6].replaceAll("\\s+","");
		    	topicKey = text[1].replaceAll("\\s+","");
		    	queryKey = text[2].replaceAll("\\s+","");
		    	docKey = text[3].replaceAll("\\s+","");
		    	docValue1 = text[4].replaceAll("\\s+","");
		    	docValue2 = text[5].replaceAll("\\s+","");  	
		    }
		    
		    doc = new DocumentOutputPIR(docKey, Integer.parseInt(docValue1), Double.parseDouble(docValue2));
		    docs.put(doc.getId(), doc);
		    query = new Query(userKey, topicKey,queryKey, docs);
		    queries.put(query.getId(),query);
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
		ConsolePrinter.endTask("Extracting " + file.getName());
		return pirs;
	
	}
	
	
}
