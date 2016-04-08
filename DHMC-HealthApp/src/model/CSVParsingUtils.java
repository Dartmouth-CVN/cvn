package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;

// TODO: We need to find a way to import Medical Staff and Medications from Strings,
// given that the constructors require more fields. A possible solution is a simpler version
// of the constructor using default values for the other fields
// This can be in Iteration 2 if necessary

public class CSVParsingUtils {

	public static LinkedList<Patient> CSVImport(String str) {
		return CSVImport(new File(str));
	}

	public static LinkedList<Patient> CSVImport(File f) {
		LinkedList<Patient> output = new LinkedList<Patient>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(f);
		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
			System.out.println("File not Found");
			e1.printStackTrace();
			return null;
		}

		while (fileReader.hasNextLine()) // Parses the file line by line
			output.add(makePatient(fileReader.nextLine().split(",")));
		fileReader.close();
		return output;
	}

	public static void CSVExport(String output, LinkedList<Patient> src) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(output, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		for (Patient pt : src) {
			toFile.println(patientToCSV(pt));
		}

		toFile.close();
	}

	public static Patient makePatient(String[] pt) {
		Patient output = new Patient(pt[2], pt[1], pt[0], 0);
		String[] staff = pt[5].split(",");
		for (String member : staff)
			output.addMedicalStaff(new MedicalStaff(member));
		String[] meds = pt[7].split(",");
		for (String med : meds)
			output.addMedication(new Medication(med));

		return output;
	}

	public static String patientToCSV(Patient pt) {
		String output = pt.getUserID();
		output += "," + pt.getLastName();
		output += "," + pt.getFirstName();
		output += ", Null,\"";
		for (MedicalStaff ms : pt.getAssignedStaff())
			output += ms.getUserID() + ",";
		output += "\",Null,";
		for (Medication med : pt.getMedication())
			output += med.getName() + ",";
		output += "\"";
		for (int i = 0; i < 13; i++)
			output += ",Null";
		output += ",*END*";

		return output;
	}
}
