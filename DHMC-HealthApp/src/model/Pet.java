package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pet {
	private StringProperty name;
	private StringProperty species;
	private BooleanProperty allergyFriendly;
	private int petID;
	
	public Pet(String name, String species, boolean allergyFriendly) {
		this.name = new SimpleStringProperty(name);
		this.species = new SimpleStringProperty(species);
		this.allergyFriendly = new SimpleBooleanProperty(allergyFriendly);
	}
	
	public Pet() {
		this.name.set("New Pet");
		this.species.set("");
		this.allergyFriendly.set(false);
	}

	public void setPetID(int id){
		this.petID = id;
	}
	
	public int getPetID(){
		return this.petID;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
    public final String getName() {
        return nameProperty().get();
    }

    public final void setName(String name) {
        nameProperty().set(name);
    }
    
	public StringProperty speciesProperty() {
		return species;
	}
	
    public final String getSpecies() {
        return speciesProperty().get();
    }

    public final void setSpecies(String species) {
        speciesProperty().set(species);
    }
    
	public BooleanProperty allergyFriendlyProperty() {
		return allergyFriendly;
	}
	
    public final boolean getAllergyFriendly() {
        return allergyFriendlyProperty().get();
    }

    public final void setAllergyFriendly(boolean allergyFriendly) {
        allergyFriendlyProperty().set(allergyFriendly);
    }
}
