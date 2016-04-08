package model;

public class SimpleUser {
	private final int userID;
	private final String firstName;
	private final String lastName;

	public SimpleUser(int userID, String firstName, String lastName) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getUserID() {
		return userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	

}
