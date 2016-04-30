package utils;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrampiah on 4/26/16.
 */
public class PatientExportWrapper implements IExportable {
    boolean userId, firstName, lastName, username, birthday, room;
    boolean picture, contactInfo, pets, meals, relations;
    boolean assignedStaff, healthProfile;

    Patient patient;
    String toImport;

    public PatientExportWrapper() {

    }

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

    public PatientExportWrapper(List<Boolean> selected, Patient p) {
        int i = 0;
        this.userId = true;
        this.firstName = selected.get(i++);
        this.lastName = selected.get(i++);
        this.username = selected.get(i++);
        this.birthday = selected.get(i++);
        this.room = selected.get(i++);
        this.picture = selected.get(i++);
        this.contactInfo = selected.get(i++);
        this.pets = selected.get(i++);
        this.meals = selected.get(i++);
        this.relations = selected.get(i++);
        this.assignedStaff = selected.get(i++);
        this.healthProfile = selected.get(i++);
        this.patient = p;

//        System.out.printf("firtname %s, lastname %s, username %s, birthday %s, room %s, picture %s, contact %s, pets %s," +
//                "meals %s, relations %s, assigned staff %s, health profile %s", firstName, lastName, username, birthday, room,
//                picture, contactInfo, pets, meals, relations, assignedStaff, healthProfile);
    }

    public PatientExportWrapper(List<Boolean> selected) {
        this(selected, new Patient());
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setImport(String toImport) {
        this.toImport = toImport;
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
                        + XMLLine(ar.getBirthday().toString(), "birthday"), "relative", true));
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
        String healthplist = "\" \",\" \" ,\" \"";

        if (contactInfo) {
            for (ContactElement c : patient.getContactInfo().getAddresses())
                addresses.add(c.getValue().replace("\n", ","));
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
            System.out.println("pet size: " + patient.getPets().size());
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
            healthplist = String.format("\"%s\", \"%s\", \"%s\"", String.join(";", hp.getHealthInfoAsString()), String.join(";", hp.getAllergies()), String.join(";", hp.getDietaryRestrictions()));
        }

        String firsthalf = String.join(delimiter, stringFields) + delimiter;

        String secondhalf = "\"" + String.join(";", addresses) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", phonenumbers) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", emails) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", relationships) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", petslist) + "\"";
        secondhalf += delimiter + "\"" + String.join(";", mealslist) + "\"";
        secondhalf += delimiter + healthplist;

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
        String[] cells = splitSVLine(toCSVString(), ",");
        LinkedList<String> tableCells = new LinkedList<String>();
        for (String s : cells) {
            tableCells.add(XMLLine(s.replace(";", "<br>"), "td"));
        }
        return XMLLine(String.join("\n", tableCells), "tr", true);
    }

    private static String[] splitSVLine(String s, String delimiter) {
        LinkedList<String> output = new LinkedList<String>();
        if (s == null || s.equals("")) {
            return new String[0];
        }
        String curVal = "";
        boolean inQuotes = false;
        for (int i = 0; i < s.length(); i++) {
            char curChar = s.charAt(i);
            if (curChar == '\"')
                inQuotes = !inQuotes;
            else if (curChar == delimiter.charAt(0) && !inQuotes) {
                String toAdd = curVal;
                output.add(toAdd);
                curVal = "";
            } else {
                curVal += curChar;
            }
        }
        if (curVal.length() > 0) {
            String toAdd = curVal;
            output.add(toAdd);
        }
        String[] outputArr = new String[output.size()];
        output.toArray(outputArr);
        return outputArr;
    }

    @Override
    //For the record, this is never used, if it is used, then it won't work, because this function doesn't work. Don't use it.
    public AbsUser fromXMLString() {
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            Patient output = new Patient();
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the XML file
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(toImport));
            dom = db.parse(is);

            //Element doc = dom.getDocumentElement();

            //The simple String fields
            String firstname = dom.getElementsByTagName("firstname").item(0).getTextContent();
            String lastname = dom.getElementsByTagName("lastname").item(0).getTextContent();
            String username = dom.getElementsByTagName("username").item(0).getTextContent();
            LocalDate birthday = LocalDate.parse(dom.getElementsByTagName("birthday").item(0).getTextContent());
            String picture = dom.getElementsByTagName("picture").item(0).getTextContent();
            String room = dom.getElementsByTagName("room").item(0).getTextContent();

            //The contact information fields
            NodeList addresses = dom.getElementsByTagName("addresses").item(0).getChildNodes();
            NodeList phonenumbers = dom.getElementsByTagName("phonenumbers").item(0).getChildNodes();
            NodeList emails = dom.getElementsByTagName("emails").item(0).getChildNodes();

            //The more complicated object fields
            NodeList relations = dom.getElementsByTagName("relatives-list").item(0).getChildNodes();
            NodeList pets = dom.getElementsByTagName("pets-list").item(0).getChildNodes();
            NodeList meals = dom.getElementsByTagName("meals-list").item(0).getChildNodes();

            //The HealthProfile fields
            NodeList allergies = dom.getElementsByTagName("allergies").item(0).getChildNodes();
            NodeList diets = dom.getElementsByTagName("diet-restrictions").item(0).getChildNodes();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Patient output = new Patient();

        String[] fields = splitSVLine(toImport, delimiter);
        if (fields.length < 14) {
            return null;
        }
        String firstname = fields[0];
        String lastname = fields[1];
        String username = fields[2];
        LocalDate birthday;
        try {
            birthday = LocalDate.parse(fields[3]);

        } catch (DateTimeParseException ex) {
            birthday = LocalDate.now();
        }
        String picture = fields[4];
        String room = fields[5];

        //The below three statements must be converted to ContactElements
        String[] addresses = splitSVLine(fields[6], ";");
        String[] phonenumbers = splitSVLine(fields[7], ";");
        String[] emails = splitSVLine(fields[8], ";");

        //The below three statements must be parsed further, into their respective objects
        String[] relationships = splitSVLine(fields[9], ";");
        String[] pets = splitSVLine(fields[10], ";");
        String[] meals = splitSVLine(fields[11], ";");

        //The last two statements must be parsed into a HealthProfile
        String[] healthInfo = splitSVLine(fields[12], ";");
        String[] allergies = splitSVLine(fields[13], ";");
        String[] diets = splitSVLine(fields[14], ";");

        //assigning the basic values
        if (true) {
            output.setFirstName(firstname);
            output.setLastName(lastname);
            output.setUsername(username);
            output.setBirthday(birthday);
            output.setPicture(picture);
            output.setRoom(room);
        }

        //Assigning contacts
        Contact ci = new Contact(0);
        if (true) {
            //Setting addresses
            for (String address : addresses)
                ci.addAddress(new ContactElement(address, "address"));
            //Setting phone numbers
            for (String phonenum : phonenumbers)
                ci.addAddress(new ContactElement(phonenum, "phonenumber"));
            //Setting emails
            for (String email : emails)
                ci.addAddress(new ContactElement(email, "email"));
        }


        //Assigning the Relations
        LinkedList<AbsRelation> rel = new LinkedList<AbsRelation>();
        for (String re : relationships) {
            Family newrel = new Family();
            String[] relfields = splitSVLine(re, ";");
            if (relfields.length < 4) break;
            newrel.setFirstName(relfields[0]);
            newrel.setLastName(relfields[1]);
            newrel.setRelationship(relfields[2]);
            newrel.setBirthday(LocalDate.parse(relfields[3]));
            rel.add(newrel);
        }
        output.setRelations(rel);

        //Assigning the Pets
        LinkedList<Pet> petL = new LinkedList<Pet>();
        for (String pe : pets) {
            Pet newpet = new Pet();
            String[] petfields = splitSVLine(pe, ";");
            if (petfields.length < 2) break;
            newpet.setName(petfields[0]);
            newpet.setSpecies(petfields[1]);
            petL.add(newpet);
        }
        output.setPets(petL);

        //Assigning the Meals
        LinkedList<Meal> mealL = new LinkedList<Meal>();
        for (String me : meals) {
            Meal newmeal = new Meal();
            String[] mealfields = splitSVLine(me, ";");
            if (mealfields.length < 3) break;
            newmeal.setFood(mealfields[0]);
            newmeal.setCalories(Integer.parseInt(mealfields[1]));
            newmeal.setNotes(mealfields[2]);
            mealL.add(newmeal);
        }
        output.setMeals(mealL);

        List<HealthAttribute<?>> attributes = new LinkedList<>();
        for (String hi : healthInfo) {
            HealthAttribute<?> healthAttribute = HealthAttribute.fromSVString(hi);
            attributes.add(healthAttribute);
        }

        //Assigning the HealthProfile
        HealthProfile hp = new HealthProfile();
        if (true) {
            hp.setHealthInfo(attributes);
            hp.setAllergies(Arrays.asList(allergies));
            hp.setDietaryRestrictions(Arrays.asList(diets));
        }
        output.setHealthProfile(hp);

        return output;
    }

}


