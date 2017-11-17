package ie.dcu.evalpir.elements;

public class Pair<K extends Comparable<K>,T> implements Comparable<Pair<K,T>>{
	
	private K key;
	private T value;
	/**
	 * @param key
	 * @param value
	 */
	public Pair(K key, T value) {
		super();
		this.key = key;
		this.value = value;
	}
	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(K key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}
	public int compareTo(Pair<K,T> o) {
		
		if (this.key.compareTo(o.getKey()) < 0) {
		      return -1;
		    } else if (this.key.compareTo(o.getKey()) > 0) {
		      return 1;
		    }
		return -1;
	}
	
	@Override
	public String toString() {
		return "Pair [key=" + key + ", value=" + value + "]";
	}
	
	
	

}
