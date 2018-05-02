/**
 * 
 */
package ie.dcu.evalpir.elements;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrea Angiolillo
 *
 *         It represents a system that must be evaluated
 */
public class PIR {

	Map<String, Query> queries;
	String name;

	/**
	 * @param name
	 */
	public PIR(String name) {
		super();
		this.queries = new HashMap<String, Query>();
		this.name = name;
	}

	/**
	 * 
	 * @param p
	 */
	public PIR(PIR p) {
		super();
		this.queries = new HashMap<String, Query>(p.getQueries());
		this.name = p.getName();
	}

	/**
	 * @param users
	 * @param name
	 */
	public PIR(String name, Map<String, Query> queries) {
		super();
		this.queries = queries;
		this.name = name;
	}

	public Map<String, Query> getQueries() {
		return queries;
	}

	public Query getQuery(String i) {
		return getQueries().get(i);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String toString() {
		String stringDoc = "";
		for (Map.Entry<String, Query> entry : getQueries().entrySet()) {
			stringDoc += entry.getValue().toString();
		}

		stringDoc += "\nNumber of queries: " + queries.size() + " ]";
		return "PIR [id=" + getName() + ", queries=" + stringDoc;
	}

}
