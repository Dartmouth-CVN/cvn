package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7223896053950926127L;
	private String id;
	private List<String> phone;
	private List<String> email;
	private List<String> address;

	public Contact(String id) {
		this.id = id;
		this.phone = new LinkedList<String>();
		this.email = new LinkedList<String>();
		this.address = new LinkedList<String>();
	}

	public Contact() {
		this.id = "Unassigned";
		this.phone = new LinkedList<String>();
		this.email = new LinkedList<String>();
		this.address = new LinkedList<String>();
	}

	public String getID() {
		return this.id;
	}

	public final List<String> getPhoneList() {
		return phone;
	}

	public void addPhoneList(LinkedList<String> phoneList) {
		phone.addAll(phoneList);
	}

	public final String getPrimaryPhone() {
		return phone.get(0);
	}

	public final void removePhone(String iphone) {
		if (phone.contains(iphone))
			phone.remove(iphone);
	}
	
	public void addPhone(String p){
		phone.add(p);
	}

	public void addEmail(String e){
		email.add(e);
	}
	

	public void addAddress(String ad){
		address.add(ad);
	}
	
	public final List<String> getEmailList() {
		return email;
	}

	public void addEmailList(LinkedList<String> emailList) {
		email.addAll(emailList);
	}

	public final String getPrimaryEmail() {
		return email.get(0);
	}

	public final void removeEmail(String iemail) {
		if (email.contains(iemail))
			email.remove(iemail);
	}

	public List<String> addressProperty() {
		return address;
	}

	public final List<String> getAddressList() {
		return address;
	}

	public void addAddressList(LinkedList<String> addressList) {
		address.addAll(addressList);
	}

	public final String getPrimaryAddress() {
		return addressProperty().get(0);
	}

	public final void removeAddress(String ad) {
		if (address.contains(ad))
			address.remove(ad);
	}

}