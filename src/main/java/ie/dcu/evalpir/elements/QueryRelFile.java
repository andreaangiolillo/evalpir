package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 * 
 *         It represents the query in the relevantFile. It contains a Map with all the measures computed for the query.
 * 
 */

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

public class QueryRelFile extends ie.dcu.evalpir.elements.Query {

	private Map<String, AbstractMeasure> measures; // key = measure's name, value = ie.dcu.evalpir.elements.AbstractMeasure
	private boolean mustBeDrawn;

	/***
	 * 
	 */
	public QueryRelFile(String user, String topic, String id) {
		super(user, topic, id);
		this.measures = new HashMap<String, AbstractMeasure>();
		this.mustBeDrawn = false; // it is true if the query is in the systems' files
	}

	/***
	 * 
	 */
	public QueryRelFile(String user, String topic, String id, Map<String, Document> docs) {
		super(user, topic, id, docs);
		this.measures = new HashMap<String, AbstractMeasure>();
		this.mustBeDrawn = false;
	}

	/**
	 * 
	 * @param query
	 */
	public QueryRelFile(QueryRelFile query) {
		super(query.getUser(), query.getTopic(), query.getId(), query.getDocs());
		this.measures = new HashMap<String, AbstractMeasure>(query.getMeasures());
		this.mustBeDrawn = query.isToConsiderForChart();
	}

	/**
	 * @param measures
	 */
	public QueryRelFile(String user, String topic, String id, Map<String, Document> docs,
			Map<String, AbstractMeasure> measures) {
		super(user, topic, id, docs);
		this.measures = measures;
		this.mustBeDrawn = false;
	}

	/**
	 * It adds a measure in measures
	 * 
	 * @param m
	 */
	public void addMeasure(AbstractMeasure m, boolean printOutput, boolean drawable) {
		m.setPrintOutput(printOutput);
		m.setMustBeDrawn(drawable);
		getMeasures().put(m.getName().trim().toLowerCase(), m);
	}

	/**
	 * It remove a measure from measures
	 */
	public void removeMeasure(String name) {
		getMeasures().remove(name.trim().toLowerCase());
	}

	/**
	 * It search a measure. In case of it doesn't find the measure, it create new measure.
	 * 
	 * @param name
	 * @return
	 */
	public AbstractMeasure searchAddMeasure(String name, boolean compound, boolean printOutput, boolean drawable) {
		if (getMeasures().get(name.trim().toLowerCase()) == null) {
			if (compound) {
				addMeasure(new MeasureCompound(name.trim()), printOutput, drawable);
			} else {
				addMeasure(new Measure(name.trim()), printOutput, drawable);
			}
		}

		return getMeasures().get(name.trim().toLowerCase());
	}

	/***
	 * it search the measure
	 * 
	 * @param name
	 * @return
	 */
	public AbstractMeasure searchMeasure(String name) {
		return getMeasures().get(name.trim().toLowerCase());
	}

	/**
	 * @return the mustBeDrawn
	 */
	public boolean isToConsiderForChart() {
		return mustBeDrawn;
	}

	/**
	 * @param
	 */
	public void setToConsiderForChart(boolean toConsiderForChart) {
		this.mustBeDrawn = toConsiderForChart;
	}

	/**
	 * @return the measures
	 */
	public Map<String, AbstractMeasure> getMeasures() {
		return measures;
	}

	/**
	 * This method return the number of relevant documents of this query.
	 * 
	 * @return nRelDoc
	 */
	public int getNRelevantDoc() {
		int nRelDoc = 0;
		boolean instance = true;
		for (Map.Entry<String, Document> entryDoc : this.getDocs().entrySet()) {
			if (!(entryDoc.getValue() instanceof DocumentRelFile)) {
				instance = false;
			}
			nRelDoc += (instance && ((DocumentRelFile) entryDoc.getValue()).getIsRelevance()) ? 1 : 0;
		}

		return nRelDoc;
	}

	/**
	 * It computes the sorting of the measures by key (Measures' names)
	 */
	public TreeMap<String, AbstractMeasure> sortMeasureForKey() {
		TreeMap<String, AbstractMeasure> measureSorted = new TreeMap<String, AbstractMeasure>(getMeasures());
		return measureSorted;
	}

	/**
	 * It returns a list contains all the relevant documents
	 * 
	 * @return
	 */
	public Map<String, Document> getRelevantDocs() {
		Map<String, Document> docs = getDocs();
		Map<String, Document> relDocs = new HashMap<String, Document>();
		for (Map.Entry<String, Document> entryDoc : docs.entrySet()) {
			if (((DocumentRelFile) entryDoc.getValue()).getIsRelevance()) {
				relDocs.put(entryDoc.getKey(), entryDoc.getValue());
			}
		}

		return relDocs;
	}

	/**
	 * It prints the measures using the library AsciiTable
	 * 
	 * @see AsciiTable
	 */
	public String printMeasures() {
		String stringDoc = "";
		AsciiTable tb = new AsciiTable();
		CWC_LongestLine cwc = new CWC_LongestLine();
		tb.getRenderer().setCWC(cwc);
		tb.addRule();
		tb.addRow("User: " + getUser(), "Topic: " + getTopic(), "Query: " + getId(), "S1 - Si", "(Si-1) - Si");
		tb.addRule();
		for (Map.Entry<String, AbstractMeasure> entryMeasure : sortMeasureForKey().entrySet()) {
			if (entryMeasure.getValue().isPrintOutput()) {
				stringDoc = ((Measure) entryMeasure.getValue()).printMeasure(tb) + "\n";
			}
		}

		return stringDoc;
	}
}
