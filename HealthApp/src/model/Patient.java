package model;

public class Patient extends AbsUser {
	int patientId;
	PatientProfile patientProfile;
	
	public Patient(){
		patientProfile = new PatientProfile();
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
}
