package model;

import javafx.beans.property.StringProperty;

public interface IDisplayable {

	public String getUserID();

	public String getFirstName();

	public String getLastName();

	public StringProperty firstNameProperty();

	public StringProperty lastNameProperty();
	
	public StringProperty userIDProperty();
	
	public void initObservers();

}
