package model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public abstract class AbsUser implements IParsable{
    //	Schedule schedule;
    long userIdValue;
    String userId;
    String firstName;
    String lastName;
    String username;
    String password;
    LocalDate birthday;
    String room;
    String picture;
    Contact contactInfo;

    public AbsUser() {

    }

    public AbsUser(long userIdValue, String firstName, String lastName, String username, String password, LocalDate birthday,
                   String room, String picture) {
        this(userIdValue, firstName, lastName, username, password, birthday, room, picture, new Contact());
    }

    public AbsUser(long userIdValue, String firstName, String lastName, String username, String password, LocalDate birthday,
                   String room, String picture, Contact contactInfo) {
        setUserIdValue(userIdValue);
        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
        setPassword(password);
        setBirthday(birthday);
        setRoom(room);
        setPicture(picture);
        setContactInfo(contactInfo);
    }

    public long getUserIdValue() {
        return userIdValue;
    }

    public void setUserIdValue(long userIdValue) {
        this.userIdValue = userIdValue;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Contact getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Contact contactInfo) {
        this.contactInfo = contactInfo;
    }

    public abstract String getUserId();

    @Override
    public int hashCode() {
        return firstName.hashCode() * lastName.hashCode() * username.hashCode();
    }

    public static String getUserType(){
        return "ABS_USER";
    }

    public static String getRole(){
        return "ABS_USER";
    }
}
