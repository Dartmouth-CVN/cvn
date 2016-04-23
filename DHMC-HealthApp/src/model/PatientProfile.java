package model;

import java.util.LinkedList;
import java.util.List;

public class PatientProfile {
	private List<Caregiver> caregivers;
	private List<Pet> pets;
    private List<Meal> menu; //to be implemented with Meal class --JD
    private List<String> allergies; //moved to healthInfo?
    private List<String> dietaryRestrictions; //might be better as part of menu? maybe a "restricted" boolean
	
	public PatientProfile() {
		this.caregivers = new LinkedList<Caregiver>();
		this.pets = new LinkedList<Pet>();
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
	public List<String> getAllergies(){
		return allergies;
	}
	public void addDietaryRestrictions(String newDietaryRestrictions){
		dietaryRestrictions.add(newDietaryRestrictions);
	}
	public void removeDietaryRestrictions(String restrictions){
		dietaryRestrictions.remove(restrictions);
	}
	public List<String> getDietaryRestrictions(){
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
		
	
	public List<Caregiver> getCaregivers() {
		return caregivers;
	}
	
	public List<Pet> getPets() {
		return pets;
	}
	
	public List<Meal> getMenu() {			//to be implemented with Meal class --JD
		return menu;
	}	 

	public void setCaregiver(List<Caregiver> newCaregiver) {
		this.caregivers = newCaregiver;
	}
	
	public void setMenu(List<Meal> list) {			//to be implemented with Meal class --JD
		this.menu = list;
	}	
	
	public void setPet(List<Pet> petlist){
		this.pets = petlist;
	}
	
}
