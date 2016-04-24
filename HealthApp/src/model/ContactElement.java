package model;

import javafx.beans.property.*;
import utils.RandomGenerator;

import javax.persistence.*;
import java.util.Random;

public class ContactElement {
	long elementId = 0L;
	String value;
	String type;
	Contact contactKey;

	LongProperty elementIdProperty;
	StringProperty valueProperty;
	StringProperty typeProperty;
	
	public ContactElement(){
		
	}

	public ContactElement(long elementId, String value, String type) {
		setElementId(elementId);
		setValue(value);
		setType(type);
	}

	public ContactElement(String value, String type) {
		this(RandomGenerator.getRandomId(), value, type);
	}

	public long getElementId() {
		return elementId;
	}
	public void setElementId(long elementId) {
		this.elementId = elementId;
		setElementIdProperty(elementId);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
		setValueProperty(value);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		setTypeProperty(type);
	}

	public void setElementIdProperty(long elementId){
		elementIdProperty = new SimpleLongProperty((elementId));
	}

	public LongProperty getElementIdProperty(){
		return elementIdProperty;
	}

	public void setValueProperty(String value){
		valueProperty = new SimpleStringProperty(value);
	}

	public StringProperty getValueProperty(){
		return valueProperty;
	}

	public void setTypeProperty(String type){
		typeProperty = new SimpleStringProperty(type);
	}

	public StringProperty getTypeProperty(){
		return typeProperty;
	}

	@Override
	public int hashCode(){
		return value.hashCode() * type.hashCode();
	}


	public Contact getContactKey(){
		return contactKey;
	}

	public void setContactKey(Contact c){
		contactKey = c;
	}
}
