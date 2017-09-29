package ie.dcu.evalpir.extractor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ie.dcu.evalpir.elements.Document;
import ie.dcu.evalpir.elements.DocumentOutputPIR;
import ie.dcu.evalpir.elements.DocumentRelevanceFile;
import ie.dcu.evalpir.elements.Query;
import ie.dcu.evalpir.elements.Topic;
import ie.dcu.evalpir.elements.User;
/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * **/
public class InputReaderImpl implements InputReader{

	public InputReaderImpl() {
		super();
	}
	
	
	/**
	 * This method extract the data from the input CSV to create a data structure. 
	 * @param csvFile
	 * @param isRealFile -> true: the file to read is the qRels.test. false: the file to read is the result.test
	 * @return map
	 * 
	 */
	public ArrayList<User> extract(String csvFile, boolean isRelevanceFile) {
	    BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		Map<String,Document> docs = new HashMap<String,Document>();
		ArrayList<Query> queries = new ArrayList<Query>();
		ArrayList<Topic> topics = new ArrayList<Topic>();
		ArrayList<User> users = new ArrayList<User>();
		Document doc;
		Query query;
		Topic topic;
		User user;
		
		
		try {
			String[] text;
			String userKey, topicKey, queryKey, docKey, docValue1, docValue2;
			userKey = topicKey = queryKey = docKey = docValue1 = docValue2 = "";
		    br = new BufferedReader(new FileReader(csvFile));
		    br.readLine();//removing first row
		   
		    while ((line = br.readLine()) != null) {	
		    	text = (line  != null) ? line.split(cvsSplitBy) : null;
		    	try {//the value (relevance, similarity, rank) should be a number
		    		if(!userKey.equalsIgnoreCase("")) {
		    			
		    			doc = isRelevanceFile ? new DocumentRelevanceFile(docKey, Integer.parseInt(docValue1)) : 
		    									new DocumentOutputPIR(docKey, Integer.parseInt(docValue1), Double.parseDouble(docValue2));
			    		docs.put(doc.getId(), doc);
		    		
		    			if (!queryKey.equalsIgnoreCase(text[2].replaceAll("\\s+",""))){
		    				query = new Query(queryKey, docs);
				    		queries.add(query);
				    		docs =  new HashMap<String,Document>();
		    			}
				    	
				    	if(!topicKey.equalsIgnoreCase(text[1].replaceAll("\\s+",""))) {
				    		topic = new Topic(topicKey, queries);
				    		topics.add(topic);
				    		queries = new ArrayList<Query>();
				    	}
				    	
				    	if(!userKey.equalsIgnoreCase(text[0].replaceAll("\\s+",""))){
				    		user = new User(userKey, topics);
				    		users.add(user);
				    		topics = new ArrayList<Topic>();
				    	}
				    
		    		}
		    			
			    	userKey = text[0].replaceAll("\\s+","");
			    	topicKey = text[1].replaceAll("\\s+","");
			    	queryKey = text[2].replaceAll("\\s+","");
			    	docKey = text[3].replaceAll("\\s+","");
			    	docValue1 = text[4].replaceAll("\\s+","");
			    	docValue2 = !isRelevanceFile ? text[5].replaceAll("\\s+","") : "";
			    	
			    	
		    	}catch (NumberFormatException e) {
					e.printStackTrace();
				}
		    	
		    }
		    
		    doc = isRelevanceFile ? new DocumentRelevanceFile(docKey, Integer.parseInt(docValue1)) : 
									new DocumentOutputPIR(docKey, Integer.parseInt(docValue1), Double.parseDouble(docValue2));
		    docs.put(doc.getId(), doc);
		    query = new Query(queryKey, docs);
		    queries.add(query);
		    topic = new Topic(topicKey, queries);
		    topics.add(topic);
		    user = new User(userKey, topics);
		    users.add(user);
		    
		    
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

		return users;
	
	}
}
