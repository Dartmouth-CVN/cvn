package controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
							+ XMLList("perscribed_meds", "medication", XMLParseMedication(p.getMedication())),
					true);

		}
		output += "</patient-list>";
		//System.out.println(output);
		if(filename!=null){
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
		String output = "<" + tag + ">";
		if (major)
			output += "\n\n";
		output += content;
		if (major)
			output += "\n";
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
	private static String XMLList(String tag, String subtag, String[] content) {
		String inner = "";
		for (String c : content)
			inner += XMLLine(subtag, c);
		return XMLLine(tag, inner, true);
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
	private static String[] XMLParseMedication(LinkedList<Medication> meds) {
		String[] output = new String[meds.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = meds.get(i).getName();
		return output;
	}

	/**
	 * Given a LinkedList of Patients, create an HTML page from the XML
	 * formatted version of that list.
	 * 
	 * @param filename
	 *            the name of the HTML file to write to
	 * @param pts
	 *            the LinkedList of Patients to convert
	 */
	public static void writePatientsToHTML(String filename, LinkedList<Patient> pts) {
		String contents = writePatientsToXML(null, pts);
		contents = XMLtoHTMLTag(contents, "patient", "tr");
		contents = XMLtoHTMLTag(contents, "patient-list", "table");
		
		System.out.println(contents);
	}
	
	/**
	 * Replaces the tag in the given String with another
	 * @param toSwap the String in which replacement occurs
	 * @param tag the tag to change
	 * @param newTag the tag to replace the old tag
	 */
	private static String XMLtoHTMLTag(String toSwap, String tag, String newTag){
		toSwap=toSwap.replace("<"+tag+">", "<"+newTag+">");
		toSwap=toSwap.replace("</"+tag+">", "</"+newTag+">");	

		return toSwap;
	}
}
