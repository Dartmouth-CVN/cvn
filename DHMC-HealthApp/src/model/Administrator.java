package model;

import java.io.File;

class Administrator extends User {

	public Administrator(String firstName, String lastName, String userid, String username, String password, int id) {
		super(firstName, lastName, userid, username, password, id);
	}
	
	public int importData(File inFile) {
		//import
		return 1; //on success
	}
	
	public int addMedicalStaff(MedicalStaff newStaff) {
		//open the add staff screen
		//save to database
		return 1; //on success 
	}
	
	public int addPatient(Patient newPatient) {
		//open the add patient screen
		//save to database
		return 1; //on success 
	}	
	
	public int assignMedicalStaff(Patient patient, MedicalStaff staff) {
		patient.addMedicalStaff(staff);
		staff.addPatient(patient);
		return 1; //after saving to database 
	}
	
	public int removePatient(Patient patient) {
		//delete patient from database
		for(int i = patient.assignedStaff.size(); i >= 0; i--) {
			patient.assignedStaff.get(i).removePatient(patient);
		}
		return 1; //after saving to database
	}
	
	public int removeStaff(MedicalStaff staff) {
		//delete staff from database
		for(int i = staff.assignedPatients.size(); i >= 0; i--) {
			staff.assignedPatients.get(i).removeMedicalStaff(staff);
		}
		return 1; //after saving to database
	}
	
	public int editPatient (Patient patient) {
		//fetch patient from database
		//bring patient to edit screen
		return 1; //on success
	}
	
	public int editStaff (MedicalStaff staff) {
		//fetch staff from database
		//bring staff to edit screen
		return 1; //on success
	}
}
