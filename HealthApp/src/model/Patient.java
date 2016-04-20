package model;

import java.util.Set;

public class Patient extends AbsUser {
	int patientId;
	PatientProfile patientProfile;
	HealthProfile healthProfile;
	Set<MedicalStaff> assignedStaff;
	
	public Patient(int userId, String firstName, String lastName, String username, String password, String birthday,
			String room, Contact contactInfo, Schedule schedule, int patientId) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
		this.patientId = patientId;
	}
	
	public Patient(int userId, String firstName, String lastName, String username, String password, String birthday,
			String room, Contact contactInfo, Schedule schedule, int patientId, PatientProfile patientProfile, 
			HealthProfile healthProfile, Set<MedicalStaff> assignedStaff) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
		this.patientId = patientId;
		this.patientProfile = patientProfile;
		this.healthProfile = healthProfile;
		this.assignedStaff = assignedStaff;
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
	
	
}
