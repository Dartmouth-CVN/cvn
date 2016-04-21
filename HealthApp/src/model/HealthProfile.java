package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

public class HealthProfile {

	int healthProfileId;
	HashMap<LocalDate, HealthInfo> healthInfo;
	Set<String> allergies;
	Set<String> dietaryRestrictions;

	public HealthProfile() {
		this.healthInfo = new HashMap<LocalDate, HealthInfo>();
		this.allergies = new HashSet<String>();
		this.dietaryRestrictions = new HashSet<String>();
	}
	
	public void addNewHealthInfo(HealthInfo info){
		healthInfo.put(info.getDate(), info);
	}

	public int getHealthProfileId() {
		return healthProfileId;
	}

	public void setHealthProfileId(int healthProfileId) {
		this.healthProfileId = healthProfileId;
	}

	public HashMap<LocalDate, HealthInfo> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(HashMap<LocalDate, HealthInfo> healthInfo) {
		this.healthInfo = healthInfo;
	}

	public void addHealthInfo(HealthInfo newInfo) {
		this.healthInfo.put(newInfo.getDate(), newInfo);
	}

	public void addHealthInfoList(HashMap<LocalDate, HealthInfo> info) {
		healthInfo.putAll(info);
	}

	public Set<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<String> allergies) {
		this.allergies = allergies;
	}

	public Set<String> getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	public void setDietaryRestrictions(Set<String> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}
	
	public String toString() {
		String retString = "Health Profile.";
		healthInfo.forEach((k, v)-> retString += ("\n" + v.toString()));
		return "";
	}
	
}
