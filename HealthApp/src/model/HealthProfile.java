package model;

import java.util.LinkedList;

public class HealthProfile {

	int healthProfileId;
	LinkedList<HealthInfo> healthInfo;
	LinkedList<String> allergies;
	LinkedList<String> dietaryRestrictions;

	public HealthProfile() {
		this.healthInfo = new LinkedList<HealthInfo>();
	}
	
	public int getHealthProfileId(){
		return healthProfileId;
	}
	
	public void setHealthProfileId(int healthProfileId){
		this.healthProfileId = healthProfileId;
	}

	public LinkedList<HealthInfo> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(LinkedList<HealthInfo> healthInfo) {
		this.healthInfo = healthInfo;
	}

	public void addHealthInfo(HealthInfo newInfo) {
		this.healthInfo.add(newInfo);
	}

	public void addHealthInfoList(LinkedList<HealthInfo> info) {
		for (int i = 0; i < info.size(); i++) {
			for (int j = 0; j < healthInfo.size(); j++) {
				if (info.get(i).equals(healthInfo.get(j))) {
					info.remove(i);
				}
			}
			healthInfo.add(info.get(i));
		}
	}

	public LinkedList<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(LinkedList<String> allergies) {
		this.allergies = allergies;
	}

	public LinkedList<String> getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	public void setDietaryRestrictions(LinkedList<String> dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}
}
