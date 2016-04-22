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

public class SVParsingController implements ParsingController {
	private String delimiter;

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

	public LinkedList<Patient> importPatients(String filename) {
		LinkedList<Patient> output = new LinkedList<Patient>();
		String[] lines = getFile(filename);
		for (String patient : lines)
			output.add(makePatient(patient));
		return output;
	}

	@Override
	public void exportPatients(String filename, boolean[] toInclude) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			MainApp.printError(e);
			return;
		}
		LinkedList<Patient> pts = DatabaseHandler.getUniqueInstance().getPatientList();
		for (Patient patient : pts) {
			toFile.println(patientToString(patient, toInclude));
		}

		toFile.close();
	}

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
		output.getContactInfo().setAddress(address);
		String[] phoneNumbers = fields[7].split(",");
		for (String number : phoneNumbers)
			output.getContactInfo().setPhone(number);
		String email = fields[8];
		output.getContactInfo().setEmail(email);
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

	private String[] splitLine(String toSplit) {
		LinkedList<String> output = new LinkedList<String>();
		String curVal = "";
		boolean inQuotes = false;
		for (int i = 0; i < toSplit.length(); i++) {
			char curChar = toSplit.charAt(i);
			if (curChar == '\"')
				inQuotes = !inQuotes;
			else if (curChar == delimiter.charAt(0) && !inQuotes) {
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

	@Override
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
				"", String.join(",", p.getContactInfo().getAddressList()), String.join(",", p.getContactInfo().getPhoneList()),
				String.join(",", p.getContactInfo().getEmailList()),
				String.join(",",
						p.getPreferences().getPets().stream().map(pet -> pet.getName()).collect(Collectors.toList())),
				String.join(",", p.getPreferences().getAllergies()),
				String.join(",", p.getPreferences().getDietaryRestrictions()) };

		for (int i = 0; i < toInclude.length; i++) // Deleting unused fields and
													// wrapping all fields with
													// quotes
			fields[i] = (toInclude[i]) ? ("\"" + fieldsFromPatient[i] + "\"") : "";

		return String.join(delimiter, fields);
	}

}
