package ie.dcu.evalpir.elements;

import java.util.ArrayList;

/**
 * @author Andrea Angiolillo
 * @version 1.0
 */
public class User {
	
	private String id;
	private ArrayList<Topic> topics;
	
	
	/**
	 * @param id
	 */
	public User(String id) {
		super();
		this.id = id;
	}

	/**
	 * @param id
	 * @param topics
	 */
	public User(String id, ArrayList<Topic> topics) {
		super();
		this.id = id;
		this.topics = topics;
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

	/**
	 * @return the topics
	 */
	public ArrayList<Topic> getTopics() {
		return topics;
	}

	/**
	 * @param topics the topics to set
	 */
	public void setTopics(ArrayList<Topic> topics) {
		this.topics = topics;
	}
	
	/**
	 * @param topId
	 * @return
	 */
	public Topic findTopic(String topId) {
		for(Topic top : topics) {
			if (top.getId().equalsIgnoreCase(topId)) {
				return top;
			}
		}
		return null;
	}

	
	@Override
	public String toString() {
		String stringDoc = "";
		for (Topic t : topics) {
			stringDoc += t.toString();
		}
		stringDoc += "\nNumber of topics: " + topics.size() + " ]";
		return "User [id=" + id + ", topics=" + stringDoc;
	}
	
	
	
	
}
