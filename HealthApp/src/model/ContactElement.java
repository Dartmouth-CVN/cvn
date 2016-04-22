package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ContactElement {
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
	int elementId = 0;
	String value;
	String type;
	
	public ContactElement(){
		
	}
	
	public ContactElement(int elementId, String value, String type) {
		this.elementId = elementId;
		this.value = value;
		this.type = type;
	}
	
	public ContactElement(String value, String type) {
		this.value = value;
		this.type = type;
	}
	
	public int getElementId() {
		return elementId;
	}
	public void setElementId(int elementId) {
		this.elementId = elementId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
