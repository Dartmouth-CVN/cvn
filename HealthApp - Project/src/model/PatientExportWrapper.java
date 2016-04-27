package model;

import sun.awt.image.ImageWatched;

import javax.management.relation.Relation;
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

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public String toXMLString() {
        String[] stringFields = {patient.getFirstName(), patient.getLastName(), patient.getUsername(),
                patient.getBirthday().toString(), patient.getPicture(), patient.getRoom()}; //to be followed by the simple String fields: contactInfo, relations, pets, and meals.
        String[] stringFieldLabels = {"firstname", "lastname", "username", "birthday", "picture", "room"}; //the XML labels for the fields
        boolean[] toExFields = {firstName, lastName, username, birthday, picture, room}; //which of the above fields to export

        for (int i = 0; i < toExFields.length; i++) { //deleting the appropriate fields
            if (!toExFields[i])
                stringFields[i] = " ";
            else
                stringFields[i] = XMLLine(stringFields[i], stringFieldLabels[i]);
        }
        LinkedList<String> addresses = new LinkedList<String>();
        LinkedList<String> phonenumbers = new LinkedList<String>();
        LinkedList<String> emails = new LinkedList<String>();

        LinkedList<String> relationships = new LinkedList<String>();
        LinkedList<String> petslist = new LinkedList<String>();

        LinkedList<String> mealslist = new LinkedList<String>();
        String healthplist = "";


        if (contactInfo) {
            for (ContactElement c : patient.getContactInfo().getAddresses())
                addresses.add(XMLLine(c.getValue(), "address"));
            for (ContactElement c : patient.getContactInfo().getPhoneNumbers())
                phonenumbers.add(XMLLine(c.getValue(), "phonenumber"));
            for (ContactElement c : patient.getContactInfo().getEmails())
                emails.add(XMLLine(c.getValue(), "email"));
        }

        if (relations) {
            for (AbsRelation ar : patient.getRelations()) {
                relationships.add(XMLLine(XMLLine(ar.getFirstName(), "firstname") + "\n"
                        + XMLLine(ar.getLastName(), "lastname") + "\n"
                        + XMLLine(ar.getRelationship(), "relation") + "\n"
                        + XMLLine(ar.getBirthday().toString(), "birthday"), "relative", true);
            }
        }
        if (pets) {
            for (Pet p : patient.getPets()) {
                petslist.add(XMLLine(XMLLine(p.getName(), "name") + "\n" + XMLLine(p.getSpecies(), "species"), "pet", true));
            }
        }
        if (meals) {
            for (Meal m : patient.getMeals()) {
                mealslist.add(XMLLine(XMLLine(m.getFood(), "food") + "\n" + XMLLine(String.valueOf(m.getCalories()), "calories")
                        + "\n" + XMLLine(m.getNotes(), "notes"), "meal", true));
            }
        }
        if (healthProfile) {
            HealthProfile hp = patient.getHealthProfile();
            LinkedList<String> allergies = new LinkedList<String>();
            for (String allergy : hp.getAllergies()) {
                allergies.add(XMLLine(allergy, "allergy"));
            }
            LinkedList<String> dietaryR = new LinkedList<String>();
            for (String diet : hp.getDietaryRestrictions()) {
                allergies.add(XMLLine(diet, "dietary-restriction"));
            }
            healthplist = XMLLine(XMLLine(String.join("\n", allergies), "allergies") + "\n" + XMLLine(String.join("\n", dietaryR), "diet-restrictions")
                    , "health-profile", true);
        }

        String firsthalf = String.join("\n", stringFields);

        String secondhalf = XMLLine(String.join("\n", addresses), "addresses", true);
        secondhalf += XMLLine(String.join("\n", phonenumbers), "phonenumbers", true);
        secondhalf += XMLLine(String.join("\n", emails), "emails", true);
        secondhalf += XMLLine(String.join("\n", relationships), "relatives-list", true);
        secondhalf += XMLLine(String.join("\n", petslist), "pets-list", true);
        secondhalf += XMLLine(String.join("\n", mealslist), "meals-list", true);
        secondhalf += healthplist;

        return XMLLine(firsthalf + secondhalf, "patient", true);
    }

    private String XMLLine(String content, String tag, boolean major) {
        if (major)
            content = "\n" + content + "\n";
        return "<" + tag + ">" + content + "</" + tag + ">";
    }

    private String XMLLine(String content, String tag) {
        return XMLLine(content, tag, false);
    }

    @Override
    public String toSVString(String delimiter) {
        String[] stringFields = {patient.getFirstName(), patient.getLastName(), patient.getUsername(),
                patient.getBirthday().toString(), patient.getPicture(), patient.getRoom()}; //to be followed by the simple String fields: contactInfo, relations, pets, and meals.

        boolean[] toExFields = {firstName, lastName, username, birthday, picture, room}; //which of the above fields to export

        for (int i = 0; i < toExFields.length; i++) //deleting the appropriate fields
            if (!toExFields[i]) stringFields[i] = " ";

        LinkedList<String> addresses = new LinkedList<String>();
        LinkedList<String> phonenumbers = new LinkedList<String>();
        LinkedList<String> emails = new LinkedList<String>();

        LinkedList<String> relationships = new LinkedList<String>();
        LinkedList<String> petslist = new LinkedList<String>();

        LinkedList<String> mealslist = new LinkedList<String>();
        String healthplist = "";


        if (contactInfo) {
            for (ContactElement c : patient.getContactInfo().getAddresses())
                addresses.add(c.getValue());
            for (ContactElement c : patient.getContactInfo().getPhoneNumbers())
                phonenumbers.add(c.getValue());
            for (ContactElement c : patient.getContactInfo().getEmails())
                emails.add(c.getValue());
        }

        if (relations) {
            for (AbsRelation ar : patient.getRelations()) {
                relationships.add(String.format("%s, %s, %s, %s", ar.getFirstName(), ar.getLastName(), ar.getRelationship(), ar.getBirthday().toString()));
            }
        }
        if (pets) {
            for (Pet p : patient.getPets()) {
                petslist.add(String.format("%s, %s", p.getName(), p.getSpecies()));
            }
        }
        if (meals) {
            for (Meal m : patient.getMeals()) {
                mealslist.add(String.format("%s, %d, %s", m.getFood(), m.getCalories(), m.getNotes()));
            }
        }
        if (healthProfile) {
            HealthProfile hp = patient.getHealthProfile();
            healthplist = String.format("%s; %s", hp.getAllergies(), hp.getDietaryRestrictions());
        }

        String firsthalf = String.join(delimiter, stringFields) + delimiter;

        String secondhalf = "\"" + String.join(";", addresses) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", phonenumbers) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", emails) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", relationships) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", petslist) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", mealslist) + "\"";
        secondhalf += delimiter + "\"" + healthplist + "\"";

        return firsthalf + secondhalf;
    }

    @Override
    public String toCSVString() {
        return toSVString(",");
    }

    @Override
    public String toTSVString() {
        return toSVString("\t");
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
        return fromSVString(",");
    }

    @Override
    public AbsUser fromTSVString() {
        return fromSVString("\t");
    }

    @Override
    public AbsUser fromSVString(String delimiter) {


        return null;
    }
}
