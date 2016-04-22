package model;

import java.time.LocalDate;
import java.util.List;

public class HealthInfo {
	int healthInfoId;
	LocalDate date;
	List<HealthAttribute<?>> attributes;
	
	public HealthInfo () {
	}

	public HealthInfo(LocalDate date, List<HealthAttribute<?>> attributes) {
		this.date = date;
		this.attributes = attributes;
	}
	
	public int getHealthInfoId() {
		return healthInfoId;
	}

	public void setHealthInfoId(int healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<HealthAttribute<?>> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<HealthAttribute<?>> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(HealthAttribute<?> att) {
		attributes.add(att);
	}
	
	public String toString() {
		String retString = "Date: " + date.toString();
		for(int i=0; i<attributes.size(); i++) {
			retString += ("\n" + attributes.get(i).getName() + ": " + attributes.get(i).getStringValue());
		}
		return retString;
	}
}
