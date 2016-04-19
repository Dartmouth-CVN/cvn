package model;

import java.util.HashSet;
import java.util.Set;

public class PatientProfile {
	int profileId;
	Set<Pet> pets;
	
	public PatientProfile(){
		pets = new HashSet<Pet>();
	}
	
	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}
	
	public int getProfileId(){
		return profileId;
	}
	
	private void setProfileId(int profileId){
		this.profileId = profileId;
	}
	
	public void addPet(Pet pet){
		pets.add(pet);
	}
	
	public void removePet(Pet pet){
		pets.remove(pet);
	}
	
	public void removePet(String name){
		
	}
}
