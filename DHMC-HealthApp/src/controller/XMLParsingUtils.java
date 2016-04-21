package controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.stream.Collectors;

import model.Patient;

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
									+ stringIfTrue(
											XMLList("phone-number-list", "phone-number", p.getContactInfo().getPhoneList()),
											shouldEx[7])
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
	public static String writePatientsToHTML(String filename, LinkedList<Patient> pts,boolean shouldEx[] ) {
		
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

			body += XMLLine("h2", stringIfTrue(p.getFirstName(), shouldEx[1]) + " " + stringIfTrue(p.getLastName(), shouldEx[2]))
					+ XMLLine("h3", stringIfTrue(p.getUserID() + " : ", shouldEx[0]))
					// String.valueOf(p.getPatientID()))
					+ XMLLine("h3", "Contact Info:")
					+ stringIfTrue(XMLList(null, null, p.getContactInfo().getEmailList()), shouldEx[8]) + " "
					+ stringIfTrue(XMLList(null, null, p.getContactInfo().getAddressList()), shouldEx[6]) + " "
					+ stringIfTrue(XMLList(null, null, p.getContactInfo().getPhoneList()), shouldEx[7]) + " " + "<hr>\n"
					+ XMLLine("h3", "Assigned Personnel:")
					+ stringIfTrue(XMLList(null, null, XMLParseMedStaff(p.getAssignedStaff())), shouldEx[4]) + "<hr>\n"
					+ XMLLine("h3", "Caregivers:")
					+ stringIfTrue(XMLList(null, null, p.getPreferences().getCaregiver().stream().map(c -> c.getName())
							.collect(Collectors.toCollection(LinkedList::new))), shouldEx[5])
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
		return writePatientsToHTML(filename,pts,null);
	}
}
