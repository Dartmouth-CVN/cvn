package model;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient extends User {
	// private LinkedList<Caregiver> caregivers;
	protected List<MedicalStaff> assignedStaff;
	private List<HealthInfo> healthInfo;
	private PatientProfile preferences;
	// private LinkedList<Medication> medication;
	private int patientID;
	private boolean newPatient = false;

	private StringProperty firstNameProperty;
	private StringProperty lastNameProperty;
	private StringProperty userIDProperty;
	
	public Patient(String firstName, String lastName, String userID, int patientID) {
		super(firstName, lastName, userID);
		this.assignedStaff = new LinkedList<MedicalStaff>();
		this.preferences = new PatientProfile();
		this.healthInfo = new LinkedList<HealthInfo>();
		// this.medication = new LinkedList<Medication>();
		this.patientID = patientID;
		initObservers();
	}
	
	public Patient(String firstName, String lastName, String userID, int patientID, Contact contactInfo) {
		super(firstName, lastName, userID, contactInfo);
		this.assignedStaff = new LinkedList<MedicalStaff>();
		this.preferences = new PatientProfile();
		this.healthInfo = new LinkedList<HealthInfo>();
		// this.medication = new LinkedList<Medication>();
		this.patientID = patientID;
		initObservers();
	}
	
	public Patient(String firstName, String lastName, String userID, Contact contactInfo) {
		super(firstName, lastName, userID, contactInfo);
		this.assignedStaff = new LinkedList<MedicalStaff>();
		this.preferences = new PatientProfile();
		this.healthInfo = new LinkedList<HealthInfo>();
		// this.medication = new LinkedList<Medication>();
		initObservers();
	}
	
	public Patient() {
		super("First Name", "Last Name", "", new Contact());
		this.assignedStaff = new LinkedList<MedicalStaff>();
		this.preferences = new PatientProfile();
		this.patientID = 0;
		newPatient = true;
		this.healthInfo = new LinkedList<HealthInfo>();
		initObservers();
	}
	
	@Override
	public void initObservers(){
		firstNameProperty = new SimpleStringProperty(getFirstName());
		lastNameProperty = new SimpleStringProperty(getLastName());
		userIDProperty = new SimpleStringProperty(getUserID());
	}
	
	public List<MedicalStaff> getAssignedStaff() {
		return this.assignedStaff;
	}
	
	public void setAssignedStaff(List<MedicalStaff> staff){
		this.assignedStaff = staff;
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
	
	public void setNewPatient(boolean patient){
		newPatient = patient;
	}
	
	public boolean getNewPatient(){
		return newPatient;
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
	*/
//	public LinkedList<Medication> getMedication() {
//		return this.medication;
//	}
//	
//	public void addMedication(Medication medication) {
//		if (this.medication.indexOf(medication) < 0) {
//			this.medication.add(medication);
//		}
//	}
//	
//	public void removeMedication(Medication medication) {
//		if (this.medication.indexOf(medication) >= 0) {
//			this.medication.remove(medication);
//		}
//	}
	
	public void setPreferences(PatientProfile preferences) {
		this.preferences = preferences;
	}
	
	public PatientProfile getPreferences() {
		return this.preferences;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public List<HealthInfo> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(List<HealthInfo> healthInfo) {
		this.healthInfo = healthInfo;
	}
	
	public void addHealthInfo(HealthInfo newInfo) {
		this.healthInfo.add(newInfo);
	}
	
	public void addHealthInfoList(List<HealthInfo> info) {
		for(int i = 0; i < info.size(); i++) {
			for(int j = 0; j< healthInfo.size(); j++) {
				if(info.get(i).equals(healthInfo.get(j))) {
					info.remove(i);
				}
			}
			healthInfo.add(info.get(i));
		}
	}
	
	public boolean update(){
		return MainApp.getDatabaseHandler().updatePatient(this);
	}
	
	public boolean save(){
		System.out.println("saving new patient");
		return MainApp.getDatabaseHandler().insertPatientAlgorithm(this);
	}
}
