package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;

import model.MainApp;
import model.MedicalStaff;
import model.Patient;
import model.Pet;

// TODO: We need to find a way to import Medical Staff and Medications from Strings,
// given that the constructors require more fields. A possible solution is a simpler version
// of the constructor using default values for the other fields
// This can be in Iteration 2 (3?) if necessary

public class CSVParsingUtils extends GeneralParsingUtils{

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
		return importSepValuesFilePatient(f, ",");
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
		return importSepValuesFilePatient(f, "\t");
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
	public static LinkedList<Patient> importSepValuesFilePatient(File f, String delimiter) {
		LinkedList<Patient> output = new LinkedList<Patient>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(f);
		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
			System.out.println("File not Found");
			MainApp.printError(e1);
			return null;
		}

		while (fileReader.hasNextLine()) // Parses the file line by line
			output.add(makePatient(splitSepValuesLine(fileReader.nextLine(), delimiter)));
		fileReader.close();
		return output;
	}

	/**
	 * 
	 */
	public static String removeCommas(String s) {
		return s.replace(",", "");
	}

	/**
	 * Splits a line of values delimited by a given delimiter, treating quoted
	 * sections as a whole, removing commas if remCommas is true
	 * 
	 * @param s
	 *            the line to split
	 * @param delimiter
	 *            the delimiter with with to split it
	 * @param remCommas
	 *            if internal commas should be removed
	 * @return an array of String containing all the individual values
	 */
	public static String[] splitSepValuesLine(String s, String delimiter, boolean remCommas) {
		LinkedList<String> output = new LinkedList<String>();
		String curVal = "";
		boolean inQuotes = false;
		for (int i = 0; i < s.length(); i++) {
			char curChar = s.charAt(i);
			if (curChar == '\"')
				inQuotes = !inQuotes;
			else if (curChar == delimiter.charAt(0) && !inQuotes) {
				String toAdd = curVal.trim();
				if (remCommas)
					toAdd=removeCommas(toAdd);
				output.add(toAdd);
				curVal = "";
			} else {
				curVal += curChar;
			}
		}
		if (curVal.length() > 0) {
			String toAdd = curVal.trim();
			if (remCommas)
				toAdd=removeCommas(toAdd);
			output.add(toAdd);
		}
		String[] outputArr = new String[output.size()];
		output.toArray(outputArr);
		return outputArr;
	}

	public static String[] splitSepValuesLine(String s, String delimiter) {
		return splitSepValuesLine(s, delimiter, false);
	}

	public static String[] splitSepValuesLineAndRemoveCommasFromVal(String s, String delimiter) {
		return splitSepValuesLine(s, delimiter, true);
	}

	/**
	 * Given a filename, writes a linked list of Patients into a CSV
	 * 
	 * @param output
	 *            the name of the CSV to write to
	 * @param src
	 *            the Patients to write
	 * @param shouldEx
	 *            which fields to write, default all
	 */
	public static void CSVExport(String output, LinkedList<Patient> src, boolean[] shouldEx) {
		ExportSepValuesFile(output, src, ",", shouldEx);
	}

	public static void CSVExport(String output, LinkedList<Patient> src) {
		ExportSepValuesFile(output, src, ",", null);
	}

	/**
	 * Given a filename, writes a linked list of Patients into a TSV
	 * 
	 * @param output
	 *            the name of the TSV to write to
	 * @param src
	 *            the Patients to write
	 * @param shouldEx
	 *            which fields to write, default all
	 */
	public static void TSVExport(String output, LinkedList<Patient> src, boolean[] shouldEx) {
		ExportSepValuesFile(output, src, "\t", shouldEx);
	}

	public static void TSVExport(String output, LinkedList<Patient> src) {
		ExportSepValuesFile(output, src, "\t", null);
	}

	/**
	 * Given a filename, writes a linked list of Patients into a delimited file
	 * 
	 * @param output
	 *            the name of the Separated Values File to write to
	 * @param src
	 *            the Patients to write
	 * @param shouldEx
	 *            which fields to write, default all
	 * @param delimiter
	 *            the delimiter of the Separated Values File
	 */
	public static void ExportSepValuesFile(String output, LinkedList<Patient> src, String delimiter,
			boolean[] shouldEx) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(output, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			MainApp.printError(e);
			return;
		}
		for (Patient pt : src) {
			toFile.println(patientToSepValuesFile(pt, delimiter, shouldEx));
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

		Patient output = new Patient(pt[1], pt[2], pt[0], 0);
		String[] staff = pt[4].split(",");
		for (String member : staff)
			output.addMedicalStaff(new MedicalStaff(member));

		@SuppressWarnings("unused") // Medication currently disabled
		String[] meds = pt[5].split(",");
		// for (String med : meds)
		// output.addMedication(new Medication(med));

		String address = pt[6];
		output.getContactInfo().addAddress(address);
		String[] phoneNumbers = pt[7].split(",");
		for (String number : phoneNumbers)
			output.getContactInfo().addPhone(number);
		String email = pt[8];
		output.getContactInfo().addEmail(email);
		String[] pets = pt[9].split(",");
		for (String pet : pets)
			output.getPreferences().addPet(new Pet(pet, null, false));
		String[] allergies = pt[10].split(",");
		for (String allergy : allergies)
			output.getPreferences().addAllergy(allergy);
		String[] dietaryNeeds = pt[11].split(",");
		for (String diet : dietaryNeeds)
			output.getPreferences().addDietaryRestrictions(diet);
		return output;
	}

	/**
	 * Given a Patient and a delimiter, create a corresponding line for a
	 * Separated Values File
	 * 
	 * @param pt
	 *            the Patient to represent
	 * @param delimiter
	 *            the one-character String with which to delimit the Strings
	 * @param shouldEx
	 *            A boolean array indicated which fields should be exported, by
	 *            default all
	 * @return the delimited String representing pt
	 */
	public static String patientToSepValuesFile(Patient pt, String delimiter, boolean[] shouldEx) {

		// Important note;
		// Woe be to those who delve into this code
		// Those sorry souls who search for meaning
		// In this wasteland
		// the light of comments
		// is but an ember
		//
		// So if you have an issue, ask Sean, since he wrote this mess
		//

		delimiter = String.valueOf(delimiter.charAt(0));
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

		String output = stringIfTrue(pt.getUserID(), shouldEx[0]);
		output += delimiter;
		output += stringIfTrue(pt.getFirstName(), shouldEx[1]);
		output += delimiter;
		output += stringIfTrue(pt.getLastName(), shouldEx[2]);
		output += delimiter;
		// output += stringIfTrue(pt.getCaregivers(), shouldEx[3]);
		// Caregivers yet to be implemented
		output += stringIfTrue("None", shouldEx[3]);
		output += delimiter;
		if (shouldEx[4]) {
			output += "\"";
			LinkedList<MedicalStaff> ms = pt.getAssignedStaff();
			if (ms.size() > 0) {
				for (int i = 0; i < ms.size() - 1; i++)
					output += ms.get(i).getUserID() + ",";
				output += ms.get(ms.size() - 1).getUserID();
			}
			output += "\"";
		}
		output += delimiter;
		// output += stringIfTrue(pt.getMedication(), shouldEx[3]);
		// Medication yet to be implemented
		output += stringIfTrue("None", shouldEx[5]);
		output += delimiter;
		if (shouldEx[6]) {
			output += "\"";
			LinkedList<String> addresses = pt.getContactInfo().getAddress();
			if (addresses.size() > 0) {
				for (int i = 0; i < addresses.size() - 1; i++)
					output += addresses.get(i) + ",";
				output += addresses.get(addresses.size() - 1);
			}
			output += "\"";
		}
		output += delimiter;
		if (shouldEx[7]) {
			output += "\"";
			LinkedList<String> phonenumbers = pt.getContactInfo().getPhone();
			if (phonenumbers.size() > 0) {
				for (int i = 0; i < phonenumbers.size() - 1; i++)
					output += phonenumbers.get(i) + ",";
				output += phonenumbers.get(phonenumbers.size() - 1);
			}
			output += "\"";
		}
		output += delimiter;
		if (shouldEx[8]) {
			output += "\"";
			LinkedList<String> emails = pt.getContactInfo().getEmail();
			if (emails.size() > 0) {
				for (int i = 0; i < emails.size() - 1; i++)
					output += emails.get(i) + ",";
				output += emails.get(emails.size() - 1);
			}
			output += "\"";
		}
		output += delimiter;
		if (shouldEx[9]) {
			output += "\"";
			LinkedList<Pet> pets = pt.getPreferences().getPets();
			if (pets.size() > 0) {
				for (int i = 0; i < pets.size() - 1; i++)
					output += pets.get(i).getSpecies() + ",";
				output += pets.get(pets.size() - 1).getSpecies();
			}
			output += "\"";
		}
		output += delimiter;
		if (shouldEx[10]) {
			output += "\"";
			LinkedList<String> allergies = pt.getPreferences().getAllergies();
			if (allergies.size() > 0) {
				for (int i = 0; i < allergies.size() - 1; i++)
					output += allergies.get(i) + ",";
				output += allergies.get(allergies.size() - 1);
			}
			output += "\"";
		}
		output += delimiter;
		if (shouldEx[11]) {
			output += "\"";
			LinkedList<String> diets = pt.getPreferences().getDietaryRestrictions();
			if (diets.size() > 0) {
				for (int i = 0; i < diets.size() - 1; i++)
					output += diets.get(i) + ",";
				output += diets.get(diets.size() - 1);
			}
			output += "\"";
		}
		return output;
	}

	public static String patientToSepValuesFile(Patient pt, String delimiter) {
		return patientToSepValuesFile(pt, delimiter, null);
	}
}
