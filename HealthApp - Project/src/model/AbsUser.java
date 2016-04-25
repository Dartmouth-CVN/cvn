package model;

import java.io.Serializable;
import java.util.Date;

public abstract class AbsUser implements Serializable {
	java.lang.String userId;
	java.lang.String firstName;
	java.lang.String lastName;
	java.lang.String username;
	java.lang.String password;
	Date birthday;
	java.lang.String room;
	String picture;

	Contact contactInfo;
//	Schedule schedule;
private static final long serialVersionUID = 1L;

	public AbsUser(){
		
	}
	
	public AbsUser(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password, Date birthday,
				   java.lang.String room, String picture, Contact contactInfo) {
		setUserId(userId);
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
		setBirthday(birthday);
		setRoom(room);
		setPicture(picture);
		setContactInfo(contactInfo);
	}
	
	public java.lang.String getUserId() {
		return userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.lang.String getFirstName() {
		return firstName;
	}
	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}
	public java.lang.String getLastName() {
		return lastName;
	}
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}
	public java.lang.String getUsername() {
		return username;
	}
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public java.lang.String getRoom() {
		return room;
	}
	public void setRoom(java.lang.String room) {
		this.room = room;
	}
	public String getPicture(){
		return picture;
	}

	public void setPicture(String picture){
		this.picture = picture;
	}
	public Contact getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(Contact contactInfo) {
		this.contactInfo = contactInfo;
	}


	@Override
	public int hashCode(){
		return firstName.hashCode() + lastName.hashCode() + username.hashCode();
	}
}
