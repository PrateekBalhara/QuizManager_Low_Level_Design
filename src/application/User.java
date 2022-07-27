/**
 * 
 */
package application;

import database.Database;

/**
 * @author Prateek Balhara C0830003
 * User of the application
 */
public class User {
	private String role, name, college_id;
	
	User(String name, String college_id, String role){
		this.name = name;
		this.role = role;
		this.college_id = college_id;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the college_id
	 */
	public String getCollege_id() {
		return college_id;
	}

	/**
	 * @param college_id the college_id to set
	 */
	public void setCollege_id(String college_id) {
		this.college_id = college_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param college_id the id assigned by college to either 
	 * Professor or to Students
	 * @param password password set by user
	 * @return User object after login
	 */
	public static User userLogin(String college_id, String password) {
		try {
			String[] user = Database.findUser(college_id,password);
			if(user==null) {
				throw new Exception("User Not Found");
			}
			return new User(user[0], user[3], user[2]);
		}catch(Exception e) {
			System.out.println("\nLogin Failed. Please try again.");
			return null;
		}
			
		
	}
	
}
