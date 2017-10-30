package ie.dcu.evalpir.elements;

import java.util.ArrayList;
import java.util.Map;

public class QueryOutputPIR extends Query {
	
	ArrayList<Pair<String, Double>> measures;

	/**
	 * 
	 */
	public QueryOutputPIR() {
		super();
		measures = new ArrayList<Pair<String, Double>>();
	}

	/**
	 * @param id
	 * @param docs
	 */
	public QueryOutputPIR(String id, Map<String, Document> docs) {
		super(id, docs);
		measures = new ArrayList<Pair<String, Double>>();
	}

	/**
	 * @param id
	 */
	public QueryOutputPIR(String id) {
		super(id);
		measures = new ArrayList<Pair<String, Double>>();
		
	}
	
	public void  addMeasure(String key, Double value) {
		measures.add(new Pair(key, value));
	}
	
	
	public double  getMeasure(String key) {
		for (Pair p : measures) {
			if (p.getKey().equals(key)) {
				return (Double) p.getValue();
			}
		}
		return 0;
	}

	public Pair  getMeasure(int i) {
		return measures.get(i);
	}
	
	
	/**
	 * @return the measures
	 */
	public ArrayList<Pair<String, Double>> getMeasures() {
		return measures;
	}

	@Override
	public String toString() {
		String measure = "";
		
		for (Pair p : measures) {
			measure += p.toString() + ",\n";
		}
		return super.toString() + "\n" + "Measures:\n" + measure;
	}
	
	

}
