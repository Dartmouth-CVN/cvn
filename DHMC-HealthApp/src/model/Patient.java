package model;
import java.util.LinkedList;

class Patient extends User {
	// private LinkedList<Caregiver> caregivers;
	protected LinkedList<MedicalStaff> assignedStaff;
	// private PatientProfile preferences;
	// private LinkedList<Medication> medication;
	// HealthProfile is going to be changed to an attribute of PatientProfile

	public Patient(String firstName, String lastName, String userid, String username, String password, int id) {
		super(firstName, lastName, userid, username, password, id);
		this.assignedStaff = new LinkedList<MedicalStaff>();
	}
	
	public LinkedList<MedicalStaff> getAssignedStaff() {
		return this.assignedStaff;
	}
	
	public void addMedicalStaff(MedicalStaff staff) {
		if (this.assignedStaff.indexOf(staff) < 0) {
			this.assignedStaff.add(staff);
		}
	}
	
	public void removeMedicalStaff(MedicalStaff staff) {
		if (this.assignedStaff.indexOf(staff) >= 0) {
			this.assignedStaff.remove(staff);
		}
	}
	/*
	public LinkedList<Caregiver> getCaregivers() {
		return this.caregivers;
	}
	 
	public void addCaregiver(Caregiver caregiver) {
		if (this.caregivers.indexOf(caregiver) < 0) {
			this.caregivers.add(caregiver);
		}
	}
	
	public void removeCaregiver(Caregiver caregiver) {
		if (this.caregivers.indexOf(caregiver) >= 0) {
			this.caregivers.remove(caregiver);
		}
	}
	
	public LinkedList<Medication> getMedication() {
		return this.medication;
	}
	
	public void addMedication(Medication medication) {
		if (this.medication.indexOf(medication) < 0) {
			this.medication.add(medication);
		}
	}
	
	public void removeMedication(Medication medication) {
		if (this.medication.indexOf(medication) >= 0) {
			this.medication.remove(medication);
		}
	}
	
	public PatientProfile getPreferences() {
		return this.preferences;
	}
	*/
}
