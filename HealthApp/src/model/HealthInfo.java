package model;

import java.time.LocalDate;
import java.util.LinkedList;

public class HealthInfo {
	LocalDate date;
	LinkedList<HealthAttribute<?>> attributes;
	
	public HealthInfo () {
	}

	public HealthInfo(LocalDate date, LinkedList<HealthAttribute<?>> attributes) {
		this.date = date;
		this.attributes = attributes;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LinkedList<HealthAttribute<?>> getAttributes() {
		return attributes;
	}

	public void setAttributes(LinkedList<HealthAttribute<?>> attributes) {
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
