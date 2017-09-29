/**
 * 
 */
package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 *
 */
public class DocumentOutputPIR extends Document{
	
	
	
	private int rank;
	private double similarity;
	
	
	
	/**
	 * @param id
	 */
	public DocumentOutputPIR(String id) {
		super(id);
	}



	/**
	 * @param rank
	 * @param similarity
	 */
	public DocumentOutputPIR(String id, int rank, double similarity) {
		super(id);
		this.rank = rank;
		this.similarity = similarity;
	}

	

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}


	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}


	/**
	 * @return the similarity
	 */
	public double getSimilarity() {
		return similarity;
	}


	/**
	 * @param similarity the similarity to set
	 */
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
	
	
	@Override
	public String toString() {
		return "Document [id=" + this.getId() + ", rank=" + rank + ", similarity=" + similarity
				+"]";
	}
	
	
	
}
