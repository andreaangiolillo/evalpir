package ie.dcu.evalpir.elements;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.vandermeer.asciitable.AsciiTable;

public class Measure extends AbstractMeasure{
	private ArrayList<Pair<String, Double>> PIRvalue;	
	/**
	 * 
	 */
	public Measure(String name) {
		super(name);
		this.PIRvalue = new ArrayList<Pair<String,Double>>();
	}

	/**
	 * @param name
	 * @param value
	 */
	public Measure(String name, ArrayList<Pair<String, Double>> value) {
		super(name);
		this.PIRvalue = value;
	}
	
	
	public void  addPIR(String key, Double value) {
		this.PIRvalue.add(new Pair<String, Double>(key, value));
	}
	
	
	public double  getPIR(String key) {
		for (Pair<String, Double> p : PIRvalue) {
			if (p.getKey().equals(key)) {
				return (Double) p.getValue();
			}
		}
		return 0;
	}

	public Pair<String, Double> getPIR(int i) {
		return PIRvalue.get(i);
	}
		
	/**
	 * @return the name
	 */
	public String getName() {
		return super.getName();
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
				return -o1.getValue().compareTo(o2.getValue());
			}
		});
	}
	
	
	
	/**
	 * 
	 */
	public String printMeasure(String user, String topic, String query) {
		sort();
		AsciiTable at = new AsciiTable();
		at.addRule();
		at.addRow(getName(),"User: " + user,"Topic: " + topic,"Query: " + query );
		at.addRule();
		at.addRow("System", "Value", "---", "---");
		at.addRule();
		ArrayList<Pair<String, Double>> m = getPIRvalue();
		int nMeasure = m.size();
		DecimalFormat df = new DecimalFormat("#.######");
		df.setRoundingMode(RoundingMode.CEILING);

		
		for (int i = 0; i < nMeasure; i++) {
			if (i == 0) {
				at.addRow(m.get(i).getKey(), df.format(m.get(i).getValue()), "---", "---");
			}else {
				at.addRow(m.get(i).getKey(), df.format(m.get(i).getValue()), df.format(m.get(0).getValue() - m.get(i).getValue()), df.format(m.get(i - 1).getValue() - m.get(i).getValue()));
			}
	
			at.addRule();
		}
		
		return at.render();
	}

	
}
