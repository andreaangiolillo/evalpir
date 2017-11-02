package ie.dcu.evalpir.elements;

public class Log {

	private String document;
	private String type;
	private String timestamp;
	private String rank;
	
	/**
	 * @param document
	 * @param type
	 * @param timestamp
	 * @param rank
	 */
	public Log(String type, String timestamp, String document,  String rank) {
		super();
		this.document = document;
		this.type = type;
		this.timestamp = timestamp;
		this.rank = rank;
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Log [document=" + document + ", type=" + type + ", timestamp=" + timestamp + ", rank=" + rank + "]";
	}
	
	
	
	
}
