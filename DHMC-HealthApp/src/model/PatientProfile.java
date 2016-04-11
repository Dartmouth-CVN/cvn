package model;

import java.util.LinkedList;

public class PatientProfile {
	private LinkedList<String> family;
	private LinkedList<String> pets;
	private LinkedList<String> likedMeals;
	private LinkedList<String> dislikedMeals;
	private LinkedList<String> fitness;//goes into health profile
//	private HealthProfile healthProfile;
//	private boolean wantsAlarm;
	
	public PatientProfile() {
		this.family = new LinkedList<String>();
		this.pets = new LinkedList<String>();
		this.likedMeals = new LinkedList<String>();
		this.dislikedMeals = new LinkedList<String>();
		this.setFitness(new LinkedList<String>());
//		this.wantsAlarm = false;
	}
	
	public void addFamily(String newFamily) {
		family.add(newFamily);
	}
	
	public void addPet(String newPet) {
		pets.add(newPet);
	}
	
	public void addLikedMeal(String newMeal) {
		likedMeals.add(newMeal);
	}
	
	public void addDislikedMeal(String newMeal) {
		dislikedMeals.add(newMeal);
	}
	
	public void removeFamily(String family) {
		this.family.remove(family);
	}
	
	public void removePet(String pet) {
		pets.remove(pet);
	}
	
	public void removeLikedMeal(String meal) {
		likedMeals.remove(meal);
	}
	
	public void removeDislikedMeal(String meal) {
		dislikedMeals.remove(meal);
	}
	
	
//	public void setHealthProfile(HealthProfile profile) {
//		healthProfile = profile;
//	}
	
//	public void setAlarm(boolean alarm) {
//		wantsAlarm = alarm;
//	}
	
	public LinkedList<String> getFamily() {
		return family;
	}
	
	public LinkedList<String> getPets() {
		return pets;
	}
	
	public LinkedList<String> getLikedMeals() {
		return likedMeals;
	}
	
	public LinkedList<String> getDislikedMeals() {
		return dislikedMeals;
	}

	public LinkedList<String> getFitness() {
		return fitness;
	}
	
	public void setFamily(LinkedList<String> family) {
		this.family = family;
	}
	
	public void setLikedMeals(LinkedList<String> LikedMeal) {
		this.likedMeals = LikedMeal;
	}
	
	public void setDislikedMeals(LinkedList<String> dislikedMeals) {
		this.dislikedMeals = dislikedMeals;
	}
	
	public void setFitness(LinkedList<String> fitness) {
		this.fitness = fitness;
	}
	
	public void addFitness(String newFitness) {
		this.fitness.add(newFitness);
	}
	
//	public HealthProfile getHealthProfile() {
//		return healthProfile;
//	}
//	
//	public boolean yesAlarm() {
//		return wantsAlarm;
//	}
}
