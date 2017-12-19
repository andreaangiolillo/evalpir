package ie.dcu.evalpir.elements;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

public class QueryRelFile extends ie.dcu.evalpir.elements.Query{
	
	private Map<String, AbstractMeasure> measures; // key = measureName, value = Measure
	private boolean mustBeDrawn;
	
	/***
	 * 
	 */
	public QueryRelFile(String user, String topic, String id) {
		super(user, topic, id);
		this.measures = new HashMap<String, AbstractMeasure>();
		this.mustBeDrawn = false;
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
	public QueryRelFile(String user, String topic, String id, Map<String, Document> docs, Map<String, AbstractMeasure> measures) {
		super(user, topic, id, docs);
		this.measures = measures;
		this.mustBeDrawn = false;
	}
	
	/**
	 * 
	 * @param m
	 */
	public void addMeasure(AbstractMeasure m, boolean drawable) {
		m.setMustBeDrawn(drawable);
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
	public AbstractMeasure searchAddMeasure(String name, boolean compound, boolean drawable) {
		
		if(getMeasures().get(name.trim().toLowerCase()) == null) {
			if(compound) {
				addMeasure(new MeasureCompound(name.trim()), drawable);
			}else {
				addMeasure(new Measure(name.trim()), drawable);
			}
		}
		
		
		return getMeasures().get(name.trim().toLowerCase());
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
	 * This method return the number of relevant documents of this query. If the query contains DocumentOutputPIR object return 0.
	 * @return nRelDoc
	 * */
	public int getNRelevantDoc() {
		int nRelDoc = 0;
		Iterator<?> it = this.getDocs().entrySet().iterator();
		boolean instance = true;
		while(it.hasNext() && instance) {
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			if (!(pair.getValue() instanceof DocumentRelFile)) {
				instance = false;
			}
			nRelDoc += (instance && ((DocumentRelFile)pair.getValue()).getIsRelevance()) ? 1 : 0;
			
		}
		
		return nRelDoc;
	}
	
	/**
	 * 
	 */
	public TreeMap<String, AbstractMeasure>  sortMeasureForKey() {
		TreeMap<String, AbstractMeasure> measureSorted = new TreeMap<String, AbstractMeasure>(getMeasures());
		return measureSorted;

		
	}
	
	public Map<String, Document>getRelevantDocs(){
		Map<String, Document> docs = getDocs();
		Map<String, Document> relDocs = new HashMap<String, Document>();
		
		Iterator<Entry<String, Document>> itDocs = docs.entrySet().iterator();
		Entry<String, Document> entryDoc;
		while(itDocs.hasNext()){
			entryDoc = itDocs.next();
			if(((DocumentRelFile)entryDoc.getValue()).getIsRelevance()) {
				relDocs.put(entryDoc.getKey(), entryDoc.getValue());
			}
		}
		
		return relDocs;
	}

	/**
	 * 
	 * @return
	 */
	public String printMeasures() {
		String stringDoc = "";
		Iterator<?> it = sortMeasureForKey().entrySet().iterator();
		AsciiTable tb = new AsciiTable();
		CWC_LongestLine cwc = new CWC_LongestLine();
		tb.getRenderer().setCWC(cwc);
		tb.addRule();
		tb.addRow("User: " + getUser(), "Topic: " + getTopic(), "Query: " + getId(), "S1 - Si", "(Si-1) - Si");
		tb.addRule();
		while(it.hasNext()) {
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			if(pair.getValue() instanceof Measure) {
				stringDoc = ((Measure)pair.getValue()).printMeasure(tb) + "\n";
			}
			
		}
		
		System.out.print(stringDoc);
		return stringDoc;
	}
	

	
	

}
