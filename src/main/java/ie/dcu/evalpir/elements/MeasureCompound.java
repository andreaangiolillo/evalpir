package ie.dcu.evalpir.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Andrea Angiolillo
 *
 *This class represents a measure made up of a set of values.
 * @param <K>
 * @param <T>
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

	public void  addPIR(String key, ArrayList<Pair<Integer, Double>> value) {
		this.PIRvalue.put(key, value);
	}
	
	
	public ArrayList<Pair<Integer, Double>> getPIR(String key) {
		return PIRvalue.get(key);
	}
	
	
	
	
	/**
	 * @return the pIRvalue
	 */
	public Map<String, ArrayList<Pair<Integer, Double>>> getPIRvalue() {
		return PIRvalue;
	}

	/**
	 * @param pIRvalue the pIRvalue to set
	 */
	public void setPIRvalue(Map<String, ArrayList<Pair<Integer, Double>>> pIRvalue) {
		PIRvalue = pIRvalue;
	}

	public int compareTo(Pair<Integer, Double> o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
