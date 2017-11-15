package ie.dcu.evalpir.elements;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Session{
	
	private static final String QUERY_SUBMISSION = "QUERY_SUBMISSION";
	private static final String OPEN_DOCUMENT = "OPEN_DOCUMENT";
	


	private String id;
	private String user;
	private String topic;
	private ArrayList<Log> logs;
	private ArrayList<String> query;
	private ArrayList<Log> docOpened;
	private boolean sessionMeasure;
	
	
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
		this.query = new ArrayList<String>();
		this.docOpened = new ArrayList<Log>();
		this.sessionMeasure = false;
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
		this.query = new ArrayList<String>();
		this.docOpened = new ArrayList<Log>();
		this.sessionMeasure = false;
	}
	
	public void addId(String id) {
		this.id += ", " + id; 
	}
	
	public void addLog(Log l) {
		if(l.getType().equalsIgnoreCase(QUERY_SUBMISSION)) {
			query.add(l.getQuery()); 
		}else if(l.getType().equalsIgnoreCase(OPEN_DOCUMENT)) {
			docOpened.add(l);
		}
		
		getLogs().add(l);
		setSessionMeasure();
	}
	
	public void removeLog(int i) {
		getLogs().remove(i);
	}

	
	/**
	 * @return the sessionMeasure
	 */
	public boolean getSessionMeasure() {
		return sessionMeasure;
	}

	/**
	 * @param sessionMeasure the sessionMeasure to set
	 */
	private void setSessionMeasure() {
		if(docOpened.size() == 0 || query.size() == 0 || query.size() == 1) {
			sessionMeasure = false;
		}else if(docOpened.size() >= query.size() || docOpened.size() >= query.size()/2) {
			sessionMeasure = true;
		}else {
			sessionMeasure = false;
		}
	}

	
	/**
	 * @param query
	 * @return
	 */
	public int setMaxK(ArrayList<Query> query) {
		ArrayList<Log> logs = getnDocOpened();
		int k = 0;
		int rank = 0;
		
		for (Log l : logs) {
			rank = Integer.parseInt(l.getRank());
			if (rank > k){
				k = rank;
			}
		}
		
		return k;
	}
	
	/**
	 * @param query
	 * @return
	 */
	public int setAverageK(ArrayList<Query> query) {
		ArrayList<Log> logs = getnDocOpened();
		int k = 0;
		int rank = 0;
		Map<String, Integer> maxRankQuery = new Hashtable<String, Integer>();
		for (Log l : logs) {
			rank = Integer.parseInt(l.getRank());
			if(!maxRankQuery.containsKey(l.getQuery())) {
				maxRankQuery.put(l.getQuery(), rank);
			}else if(rank > maxRankQuery.get(l.getQuery())){
				maxRankQuery.put(l.getQuery(), rank);
			}
		}
		
		Iterator<Entry<String, Integer>> it = maxRankQuery.entrySet().iterator();
		while(it.hasNext()) {
			k += it.next().getValue();
		}
		
		return k/logs.size();
	}
	
	
	
	/**
	 * @return the nQuery
	 */
	public ArrayList<String> getQuery() {
		return query;
	}

	/**
	 * @return the nDocOpened
	 */
	public ArrayList<Log> getnDocOpened() {
		return docOpened;
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
		
		return "Session [id=" + id + ", user=" + getUser() + ", topic=" + getTopic() + ", nQuery=" + query.size() + ", sessionMeasure=" + sessionMeasure + ", logs=\n" + logs + "]";
	}

	
	
}
