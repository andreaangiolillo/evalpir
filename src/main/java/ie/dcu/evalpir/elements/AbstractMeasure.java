package ie.dcu.evalpir.elements;

public abstract class AbstractMeasure {

	private String name;
	private boolean drawable;

	
	/**
	 * @param name
	 */
	public AbstractMeasure(String name) {
		super();
		this.name = name;
		this.drawable = false;
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

	public boolean mustBeDrawn() {
		return drawable;
	}

	public void setMustBeDrawn(boolean mustBeDrawn) {
		this.drawable = mustBeDrawn;
	}
	
	
	
}
