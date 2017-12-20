package ie.dcu.evalpir.elements;

public abstract class AbstractMeasure {

	private String name;
	private boolean drawable;
	private boolean stackedBar; // if it is true the measure is used to create a stack bar 
	private boolean printOutput; // if it is true the measure is printed in the output.txt

	
	/**
	 * @param name
	 */
	public AbstractMeasure(String name) {
		super();
		this.name = name;
		this.drawable = false;
		this.stackedBar = false;
		this.printOutput = false;
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

	public boolean isDrawable() {
		return drawable;
	}

	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}

	public boolean isStackedBar() {
		return stackedBar;
	}

	public void setStackedBar(boolean stackedBar) {
		this.stackedBar = stackedBar;
	}

	public boolean isPrintOutput() {
		return printOutput;
	}

	public void setPrintOutput(boolean printOutput) {
		this.printOutput = printOutput;
	}
	
	
	
}
