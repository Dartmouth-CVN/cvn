package model;

import java.util.HashSet;
import java.util.Set;

public class HealthProfile {

	int healthProfileId;
	Set<HealthInfo> healthInfo;
	Set<String> allergies;
	Set<String> dietaryRestrictions;

	public HealthProfile() {
		this.healthInfo = new HashSet<HealthInfo>();
		this.allergies = new HashSet<String>();
		this.dietaryRestrictions = new HashSet<String>();
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

	public Set<HealthInfo> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(Set<HealthInfo> healthInfo) {
		this.healthInfo = healthInfo;
	}

	public void addHealthInfo(HealthInfo newInfo) {
		this.healthInfo.add(newInfo);
	}

	public void addHealthInfoList(Set<HealthInfo> info) {
		healthInfo.addAll(info);
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
	
	
}
