package ie.dcu.evalpir.elements;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 *          It represent a query in the systems' files
 */

public class Query {

	private String user;
	private String topic;
	private String id;
	private Map<String, Document> docs; // key = docID, value = Document

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
		this.docs = new HashMap<String, Document>(docs);
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

	public void addDoc(Document doc) {
		docs.put(doc.getId(), doc);
	}

	public void removeDoc(String docId) {
		docs.remove(docId);
	}

	public String getId() {
		return id;
	}

	public Map<String, Document> getDocs() {
		return docs;
	}

	public void setDocs(Map<String, Document> docs) {
		this.docs = docs;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		String stringDoc = "";
		for (Map.Entry<String, Document> entry : docs.entrySet()) {
			stringDoc += ((Document) entry.getValue()).toString() + "\n";
		}

		stringDoc += "] \nNumber of documents: " + docs.size() + " ]";
		return "\nUser = " + user + " Topic = " + topic + " Query [id = " + id + ", docs =\n" + stringDoc;
	}

}
