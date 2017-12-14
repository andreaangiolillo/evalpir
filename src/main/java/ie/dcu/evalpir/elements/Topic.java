package ie.dcu.evalpir.elements;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

public class Topic{
	private String userId;
	private String topicId;
	private Map<String, AbstractMeasure> measures;
	private Map<String, Query> queries;
	
	
	public Topic(String userId, String id, Map<String, AbstractMeasure> measures, Map<String, Query> queries) {
		this.userId = userId;
		this.topicId = id;
		this.measures = measures;
		this.queries = queries;
	}
	
	public Topic(String userId, String id) {
		this.userId = userId;
		this.topicId = id;
		this.measures = new HashMap<String, AbstractMeasure>();
		this.queries = new HashMap<String, Query>();
	}
	
	public Topic(String userId, String id, Map<String, Query> queries) {
		this.userId = userId;
		this.topicId = id;
		this.measures = new HashMap<String, AbstractMeasure>();
		this.queries = queries;
	}

	public Map<String, AbstractMeasure> getMeasures() {
		return measures;
	}

	public void setMeasures(Map<String, AbstractMeasure> measures) {
		this.measures = measures;
	}

	public Map<String, Query> getQueries() {
		return queries;
	}

	public void setQueries(Map<String, Query> queries) {
		this.queries = queries;
	}
	
	public void addQuery(Query q) {
		getQueries().put(q.getId(), q);
	}
	
	public Query getQuery(String key){
		return getQuery(key);
	}
	
	public Query getQuery(Query q){
		return getQuery(q.getId());
	}
	
	/**
	 * 
	 * @param m
	 */
	public void addMeasure(AbstractMeasure m) {
		getMeasures().put(m.getName().trim().toLowerCase(), m);
	}
	

	/**
	 * 
	 */
	public void removeMeasure(String name) {
		getMeasures().remove(name.trim().toLowerCase());
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public AbstractMeasure searchAddMeasure(String name, boolean compound) {
		if(searchMeasure(name) == null) {
			if(compound) {
				addMeasure(new MeasureCompound(name.trim()));
			}else {
				addMeasure(new Measure(name.trim()));
			}
		}
		
		
		return searchMeasure(name);
	}
	
	
	/***
	 * 
	 * @param name
	 * @return
	 */
	public AbstractMeasure searchMeasure(String name) {
		return getMeasures().get(name.trim().toLowerCase());
	}
	
	/**
	 * 
	 */
	public TreeMap<String, AbstractMeasure>  sortMeasureForKey() {
		TreeMap<String, AbstractMeasure> measureSorted = new TreeMap<String, AbstractMeasure>(getMeasures());
		return measureSorted;

		
	}
	
	public String printMeasures() {
		
		String stringDoc = "";
		Iterator<?> it = sortMeasureForKey().entrySet().iterator();
		AsciiTable tb = new AsciiTable();
		CWC_LongestLine cwc = new CWC_LongestLine();
		tb.getRenderer().setCWC(cwc);
		tb.addRule();
		tb.addRow("User: " + getUserId(), "Topic: " + getTopicId(), "", "S1 - Si", "(Si-1) - Si");
		tb.addRule();
		while(it.hasNext()) {
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			if(pair.getValue() instanceof Measure) {
				stringDoc = ((Measure)pair.getValue()).printMeasure(tb) + "\n";
			}
			
		}
		
		System.out.println(stringDoc);
		return stringDoc;
	}
	

	public String getUserId() {
		return userId;
	}

	public String getTopicId() {
		return topicId;
	}
	
	
	
}
