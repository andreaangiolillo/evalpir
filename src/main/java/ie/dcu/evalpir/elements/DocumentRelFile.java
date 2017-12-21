/**
 * 
 */
package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 *
 */
public class DocumentRelFile extends Document{
	
	static final int  THRESHOLD = 3; //it is used to set isRelevance
	
	private int relevance;
	private boolean isRelevance;
	
	

	/**
	 * @param id
	 */
	public DocumentRelFile(String id) {
		super(id);
	}	
	
	/**
	 * 
	 * @param doc
	 */
	public DocumentRelFile(DocumentRelFile doc) {
		super(doc.getId());
		this.relevance = doc.relevance;
		this.isRelevance = doc.isRelevance;
	}
	
	/**
	 * @param id
	 * @param relevance
	 */
	public DocumentRelFile(String id, int relevance) {
		super(id);
		this.relevance = relevance;
		setIsRelevance();
	}
	
	


	/**
	 * @return the relevance
	 */
	public int getRelevance() {
		return relevance;
	}

	/**
	 * @param relevance the relevance to set
	 */
	public void setRelevance(int relevance) {
		this.relevance = relevance;
		setIsRelevance();
	
	}

	/**
	 * @return the isRelevance
	 */
	public boolean getIsRelevance() {
		return isRelevance;
	}

	/**
	 * set the IsRelevance according to THRESHOLD
	 */
	public void setIsRelevance() {
		
		if( getRelevance() >= THRESHOLD) {
			this.isRelevance = true;
		}else {
			this.isRelevance = false;
		}
		
		
	}
	
	
	
	@Override
	public String toString() {
		return "Document [id=" + this.getId() + ", relevance=" + relevance + ", isRelevance=" + isRelevance +"]";
	}
}
