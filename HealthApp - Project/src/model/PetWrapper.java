package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by mrampiah on 4/24/16.
 */
public class PetWrapper {

    StringProperty nameProperty;
    StringProperty speciesProperty;
    BooleanProperty allergyFriendlyProperty;
    Pet pet;

    public PetWrapper(Pet p) {
        this.pet = p;
        setNameProperty(p.getName());
        setAllergyFriendlyProperty(p.allergyFriendly);
        setSpeciesProperty(p.getSpecies());
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public void setNameProperty(String name) {
        nameProperty = new SimpleStringProperty(name);
    }

    public StringProperty getSpeciesProperty() {
        return speciesProperty;
    }

    public void setSpeciesProperty(String species) {
        speciesProperty = new SimpleStringProperty(species);
    }

    public BooleanProperty getAllergyFriendlyProperty() {
        return allergyFriendlyProperty;
    }

    public void setAllergyFriendlyProperty(boolean allergyFriendly) {
        allergyFriendlyProperty = new SimpleBooleanProperty(allergyFriendly);
    }

    public Pet toPet() {
        return this.pet;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof PetWrapper){
            Pet other = ((PetWrapper) o).toPet();
            return other.getName().equals(pet.getName()) && other.getSpecies().equals(pet.getSpecies());
        }
        return false;
    }
}
