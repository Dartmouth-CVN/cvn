package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleUser {

	private final StringProperty firstName;
	private final StringProperty lastName;
	private final IntegerProperty userID;

	public SimpleUser(int userID, String firstName, String lastName) {
		this.userID = new SimpleIntegerProperty(userID);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
	}

	public int getUserID() {
		return userID.get();
	}

	public String getFirstName() {
		return firstName.get();
	}

	public String getLastName() {
		return lastName.get();
	}

	public StringProperty getFirstNameProperty() {
		return firstName;
	}

	public StringProperty getLastNameProperty() {
		return lastName;
	}

	public IntegerProperty getUserIDProperty() {
		return userID;
	}

}
