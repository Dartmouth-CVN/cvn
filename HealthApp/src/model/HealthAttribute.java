package model;

import javafx.beans.property.*;
import utils.RandomGenerator;

import java.time.LocalDate;
import java.util.Date;

public class HealthAttribute<V> {
	long healthAttributeId;
	Date date;
	String name;
	V value;
	String stringValue;

	LongProperty healthAttributeIdProperty;
	StringProperty nameProperty;
	ObjectProperty<V> valueProperty;
	StringProperty stringValueProperty;

	HealthProfile healthProfileKey;
	
	public HealthAttribute(long healthAttributeId, Date date, String name, V value) {
		this.date = date;
		this.healthAttributeId = healthAttributeId;
		this.name = name;
		this.value = value;
	}


	public HealthAttribute(Date date, String name, V value) {
		this(RandomGenerator.getRandomId(new Object()), date, name, value);
	}
	
	public long getHealthAttributeId() {
		return healthAttributeId;
	}
	public void setHealthAttributeId(long healthAttributeId) {
		this.healthAttributeId = healthAttributeId;
		setHealthAttributeIdProperty(healthAttributeId);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		setNameProperty(name);
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
		setStringValue();
		setValueProperty(value);
	}
	public String getStringValue() {
		setStringValue();
		return stringValue;
	}
	public void setStringValue() {
		stringValue = String.valueOf(value);
		setStringValueProperty(stringValue);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}


	public LongProperty getHealthAttributeIdProperty() {
		return healthAttributeIdProperty;
	}

	public StringProperty getNameProperty() {
		return nameProperty;
	}

	public ObjectProperty<V> getValueProperty() {
		return valueProperty;
	}

	public StringProperty getStringValueProperty() {
		return stringValueProperty;
	}

	public void setHealthAttributeIdProperty(long healthAttributeId) {
		healthAttributeIdProperty = new SimpleLongProperty(healthAttributeId);
	}

	public void setNameProperty(String name) {
		nameProperty = new SimpleStringProperty(name);
	}

	public void setValueProperty(V value) {
		valueProperty = new SimpleObjectProperty<V>(value);
	}

	public void setStringValueProperty(String stringValue) {
		stringValueProperty = new SimpleStringProperty(stringValue);
	}

	@Override
	public int hashCode(){
		return  name.hashCode() * stringValue.hashCode();
	}

	public HealthProfile getHealthProfileKey(){
		return healthProfileKey;
	}

	public void setHealthProfileKey(HealthProfile healthProfileKey){
		this.healthProfileKey = healthProfileKey;
	}
}
