/**
 * 
 */
package ie.dcu.evalpir.elements;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Andrea Angiolillo	
 *
 */
public class PIR {

	
	Map<String, Query> queries;
	String name;
	
	/**
	 * @param name
	 */
	public PIR(String name) {
		super();
		this.queries = new HashMap<String, Query >();
		this.name = name;
	}
	
	/*@param PIR
	 * 
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
	public PIR(String name, Map<String, Query>queries) {
		super();
		this.queries = queries;
		this.name = name;
	}
	
	/**
	 * @return the users
	 */
	public Map<String, Query> getQueries() {
		return queries;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public Query getQuery(String i) {
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
		Iterator<Entry<String, Query>> it = getQueries().entrySet().iterator();
		while (it.hasNext()) {
			stringDoc += it.next().getValue().toString();
		}
		stringDoc += "\nNumber of queries: " + queries.size() + " ]";
		return "PIR [id=" + getName()  + ", queries=" + stringDoc;
	}
	
	
}
