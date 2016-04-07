package model;
import java.util.LinkedList;

public class MedicalStaff extends User {
	
	protected LinkedList<Patient> assignedPatients;
	private int medID;
	
	public MedicalStaff(String firstName, String lastName, String username, String userID, int medID) {
		super(firstName, lastName, username, userID, "MedicalStaff");
		this.setMedID(medID);
	}
	
	public MedicalStaff(String username) {
		super("Enter First Name", "Enter Last Name", username, "Enter userID", "MedicalStaff");
		this.setMedID(0);
	}
	
	public int addPatient (Patient patient) {
		if(this.assignedPatients.contains(patient)){
			System.out.println("The patient already exists");
			return 0;
		} else {
			this.assignedPatients.add(patient);
			return 1; //Will be changed to return 1 upon successfully updating the database
		}
	}
	
	public int removePatient (Patient patient) {
		if(this.assignedPatients.contains(patient)){
			this.assignedPatients.remove(patient);
			return 1; //Will be changed to return 1 upon successfully updating the database
		} else {
			System.out.println("The patient requested doesn't seem to exist");
			return 0;
		}
	}
	
	public int editPatient (Patient patient) {
		if(this.assignedPatients.contains(patient)) {
			//edit the patient
			return 1;
		} else {
			System.out.println("The patient requested doesn't seem to exist");
			return 0;
		}
	}
	
	public int exportPatient (Patient patient) {
		if(this.assignedPatients.contains(patient)) {
			//export the patient
			return 1;
		} else {
			System.out.println("The patient requested doesn't seem to exist");
			return 0;
		}
	}
	
	public int prescribe(Patient patient, Medication medication) {
		return 0;
	}

	public int getMedID() {
		return medID;
	}

	public void setMedID(int medID) {
		this.medID = medID;
	}
}
