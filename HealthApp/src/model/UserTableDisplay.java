package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserTableDisplay {

	IntegerProperty userId;
	StringProperty firstName;
	StringProperty lastName;

	public UserTableDisplay(int userId, String firstName, String lastName) {
		this.userId = new SimpleIntegerProperty(userId);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
	}

	public int getUserId() {
		return userId.get();
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

	public IntegerProperty getUserIdProperty() {
		return userId;
	}
}
