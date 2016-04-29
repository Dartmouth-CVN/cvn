package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParsingUtils implements ParsingUtils {

    public static Set<Patient> importData(String filename) {
        try {
            File inputFile = new File("patients.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Set<Patient> output = new LinkedHashSet<Patient>();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList patientList = doc.getElementsByTagName("patient");
            System.out.println("----------------------------" + doc.getDocumentElement().getTextContent());
            for (int i = 0; i < patientList.getLength(); i++) {
                output.add(makePatient(patientList.item(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        importData("patients.xml");
        if (true)
            return;/*
                     * try { File inputFile = new File("patients.xml");
					 * DocumentBuilderFactory dbFactory =
					 * DocumentBuilderFactory.newInstance(); DocumentBuilder
					 * dBuilder = dbFactory.newDocumentBuilder(); Document doc =
					 * dBuilder.parse(inputFile);
					 * doc.getDocumentElement().normalize(); System.out.println(
					 * "Root element :" +
					 * doc.getDocumentElement().getNodeName()); NodeList nList =
					 * doc.getElementsByTagName("student");
					 * System.out.println("----------------------------"); for
					 * (int temp = 0; temp < nList.getLength(); temp++) { Node
					 * nNode = nList.item(temp); System.out.println(
					 * "\nCurrent Element :" + nNode.getNodeName()); if
					 * (nNode.getNodeType() == Node.ELEMENT_NODE) { Element
					 * eElement = (Element) nNode; System.out.println(
					 * "Student roll no : " + eElement.getAttribute("rollno"));
					 * System.out.println( "First Name : " +
					 * eElement.getElementsByTagName("firstname").item(0).
					 * getTextContent()); System.out.println( "Last Name : " +
					 * eElement.getElementsByTagName("lastname").item(0).
					 * getTextContent()); System.out.println( "Nick Name : " +
					 * eElement.getElementsByTagName("nickname").item(0).
					 * getTextContent()); System.out.println("Marks : " +
					 * eElement.getElementsByTagName("marks").item(0).
					 * getTextContent()); } } } catch (Exception e) {
					 * e.printStackTrace(); }
					 */
    }

    public static void exportData(File f, List<Patient> patients, PatientExportWrapper exportWrapper) {
        try {
            PrintWriter toFile = new PrintWriter(f);
            toFile.println("<patient-list>\n");
            for(Patient p : patients){
                exportWrapper.setPatient(p);
                toFile.println(exportWrapper.toXMLString());
            }
            toFile.println("\n</patient-list>");
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
                output.add(makePatient(patientList.item(i)));
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

        String[] fields = {"firstname", "lastname", "caregivers", "assignedstaff", "medication", "address", "phone",
                "email", "pets", "allergies", "dietrestrictions"};

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
		for(String address : addressArray)
			output.getContactInfo().addAddress(new ContactElement("address", address));
		String[] phonenumArray = fields[6].split(",");
		for(String phone : phonenumArray)
			output.getContactInfo().addPhone(new ContactElement("phone",phone));
		String[] emails = fields[7].split(",");
		for(String email : emails)
			output.getContactInfo().addEmail(new ContactElement("email",email));
		String[] pets = fields[8].split(",");
		for (String pet : pets);
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
        return new File(name);
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
