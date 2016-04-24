package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Date;

public abstract class AbsUser {
	String userId;
	String firstName;
	String lastName;
	String username;
	String password;
	Date birthday;
	String room;

	Contact contactInfo;
//	Schedule schedule;

	StringProperty userIdProperty;
	StringProperty firstNameProperty;
	StringProperty lastNameProperty;
	StringProperty usernameProperty;
	StringProperty passwordProperty;
	StringProperty roomProperty;
	StringProperty birthdayProperty;
	
	public AbsUser(){
		
	}
	
	public AbsUser(String userId, String firstName, String lastName, String username, String password, Date birthday,
			String room, Contact contactInfo) {
		setUserId(userId);
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
		setBirthday(birthday);
		setRoom(room);
		setContactInfo(contactInfo);
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
		setUserIdProperty(userId);
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		setFirstNameProperty(firstName);
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
		setLastNameProperty(lastName);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
		setUsernameProperty(username);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		setPasswordProperty(password);
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
		setBirthdayProperty(birthday);
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
		setRoomProperty(room);
	}
	public Contact getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(Contact contactInfo) {
		this.contactInfo = contactInfo;
	}

	public StringProperty getUserIdProperty(){
		return userIdProperty;
	}

	public void setUserIdProperty(String userId){
		userIdProperty = new SimpleStringProperty(userId);
	}

	public StringProperty getFirstNameProperty() {
		return firstNameProperty;
	}

	public void setFirstNameProperty(String firstName){
		firstNameProperty = new SimpleStringProperty(firstName);
	}

	public StringProperty getLastNameProperty(){
		return lastNameProperty;
	}

	public void setLastNameProperty(String lastName){
		lastNameProperty = new SimpleStringProperty(lastName);
	}

	public StringProperty getUsernameProperty(){
		return usernameProperty;
	}

	public void setUsernameProperty(String username){
		usernameProperty = new SimpleStringProperty(username);
	}

	public StringProperty getPasswrrdProperty(){
		return passwordProperty;
	}

	public void setPasswordProperty(String password) {
		passwordProperty = new SimpleStringProperty(password);
	}

	public StringProperty getRoomProperty(){
		return roomProperty;
	}

	public void setRoomProperty(String room){
		roomProperty = new SimpleStringProperty(room);
	}

	public void setBirthdayProperty(Date birthday){
		birthdayProperty = new SimpleStringProperty(birthday.toString());
	}

	public StringProperty getBirthdayProperty(){
		return birthdayProperty;
	}

	@Override
	public int hashCode(){
		return firstName.hashCode() + lastName.hashCode() + username.hashCode();
	}
}
