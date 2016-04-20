package model;

import java.util.LinkedList;

public class Patient extends AbsUser {
	int patientId;
	PatientProfile patientProfile;
	HealthProfile healthProfile;
	protected LinkedList<MedicalStaff> assignedStaff;
	
	public Patient(){
		patientProfile = new PatientProfile();
		healthProfile = new HealthProfile();
	}
	
	public Patient(String firstName, String lastName, String username, String password, String birthday){
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.birthday = birthday;
	}
	
	public Patient(int patientId, String firstName, String lastName, String username, String password, String birthday){
		this(firstName, lastName, username, password, birthday);
		this.patientId = patientId;
	}
	
	public int getPatientId(){
		return patientId;
	}
	
	private void setPatientId(int patientId){
		this.patientId = patientId;
	}
	
	public void setPatientProfile(PatientProfile patientProfile) {
		this.patientProfile = patientProfile;
	}

	public PatientProfile getPatientProfile(){
		if(patientProfile == null)
			return new PatientProfile();
		return patientProfile;
	}
	
	public void setHealthProfile(HealthProfile healthProfile) {
		this.healthProfile = healthProfile;
	}

	public HealthProfile getHealthProfile(){
		if(healthProfile == null)
			return new HealthProfile();
		return healthProfile;
	}
}
