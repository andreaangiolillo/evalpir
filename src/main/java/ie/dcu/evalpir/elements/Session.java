package ie.dcu.evalpir.elements;

import java.util.ArrayList;

public class Session{

	private String id;
	private String user;
	private String topic;
	private ArrayList<Log> logs;
	
	/**
	 * @param id
	 * @param user
	 * @param topic
	 */
	public Session(String id, String user, String topic) {
		this.id = id;
		this.user = user;
		this.topic = topic;
		this.logs = new ArrayList<Log>();
	}
	
	/**
	 * @param id
	 * @param user
	 * @param topic
	 * @param logs
	 */
	public Session(String id, String user, String topic, ArrayList<Log> logs) {
		this.id = id;
		this.user = user;
		this.topic = topic;
		this.logs = logs;
	}
	
	public void addId(String id) {
		this.id += ", " + id; 
	}
	
	public void addLog(Log l) {
		getLogs().add(l);
	}
	
	public void removeLog(int i) {
		getLogs().remove(i);
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @return the logs
	 */
	public ArrayList<Log> getLogs() {
		return logs;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String logs = "";
		
		for (Log l : getLogs()) {
			logs += l.toString() + "\n";
		}
		
		return "Session [id=" + id + ", user=" + getUser() + ", topic=" + getTopic() + ", logs=\n" + logs + "]";
	}

	
	
}
