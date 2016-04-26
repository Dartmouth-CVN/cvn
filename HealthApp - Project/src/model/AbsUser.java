package model;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public abstract class AbsUser implements IParsable{
    //	Schedule schedule;
    String userId;
    String firstName;
    String lastName;
    String username;
    String password;
    Date birthday;
    String room;
    String picture;
    Contact contactInfo;

    public AbsUser() {

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

    @Override
    public int hashCode() {
        return firstName.hashCode() * lastName.hashCode() * username.hashCode();
    }

    public String toXMLString(){
        return null;
    }

    public String toSVString(String delimiter){
        String[] fields = {firstName,lastName,username,birthday.toString(),picture}; //to be followed by the addresses, phone numbers, and emails.

        LinkedList<String> addresses = new LinkedList<String>();
        for(ContactElement c : contactInfo.getAddresses())
            addresses.add(c.getValue());

        LinkedList<String> phonenumbers = new LinkedList<String>();
        for(ContactElement c : contactInfo.getPhoneNumbers())
           phonenumbers.add(c.getValue());

        LinkedList<String> emails = new LinkedList<String>();
        for(ContactElement c : contactInfo.getEmails())
            emails.add(c.getValue());

        String firsthalf = String.join(delimiter, fields)+delimiter;
        String secondhalf = "\""+String.join("," ,addresses)+"\"";
        secondhalf = ",\""+String.join("," ,phonenumbers)+"\"";
        secondhalf = ",\""+String.join("," ,emails)+"\"";
        return firsthalf+secondhalf;
    };

    public String toCSVString(){
        return toSVString(",");
    }

    public String toTSVString(){
        return toSVString(",");
    }

    public String toHTMLString(){
        return null;
    }
}
