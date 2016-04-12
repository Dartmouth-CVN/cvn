package controller;

import java.util.LinkedList;

import model.MedicalStaff;
import model.Medication;
import model.Patient;

public class XMLParsingUtils {
	public static void writePatientsToXML(String filename, LinkedList<Patient> pts) {
		String output = "";
		for (Patient p : pts) {
			output += XMLLine("patient",
					XMLLine("firstname", p.getFirstName()) + XMLLine("lastname", p.getLastName())
							+ XMLLine("id", p.getUserID()) + XMLLine("patient_id", String.valueOf(p.getPatientID()))
							+ XMLList("assigned_staff", "staff", XMLParseMedStaff(p.getAssignedStaff()))
							+ XMLList("perscribed_meds", "medication", XMLParseMedication(p.getMedication())),
					true);

		}
		System.out.println(output);
	}

	private static String XMLLine(String tag, String content) {
		return XMLLine(tag, content, false);
	}

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

	private static String XMLList(String tag, String subtag, String[] content) {
		String inner = "";
		for (String c : content)
			inner += XMLLine(subtag, c);
		return XMLLine(tag, inner, true);
	}

	private static String[] XMLParseMedStaff(LinkedList<MedicalStaff> staffMembers) {
		String[] output = new String[staffMembers.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = staffMembers.get(i).getUserID();
		return output;
	}

	private static String[] XMLParseMedication(LinkedList<Medication> meds) {
		String[] output = new String[meds.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = meds.get(i).getName();
		return output;
	}
}
