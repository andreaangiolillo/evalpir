package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 *
 */
public abstract class Document {
	
	private String id;
	
	

	/**
	 * @param id
	 */
	public Document(String id) {
		super();
		this.id = id;
	}




	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}	
	
}
