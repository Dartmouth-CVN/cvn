package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface IDisplayable {

	public String getUserID();

	public String getFirstName();

	public String getLastName();

	public StringProperty getFirstNameProperty();

	public StringProperty getLastNameProperty() ;

	public IntegerProperty getUserIDProperty();

}
