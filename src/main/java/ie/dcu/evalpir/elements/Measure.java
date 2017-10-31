package ie.dcu.evalpir.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.vandermeer.asciitable.AsciiTable;

public class Measure {
	private String name;
	private ArrayList<Pair<String, Double>> PIRvalue;
	
	
	
	
	/**
	 * 
	 */
	public Measure(String name) {
		this.name = name;
		this.PIRvalue = new ArrayList<Pair<String,Double>>();
	}

	/**
	 * @param name
	 * @param value
	 */
	public Measure(String name, ArrayList<Pair<String, Double>> value) {
		super();
		this.name = name;
		this.PIRvalue = value;
	}
	
	
	public void  addPIR(String key, Double value) {
		this.PIRvalue.add(new Pair(key, value));
	}
	
	
	public double  getPIR(String key) {
		for (Pair p : PIRvalue) {
			if (p.getKey().equals(key)) {
				return (Double) p.getValue();
			}
		}
		return 0;
	}

	public Pair getPIR(int i) {
		return PIRvalue.get(i);
	}
		
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}




	/**
	 * @return the pIRvalue
	 */
	public ArrayList<Pair<String, Double>> getPIRvalue() {
		return PIRvalue;
	}

	/**
	 * 
	 */
	public void sort() {
		Collections.sort(getPIRvalue(), new Comparator<Pair<String, Double>>(){
			public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().compareTo(o2.getValue());
			}
		});
	}
	
	
	
	/**
	 * 
	 */
	public String printMeasure(String user, String topic) {
		sort();
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow(getName(),"User: " + user,"Topic: " + topic,"");
		at.addRule();
		at.addRow("System", "Value", "---", "---");
		at.addRule();
		ArrayList<Pair<String, Double>> m = getPIRvalue();
		int nMeasure = m.size();
		
		for (int i = 0; i < nMeasure; i++) {
			if (i == 0) {
				at.addRow(m.get(i).getKey(), m.get(i).getValue(), "---", "---");
			}else {
				at.addRow(m.get(i).getKey(), m.get(i).getValue(),m.get(0).getValue() - m.get(i).getValue(), m.get(i - 1).getValue() - m.get(i).getValue());
			}
	
			at.addRule();
		}
		
		return at.render();
	}

	
}
