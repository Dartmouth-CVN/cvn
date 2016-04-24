package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Patient extends AbsUser {

	List<Pet> pets;
	List<Meal> meals;
	List<AbsRelation> relations;
	List<MedicalStaff> assignedStaff;
	HealthProfile healthProfile;

	MedicalStaff medStaffKey;
	
	public Patient(){
		
	}
	
	public Patient(String userId, String firstName, String lastName, String username, String password, Date birthday,
			String room, Contact contactInfo) {
		this(userId, firstName, lastName, username, password, birthday, room, contactInfo, new LinkedList<Pet>(),
				new LinkedList<Meal>(), new LinkedList<AbsRelation>(),  new LinkedList<MedicalStaff>(), new HealthProfile());
	}
	
	public Patient(String userId, String firstName, String lastName, String username, String password, Date birthday,
			String room, Contact contactInfo, List<Pet> pets, List<Meal> meals, List<AbsRelation> relations,
				   List<MedicalStaff> assignedStaff, HealthProfile healthProfile) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
		this.pets = pets;
		this.meals = meals;
		this.relations = relations;
		this.assignedStaff = assignedStaff;
		this.healthProfile = healthProfile;
	}
	
	public Patient(String firstName, String lastName){
		super("", firstName, lastName, "", "", null,"", new Contact());
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
		for(MedicalStaff m : assignedStaff)
			addAssignedStaff(m);
	}

	public void addAssignedStaff(MedicalStaff m){
		this.assignedStaff.add(m);
		m.addPatient(this);
	}
	
	public List<AbsRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<AbsRelation> relations) {
		for(AbsRelation rel : relations)
			addRelation(rel);
	}

	public void addRelation(AbsRelation relation){
		this.relations.add(relation);
		relation.setPatientKey(this);
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		for(Pet p: pets)
			addPet(p);
	}

	public void addPet(Pet p){
		this.pets.add(p);
		p.setPatientKey(this);
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		for(Meal m : meals)
			addMeal(m);
	}

	public void addMeal(Meal m){
		this.meals.add(m);
		m.setPatientKey(this);
	}
}
