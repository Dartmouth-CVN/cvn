package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

import model.MainApp;
import model.Patient;
import model.Pet;

public abstract class SVParsingUtils implements ParsingUtils {
	static String getDelimiter() {
		return null;
	}

	/**
	 * getFile takes in the name of a file and returns its lines as an array of
	 * Strings
	 *
	 * @param filename
	 *            the name of the file to import
	 * @return the lines of the file as an array of Strings
	 */
	@Override
	public static File getFile(String filename) {
		return new File(filename);
	}

	@Override
	public static List<String> getFileContents(File file){
		List<String> lines = null;
		try {
			Scanner fileReader = new Scanner(file);
			lines = new LinkedList<String>();
			while (fileReader.hasNextLine())
				lines.add(fileReader.nextLine());
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * importData takes in the name of a separated values file and uses those
	 * fields to construct a LinkedList of Patients
	 *
	 * @param filename
	 *            the name of the separated values file
	 * @return a LinkedList of the imported Patients
	 */
	public static List<Patient> importData(String filename) {
		List<String> lines = getFileContents(getFile(filename));
		List<Patient> output = new LinkedList<Patient>();
		Patient placeholder = new Patient();
		for (String patient : lines)
			output.add((Patient)placeholder.fromSVString(getDelimiter()));
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
	public static void exportData(String filename, Set<Patient> patients, boolean[] toInclude) {
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
	public static Patient makePatient(String pt) {
		String[] fields = splitLine(pt);
		if (fields.length != 11)
			return null;
		Patient output = new Patient();
		String[] staff = fields[3].split(",");
		for (String member : staff);
//			output.getAssignedStaff().add(MainApp.getDBHandler().getMedicalStaff(member));

//		String[] meds = fields[4].split(",");
		// for (String med : meds)
		// output.addMedication(new Medication(med));

//		String address = fields[5];
//		output.getContactInfo().addAddress(address);
//		String[] phoneNumbers = fields[6].split(",");
//		for (String number : phoneNumbers)
//			output.getContactInfo().addPhone(number);
//		String email = fields[7];
//		output.getContactInfo().addEmail(email);
//		String[] pets = fields[8].split(",");
//		for (String pet : pets)
//			output.getPatientProfile().addPet(new Pet(pet, null, false));
//		String[] allergies = fields[9].split(",");
//		for (String allergy : allergies)
//			output.getHealthProfile().getAllergies().add(allergy);
//		String[] dietaryNeeds = fields[10].split(",");
//		for (String diet : dietaryNeeds)
//			output.getHealthProfile().getDietaryRestrictions().add(diet);
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
	private static String[] splitLine(String toSplit) {
		LinkedList<String> output = new LinkedList<String>();
		String curVal = "";
		boolean inQuotes = false;
		for (int i = 0; i < toSplit.length(); i++) {
			char curChar = toSplit.charAt(i);
			if (curChar == '\"')
				inQuotes = !inQuotes;
			else if (curChar == getDelimiter().charAt(0) && !inQuotes) {
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
	 *
	 * @param p
	 *            the Patient to convert
	 * @param toInclude
	 *            which fields to export
	 * @return the Patient as a String of Separated Values
	 */
	public static String patientToString(Patient p, boolean[] toInclude) {
		// Padding the array
		if (toInclude == null)
			toInclude = new boolean[0];
		if (toInclude.length < 11) {
			boolean[] newArr = new boolean[11];
			for (int i = 0; i < toInclude.length; i++)
				newArr[i] = toInclude[i];
			for (int i = toInclude.length; i < newArr.length; i++)
				newArr[i] = true;
			toInclude = newArr;
		}

		// The below array mirrors the format of toInclude[], for example, if
		// toInclude[3] is false, assignedstaff will not be included.
		String[] fields = { "firstname", "lastname", "caregivers", "assignedstaff", "medication", "address", "phone",
				"email", "pets", "allergies", "dietrestrictions" };

//		String[] fieldsFromPatient = {
//				p.getFirstName(), p
//						.getLastName(),
//				String.join(",",
//						p.getRelations().stream()
//								.map(caregiver -> caregiver.getFirstName() + " " + caregiver.getLastName())
//								.collect(Collectors.toList())),
//				String.join(",",
//						p.getAssignedStaff().stream().map(staff -> staff.getFirstName() + " " + staff.getLastName())
//								.collect(Collectors.toList())),
//				// String.join(",", p.getMedication().stream().map(med ->
//				// med.getName())
//				// .collect(Collectors.toList()))
//				"", String.join(",", p.getContactInfo().getAddresses()), String.join(",", p.getContactInfo().getPhoneNumbers()),
//				String.join(",", p.getContactInfo().getEmails()),
//				String.join(",",
//						p.getPatientProfile().getPets().stream().map(pet -> pet.getName())
//								.collect(Collectors.toList())),
//				String.join(",", p.getHealthProfile().getAllergies()),
//				String.join(",", p.getHealthProfile().getDietaryRestrictions()) };
//
//		for (int i = 0; i < toInclude.length; i++) // Deleting unused fields and
//													// wrapping all fields with
//													// quotes
//			fields[i] = (toInclude[i]) ? ("\"" + fieldsFromPatient[i] + "\"") : "";

		return String.join(getDelimiter(), fields);
	}

}
