 /**
 * 
 */
package ie.dcu.evalpir.elements;

import java.util.ArrayList;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 */
public class Topic {

	private String id;
	private ArrayList<Query> queries;
	
	/**
	 * 
	 */
	public Topic() {
		super();
		this.id = "";
	}
	
	
	/**
	 * @param id
	 */
	public Topic(String id) {
		super();
		this.id = id;
	}

	/**
	 * @param id
	 * @param queries
	 */
	public Topic(String id, ArrayList<Query> queries) {
		super();
		this.id = id;
		this.queries = queries;
	}
	
	/**
	 * @param queryId
	 * @return Query Object
	 */
	public Query findQuery(String queryId) {
		for( Query q : queries) {
			if (q.getId().equalsIgnoreCase(queryId)) {
				return q;
			}
		}
		return null;
	}
		
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the queries
	 */
	public ArrayList<Query> getQueries() {
		return queries;
	}
	
	/**
	 * @param queries the queries to set
	 */
	public void setQueries(ArrayList<Query> queries) {
		this.queries = queries;
	}


	@Override
	public String toString() {
		String stringDoc = "";
		
		for (Query q : queries) {
			stringDoc += q.toString();
		}
		stringDoc += "\nNumber of queries: " + queries.size() + " ]";
		return "\nTopic [id=" + id + ", queries=" + stringDoc;
	}
	
	
	
}
