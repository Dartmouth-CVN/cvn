package model;

public class ContactElement {
	int elementId;
	String value;
	String type;
	
	public ContactElement(int elementId, String value, String type) {
		super();
		this.elementId = elementId;
		this.value = value;
		this.type = type;
	}
	
	public ContactElement(String value, String type) {
		elementId = 0;
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
