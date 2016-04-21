package utils;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.MainApp;
import model.Patient;
import model.Pet;

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

	public static void exportData(String filename, Set<Patient> patients, boolean[] toInclude) {
		// TODO Auto-generated method stub

	}

	public static Patient makePatient(Node pt) {
		System.out.println("\nCurrent Element :" + pt.getNodeName());
		Element patient = (Element) pt;

		String[] fields = { "firstname", "lastname", "caregivers", "assignedstaff", "medication", "address", "phone",
				"email", "pets", "allergies", "dietrestrictions" };

		fields[0] = patient.getElementsByTagName("firstname").item(0).getTextContent();
		fields[1] = patient.getElementsByTagName("lastname").item(0).getTextContent();
		fields[2] = patient.getElementsByTagName("caregivers-list").item(0).getTextContent();

		// The code block below sets the value of fields	
		System.out.println("Student roll no : " + patient.getAttribute("rollno"));
		System.out.println("First Name : " + patient.getElementsByTagName("firstname").item(0).getTextContent());
		System.out.println("Last Name : " + patient.getElementsByTagName("lastname").item(0).getTextContent());
		System.out.println("Nick Name : " + patient.getElementsByTagName("pets-list").item(0).getTextContent());
		System.out.println("Marks : " + patient.getElementsByTagName("pets-list").item(0).getTextContent());

		// The code block below assigns the values of fields[] to the patient
		Patient output = new Patient(fields[0], fields[1]);
		String[] staff = fields[3].split(",");
		for (String member : staff)
			output.getAssignedStaff().add(MainApp.getDatabaseHandler().getMedicalStaff(member));

		@SuppressWarnings("unused") // Medication currently disabled
		String[] meds = fields[4].split(",");
		// for (String med : meds)
		// output.addMedication(new Medication(med));

		String address = fields[5];
		output.getContactInfo().addAddress(address);
		String[] phoneNumbers = fields[6].split(",");
		for (String number : phoneNumbers)
			output.getContactInfo().addPhone(number);
		String email = fields[7];
		output.getContactInfo().addEmail(email);
		String[] pets = fields[8].split(",");
		for (String pet : pets)
			output.getPatientProfile().addPet(new Pet(pet, null, false));
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

}
