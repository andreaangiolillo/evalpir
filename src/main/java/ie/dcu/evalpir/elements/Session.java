package ie.dcu.evalpir.elements;

/**
 * @author Andrea Angiolillo
 * 
 *         It represents a session performed by the user as a set of LOG contained in the logFile
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import ie.dcu.evalpir.EvalEpir;
import ie.dcu.evalpir.exceptions.QueryNotInTheLogFileException;

public class Session {

	private static final String QUERY_SUBMISSION = "QUERY_SUBMISSION";
	private static final String OPEN_DOCUMENT = "OPEN_DOCUMENT";

	private String id; // The id is made up of a set of session_id
	private String user;
	private String topic;
	private ArrayList<Log> logs;
	private Map<String, Log> queries;
	private ArrayList<Log> docOpened;

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
		this.queries = new HashMap<String, Log>();
		this.docOpened = new ArrayList<Log>();
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
		this.queries = new HashMap<String, Log>();
		this.docOpened = new ArrayList<Log>();
	}

	/**
	 * 
	 * @param id
	 */
	public void addId(String id) {
		this.id += ", " + id;
	}

	/**
	 * It adds a new log
	 * 
	 * @param l
	 */
	public void addLog(Log l) {
		if (l.getType().equalsIgnoreCase(QUERY_SUBMISSION)) {
			queries.put(l.getQuery().trim().toLowerCase(), l);
		} else if (l.getType().equalsIgnoreCase(OPEN_DOCUMENT)) {
			docOpened.add(l);
		}

		getLogs().add(l);
	}

	/**
	 * This method return the deepest document opened in a ranked list into the session
	 * If there are not documents opened in the log file it return 10
	 * 
	 * @return
	 */
	public int getMaxK() {
		if (getDocOpened().size() != 0) {
			ArrayList<Log> logs = getDocOpened();
			int k = 0;
			int rank = -1;

			for (Log log : logs) {
				rank = Integer.parseInt(log.getRank());
				if (rank > k) {
					k = rank;
				}
			}

			return k + 1; // sum + 1 because in the logsfile the ranklists start to 0
		}

		return 10;
	}

	/**
	 * This method returns the position average of the documents opened across the session
	 * If there are not documents opened in the log file it return 10
	 * 
	 * @return
	 */
	public int getAverageK() {

		if (getDocOpened().size() != 0) {
			int k = 0;
			Iterator<Entry<String, Integer>> it = getPath().entrySet().iterator();
			while (it.hasNext()) {
				k += it.next().getValue() + 1; // sum + 1 because in the logsfile the ranklists start to 0
			}

			return k / getDocOpened().size();
		}

		return 10;
	}

	/**
	 * It computes the sorting of the log by time
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
	 * In the logsfile the ranklists start to 0 so the rank is incremented by 1
	 * 
	 * @return path
	 */
	public Map<String, Integer> getPath() {
		ArrayList<Log> queries = getQuery();
		Map<String, Integer> path = new HashMap<String, Integer>();
		for (Log query : queries) {
			path.put(query.getQuery().trim().toLowerCase(), 1);

		}

		ArrayList<Log> docsOpened = getDocOpened();
		int rank = 0;
		for (Log docOpened : docsOpened) {
			rank = Integer.parseInt(docOpened.getRank()) + 1;
			if (rank > path.get(docOpened.getQuery().trim().toLowerCase())) {
				path.put(docOpened.getQuery().trim().toLowerCase(), rank);
			}
		}

		return path;
	}

	/**
	 * This method return the last document opened in each ranked list across the session.
	 * We define the last document opened as the first not relevant document found after the last
	 * document opened by the user in that ranked list
	 * 
	 * @param pirQueries
	 * @return
	 */
	public Map<String, Integer> getPath(Map<String, Query> pirQueries) {
		Map<String, Integer> path = getPath();
		Map<String, Query> relQueries = EvalEpir.QUERYREL;
		Map<String, Document> relDocs;
		Map<String, Document> pirDocs;
		int lastDocOpenedInLog = 0;
		int lastDocOpened = 0;
		for (Map.Entry<String, Integer> entryPath : path.entrySet()) {
			relDocs = relQueries.get(entryPath.getKey().trim().toLowerCase()).getDocs();
			pirDocs = pirQueries.get(entryPath.getKey().trim().toLowerCase()).getDocs();
			if (relDocs == null || pirDocs == null) {
				throw new QueryNotInTheLogFileException("QueryID: " + entryPath.getKey());
			}

			lastDocOpenedInLog = entryPath.getValue() - 1; // the doc opened starts to 0
			lastDocOpened = 100000; //
			for (Map.Entry<String, Document> entryDocPir : pirDocs.entrySet()) {
				if (((DocumentOutputPIR) entryDocPir.getValue()).getRank() > lastDocOpenedInLog) {
					if (relDocs.containsKey(entryDocPir.getKey().trim().toLowerCase())) {
						if (!((DocumentRelFile) relDocs.get(entryDocPir.getKey().trim().toLowerCase()))
								.getIsRelevance()) {
							if (((DocumentOutputPIR) entryDocPir.getValue()).getRank() < lastDocOpened) {
								lastDocOpened = ((DocumentOutputPIR) entryDocPir.getValue()).getRank();
							}
						}

					}
				}
			}

			lastDocOpened = (lastDocOpened == 100000) ? entryPath.getValue() : lastDocOpened + 1;
			entryPath.setValue(lastDocOpened); // the doc opened starts to 0
		}

		return path;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public Map<String, Integer> getPathbyMoffatZobelDistribution(double p) {
		Map<String, Integer> path = getPath();
		for (Map.Entry<String, Integer> entryPath : path.entrySet()) {
			entryPath.setValue(getLastDocOpenedByDistribution(p));
		}

		return path;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public int getLastDocOpenedByDistribution(double p) {
		boolean changeQuery = false;
		Random random = new Random();
		int i = 1;
		double probChangeQuery = 0.0;
		double randNumber = 0.0;

		while (!changeQuery) {
			randNumber = random.nextFloat();// which returns the random float number between 0.0f (inclusive) and 1.0f(exclusive).
			probChangeQuery = 1 - moffatZobelDistribution(p, i + 1);
			if (randNumber > probChangeQuery) {
				i++;
			} else {
				changeQuery = true;
			}
		}

		return i;
	}

	/**
	 * 
	 * @param p
	 * @param i
	 * @return
	 * @see https://pdfs.semanticscholar.org/471c/b4c2e5039bdaacb0274fee70c7fe2e93493e.pdf
	 */
	public static double moffatZobelDistribution(double p, int i) {
		if (i == 1) {
			return 1;
		} else {
			return Math.pow(p, i - 1);
		}
	}

	public ArrayList<Log> getQuery() {
		ArrayList<Log> valuesList = new ArrayList<Log>(queries.values());
		sortLog(valuesList);
		return valuesList;
	}

	public ArrayList<Log> getDocOpened() {
		sortLog(docOpened);
		return docOpened;
	}

	public String getUser() {
		return user;
	}

	public String getTopic() {
		return topic;
	}

	public ArrayList<Log> getLogs() {
		sortLog(logs);
		return logs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		String logs = "";
		for (Log l : getLogs()) {
			logs += l.toString() + "\n";
		}

		return "Session [id=" + id + ", user=" + getUser() + ", topic=" + getTopic() + ", nQuery=" + queries.size()
				+ ", logs=\n" + logs + "]";
	}

}
