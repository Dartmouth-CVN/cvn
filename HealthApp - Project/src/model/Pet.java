package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class Pet implements Serializable{
	long petId;
	String name;
	String species;
	boolean allergyFriendly;
	String userId;
	
	public Pet(){
		this(0, "Enter name", "Enter species", true, "");
	}
	
	public Pet(String name, String species, boolean allergyFriendly, String userId){
		this(0, name, species, allergyFriendly, userId);
	}

	public Pet(long id, String name, String species, boolean allergyFriendly, String userId){
		this.petId = id;
		this.name = name;
		this.species = species;
		this.allergyFriendly = allergyFriendly;
		this.userId = userId;
	}
	
	public long getPetId(){
		return petId;
	}
	
	@SuppressWarnings("unused")
	private void setPetId(long id){
		this.petId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public boolean isAllergyFriendly() {
		return allergyFriendly;
	}

	public void setAllergyFriendly(boolean allergyFriendly) {
		this.allergyFriendly = allergyFriendly;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserId(String id){
		userId = id;
	}
}
