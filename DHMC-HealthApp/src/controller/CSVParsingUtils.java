package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;
import model.MedicalStaff;
import model.Medication;
import model.Patient;

// TODO: We need to find a way to import Medical Staff and Medications from Strings,
// given that the constructors require more fields. A possible solution is a simpler version
// of the constructor using default values for the other fields
// This can be in Iteration 2 if necessary

public class CSVParsingUtils {

	/**
	 * Imports patients from a CSV with the given filename
	 * 
	 * @param str
	 *            the filename
	 * @return LinkedList of Patients in the CSV
	 */
	public static LinkedList<Patient> CSVImport(String str) {
		return CSVImport(new File(str));
	}

	/**
	 * Imports patients from the given CSV
	 * 
	 * @param f
	 *            the file
	 * @return LinkedList of Patients in the CSV
	 */
	public static LinkedList<Patient> CSVImport(File f) {
		return ImportSepValuesFile(f, ",");
	}

	/**
	 * Imports patients from a TSV with the given filename
	 * 
	 * @param str
	 *            the filename
	 * @return LinkedList of Patients in the TSV
	 */
	public static LinkedList<Patient> TSVImport(String str) {
		return TSVImport(new File(str));
	}

	/**
	 * Imports patients from the given TSV
	 * 
	 * @param f
	 *            the file
	 * @return LinkedList of Patients in the TSV
	 */
	public static LinkedList<Patient> TSVImport(File f) {
		return ImportSepValuesFile(f, "\t");
	}

	/**
	 * Given a file and the one character delimiter it uses, parses that file
	 * into Patients
	 * 
	 * @param f
	 *            the file to parse
	 * @param delimiter
	 *            the delimiter with which to parse it
	 * @return LinkedList of Patients from the file
	 */
	public static LinkedList<Patient> ImportSepValuesFile(File f, String delimiter) {
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
			output.add(makePatient(splitSepValuesLineAndRemoveCommasFromVal(fileReader.nextLine(), delimiter)));
		fileReader.close();
		return output;
	}

	
	/**
	 * 
	 */
	public static String removeCommas(String s) {
		String retVal = "";
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != ',') {
				retVal += s.charAt(i);
			}
		}
		return retVal;
	}
	/**
	 * Splits a line of values delimited by a given delimiter, treating quoted
	 * sections as a whole.
	 * 
	 * @param s
	 *            the line to split
	 * @param delimiter
	 *            the delimiter with with to split it
	 * @return an array of String containing all the individual values
	 */
	public static String[] splitSepValuesLineAndRemoveCommasFromVal(String s, String delimiter) {
		LinkedList<String> output = new LinkedList<String>();
		String curVal = "";
		boolean inQuotes = false;
		for (int i = 0; i < s.length(); i++) {
			char curChar = s.charAt(i);
			if (curChar == '\"')
				inQuotes = !inQuotes;
			else if (curChar == delimiter.charAt(0) && !inQuotes) {
				output.add(removeCommas(curVal.trim()));
				curVal = "";
			} else {
				curVal += curChar;
			}
		}
		if(curVal.length() > 0) {
			output.add(removeCommas(curVal.trim()));
		}
		String[] outputArr = new String[output.size()];
		output.toArray(outputArr);
		return outputArr;
	}

	
	/**
	 * Given a filename, writes a linked list of Patients into a CSV
	 * 
	 * @param output
	 *            the name of the CSV to write to
	 * @param src
	 *            the Patients to write
	 */
	public static void CSVExport(String output, LinkedList<Patient> src) {
		ExportSepValuesFile(output, src, ",");
	}

	/**
	 * Given a filename, writes a linked list of Patients into a TSV
	 * 
	 * @param output
	 *            the name of the TSV to write to
	 * @param src
	 *            the Patients to write
	 */
	public static void TSVExport(String output, LinkedList<Patient> src) {
		ExportSepValuesFile(output, src, "\t");
	}

	/**
	 * Given a filename, writes a linked list of Patients into a delimited file
	 * 
	 * @param output
	 *            the name of the Separated Values File to write to
	 * @param src
	 *            the Patients to write
	 * @param delimiter
	 *            the delimiter of the Separated Values File
	 */
	public static void ExportSepValuesFile(String output, LinkedList<Patient> src, String delimiter) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(output, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		for (Patient pt : src) {
			toFile.println(patientToSepValuesFile(pt, delimiter));
		}

		toFile.close();
	}

	/**
	 * Given an array of Strings, creates a new Patient object
	 * 
	 * @param pt
	 *            the array of Strings to parse
	 * @return the Patient represented by pt.
	 */
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

	/**
	 * Given a Patient and a delimiter, create a corresponding line for a
	 * Separated Values File
	 * 
	 * @param pt the Patient to represent
	 * @param delimiter the one-character String with which to delimit the Strings
	 * @return the delimited String representing pt
	 */
	public static String patientToSepValuesFile(Patient pt, String delimiter) {
		delimiter = String.valueOf(delimiter.charAt(0));
		String output = pt.getUserID();
		output += delimiter + pt.getLastName();
		output += delimiter + pt.getFirstName();
		output += delimiter + "Null" + delimiter + "\"";
		for (MedicalStaff ms : pt.getAssignedStaff())
			output += ms.getUserID() + delimiter;
		output += "\"" + delimiter + "Null" + delimiter;
		for (Medication med : pt.getMedication())
			output += med.getName() + delimiter;
		output += "\"";
		for (int i = 0; i < 13; i++)
			output += delimiter + "Null";
		output += delimiter + "*END*";

		return output;
	}
}
