package model;

import java.io.Serializable;
import java.util.LinkedList;

import javafx.beans.property.StringProperty;

public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2789588534495622114L;
	private String id;
	private LinkedList<String> phone;
	private LinkedList<String> email;
	private LinkedList<String> address;

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

	public LinkedList<String> getPhone() {
		return this.phone;
	}

	public LinkedList<String> getEmail() {
		return this.email;
	}

	public LinkedList<String> getAddress() {
		return this.address;
	}

	public String getPrimaryPhone() {
		return this.phone.getFirst();
	}

	public String getPrimaryEmail() {
		if (!this.address.isEmpty())
			return this.address.getFirst();
		else
			return "";
	}

	public void setPhone(LinkedList<String> phone) {
		this.phone = phone;
	}

	public void setEmail(LinkedList<String> email) {
		this.email = email;
	}

	public void setAddress(LinkedList<String> address) {
		this.address = address;
	}

	public String getPrimaryAddress() {
		if (!this.address.isEmpty())
			return this.address.getFirst();
		else
			return "";
	}

	public void addPhone(String phone) {
		if (this.phone.indexOf(phone) < 0) {
			this.phone.add(phone);
		}
	}

	public void addEmail(String email) {
		if (this.email.indexOf(email) < 0) {
			this.email.add(email);
		}
	}

	public void addAddress(String address) {
		if (this.address.indexOf(address) < 0) {
			this.address.add(address);
		}
	}

	public void removePhone(String phone) {
		if (this.phone.indexOf(phone) >= 0) {
			this.phone.remove(phone);
		}
	}

	public void removeEmail(String email) {
		if (this.email.indexOf(email) >= 0) {
			this.email.remove(email);
		}
	}

	public void removeAddress(String address) {
		if (this.address.indexOf(address) >= 0) {
			this.address.remove(address);
		}
	}

	public void makePrimaryPhone(String phone) {
		this.removePhone(phone);
		this.phone.addFirst(phone);
	}

	public void makePrimaryEmail(String email) {
		this.removeEmail(email);
		this.email.addFirst(email);
	}

	public void makePrimaryAddress(String address) {
		this.removeAddress(address);
		this.address.addFirst(address);
	}

	public StringProperty getAddressProperty() {
		// TODO getAddressProperty
		return null;
	}

	public StringProperty getPhoneProperty() {
		// TODO getPhoneProperty
		return null;
	}

	public StringProperty getEmailProperty() {
		// TODO getEmailProperty
		return null;
	}

}
