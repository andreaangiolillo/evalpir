package ie.dcu.evalpir.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/**
 * @author Andrea Angiolillo
 * @version 1.0
 * 
 * */


public class Query {
	
	private String id;
	private Map<String,Document> docs;
	

	/**
	 * 
	 */
	public Query() {
		super();
		id = "None";
	}



	/**
	 * @param id
	 * @param docs
	 */
	public Query(String id, Map<String, Document> docs) {
		super();
		this.id = id;
		this.docs = docs;
	}
	
	

	/**
	 * @param id
	 */
	public Query(String id) {
		super();
		this.id = id;
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



	
	
	
	
	@Override
	public String toString() {
		String stringDoc = "";
		Iterator it = docs.entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			stringDoc += ((Document)pair.getValue()).toString() + "\n";
		}
		stringDoc += "] \nNumber of documents: " + docs.size() + " ]";
		return "\nQuery [id=" + id + ", docs=\n" + stringDoc;
	}
	
	
	
	

}
