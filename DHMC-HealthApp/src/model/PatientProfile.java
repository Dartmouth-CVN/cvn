package model;

import java.util.LinkedList;

public class PatientProfile {
	private LinkedList<FamilyMember> family;
	private LinkedList<Pet> pets; 
	//private LinkedList<String> dislikedMeals;
	private LinkedList<String> fitness;//goes into health profile
//	private HealthProfile healthProfile;
//	private boolean wantsAlarm;
    private LinkedList<Meal> menu; //to be implemented with Meal class --JD
    private LinkedList<String> allergies;
    private LinkedList<String> dietaryRestrictions;
	
	public PatientProfile() {
		this.family = new LinkedList<FamilyMember>();
		this.pets = new LinkedList<Pet>();
		this.setFitness(new LinkedList<String>());
//		this.wantsAlarm = false;
        this.menu = new LinkedList<Meal>();     //to be implemented with Meal class --JD
        this.allergies = new LinkedList<String>();
        this.dietaryRestrictions = new LinkedList<String>();
	}
	
	public void addAllergy(String newAllergy){
		allergies.add(newAllergy);
	}
	public void removeAllergy(String allergy){
		allergies.remove(allergy);
	}
	public LinkedList<String> getAllergies(){
		return allergies;
	}
	public void addDietaryRestrictions(String newDietaryRestrictions){
		dietaryRestrictions.add(newDietaryRestrictions);
	}
	public void removeDietaryRestrictions(String restrictions){
		dietaryRestrictions.remove(restrictions);
	}
	public LinkedList<String> getDietaryRestrictions(){
		return dietaryRestrictions;
	}
	public void addFamily(FamilyMember newFamily) {
		family.add(newFamily);
	}
	
	public void addPet(Pet newPet) {
		pets.add(newPet);
	}
	
	public void addMeal(Meal newMeal) { 	//to be implemented with Meal class --JD
		menu.add(newMeal);
	}
		
	public void removeFamily(FamilyMember family) {
		this.family.remove(family);
	}
	
	public void removePet(Pet pet) {
		pets.remove(pet);
	}
	
	public void removeMeal (Meal removedmeal) {	//to be implemented with Meal class --JD
		menu.remove(removedmeal);
	}
		
//	public void setHealthProfile(HealthProfile profile) {
//		healthProfile = profile;
//	}
	
//	public void setAlarm(boolean alarm) {
//		wantsAlarm = alarm;
//	}
	
	public LinkedList<FamilyMember> getFamily() {
		return family;
	}
	
	public LinkedList<Pet> getPets() {
		return pets;
	}
	
	public LinkedList<Meal> getMenu() {			//to be implemented with Meal class --JD
		return menu;
	}	 

	public LinkedList<String> getFitness() {
		return fitness;
	}
	
	public void setFamily(LinkedList<FamilyMember> family) {
		this.family = family;
	}
	
	public void setFitness(LinkedList<String> fitness) {
		this.fitness = fitness;
	}
	
	public void addFitness(String newFitness) {
		this.fitness.add(newFitness);
	}
	public void setMenu(LinkedList<Meal> menu) {			//to be implemented with Meal class --JD
		this.menu = menu;
	}			
	
	
//	public HealthProfile getHealthProfile() {
//		return healthProfile;
//	}
//	
//	public boolean yesAlarm() {
//		return wantsAlarm;
//	}
}
