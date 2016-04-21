package model;

public class HealthAttribute<V> {
	int healthAttributeId;
	String name;
	V value;
	String stringValue;
	
	
	public HealthAttribute(int healthAttributeId, String name, V value) {
		this.healthAttributeId = healthAttributeId;
		this.name = name;
		this.value = value;
	}
	
	public HealthAttribute(String name, V value) {
		this.name = name;
		this.value = value;
	}
	
	public int getHealthAttributeId() {
		return healthAttributeId;
	}
	public void setHealthAttributeId(int healthAttributeId) {
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
	}
	public String getStringValue() {
		setStringValue();
		return stringValue;
	}
	public void setStringValue() {
		stringValue = String.valueOf(value);
	}
	
	
}
