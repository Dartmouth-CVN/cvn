package model;

import utils.RandomGenerator;

import java.io.Serializable;
import java.time.LocalDate;

public class HealthAttribute<V>{
    long healthAttributeId;
    LocalDate date;
    String name;
    V value;
    String stringValue;

    public HealthAttribute(long healthAttributeId, LocalDate date, String name, V value) {
        this.date = date;
        this.healthAttributeId = healthAttributeId;
        this.name = name;
        this.value = value;
    }


    public HealthAttribute(LocalDate date, String name, V value) {
        this(RandomGenerator.getRandomId(new Object()), date, name, value);
    }

    public HealthAttribute(long healthAttributeId, LocalDate date, String name, String stringValue) {
        this.date = date;
        this.healthAttributeId = healthAttributeId;
        this.name = name;
        this.stringValue = stringValue;
        this.value = (V) stringValue;
    }

    public long getHealthAttributeId() {
        return healthAttributeId;
    }

    public void setHealthAttributeId(long healthAttributeId) {
        this.healthAttributeId = healthAttributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
        setStringValue();
    }

    public String getStringValue() {
        setStringValue();
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public void setStringValue() {
        stringValue = String.valueOf(value);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", name, date, stringValue == null? String.valueOf(value) : stringValue);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * stringValue.hashCode();
    }
}
