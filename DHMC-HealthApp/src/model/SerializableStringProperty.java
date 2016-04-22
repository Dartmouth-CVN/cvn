package model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class SerializableStringProperty extends SimpleStringProperty implements Serializable {
	
	public SerializableStringProperty(String num){
		super(num);
	}

}
