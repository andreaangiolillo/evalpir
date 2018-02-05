package ie.dcu.evalpir.elements;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.vandermeer.asciitable.AsciiTable;

public class Measure extends AbstractMeasure{
	private ArrayList<Pair<String, Double>> PIRValue;	
	
	/**
	 * @param name
	 */
	public Measure(String name) {
		super(name);
		this.PIRValue = new ArrayList<Pair<String,Double>>();
	}

	/**
	 * @param name
	 * @param value
	 */
	public Measure(String name, ArrayList<Pair<String, Double>> value) {
		super(name);
		this.PIRValue = value;
	}
	
	/**
	 * It adds a new pair <System's name, measure's value>
	 * @param key
	 * @param value
	 */
	public void  addPIR(String key, Double value) {
		this.PIRValue.add(new Pair<String, Double>(key, value));
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public double  getPIRValue(String key) {
		for (Pair<String, Double> p : PIRValue) {
			if (p.getKey().equals(key)) {
				return (Double) p.getValue();
			}
		}
		return 0;
	}

	public Pair<String, Double> getPIR(int i) {
		return PIRValue.get(i);
	}
	
	public boolean containsPIR(String key) {
		for (Pair<String, Double> p : PIRValue) {
			if (p.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}
		
	public String getName() {
		return super.getName();
	}

	public ArrayList<Pair<String, Double>> getPIRvalue() {
		return PIRValue;
	}
	
	/**
	 * It returns the PIRvalue sorted by key(System's name)
	 * @return
	 */
	public ArrayList<Pair<String, Double>> getPIRvalueSortedByKey() {
		sortbyKey();
		return PIRValue;
	}
	
	/**
	 * It returns the PIRvalue sorted by value(measure's value)
	 * @return
	 */
	public ArrayList<Pair<String, Double>> getPIRvalueSortedByValue() {
		sortbyValue();
		return PIRValue;
	}

	/**
	 * it computes the sorting on PIRvalue by value 
	 */
	public void sortbyValue() {
		Collections.sort(getPIRvalue(), new Comparator<Pair<String, Double>>(){
			public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
				// TODO Auto-generated method stub
				return -o1.getValue().compareTo(o2.getValue());
			}
		});
	}
	
	/**
	 * it computes the sorting on PIRvalue by value 
	 */
	public void sortbyKey() {
		Collections.sort(getPIRvalue(), new Comparator<Pair<String, Double>>(){
			public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
				// TODO Auto-generated method stub
				return o1.getKey().compareTo(o2.getKey());
			}
		});
	}
	
	/**
	 * It prints the measure's value using the library AsciiTable
	 * @input Asciitable at
	 * @see AsciiTable
	 */
	public String printMeasure(AsciiTable at) {
		sortbyValue();
		ArrayList<Pair<String, Double>> m = getPIRvalue();
		int nMeasure = m.size();
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		for (int i = 0; i < nMeasure; i++) {
			if (i == 0) {
				at.addRow(getName(), m.get(i).getKey(), df.format(m.get(i).getValue()), "---", "---");
			}else {
				at.addRow("", m.get(i).getKey(), df.format(m.get(i).getValue()), df.format(m.get(0).getValue() - m.get(i).getValue()), df.format(m.get(i - 1).getValue() - m.get(i).getValue()));
			}
	
			at.addRule();
		}
		
		return at.render();
	}

	
}
