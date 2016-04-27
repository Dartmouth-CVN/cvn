package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public abstract class AbsUser implements Serializable {
	String userId;
	String firstName;
	String lastName;
	String username;
	String password;
	Date birthday;
	String room;
	String picture;

	Contact contactInfo;
//	Schedule schedule;
private static final long serialVersionUID = 1L;

	public AbsUser(){
		
	}
	
	public AbsUser(String userId, String firstName, String lastName, String username, String password, Date birthday,
				   String room, String picture, Contact contactInfo) {
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
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
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
