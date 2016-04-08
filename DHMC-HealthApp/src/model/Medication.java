package model;

import java.time.LocalDate;

public class Medication {
	private String name;
	private String dosage;
	private String directions;
	private int refills;
	private LocalDate nextRefillDate;

	public Medication(String name, String dosage, String directions, int refills, LocalDate nextRefillDate) {
		this.name = name;
		this.dosage = dosage;
		this.directions = directions;
		this.refills = refills;
		this.nextRefillDate = nextRefillDate;
	}
	
	public Medication(String name) {
		this.name = name;
		this.dosage = "Enter Dosage";
		this.directions = "Enter Directions";
		this.refills = 0;
		this.nextRefillDate = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDosage() {
		return this.dosage;
	}
	
	public String getDirections() {
		return this.directions;
	}
	
	public int getRefills() {
		return this.refills;
	}
	
	public LocalDate getNextRefillDate() {
		return this.nextRefillDate;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	
	public void setDirections(String directions) {
		this.directions = directions;
	}
	
	public void setRefills(int refills) {
		this.refills = refills;
	}
	
	public void getNextRefillDate(LocalDate date) {
		this.nextRefillDate = date;
	}
}
