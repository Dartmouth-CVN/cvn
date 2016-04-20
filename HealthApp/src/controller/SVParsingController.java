package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.MainApp;
import model.MedicalStaff;
import model.Patient;
import model.Pet;

public abstract class SVParsingController implements ParsingController {
	abstract String getDelimiter();

	/**
	 * getFile takes in the name of a file and returns its lines as an array of
	 * Strings
	 * 
	 * @param filename
	 *            the name of the file to import
	 * @return the lines of the file as an array of Strings
	 */
	public String[] getFile(String filename) {
		Scanner fileReader;
		try {
			fileReader = new Scanner(new File(filename));
		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
			System.out.println("File not Found");
			MainApp.printError(e1);
			return null;
		}
		LinkedList<String> lines = new LinkedList<String>();
		while (fileReader.hasNextLine())
			lines.add(fileReader.nextLine());
		fileReader.close();
		return null;
	}

	/**
	 * importData takes in the name of a separated values file and uses
	 * those fields to construct a LinkedList of Patients
	 * 
	 * @param filename
	 *            the name of the separated values file
	 * @return a LinkedList of the imported Patients
	 */
	public LinkedList<Patient> importData(String filename) {
		LinkedList<Patient> output = new LinkedList<Patient>();
		String[] lines = getFile(filename);
		for (String patient : lines)
			output.add(makePatient(patient));
		return output;
	}

	/**
	 * Outputs all patients from the database to the specified file
	 * 
	 * @param filename
	 *            the file to write to
	 * @param toInclude
	 *            which fields to export
	 */
	public void exportData(String filename, LinkedList<Patient> patients, boolean[] toInclude) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			MainApp.printError(e);
			return;
		}
		for (Patient patient : patients) {
			toFile.println(patientToString(patient, toInclude));
		}

		toFile.close();
	}

	/**
	 * Converts a line of Separated Values into a patient
	 * 
	 * @param pt
	 *            the values of the patient
	 * @return the new Patient
	 */
	public Patient makePatient(String pt) {
		String[] fields = splitLine(pt);
		Patient output = new Patient(fields[1], fields[2], "", 0);
		String[] staff = fields[4].split(",");
		for (String member : staff)
			output.addMedicalStaff(new MedicalStaff(member));

		@SuppressWarnings("unused") // Medication currently disabled
		String[] meds = fields[5].split(",");
		// for (String med : meds)
		// output.addMedication(new Medication(med));

		String address = fields[6];
		output.getContactInfo().addAddress(address);
		String[] phoneNumbers = fields[7].split(",");
		for (String number : phoneNumbers)
			output.getContactInfo().addPhone(number);
		String email = fields[8];
		output.getContactInfo().addEmail(email);
		String[] pets = fields[9].split(",");
		for (String pet : pets)
			output.getPreferences().addPet(new Pet(pet, null, false));
		String[] allergies = fields[10].split(",");
		for (String allergy : allergies)
			output.getPreferences().addAllergy(allergy);
		String[] dietaryNeeds = fields[11].split(",");
		for (String diet : dietaryNeeds)
			output.getPreferences().addDietaryRestrictions(diet);
		return output;
	}

	/**
	 * splitLine takes a line of Separated Values and returns it as an array of
	 * Strings
	 * 
	 * @param toSplit
	 *            the line to split
	 * @return the array of values
	 */
	private String[] splitLine(String toSplit) {
		LinkedList<String> output = new LinkedList<String>();
		String curVal = "";
		boolean inQuotes = false;
		for (int i = 0; i < toSplit.length(); i++) {
			char curChar = toSplit.charAt(i);
			if (curChar == '\"')
				inQuotes = !inQuotes;
			else if (curChar == getDelimiter.charAt(0) && !inQuotes) {
				String toAdd = curVal.trim();
				output.add(toAdd);
				curVal = "";
			} else {
				curVal += curChar;
			}
		}
		if (curVal.length() > 0) {
			String toAdd = curVal.trim();
			output.add(toAdd);
		}
		String[] outputArr = new String[output.size()];
		output.toArray(outputArr);
		return outputArr;

	}

	/**
	 * Given a Patient, converts it into a line of Separated Values
	 * @param p the Patient to convert
	 * @param toInclude which fields to export
	 * @return the Patient as a String of Separated Values
	 */
	public String patientToString(Patient p, boolean[] toInclude) {
		// Padding the array
		if (toInclude == null)
			toInclude = new boolean[0];
		if (toInclude.length < 12) {
			boolean[] newArr = new boolean[12];
			for (int i = 0; i < toInclude.length; i++)
				newArr[i] = toInclude[i];
			for (int i = toInclude.length; i < newArr.length; i++)
				newArr[i] = true;
			toInclude = newArr;
		}

		// The below array mirrors the format of toInclude[], for example, if
		// toInclude[3] is false, caregivers will not be included.
		String[] fields = { "userid", "firstname", "lastname", "caregivers", "assignedstaff", "medication", "address",
				"phone", "email", "pets", "allergies", "dietrestrictions" };

		String[] fieldsFromPatient = { p.getUserID(), p.getFirstName(), p.getLastName(),
				String.join(",",
						p.getPreferences().getCaregiver().stream().map(caregiver -> caregiver.getName())
								.collect(Collectors.toList())),
				String.join(",",
						p.getAssignedStaff().stream().map(staff -> staff.getFirstName() + " " + staff.getLastName())
								.collect(Collectors.toList())),
				// String.join(",", p.getMedication().stream().map(med ->
				// med.getName())
				// .collect(Collectors.toList()))
				"", String.join(",", p.getContactInfo().getAddress()), String.join(",", p.getContactInfo().getPhone()),
				String.join(",", p.getContactInfo().getEmail()),
				String.join(",",
						p.getPreferences().getPets().stream().map(pet -> pet.getName()).collect(Collectors.toList())),
				String.join(",", p.getPreferences().getAllergies()),
				String.join(",", p.getPreferences().getDietaryRestrictions()) };

		for (int i = 0; i < toInclude.length; i++) // Deleting unused fields and
													// wrapping all fields with
													// quotes
			fields[i] = (toInclude[i]) ? ("\"" + fieldsFromPatient[i] + "\"") : "";

		return String.join(getDelimiter, fields);
	}

}
