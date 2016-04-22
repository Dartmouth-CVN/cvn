package model;

import java.util.LinkedList;
import java.util.List;

public class HealthProfile {

	int healthProfileId;
	List<HealthInfo> healthInfo;
	List<String> allergies;
	List<String> dietaryRestrictions;

	public HealthProfile() {
		this.healthInfo = new LinkedList<HealthInfo>();
		this.allergies = new LinkedList<String>();
		this.dietaryRestrictions = new LinkedList<String>();
	}
	
	public void addNewHealthInfo(HealthInfo info){
		healthInfo.add(info);
	}

	public int getHealthProfileId() {
		return healthProfileId;
	}

	public void setHealthProfileId(int healthProfileId) {
		this.healthProfileId = healthProfileId;
	}

	public List<HealthInfo> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(List<HealthInfo> healthInfo) {
		this.healthInfo = healthInfo;
	}

	public void addHealthInfoList(List<HealthInfo> info) {
		healthInfo.addAll(info);
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	public List<String> getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	public void setDietaryRestrictions(List<String> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}
	
	public String toString() {
//		String retString = "Health Profile.";
//		healthInfo.forEach((k, v)-> retString += ("\n" + v.toString()));
		return "";
	}
	
}
