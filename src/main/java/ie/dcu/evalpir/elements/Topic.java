package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 * 
 *         It represents the queries in the same user-topic contained in the relevant file.
 *         It contains a Map with all the measures computed for the query.
 * 
 */

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

public class Topic {
	private String userId;
	private String topicId;
	private Map<String, AbstractMeasure> measures;
	private Map<String, Query> queries;

	/**
	 * 
	 * @param userId
	 * @param id
	 * @param measures
	 * @param queries
	 */
	public Topic(String userId, String id, Map<String, AbstractMeasure> measures, Map<String, Query> queries) {
		this.userId = userId;
		this.topicId = id;
		this.measures = measures;
		this.queries = queries;
	}

	/**
	 * 
	 * @param userId
	 * @param id
	 */
	public Topic(String userId, String id) {
		this.userId = userId;
		this.topicId = id;
		this.measures = new HashMap<String, AbstractMeasure>();
		this.queries = new HashMap<String, Query>();
	}

	/**
	 * 
	 * @param userId
	 * @param id
	 * @param queries
	 */
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

	public Query getQuery(String key) {
		return getQuery(key);
	}

	public Query getQuery(Query q) {
		return getQuery(q.getId());
	}

	/**
	 * It add a new measure
	 * 
	 * @param m
	 */
	public void addMeasure(AbstractMeasure m, boolean printOutput, boolean drawable) {
		m.setPrintOutput(printOutput);
		m.setMustBeDrawn(drawable);
		getMeasures().put(m.getName().trim().toLowerCase(), m);
	}

	/**
	 * It remove a measure
	 */
	public void removeMeasure(String name) {
		getMeasures().remove(name.trim().toLowerCase());
	}

	/**
	 * It search a measure. In case of it doesn't find the measure, it create a new measure.
	 * 
	 * @param name
	 * @return
	 */
	public AbstractMeasure searchAddMeasure(String name, boolean compound, boolean printOutput, boolean drawable) {
		if (searchMeasure(name) == null) {
			if (compound) {
				addMeasure(new MeasureCompound(name.trim()), printOutput, drawable);
			} else {
				addMeasure(new Measure(name.trim()), printOutput, drawable);
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
	 * It computes the sorting of the measures by key (Measures' names)
	 */
	public TreeMap<String, AbstractMeasure> sortMeasureForKey() {
		TreeMap<String, AbstractMeasure> measureSorted = new TreeMap<String, AbstractMeasure>(getMeasures());
		return measureSorted;

	}

	public String getUserId() {
		return userId;
	}

	public String getTopicId() {
		return topicId;
	}

	/**
	 * It prints the measures using the library AsciiTable
	 * 
	 * @see AsciiTable
	 * @return
	 */
	public String printMeasures() {
		String stringDoc = "";
		AsciiTable tb = new AsciiTable();
		CWC_LongestLine cwc = new CWC_LongestLine();
		tb.getRenderer().setCWC(cwc);
		tb.addRule();
		tb.addRow("User: " + getUserId(), "Topic: " + getTopicId(), "", "S1 - Si", "(Si-1) - Si");
		tb.addRule();
		for (Map.Entry<String, AbstractMeasure> entryMeasure : sortMeasureForKey().entrySet()) {
			if (entryMeasure.getValue().isPrintOutput()) {
				stringDoc = ((Measure) entryMeasure.getValue()).printMeasure(tb) + "\n";
			}
		}

		return stringDoc;
	}
}
