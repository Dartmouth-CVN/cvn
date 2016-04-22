package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class Pet {
	int petId;
	String name;
	String species;
	boolean allergyFriendly;
	
	public Pet(){}
	
	public Pet(int id, String name, String species, boolean allergyFriendly){
		this(name, species, allergyFriendly);
		this.petId = id;
	}
	
	public Pet(String name, String species, boolean allergyFriendly){
		this.name = name;
		this.species = species;
		this.allergyFriendly = allergyFriendly;
	}
	
	public int getPetId(){
		return petId;
	}
	
	@SuppressWarnings("unused")
	private void setPetId(int id){
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
}
