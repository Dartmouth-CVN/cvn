package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class HealthProfile implements Serializable{
	long healthProfileId;
	List<HealthAttribute<?>> healthInfo;
	List<String> allergies;
	List<String> dietaryRestrictions;
	String userId;

	public HealthProfile() {
		this( 0, "", new LinkedList<HealthAttribute<?>>(), new LinkedList<String>(), new LinkedList<String>());
	}

	public HealthProfile(long id, String userId, List<HealthAttribute<?>> healthInfo, List<String> allergies, List<String> dietaryRestrictions) {
		this.healthProfileId = id;
		this.userId = userId;
		this.healthInfo = healthInfo;
		this.allergies = allergies;
		this.dietaryRestrictions = dietaryRestrictions;
	}
	
	public void addNewHealthInfo(HealthAttribute<?> info){
		healthInfo.add(info);
	}

	public long getHealthProfileId() {
		return healthProfileId;
	}

	public void setHealthProfileId(long healthProfileId) {
		this.healthProfileId = healthProfileId;
	}

	public List<HealthAttribute<?>> getHealthInfo() {
		return healthInfo;
	}

	public void setHealthInfo(List<HealthAttribute<?>> healthInfo) {
		this.healthInfo = healthInfo;
	}

	public void addHealthInfoList(List<HealthAttribute<?>> info) {
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
		return "";
	}

	@Override
	public int hashCode(){
		return healthInfo.hashCode() * allergies.hashCode() * dietaryRestrictions.hashCode();
	}
	
}
