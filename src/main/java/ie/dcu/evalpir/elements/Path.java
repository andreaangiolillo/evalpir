package ie.dcu.evalpir.elements;

import java.util.ArrayList;

public class Path {

	private ArrayList<Integer> path;

	/**
	 * @param path
	 */
	public Path() {
		super();
		this.path = new ArrayList<Integer>();
	}

	/**
	 * @param path
	 */
	public Path(ArrayList<Integer> path) {
		super();
		this.path = path;
	}

	
	public void addElement(int i) {
		path.add(i);
	}
	
	public void removeElement(int i) {
		path.remove(i);
	}
	
	@Override
	public String toString() {
		String output = "(";
		for (Integer i : path) {
			output += i.toString() + ", ";
		}
		
		return output + ")";
	}
	
	
	
	
}
