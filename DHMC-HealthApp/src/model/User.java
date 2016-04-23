package model;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class User implements IDisplayable{
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty userID;
	private Contact contactInfo;
	private LocalDate birthday;
	// private Schedule schedule;
	// private Location room;
	//role is either "Patient", "MedicalStaff", "Administrator"
	public User(String firstName, String lastName, String id) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.contactInfo = new Contact(id);
		this.setBirthday(LocalDate.now());
		//this.schedule = new Schedule(id);
		// this.room = null;
		this.userID = new SimpleStringProperty(id);
	}
	
	public User(String firstName, String lastName, String id, Contact contactInfo) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.contactInfo = contactInfo;
		this.setBirthday(LocalDate.now());
		//this.schedule = new Schedule(id);
		// this.room = null;
		this.userID = new SimpleStringProperty(id);
	}
	
	@Override
	public StringProperty firstNameProperty() {
		return firstName;
	}
	
    @Override
	public final String getFirstName() {
        return firstNameProperty().get();
    }

    public final void setFirstName(String firstName) {
        firstNameProperty().set(firstName);
    }
	
	@Override
	public StringProperty lastNameProperty() {
		return lastName;
	}
	
    @Override
	public final String getLastName() {
        return lastNameProperty().get();
    }

    public final void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }
    
	@Override
	public StringProperty userIDProperty() {
		
		return userID;
	}
	
    @Override
	public final String getUserID() {
        return this.userID.get();
    }

    public final void setUserID(String userID) {
        this.userID = new SimpleStringProperty(userID);
    }
	
	public Contact getContactInfo() {
		return this.contactInfo;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
//	public Location getRoom() {
//		return this.room;
//	}
//	
//	public void setRoom(Location room) {
//		this.room = room;
//	}
	
//	public Schedule getSchedule() {
//		return this.schedule;
//	}
}
