/**
 * 
 */
package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 *
 *This class represents the documents in the file with the systems' output 
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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}
		
	public String toString() {
		return "Document [id=" + this.getId() + ", rank=" + rank + ", similarity=" + similarity
				+"]";
	}
	
}
