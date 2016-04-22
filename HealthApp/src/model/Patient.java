package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Patient extends AbsUser {
	int patientId;
	HealthProfile healthProfile;
	List<Pet> pets;
	List<Meal> meals;
	List<Caregiver> caregivers;
	List<MedicalStaff> assignedStaff;
	
	public Patient(){
		
	}

	public Patient(int userId, String firstName, String lastName, String username, String password, Date birthday,
			String room, Contact contactInfo) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
		healthProfile = new HealthProfile();
		pets = new LinkedList<Pet>();
		meals = new LinkedList<Meal>();
		caregivers = new LinkedList<Caregiver>();
		assignedStaff = new LinkedList<MedicalStaff>();
	}
	
	public Patient(int userId, String firstName, String lastName, String username, String password, Date birthday,
			String room, Contact contactInfo, int patientId) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
		this.patientId = patientId;
		healthProfile = new HealthProfile();
		pets = new LinkedList<Pet>();
		meals = new LinkedList<Meal>();
		caregivers = new LinkedList<Caregiver>();
		assignedStaff = new LinkedList<MedicalStaff>();
	}
	
	public Patient(int userId, String firstName, String lastName, String username, String password, Date birthday,
			String room, Contact contactInfo, int patientId, HealthProfile healthProfile,
			List<MedicalStaff> assignedStaff, List<Caregiver> caregivers) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
		this.patientId = patientId;
		this.healthProfile = healthProfile;
		this.assignedStaff = assignedStaff;
		this.caregivers = caregivers;
	}
	
	public Patient(String firstName, String lastName){
		super(0, firstName, lastName, "", "", null,"", new Contact());
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public HealthProfile getHealthProfile() {
		return healthProfile;
	}

	public void setHealthProfile(HealthProfile healthProfile) {
		this.healthProfile = healthProfile;
	}

	public List<MedicalStaff> getAssignedStaff() {
		return assignedStaff;
	}

	public void setAssignedStaff(List<MedicalStaff> assignedStaff) {
		this.assignedStaff = assignedStaff;
	}
	
	public List<Caregiver> getCaregivers() {
		return caregivers;
	}
	
	public void setCaregivers(List<Caregiver> caregivers) {
		this.caregivers = caregivers;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}
}
