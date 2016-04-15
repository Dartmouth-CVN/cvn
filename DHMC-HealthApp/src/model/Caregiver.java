package model;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Caregiver {
	private String name;
	private LocalDate birthday;
	private String relation;
	private Contact contactInfo;
	private boolean isFamily;
	private int caregiverID;
	
	private StringProperty nameProperty;
	private StringProperty relationProperty;
	
	public Caregiver (String name, LocalDate birthday, String relation, Contact contactInfo, boolean isFamily){
		this.name = name;
		this.birthday = birthday;
		this.relation = relation;
		this.contactInfo = contactInfo;
		this.isFamily = isFamily;
		nameProperty = new SimpleStringProperty(name);
		relationProperty = new SimpleStringProperty(relation);	
	}
	
	public StringProperty getNameProperty(){
		return nameProperty;
	}
	
	public StringProperty getRelationProperty(){
		return relationProperty;
	}
	
	public Caregiver() {
		this.name = "New Caregiver";
		this.birthday = LocalDate.now();
		this.relation = "";
		this.contactInfo = new Contact();
		this.isFamily = true;
	}
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Contact getContactInfo() {
		return contactInfo;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsFamily() {
		return isFamily;
	}

	public void setIsFamily(boolean familymember) {
		this.isFamily = familymember;
	}

	public int getCaregiverID() {
		return caregiverID;
	}

	public void setCaregiverID(int caregiverID) {
		this.caregiverID = caregiverID;
	}
	

}

