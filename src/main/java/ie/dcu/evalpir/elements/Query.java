package ie.dcu.evalpir.elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * */


public class Query {
	
	private String user;
	private String topic;
	private String id;
	private Map<String,Document> docs;
	

	/**
	 * 
	 */
	public Query() {
		super();
		id = "None";
		this.topic = "None";
		this.user = "None";
	}

	/**
	 * @param id
	 * @param docs
	 */
	public Query(String user, String topic, String id, Map<String, Document> docs) {
		super();
		this.id = id;
		this.topic = topic;
		this.user = user;
		this.docs = docs;
	}
	
	

	/**
	 * @param id
	 */
	public Query(String user, String topic, String id) {
		super();
		this.id = id;
		this.topic = topic;
		this.user = user;
		this.docs = new HashMap<String, Document>();
				
	}	
	
	/**
	 * @param docId
	 * @return
	 */
	public Document findDoc(String docId) {
		return docs.get(docId);
	}
	
	/**
	 * @param doc
	 * 
	 **/
	public void addDoc(Document doc) {
		docs.put(doc.getId(), doc);
	}
	
	
	/**
	 * @param doc
	 * 
	 */
	public void removeDoc(String docId) {
		docs.remove(docId);
	}
	
	
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the docs
	 */
	public Map<String, Document> getDocs() {
		return docs;
	}

	/**
	 * @param docs the docs to set
	 */
	public void setDocs(Map <String, Document> docs) {
		this.docs = docs;
	}




	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}



	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}



	/**
	 * @param topic the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	
	
	
	@Override
	public String toString() {
		String stringDoc = "";
		Iterator<?> it = docs.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			stringDoc += ((Document)pair.getValue()).toString() + "\n";
		}
		stringDoc += "] \nNumber of documents: " + docs.size() + " ]";
		return "\nUser = " + user + " Topic = " + topic + " Query [id = " + id + ", docs =\n" + stringDoc ;
	} 
	
	
	
	

}
