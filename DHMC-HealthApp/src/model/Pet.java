package model;

public class Pet {
	private String species;
	private int quantity;
	private boolean allergyFriendly;
	
	public Pet(String species, int quantity, boolean allergyFriendly){
		this.species = species;
		this.quantity = quantity;
		this.allergyFriendly = allergyFriendly;
	}
public String getSpecies(){
	return this.species;
}

public int getQuantity(){
	return this.quantity;
}

public boolean isAllergy(){
	return this.allergyFriendly;
}

public void setSpecies(String species){
	this.species = species;
}

public void setQuantity(int quantity){
	this.quantity = quantity;
}

public void setAllergyFriendly(boolean allergyFriendly){
	this.allergyFriendly = allergyFriendly
}

}
}
