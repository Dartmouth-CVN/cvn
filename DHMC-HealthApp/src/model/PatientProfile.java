package model;

import java.util.LinkedList;

public class PatientProfile {
	private LinkedList<Caregiver> caregivers;
	private LinkedList<Pet> pets; 
	private LinkedList<String> fitness;//goes into health profile
//	private HealthProfile healthProfile;
//	private boolean wantsAlarm;
    private LinkedList<Meal> menu; //to be implemented with Meal class --JD
    private LinkedList<String> allergies;
    private LinkedList<String> dietaryRestrictions;
	
	public PatientProfile() {
		this.caregivers = new LinkedList<Caregiver>();
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
	public void addCaregiver(Caregiver newCaregiver) {
		caregivers.add(newCaregiver);
	}
	
	public void addPet(Pet newPet) {
		pets.add(newPet);
	}
	
	public void addMeal(Meal newMeal) { 	//to be implemented with Meal class --JD
		menu.add(newMeal);
	}
		
	public void removeCaregiver(Caregiver newCaregiver) {
		this.caregivers.remove(newCaregiver);
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
	
	public LinkedList<Caregiver> getCaregiver() {
		return caregivers;
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
	
	public void setCaregiver(LinkedList<Caregiver> newCaregiver) {
		this.caregivers = newCaregiver;
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
