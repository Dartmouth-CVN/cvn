package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.MainApp;
import model.Patient;
import model.Pet;

public class XMLParsingUtils extends GeneralParsingUtils {
	/**
	 * Given a LinkedList of patients, write an XML file to the provided
	 * location from the Patients
	 * 
	 * @param filename
	 *            the name of the file to write to
	 * @param pts
	 *            the patients to write
	 * @param shouldEx
	 *            which fields to export
	 * @return the contents of the file as a String
	 */
	public static String writePatientsToXML(String filename, LinkedList<Patient> pts, boolean[] shouldEx) {
		if (shouldEx == null) {
			shouldEx = new boolean[0];
		}
		if (shouldEx.length < 12) {
			boolean[] newArr = new boolean[12];
			for (int i = 0; i < shouldEx.length; i++)
				newArr[i] = shouldEx[i];
			for (int i = shouldEx.length; i < newArr.length; i++)
				newArr[i] = true;
			shouldEx = newArr;
		}
		String output = "<patient-list>\n";
		for (Patient p : pts) {
			output += XMLLine("patient", stringIfTrue(XMLLine("userID", p.getUserID()), shouldEx[0])
					+ stringIfTrue(XMLLine("firstname", p.getFirstName()),
							shouldEx[1])
					+ stringIfTrue(XMLLine("lastname", p.getLastName()), shouldEx[2])
					+ stringIfTrue(XMLList("caregivers-list", "caregiver",
							p.getPreferences().getCaregiver().stream().map(c -> c.getName())
									.collect(Collectors.toCollection(LinkedList::new))),
							shouldEx[3])
					+ stringIfTrue(XMLList("staff-list", "staff", XMLParseMedStaff(p.getAssignedStaff())), shouldEx[4])
					// +
					// stringIfTrue(XMLList("medication-list","medication",p.getMedication().stream().map(
					// c ->
					// c.getName()).collect(Collectors.toCollection(LinkedList::new))),
					// shouldEx[5])
					+ XMLLine("contact-info",
							stringIfTrue(XMLList("address-list", "address", p.getContactInfo().getAddressList()),
									shouldEx[6])
									+ stringIfTrue(XMLList("phone-number-list", "phone-number",
											p.getContactInfo().getPhoneList()), shouldEx[7])
									+ stringIfTrue(XMLList("email-list", "email", p.getContactInfo().getEmailList()),
											shouldEx[8]))
					+ stringIfTrue(XMLList("pets-list", "pet",
							p.getPreferences().getPets().stream().map(c -> c.getName())
									.collect(Collectors.toCollection(LinkedList::new))),
							shouldEx[9])
					+ stringIfTrue(XMLList("allergies-list", "allergy", p.getPreferences().getAllergies()),
							shouldEx[10])
					+ stringIfTrue(XMLList("diets-list", "diet", p.getPreferences().getDietaryRestrictions()),
							shouldEx[11]),
					true);

		}
		output += "</patient-list>";
		// System.out.println(output);
		if (filename != null) {
			try {
				PrintWriter toFile = new PrintWriter(filename);
				toFile.write(output);
				toFile.close();
			} catch (FileNotFoundException e) {
				System.out.println("CVN does not have write permission to this location.");
			}

		}

		return output;
	}

	public static String writePatientsToXML(String filename, LinkedList<Patient> pts) {
		return writePatientsToXML(filename, pts, null);
	}

	/**
	 * Given a LinkedList of patients, write an HTML file to the provided
	 * location from the Patients
	 * 
	 * @param filename
	 *            the name of the file to write to
	 * @param pts
	 *            the patients to write
	 * @return the contents of the file as a String
	 */
	public static String writePatientsToHTML(String filename, LinkedList<Patient> pts, boolean shouldEx[]) {

		if (shouldEx == null) {
			shouldEx = new boolean[0];
		}
		if (shouldEx.length < 12) {
			boolean[] newArr = new boolean[12];
			for (int i = 0; i < shouldEx.length; i++)
				newArr[i] = shouldEx[i];
			for (int i = shouldEx.length; i < newArr.length; i++)
				newArr[i] = true;
			shouldEx = newArr;
		}

		String output = ""; // The HTML String to output

		String head = "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"> \n";
		// The head tag in the HTML document
		head += XMLLine("title", "Patient List");
		head += XMLLine("style type=\"text/css\"",
				"h1{color:blue;}hr{border-style:dashed;}hr.main{border-width:5px;border-style:solid;}hr.end{border-width:3px;border-style:solid;}");

		String body = XMLLine("h1", "List of Patients: " + pts.size() + " Total");
		// The body tag in the HTML document
		body += "<hr class=\"main\">\n";
		for (Patient p : pts) {

			body += XMLLine("h2",
					stringIfTrue(p.getFirstName(), shouldEx[1]) + " " + stringIfTrue(p.getLastName(), shouldEx[2]))
					+ XMLLine("h3", stringIfTrue(p.getUserID() + " : ", shouldEx[0]))
					// String.valueOf(p.getPatientID()))
					+ XMLLine("h3", "Contact Info:")
					+ stringIfTrue(XMLList(null, null, p.getContactInfo().getEmailList()),
							shouldEx[8])
					+ " "
					+ stringIfTrue(XMLList(null, null, p.getContactInfo().getAddressList()),
							shouldEx[6])
					+ " " + stringIfTrue(XMLList(null, null, p.getContactInfo().getPhoneList()), shouldEx[7]) + " "
					+ "<hr>\n"
					+ XMLLine("h3",
							"Assigned Personnel:")
					+ stringIfTrue(
							XMLList(null, null,
									XMLParseMedStaff(
											p.getAssignedStaff())),
							shouldEx[4])
					+ "<hr>\n" + XMLLine("h3", "Caregivers:")
					+ stringIfTrue(XMLList(null, null,
							p.getPreferences().getCaregiver().stream().map(c -> c.getName())
									.collect(Collectors.toCollection(LinkedList::new))),
							shouldEx[5])
					+ "<hr class=\"end\">\n";
			/*
			 * +XMLLine("p","Perscribed Meds:\n") + XMLList("div", "p", //
			 * XMLParseMedication(p.getMedication())) + "<hr>"; output +=
			 * XMLLine("h2", p.getFirstName() + " " + p.getLastName()) +
			 * XMLLine("h3", p.getUserID() + " : " +
			 * String.valueOf(p.getPatientID())) + "<hr>" + XMLLine("p",
			 * "Assigned Personnel:") + XMLList("div", "p", XMLParseMedStaff(
			 * p.getAssignedStaff()))/* + "<hr>" +XMLLine("p",
			 * "Perscribed Meds:\n") + XMLList("div", "p", XMLParseMedication(p.
			 * getMedication()))
			 * 
			 * + "<hr>";
			 */

		}

		// Putting the head and body together
		output += XMLLine("head", head, true);
		output += XMLLine("body", body, true);
		output = XMLLine("html", output, true);
		output = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">" + "\n"
				+ output;

		if (filename != null) {
			try {
				PrintWriter toFile = new PrintWriter(filename);
				toFile.write(output);
				toFile.close();
			} catch (FileNotFoundException e) {
				System.out.println("CVN does not have write permission to this location.");
			}

		}

		return output;
	}

	public static String writePatientsToHTML(String filename, LinkedList<Patient> pts) {
		return writePatientsToHTML(filename, pts, null);
	}

	public static LinkedList<Patient> importData(File inputFile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			LinkedList<Patient> output = new LinkedList<Patient>();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList patientList = doc.getElementsByTagName("patient");
			System.out.println("----------------------------" + doc.getDocumentElement().getTextContent());
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

		String[] fields = { "firstname", "lastname", "caregivers", "assignedstaff", "medication", "address", "phone",
				"email", "pets", "allergies", "dietrestrictions" };

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
		 * ((Element)patient.getElementsByTagName("medications-list").item(0)).
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


		// The code block below assigns the values of fields[] to the patient
		Patient output = new Patient(fields[0], fields[1], null, 0);
		String[] staff = fields[3].split(",");
		for (String member : staff)
			output.getAssignedStaff().add(MainApp.getDatabaseHandler().getMedicalStaff(member));

		@SuppressWarnings("unused") // Medication currently disabled
		String[] meds = fields[4].split(",");
		// for (String med : meds)
		// output.addMedication(new Medication(med));

		output.getContactInfo().addAddressList(new LinkedList<String>(Arrays.asList(fields[5].split(","))));

		output.getContactInfo().addPhoneList(new LinkedList<String>(Arrays.asList(fields[6].split(","))));

		output.getContactInfo().addEmailList(new LinkedList<String>(Arrays.asList(fields[7].split(","))));
		String[] pets = fields[8].split(",");
		for (String pet : pets)
			output.getPreferences().addPet(new Pet(pet, null, false));
		String[] allergies = fields[9].split(",");
		for (String allergy : allergies)
			output.getPreferences().getAllergies().add(allergy);
		String[] dietaryNeeds = fields[10].split(",");
		for (String diet : dietaryNeeds)
			output.getPreferences().getDietaryRestrictions().add(diet);

		return output;
	}

}
