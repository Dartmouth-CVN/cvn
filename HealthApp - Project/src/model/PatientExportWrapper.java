package model;

import java.util.LinkedList;

/**
 * Created by mrampiah on 4/26/16.
 */
public class PatientExportWrapper implements IExportable {
    boolean userId, firstName, lastName, username, birthday, room;
    boolean picture, contactInfo, pets, meals, relations;
    boolean assignedStaff, healthProfile;

    Patient patient;

    public PatientExportWrapper(boolean userId, boolean firstName, boolean lastName, boolean username, boolean birthday,
                                boolean room, boolean picture, boolean contactInfo, boolean pets, boolean meals,
                                boolean relations, boolean assignedStaff, boolean healthProfile) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.birthday = birthday;
        this.room = room;
        this.picture = picture;
        this.contactInfo = contactInfo;
        this.pets = pets;
        this.meals = meals;
        this.relations = relations;
        this.assignedStaff = assignedStaff;
        this.healthProfile = healthProfile;
    }

    public PatientExportWrapper(boolean userId, boolean firstName, boolean lastName, boolean username, boolean birthday,
                                boolean room, boolean picture, boolean contactInfo, boolean pets, boolean meals,
                                boolean relations, boolean assignedStaff, boolean healthProfile, Patient p) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.birthday = birthday;
        this.room = room;
        this.picture = picture;
        this.contactInfo = contactInfo;
        this.pets = pets;
        this.meals = meals;
        this.relations = relations;
        this.assignedStaff = assignedStaff;
        this.healthProfile = healthProfile;
        this.patient = p;
    }

    public void setPatient(Patient patient){
        this.patient = patient;
    }

    public Patient getPatient(){
        return patient;
    }

    @Override
    public String toXMLString() {
        return null;
    }

    @Override
    public String toSVString(String delimiter){
        String[] fields = {patient.getFirstName(), patient.getLastName(), patient.getUsername(),
                patient.getBirthday().toString(), patient.getPicture()}; //to be followed by the addresses, phone numbers, and emails.

        LinkedList<String> addresses = new LinkedList<String>();
        for(ContactElement c : patient.getContactInfo().getAddresses())
            addresses.add(c.getValue());

        LinkedList<String> phonenumbers = new LinkedList<String>();
        for(ContactElement c : patient.getContactInfo().getPhoneNumbers())
            phonenumbers.add(c.getValue());

        LinkedList<String> emails = new LinkedList<String>();
        for(ContactElement c : patient.getContactInfo().getEmails())
            emails.add(c.getValue());

        String firsthalf = String.join(delimiter, fields) + delimiter;
        String secondhalf = "\"" + String.join("," ,addresses)+"\"";
        secondhalf = ",\"" + String.join("," ,phonenumbers) + "\"";
        secondhalf = ",\"" + String.join("," ,emails) + "\"";
        return firsthalf+secondhalf;
    }

    @Override
    public String toCSVString() {
        return null;
    }

    @Override
    public String toTSVString() {
        return null;
    }

    @Override
    public String toHTMLString() {
        return null;
    }
    @Override
    public AbsUser fromXMLString() {
        return null;
    }

    @Override
    public AbsUser fromCSVString() {
        return null;
    }

    @Override
    public AbsUser fromTSVString() {
        return null;
    }

    @Override
    public AbsUser fromSVString(String delimiter) {
        return null;
    }
}
