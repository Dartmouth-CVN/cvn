package model;

import java.util.LinkedList;
import java.util.List;

import utils.ObjectNotFoundException;

public class Contact {
	 int contactId;
	 List<ContactElement> phoneNumbers;
	 List<ContactElement> emails;
	 List<ContactElement> addresses;

	public Contact(int id) {
		this.contactId = id;
		this.phoneNumbers = new LinkedList<ContactElement>();
		this.emails = new LinkedList<ContactElement>();
		this.addresses = new LinkedList<ContactElement>();
	}

	public Contact() {
		this.phoneNumbers = new LinkedList<ContactElement>();
		this.emails = new LinkedList<ContactElement>();
		this.addresses = new LinkedList<ContactElement>();
	}
	
	
	public Contact(List<ContactElement> phoneNumbers, List<ContactElement> emails,
			List<ContactElement> addresses) {
		contactId = 0;
		this.phoneNumbers = phoneNumbers;
		this.emails = emails;
		this.addresses = addresses;
	}

	public int getContactId(){
		return contactId;
	}
	
	public void setContactId(int id){
		contactId = id;
	}

	public List<ContactElement> getPhoneNumbers() {
		return this.phoneNumbers;
	}

	public List<ContactElement> getEmails() {
		return this.emails;
	}

	public List<ContactElement> getAddresses() {
		return this.addresses;
	}

	public ContactElement getPrimaryPhone() {
		return ((LinkedList<ContactElement>) this.phoneNumbers).getFirst();
	}

	public ContactElement getPrimaryEmail() throws ObjectNotFoundException {
		if (!this.addresses.isEmpty())
			return ((LinkedList<ContactElement>) this.addresses).getFirst();
		else
			throw new ObjectNotFoundException("Phone number ");
	}

	public void setPhoneNumbers(List<ContactElement> phone) {
		this.phoneNumbers = phone;
	}

	public void setEmails(List<ContactElement> email) {
		this.emails = email;
	}

	public void setAddresses(List<ContactElement> address) {
		this.addresses = address;
	}

	public ContactElement getPrimaryAddress() throws ObjectNotFoundException {
		if (!this.addresses.isEmpty())
			return ((LinkedList<ContactElement>) this.addresses).getFirst();
		else
			throw new ObjectNotFoundException("Address ");
	}

	public void addPhone(ContactElement phone) {
		if (this.phoneNumbers.indexOf(phone) < 0) {
			this.phoneNumbers.add(phone);
		}
	}

	public void addEmail(ContactElement email) {
		if (this.emails.indexOf(email) < 0) {
			this.emails.add(email);
		}
	}

	public void addAddress(ContactElement address) {
		if (this.addresses.indexOf(address) < 0) {
			this.addresses.add(address);
		}
	}

	public void removePhone(ContactElement phone) {
		if (this.phoneNumbers.indexOf(phone) >= 0) {
			this.phoneNumbers.remove(phone);
		}
	}

	public void removeEmail(ContactElement email) {
		if (this.emails.indexOf(email) >= 0) {
			this.emails.remove(email);
		}
	}

	public void removeAddress(ContactElement address) {
		if (this.addresses.indexOf(address) >= 0) {
			this.addresses.remove(address);
		}
	}

	public void makePrimaryPhone(ContactElement phone) {
		this.removePhone(phone);
		((LinkedList<ContactElement>) this.phoneNumbers).addFirst(phone);
	}

	public void makePrimaryEmail(ContactElement email) {
		this.removeEmail(email);
		((LinkedList<ContactElement>) this.emails).addFirst(email);
	}

	public void makePrimaryAddress(ContactElement address) {
		this.removeAddress(address);
		((LinkedList<ContactElement>) this.addresses).addFirst(address);
	}
}
