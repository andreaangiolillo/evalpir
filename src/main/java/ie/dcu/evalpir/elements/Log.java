package ie.dcu.evalpir.elements;

import java.sql.Timestamp;

public class Log {

	private String document;
	private String type;
	private Timestamp timestamp;
	private String query;
	private String rank;
	
	/**
	 * @param document
	 * @param type
	 * @param timestamp
	 * @param rank
	 */
	public Log(String type, String timestamp, String document, String query, String rank) {
		super();
		this.document = document;
		this.type = type;
		this.timestamp = java.sql.Timestamp.valueOf(timestamp);
		this.rank = rank;
		this.query = query;
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
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}


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
		return "Log [document=" + document + ", type=" + type + ", timestamp=" + timestamp + ", query=" + query
				+ ", rank=" + rank + "]";
	}


	
	
	
	
}
