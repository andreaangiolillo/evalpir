package ie.dcu.evalpir.elements;

public class Pair implements Comparable<Pair>{
	
	private int key;
	private double value;
	/**
	 * @param key
	 * @param value
	 */
	public Pair(int key, double value) {
		super();
		this.key = key;
		this.value = value;
	}
	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	public int compareTo(Pair o) {
		
		if (this.key < o.getKey()) {
		      return -1;
		    } else if (this.key > o.getKey()) {
		      return 1;
		    }
		return -1;
	}
	
	@Override
	public String toString() {
		return "Pair [key=" + key + ", value=" + value + "]";
	}
	
	
	

}
