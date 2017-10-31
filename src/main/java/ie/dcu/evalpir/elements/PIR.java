/**
 * 
 */
package ie.dcu.evalpir.elements;

import java.util.ArrayList;

/**
 * @author Andrea Angiolillo	
 *
 */
public class PIR {

	
	ArrayList<Query> queries;
	String name;
	
	/**
	 * @param name
	 */
	public PIR(String name) {
		super();
		this.queries = new ArrayList<Query>();
		this.name = name;
	}
	
	
	/**
	 * @param users
	 * @param name
	 */
	public PIR(String name, ArrayList<Query> queries) {
		super();
		this.queries = queries;
		this.name = name;
	}
	
	/**
	 * @return the users
	 */
	public ArrayList<Query> getQueries() {
		return queries;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Query getQuery(int i) {
		return getQueries().get(i);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		String stringDoc = "";
		for (Query s : queries) {
			stringDoc += s.toString();
		}
		stringDoc += "\nNumber of queries: " + queries.size() + " ]";
		return "PIR [id=" + getName()  + ", queries=" + stringDoc;
	}
	
	
}
