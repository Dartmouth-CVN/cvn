package model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class SerializableStringProperty extends SimpleStringProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8304009182069183623L;
	
	public SerializableStringProperty(String num){
		super(num);
	}

}
