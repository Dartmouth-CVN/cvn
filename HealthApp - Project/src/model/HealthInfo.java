package model;

import utils.RandomGenerator;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HealthInfo {
	long healthInfoId;

	LocalDate date;
	List<HealthAttribute<?>> attributes;
	
	public HealthInfo () {
		this.attributes = new LinkedList<HealthAttribute<?>>();
	}

	public HealthInfo(LocalDate date, List<HealthAttribute<?>> attributes) {
		this.date = date;
		this.attributes = attributes;
	}
	
	public long getHealthInfoId() {
		return healthInfoId;
	}

	public void setHealthInfoId(long healthInfoId) {
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

	@Override
	public String toString() {
		String retString = "Date: " + date.toString();
		for(int i=0; i<attributes.size(); i++) {
			retString += ("\n" + attributes.get(i).getName() + ": " + attributes.get(i).getStringValue());
		}
		return retString;
	}

	@Override
	public int hashCode(){
		return  (int ) (RandomGenerator.getRandomNumber().nextLong() * attributes.hashCode());
	}
}
