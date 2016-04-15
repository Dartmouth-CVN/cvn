package model;

public class Pet {
	private String name;
	private String species;
	private boolean allergyFriendly;
	private int petID;
	
	public Pet(String name, String species, boolean allergyFriendly) {
		this.setName(name);
		this.species = species;
		this.allergyFriendly = allergyFriendly;
	}
	
	public Pet() {
		this.name = "New Pet";
		this.species = "";
		this.allergyFriendly = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return this.species;
	}


	public boolean isAllergyFriendly() {
		return this.allergyFriendly;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	
	public boolean getAllergyFriendly() {
		return this.allergyFriendly;
	}

	public void setAllergyFriendly(boolean allergyFriendly) {
		this.allergyFriendly = allergyFriendly;
	}

	public void setPetID(int id){
		this.petID = id;
	}
	
	public int getPetID(){
		return this.petID;
	}
}
