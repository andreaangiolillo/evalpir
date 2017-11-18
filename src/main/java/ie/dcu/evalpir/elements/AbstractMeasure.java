package ie.dcu.evalpir.elements;

public abstract class AbstractMeasure {

	private String name;

	
	/**
	 * @param name
	 */
	public AbstractMeasure(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
