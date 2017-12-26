package ie.dcu.evalpir.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ie.dcu.evalpir.utilities.Pair;

/**
 * @author Andrea Angiolillo
 *
 *This class represents a measure made up of a set of values.
 *
 */
public class MeasureCompound extends AbstractMeasure {
	 
	private Map<String, ArrayList<Pair<Integer,Double>>> PIRvalue;
	
	public MeasureCompound(String name) {
		super(name);
		this.PIRvalue = new HashMap<String, ArrayList<Pair<Integer,Double>>>();
	}
	
	public MeasureCompound(String name, Map<String, ArrayList<Pair<Integer,Double>>> value) {
		super(name);
		this.PIRvalue = value;
	}

	/**
	 * @return the value
	 */
	public Map<String, ArrayList<Pair<Integer,Double>>> getValue() {
		return PIRvalue;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Map<String, ArrayList<Pair<Integer,Double>>>value) {
		this.PIRvalue = value;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void  addPIR(String key, ArrayList<Pair<Integer, Double>> value) {
		this.PIRvalue.put(key, value);
	}
	
	public ArrayList<Pair<Integer, Double>> getPIR(String key) {
		return PIRvalue.get(key);
	}
	
	/**
	 * it sorted the pirValue by key creating a TreeMap
	 * @return
	 */
	public Map<String, ArrayList<Pair<Integer,Double>>> getPIRvalueSortedByKey() {
		Map<String, ArrayList<Pair<Integer,Double>>> map = new TreeMap<String, ArrayList<Pair<Integer,Double>>>(PIRvalue);
		return map;
	}
	
	public Map<String, ArrayList<Pair<Integer, Double>>> getPIRvalue() {
		return PIRvalue;
	}

	public void setPIRvalue(Map<String, ArrayList<Pair<Integer, Double>>> value) {
		PIRvalue = value;
	}
	
}
