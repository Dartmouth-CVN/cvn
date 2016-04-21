package model;

import java.time.LocalDate;

public class HealthInfo {
	int healthInfoId;
	LocalDate date;
	HealthAttribute<?> attribute;
	
	public HealthInfo () {
	}

	public HealthInfo(LocalDate date, HealthAttribute<?> attribute) {
		this.date = date;
		this.attribute = attribute;
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

	public HealthAttribute<?> getAttribute() {
		return attribute;
	}

	public void setAttribute(HealthAttribute<?> attribute) {
		this.attribute = attribute;
	}
	
}
