package model;

import java.time.LocalDate;
import java.util.Set;

public class Patient extends AbsUser {
	int patientId;
	PatientProfile patientProfile;
	HealthProfile healthProfile;
	Set<Caregiver> caregivers;
	Set<MedicalStaff> assignedStaff;
	
	public Patient(int userId, String firstName, String lastName, String username, String password, LocalDate birthday,
			String room, Contact contactInfo, Schedule schedule, int patientId) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
		this.patientId = patientId;
	}
	
	public Patient(int userId, String firstName, String lastName, String username, String password, LocalDate birthday,
			String room, Contact contactInfo, Schedule schedule, int patientId, PatientProfile patientProfile, 
			HealthProfile healthProfile, Set<MedicalStaff> assignedStaff, Set<Caregiver> caregivers) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
		this.patientId = patientId;
		this.patientProfile = patientProfile;
		this.healthProfile = healthProfile;
		this.assignedStaff = assignedStaff;
		this.caregivers = caregivers;
	}
	
	public Patient(String firstName, String lastName){
		super(0, firstName, lastName, "", "", null,"", new Contact(), new Schedule());
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public PatientProfile getPatientProfile() {
		return patientProfile;
	}

	public void setPatientProfile(PatientProfile patientProfile) {
		this.patientProfile = patientProfile;
	}

	public HealthProfile getHealthProfile() {
		return healthProfile;
	}

	public void setHealthProfile(HealthProfile healthProfile) {
		this.healthProfile = healthProfile;
	}

	public Set<MedicalStaff> getAssignedStaff() {
		return assignedStaff;
	}

	public void setAssignedStaff(Set<MedicalStaff> assignedStaff) {
		this.assignedStaff = assignedStaff;
	}
	
	public Set<Caregiver> getCaregivers() {
		return caregivers;
	}
	
	public void setCaregivers(Set<Caregiver> caregivers) {
		this.caregivers = caregivers;
	}

	public String getUserID() {
		// TODO getUserID
		return null;
	}
}
