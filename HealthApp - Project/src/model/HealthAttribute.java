package model;

import utils.RandomGenerator;
import java.io.Serializable;
import java.util.Date;

public class HealthAttribute<V> implements Serializable{
	long healthAttributeId;
	Date date;
	String name;
	V value;
	String stringValue;
	
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
	public void setStringValue() {
		stringValue = String.valueOf(value);
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


	@Override
	public int hashCode(){
		return  name.hashCode() * stringValue.hashCode();
	}
}
