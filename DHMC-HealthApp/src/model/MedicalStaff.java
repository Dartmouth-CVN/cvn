package model;
import java.util.LinkedList;

class MedicalStaff extends User {
	
	protected LinkedList<Patient> assignedPatients;
	
	
	public MedicalStaff(String firstName, String lastName, String userid, String username, int id) {
		super(firstName, lastName, userid, username, id);
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
	
	public int perscribe(Patient patient, Medication medication) {
		return 0;
	}
}
