package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParsingUtils implements ParsingUtils {

    public static List<Patient> importData(String filename) {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            LinkedList<Patient> output = new LinkedList<Patient>();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList patientList = doc.getElementsByTagName("patient");
            System.out.println("----------------------------");

            for (int i = 0; i < patientList.getLength(); i++) {
                //PatientExportWrapper pw = new PatientExportWrapper(true,true,true,true,true,true,true,true,true,true,true,true,true);
                //pw.setImport(patientList.item(i).toString());
                Element patient = (Element) patientList.item(i);
                Patient newpat = new Patient();

                String firstname = (getSimpleChildString(patient, "firstname"));
                String lastname = (getSimpleChildString(patient, "lastname"));
                String username = (getSimpleChildString(patient, "username"));
                LocalDate birthday = LocalDate.parse(getSimpleChildString(patient, "birthday"));
                String picture = (getSimpleChildString(patient, "picture"));
                String room = (getSimpleChildString(patient, "room"));

                String[] addresses = (getListChild(patient, "addresses", "address"));
                String[] phonenumbers = (getListChild(patient, "phonenumbers", "phonenumber"));
                String[] emails = (getListChild(patient, "emails", "email"));


                Element[] relationships = getComplexChild(patient, "relatives-list", "relation");
                Element[] pets = getComplexChild(patient, "pets-list", "pet");
                Element[] meals = getComplexChild(patient, "meals-list", "meal");

                String[] allergies = getListChild(getSimpleChild(patient, "health-profile"), "allergies", "allergy");
                String[] diets = getListChild(getSimpleChild(patient, "health-profile"), "allergies", "allergy");

                //assigning the basic values
                if (true) {
                    newpat.setFirstName(firstname);
                    newpat.setLastName(lastname);
                    newpat.setUsername(username);
                    newpat.setBirthday(birthday);
                    newpat.setPicture(picture);
                    newpat.setRoom(room);
                }

                //Assigning contacts
                Contact ci = new Contact(0);
                if (true) {
                    //Setting addresses
                    for (String address : addresses)
                        ci.setAddress(new ContactElement(address, "address"));
                    //Setting phone numbers
                    for (String phonenum : phonenumbers)
                        ci.addPhone(new ContactElement(phonenum, "phonenumber"));
                    //Setting emails
                    for (String email : emails)
                        ci.addEmail(new ContactElement(email, "email"));
                }


                //Assigning the Relations

                LinkedList<AbsRelation> rel = new LinkedList<AbsRelation>();
                for (Element re : relationships) {
                    Family newrel = new Family();
                    newrel.setFirstName(getSimpleChildString(re, "firstname"));
                    newrel.setLastName(getSimpleChildString(re, "lastname"));
                    newrel.setRelationship(getSimpleChildString(re, "relation"));
                    newrel.setBirthday(LocalDate.parse(getSimpleChildString(re, "birthday")));
                    rel.add(newrel);
                }
                newpat.setRelations(rel);

                //Assigning the Pets
                LinkedList<Pet> petL = new LinkedList<Pet>();
                for (Element pe : pets) {
                    Pet newpet = new Pet();
                    newpet.setName(getSimpleChildString(pe, "name"));
                    newpet.setSpecies(getSimpleChildString(pe, "species"));
                    petL.add(newpet);
                }
                newpat.setPets(petL);

                //Assigning the Meals
                LinkedList<Meal> mealL = new LinkedList<Meal>();
                for (Element me : meals) {
                    Meal newmeal = new Meal();
                    newmeal.setFood(getSimpleChildString(me, "food"));
                    newmeal.setCalories(Integer.parseInt(getSimpleChildString(me, "calories")));
                    newmeal.setNotes(getSimpleChildString(me, "notes"));
                    mealL.add(newmeal);
                }
                newpat.setMeals(mealL);

                //Assigning the HealthProfile
                HealthProfile hp = new HealthProfile();
                if (true) {
                    hp.setAllergies(Arrays.asList(allergies));
                    hp.setDietaryRestrictions(Arrays.asList(diets));
                }
                newpat.setHealthProfile(hp);

                output.add(newpat);
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Element getSimpleChild(Element e, String tag) {
        if (e == null || e.getElementsByTagName(tag).getLength() < 1) return null;
        return (Element) e.getElementsByTagName(tag).item(0);
    }

    private static String getSimpleChildString(Element e, String tag) {
        if (e == null || e.getElementsByTagName(tag).getLength() < 1) return "";
        return e.getElementsByTagName(tag).item(0).getFirstChild().getTextContent();
    }

    private static String[] getListChild(Element e, String tag, String subtag) {
        if (e == null) return new String[0];
        NodeList layerone = e.getElementsByTagName(tag);
        String[] output = new String[layerone.getLength()];
        for (int i = 0; i < output.length; i++) {
            output[i] = layerone.item(i).getTextContent();
        }
        return output;
    }

    private static Element[] getComplexChild(Element e, String tag, String subtag) {
        if (e == null) return new Element[0];
        NodeList layerone = getSimpleChild(e, tag).getElementsByTagName(subtag);
        Element[] output = new Element[layerone.getLength()];
        for (int i = 0; i < output.length; i++) {
            output[i] = (Element) layerone.item(i);
        }
        return output;
    }

    public static void exportData(File f, List<Patient> patients, PatientExportWrapper exportWrapper) {
        try {
            PrintWriter toFile = new PrintWriter(f);
            toFile.println("<patient-list>\n");
            for (Patient p : patients) {
                exportWrapper.setPatient(p);
                toFile.println(exportWrapper.toXMLString());
            }
            toFile.println("\n</patient-list>");
            toFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("The file selected is either inaccessible or does not exist.");
        }
    }


    public static LinkedList<Patient> importData(File inputFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            LinkedList<Patient> output = new LinkedList<Patient>();

            NodeList patientList = doc.getElementsByTagName("patient");

            for (int i = 0; i < patientList.getLength(); i++) {
                Patient toAdd = makePatient(patientList.item(i));
                if (toAdd != null)
                    output.add(toAdd);
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Patient makePatient(Node pt) {
        System.out.println("\nCurrent Element :" + pt.getNodeName());
        Element patient = (Element) pt;

        String[] fields = {"firstname", "lastname", "username", "birthday", "picture", "room", "addresses",
                "phonenumbers", "emails", "allergies", "dietrestrictions"};

        // The code block below sets the value of fields

        fields[0] = patient.getElementsByTagName("firstname").item(0).getTextContent();
        fields[1] = patient.getElementsByTagName("lastname").item(0).getTextContent();

        NodeList caregivers = ((Element) patient.getElementsByTagName("caregivers-list").item(0))
                .getElementsByTagName("caregiver");
        LinkedList<String> caregiversStr = new LinkedList<String>();
        for (int i = 0; i < caregivers.getLength(); i++)
            caregiversStr.add(caregivers.item(i).getTextContent());
        fields[2] = String.join(",", caregiversStr);

        NodeList stafflist = ((Element) patient.getElementsByTagName("staff-list").item(0))
                .getElementsByTagName("staff");
        LinkedList<String> staffStr = new LinkedList<String>();
        for (int i = 0; i < stafflist.getLength(); i++)
            staffStr.add(stafflist.item(i).getTextContent());
        fields[3] = String.join(",", staffStr);
        /*
         * NodeList medications =
		 * ((Element)patient.getElementsByTagName("medications-list").item(0 )).
		 * getElementsByTagName("medication"); LinkedList<String> medicationsStr
		 * = new LinkedList<String>(); for(int
		 * i=0;i<medications.getLength();i++)
		 * medicationsStr.add(medications.item(i).getTextContent()); fields[4] =
		 * String.join(",", medicationsStr);
		 */
        NodeList addresses = ((Element) patient.getElementsByTagName("address-list").item(0))
                .getElementsByTagName("address");
        LinkedList<String> addressStr = new LinkedList<String>();
        for (int i = 0; i < addresses.getLength(); i++)
            addressStr.add(addresses.item(i).getTextContent());
        fields[5] = String.join(",", addressStr);

        NodeList phonenumbers = ((Element) patient.getElementsByTagName("phone-number-list").item(0))
                .getElementsByTagName("phone-number");
        LinkedList<String> phonenumberStr = new LinkedList<String>();
        for (int i = 0; i < phonenumbers.getLength(); i++)
            phonenumberStr.add(phonenumbers.item(i).getTextContent());
        fields[6] = String.join(",", phonenumberStr);

        NodeList emaillist = ((Element) patient.getElementsByTagName("email-list").item(0))
                .getElementsByTagName("email");
        LinkedList<String> emailStr = new LinkedList<String>();
        for (int i = 0; i < emaillist.getLength(); i++)
            emailStr.add(emaillist.item(i).getTextContent());
        fields[7] = String.join(",", emailStr);

        NodeList petslist = ((Element) patient.getElementsByTagName("pets-list").item(0)).getElementsByTagName("pet");
        LinkedList<String> petsStr = new LinkedList<String>();
        for (int i = 0; i < petslist.getLength(); i++)
            petsStr.add(petslist.item(i).getTextContent());
        fields[8] = String.join(",", petsStr);

        NodeList allergylist = ((Element) patient.getElementsByTagName("allergies-list").item(0))
                .getElementsByTagName("allergy");
        LinkedList<String> allergyStr = new LinkedList<String>();
        for (int i = 0; i < allergylist.getLength(); i++)
            allergyStr.add(allergylist.item(i).getTextContent());
        fields[9] = String.join(",", allergyStr);

        NodeList dietlist = ((Element) patient.getElementsByTagName("diets-list").item(0)).getElementsByTagName("diet");
        LinkedList<String> dietStr = new LinkedList<String>();
        for (int i = 0; i < dietlist.getLength(); i++)
            dietStr.add(dietlist.item(i).getTextContent());
        fields[10] = String.join(",", dietStr);

        // The code block below assigns the values of fields[] to the
        // patient
        Patient output = new Patient();
        String[] staff = fields[3].split(",");
        for (String member : staff)
//			output.getAssignedStaff().add(new MedicalStaff("", member, member, member,  member, null, member, null));
            //TODO: get actual medical staff here again
            ;
        @SuppressWarnings("unused") // Medication currently disabled
                String[] meds = fields[4].split(",");
        // for (String med : meds)
        // output.addMedication(new Medication(med));

        String[] addressArray = fields[5].split(",");
        for (String address : addressArray)
            output.getContactInfo().setAddress(new ContactElement("address", address));
        String[] phonenumArray = fields[6].split(",");
        for (String phone : phonenumArray)
            output.getContactInfo().addPhone(new ContactElement("phone", phone));
        String[] emails = fields[7].split(",");
        for (String email : emails)
            output.getContactInfo().addEmail(new ContactElement("email", email));
        String[] pets = fields[8].split(",");
        for (String pet : pets) ;
//			output.getPets().add(new Pet(pet, null, false, ""));
        String[] allergies = fields[9].split(",");
        for (String allergy : allergies)
            output.getHealthProfile().getAllergies().add(allergy);
        String[] dietaryNeeds = fields[10].split(",");
        for (String diet : dietaryNeeds)
            output.getHealthProfile().getDietaryRestrictions().add(diet);

        return output;
    }

    public static Node patientToNode(Patient p, boolean[] toInclude) {
        return null;
    }

    @Override
    public File getFile(String name) {
        return null;
    }

    @Override
    public List<String> getFileContents(File file) {
        return null;
    }

    @Override
    public AbsUser getUserFromContents(String contents) {
        return null;
    }
}
