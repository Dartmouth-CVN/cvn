package model;

import java.util.LinkedList;

class PatientProfile {
	private LinkedList<String> family;
	private LinkedList<String> pets;
	private LinkedList<String> likedMeals;
	private LinkedList<String> dislikedMeals;
	private HealthProfile healthProfile;
	private boolean wantsAlarm;
	
	public PatientProfile() {
		this.family = new LinkedList<String>();
		this.pets = new LinkedList<String>();
		this.likedMeals = new LinkedList<String>();
		this.dislikedMeals = new LinkedList<String>();
		this.wantsAlarm = false;
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
	
	public void setHealthProfile(HealthProfile profile) {
		healthProfile = profile;
	}
	
	public void setAlarm(boolean alarm) {
		wantsAlarm = alarm;
	}
	
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
	
	public HealthProfile getHealthProfile() {
		return healthProfile;
	}
	
	public boolean yesAlarm() {
		return wantsAlarm;
	}
}
