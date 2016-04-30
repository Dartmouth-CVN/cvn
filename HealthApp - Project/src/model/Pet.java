package model;

public class Pet{
    long petId;
    String name;
    String species;
    boolean allergyFriendly;

    public Pet() {
        this(0, "Enter name", "Enter species", true);
    }

    public Pet(String name, String species, boolean allergyFriendly) {
        this(0, name, species, allergyFriendly);
    }

    public Pet(long id, String name, String species, boolean allergyFriendly) {
        this.petId = id;
        this.name = name;
        this.species = species;
        this.allergyFriendly = allergyFriendly;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long id) {
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

    @Override
    public String toString(){
        return String.format("%s, %s, %s", name, species, allergyFriendly);
    }

    public static Pet fromSVString(String line){
        String[] values = line.split(",");
        String name = "";
        String species = "";
        boolean allergyFriendly = false;
        if(values.length > 0) {
            name = values[0];
            species = values[1];
            allergyFriendly = Boolean.parseBoolean(values[2]);
        }
        return new Pet(name, species, allergyFriendly);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof  Pet){
            Pet other = (Pet) o;
            return name.equals(other.getName()) && species.equals(other.getSpecies());
        }
        return false;
    }
}
