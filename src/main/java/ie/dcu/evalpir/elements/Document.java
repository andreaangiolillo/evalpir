package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 *
 *         This class defines the common properties of the measures
 *
 */
public abstract class Document {

	private String id;

	public Document(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
