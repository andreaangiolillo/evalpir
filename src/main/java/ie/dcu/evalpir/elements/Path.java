package ie.dcu.evalpir.elements;

import java.util.ArrayList;
public class Path {

	private ArrayList<Integer> path;
	private int k;
	
	/**
	 * @param path
	 */
	public Path(int j) {
		super();
		path = new ArrayList<Integer>();
		for (int i = 0; i < j; i++) {
			getPath().add(1);
		}
		
		k = j;
	}
	
	public Path() {
		path = new ArrayList<Integer>();
	}

	public Path(Path another) {
		path = new ArrayList<Integer>(another.getPath());
	}

	
	/**
	 * @return the path
	 */
	public ArrayList<Integer> getPath() {
		return path;
	}

	public boolean contains(int i) {
		return getPath().contains(i);
	}
	
	public void addColumn() {
		getPath().add(1);
		k++;
	}
	
	public void addColumn(int value) {
		getPath().add(value);
		k += value;
	}
	
	public int getColumn(int i) {
		return (int) getPath().get(i);
	}
	
	public void increaseColumn(int i) {
		int value = getPath().get(i);
		getPath().set(i, value + 1);
		k++;
	}
	
	public void decreaseColumn(int i) {
		int value = getPath().get(i);
		getPath().set(i, value - 1);	
		k--;
	}
	
	
	public void setColumn(int i, int value) {
		k = k - getColumn(i);
		k = k + value;
		getPath().set(i, value);
	}
	
	
	/**
	 * @return the k
	 */
	public int getK() {
		return k;
	}

	public int size() {
		return getPath().size();
	}
	
	
	public String toString() {
		String output = "(";
		int lenght = path.size();
		for (int i = 0; i<lenght; i++) {		
			output += (i != 0) ? ", " + path.get(i) : path.get(i);
		}
		
		return output + ")";
	}
	
	
	
}
