package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 * 
 * It represents a row in the logFile
 */

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

	public String getDocument() {
		return document;
	}

	public String getType() {
		return type;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getQuery() {
		return query;
	}

	public String getRank() {
		return rank;
	}

	public String toString() {
		return "Log [document=" + document + ", type=" + type + ", timestamp=" + timestamp + ", query=" + query
				+ ", rank=" + rank + "]";
	}
	
}
