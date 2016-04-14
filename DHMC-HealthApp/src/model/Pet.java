package model;

public class Pet {
	private String species;
	private int quantity;
	private boolean allergyFriendly;
	private int petID;
	public Pet(String species, int quantity, boolean allergyFriendly) {
		this.species = species;
		this.quantity = quantity;
		this.allergyFriendly = allergyFriendly;
	}

	public String getSpecies() {
		return this.species;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public boolean isAllergy() {
		return this.allergyFriendly;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
