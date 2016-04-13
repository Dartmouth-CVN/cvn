package model;
import java.util.LinkedList;

public class Contact {
	private String id;
	private String phone;
	private String secondaryphone;
	private String email;
	private String address;

	public Contact(String id2, String phone, String secondaryphone, String email, String address) {
		this.id = id2;
		this.phone = phone;
		this.secondaryphone = secondaryphone;
		this.email = email;
		this.address = address;
	}
	//set id
	public String getID() {
		return this.id;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getAddress() {
		return this.address;
	
	}
	
	public void addPhone(String phone) {
		this.phone = phone;
	}
	
	public void addEmail(String email) {
		this.email = email;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSecondaryphone() {
		return secondaryphone;
	}

	public void setSecondaryphone(String secondaryphone) {
		this.secondaryphone = secondaryphone;
	}

}
