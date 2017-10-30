/**
 * 
 */
package ie.dcu.evalpir.elements;

import java.util.ArrayList;

/**
 * @author Andrea Angiolillo	
 *
 */
public class PIR {

	
	ArrayList<User> users;
	String name;
	
	/**
	 * @param name
	 */
	public PIR(String name) {
		super();
		this.users = new ArrayList<User>();
		this.name = name;
	}
	
	
	/**
	 * @param users
	 * @param name
	 */
	public PIR(String name, ArrayList<User> users) {
		super();
		this.users = users;
		this.name = name;
	}
	
	/**
	 * @return the users
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		String stringDoc = "";
		for (User s : users) {
			stringDoc += s.toString();
		}
		stringDoc += "\nNumber of users: " + users.size() + " ]";
		return "PIR [id=" + getName()  + ", users=" + stringDoc;
	}
	
	
}
