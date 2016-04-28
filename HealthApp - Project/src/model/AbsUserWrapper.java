package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by mrampiah on 4/24/16.
 */
public class AbsUserWrapper {

    StringProperty userIdProperty;
    LongProperty userIdValueProperty;
    StringProperty firstNameProperty;
    StringProperty lastNameProperty;
    StringProperty usernameProperty;
    StringProperty passwordProperty;
    StringProperty roomProperty;
    StringProperty birthdayProperty;

    public AbsUserWrapper() {

    }

    public AbsUserWrapper(AbsUser user) {
        setUserIdProperty(user.getUserId());
        setUserIdValueProperty(user.getUserIdValue());
        setFirstNameProperty(user.getFirstName());
        setLastNameProperty(user.getLastName());
        setUsernameProperty(user.getUsername());
        setPasswordProperty(user.getPassword());
        setRoomProperty(user.getRoom());
        setBirthdayProperty(user.getBirthday());
    }


    public StringProperty getUserIdProperty() {
        return userIdProperty;
    }

    public void setUserIdProperty(String userId) {
        userIdProperty = new SimpleStringProperty(userId);
    }

    public LongProperty getUserIdValueProperty(){
        return userIdValueProperty;
    }

    public void setUserIdValueProperty(long userIdValue){
        userIdValueProperty = new SimpleLongProperty(userIdValue);
    }

    public StringProperty getFirstNameProperty() {
        return firstNameProperty;
    }

    public void setFirstNameProperty(String firstName) {
        firstNameProperty = new SimpleStringProperty(firstName);
    }

    public StringProperty getLastNameProperty() {
        return lastNameProperty;
    }

    public void setLastNameProperty(String lastName) {
        lastNameProperty = new SimpleStringProperty(lastName);
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public void setUsernameProperty(String username) {
        usernameProperty = new SimpleStringProperty(username);
    }

    public StringProperty getPasswrrdProperty() {
        return passwordProperty;
    }

    public void setPasswordProperty(String password) {
        passwordProperty = new SimpleStringProperty(password);
    }

    public StringProperty getRoomProperty() {
        return roomProperty;
    }

    public void setRoomProperty(String room) {
        roomProperty = new SimpleStringProperty(room);
    }

    public StringProperty getBirthdayProperty() {
        return birthdayProperty;
    }

    public void setBirthdayProperty(LocalDate birthday) {
        birthdayProperty = new SimpleStringProperty(birthday.toString());
    }
}
