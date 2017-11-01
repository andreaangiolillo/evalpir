package ie.dcu.evalpir.elements;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueryRelFile extends ie.dcu.evalpir.elements.Query{
	
	private Map<String, Measure> measures;
	
	/***
	 * 
	 */
	public QueryRelFile(String user, String topic, String id) {
		super(user, topic, id);
		this.measures = new HashMap<String, Measure>();
	}

	/***
	 * 
	 */
	public QueryRelFile(String user, String topic, String id, Map<String, Document> docs) {
		super(user, topic, id, docs);
		this.measures = new HashMap<String, Measure>();
	}


	/**
	 * @param measures
	 */
	public QueryRelFile(String user, String topic, String id, Map<String, Document> docs, HashMap<String, Measure> measures) {
		super(user, topic, id, docs);
		this.measures = measures;
	}
	
	/**
	 * 
	 * @param m
	 */
	public void addMeasure(Measure m) {
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
	public Measure searchMeasure(String name) {
		return getMeasures().get(name.trim().toLowerCase());
	}

	

	/**
	 * @return the measures
	 */
	public Map<String, Measure> getMeasures() {
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
	 * @return
	 */
	public String printMeasures() {
		String stringDoc = "";
		Iterator<?> it = getMeasures().entrySet().iterator();
		
		while(it.hasNext()) {
			Map.Entry<?,?> pair = (Map.Entry<?,?>)it.next();
			stringDoc += ((Measure)pair.getValue()).printMeasure(getUser(), getTopic(), getId()) + "\n";
		}
		
		return stringDoc;
	}
	

	
	

}
