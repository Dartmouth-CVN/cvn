package controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

import model.MedicalStaff;
import model.Medication;
import model.Patient;

public class XMLParsingUtils {
	/**
	 * Given a LinkedList of patients, write an XML file to the provided
	 * location from the Patients
	 * 
	 * @param filename
	 *            the name of the file to write to
	 * @param pts
	 *            the patients to write
	 * @return the contents of the file as a String
	 */
	public static String writePatientsToXML(String filename, LinkedList<Patient> pts) {
		String output = "<patient-list>\n";
		for (Patient p : pts) {
			output += XMLLine("patient",
					XMLLine("firstname", p.getFirstName()) + XMLLine("lastname", p.getLastName())
							+ XMLLine("id", p.getUserID()) + XMLLine("patient_id", String.valueOf(p.getPatientID()))
							+ XMLList("assigned_staff", "staff", XMLParseMedStaff(p.getAssignedStaff()))
							+ XMLLine("contact-info",
									XMLList("address-list", "address", p.getContactInfo().getAddress())
											+ XMLList("phone-number-list", "phone-number",
													p.getContactInfo().getPhone())
											+ XMLList("email-list", "email", p.getContactInfo().getEmail())),
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

	/**
	 * Writes a String of XML in the format < tag >content< /tag >
	 * 
	 * @param tag
	 *            the tag to envelop the content
	 * @param content
	 *            the content to be enveloped
	 * @return the String in XML format
	 */
	private static String XMLLine(String tag, String content) {
		return XMLLine(tag, content, false);
	}

	/**
	 * Writes a String of XML in the format < tag >content< /tag > with newlines
	 * if major is true
	 * 
	 * @param tag
	 *            the tag to envelop the content
	 * @param content
	 *            the content to be enveloped
	 * @param major
	 *            whether or not the XML should be formatted such that
	 *            multi-line structures look nicer, by adding newlines
	 * @return the XMLified String
	 */
	private static String XMLLine(String tag, String content, boolean major) {
		String output = (tag != null) ? "<" + tag + ">" : "";
		if (major)
			output += "\n\n";
		output += content;
		if (major)
			output += "\n";
		if (tag == null)
			output += "\n";
		else if (tag.split(" ").length > 1)
			output += "</" + tag.split(" ")[0] + ">\n";
		else
			output += "</" + tag + ">\n";
		return output;
	}

	/**
	 * Given an array of Strings to each be parsed by one sub-tag, surround that
	 * list with a main tag
	 * 
	 * @param tag
	 *            the tag to surround the whole list
	 * @param subtag
	 *            the tag to surround each entry in the list
	 * @param content
	 *            the entries for the list
	 * @return the String in XML format
	 */
	private static String XMLList(String tag, String subtag, Iterable<String> content) {
		String inner = "";
		for (String c : content)
			inner += XMLLine(subtag, c) +"<br>";
		return XMLLine(tag, inner, true);
	}

	private static String XMLList(String tag, String subtag, String[] content) {
		return XMLList(tag, subtag, Arrays.asList(content));
	}

	/**
	 * Given a LinkedList of MedicalStaff, produce the appropriate XML String
	 * 
	 * @param staffMembers
	 *            the LinkedList to provide
	 * @return the String in XML format
	 */
	private static String[] XMLParseMedStaff(LinkedList<MedicalStaff> staffMembers) {
		String[] output = new String[staffMembers.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = staffMembers.get(i).getUserID();

		return output;
	}

	/**
	 * Given a LinkedList of Medication, produce the appropriate XML String
	 * 
	 * @param meds
	 *            the LinkedList to provide
	 * @return the String in XML format
	 */
	@SuppressWarnings("unused") // Medication currently disabled
	private static String[] XMLParseMedication(LinkedList<Medication> meds) {
		String[] output = new String[meds.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = meds.get(i).getName();
		return output;
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
	public static String writePatientsToHTML(String filename, LinkedList<Patient> pts) {
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

			body += XMLLine("h2", p.getFirstName() + " " + p.getLastName())
					//+ XMLLine("h3", p.getUserID() + " : " + String.valueOf(p.getPatientID()))
					+ XMLLine("h3", "Contact Info:") 
					+ XMLList(null,null,p.getContactInfo().getEmail()) + "<br>"
					+ XMLList(null,null,p.getContactInfo().getAddress()) + "<br>"
					+ XMLList(null,null,p.getContactInfo().getPhone()) + "<br>"
					+"<hr>\n" + XMLLine("h3", "Assigned Personnel:")
					+ XMLList(null, null, XMLParseMedStaff(p.getAssignedStaff())) + "<hr class=\"end\">\n";
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

}
