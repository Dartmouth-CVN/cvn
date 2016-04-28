package model;

import java.time.LocalDate;

public class Medication {
    String name;
    String dosage;
    String directions;
    int refills;
    LocalDate nextRefillDate;

    public Medication(String name, String dosage, String directions, int refills, LocalDate nextRefillDate) {
        this.name = name;
        this.dosage = dosage;
        this.directions = directions;
        this.refills = refills;
        this.nextRefillDate = nextRefillDate;
    }

    public Medication(String name) {
        this.name = name;
        this.dosage = "Enter Dosage";
        this.directions = "Enter Directions";
        this.refills = 0;
        this.nextRefillDate = null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return this.dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDirections() {
        return this.directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public int getRefills() {
        return this.refills;
    }

    public void setRefills(int refills) {
        this.refills = refills;
    }

    public LocalDate getNextRefillDate() {
        return this.nextRefillDate;
    }

    public void getNextRefillDate(LocalDate date) {
        this.nextRefillDate = date;
    }
}
