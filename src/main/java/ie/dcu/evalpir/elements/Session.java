package ie.dcu.evalpir.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
	private ArrayList<Log> query;
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
		this.query = new ArrayList<Log>();
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
		this.query = new ArrayList<Log>();
		this.docOpened = new ArrayList<Log>();
		this.sessionMeasure = false;
	}
	
	public void addId(String id) {
		this.id += ", " + id; 
	}
	
	public void addLog(Log l) {
		if(l.getType().equalsIgnoreCase(QUERY_SUBMISSION)) {
			query.add(l); 
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
	 * This method return the deepest document opened in a ranked list into the session
	 * If there are not documents opened in the log file it return 10
	 * @return
	 */
	public int getMaxK() {
		if(getDocOpened().size() != 0) {
			ArrayList<Log> logs = getDocOpened();
			int k = 0;
			int rank = 0;
			
			for(Log log : logs) {
				rank = Integer.parseInt(log.getRank());
				if (rank > k){
					k = rank;
				}
			}
			
			return k;
		}
		
		return 10;
	}
	
	/**
	 * This method returns the position average of the documents opened across the session
	 * If there are not documents opened in the log file it return 10
	 * @return
	 */
	public int getAverageK() {
		
		if(getDocOpened().size() != 0) {
			int k = 0;
			Iterator<Entry<String, Integer>> it = getPath().entrySet().iterator();
			while(it.hasNext()) {
				k += it.next().getValue();
			}
			
			return k/getDocOpened().size();	
		}
		
		return 10;
	}
	
	/**
	 * 
	 * @param myList
	 */
	public void sortLog(ArrayList<Log> myList) {
		Collections.sort(myList, new Comparator<Log>() {
			  public int compare(Log o1, Log o2) {
			      return o1.getTimestamp().compareTo(o2.getTimestamp());
			  }
			});
	}
	
	
	
	/**
	 * This method returns the last document opened in each ranked list across the session
	 * If a query has not documents opened is set to 1
	 * @return
	 */
	public Map<String, Integer> getPath() {
		ArrayList<Log> queries = getQuery();
		Map<String, Integer> path = new HashMap<String, Integer>();
		for (Log query : queries){
			path.put(query.getQuery().trim().toLowerCase(), 1);
		
		}
		
		ArrayList<Log> docsOpened = getDocOpened();	
		int rank = 0;
		for(Log docOpened : docsOpened) {
			rank = Integer.parseInt(docOpened.getRank()) + 1;
			if(rank > path.get(docOpened.getQuery().trim().toLowerCase())) {
				path.put(docOpened.getQuery().trim().toLowerCase(), rank);
			}
		}	
		
		return path;
	}
	
	
	/**
	 * @return the nQuery
	 */
	public ArrayList<Log> getQuery() {
		sortLog(query);
		return query;
	}

	/**
	 * @return the nDocOpened
	 */
	public ArrayList<Log> getDocOpened() {
		sortLog(docOpened);
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
		sortLog(logs);
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
