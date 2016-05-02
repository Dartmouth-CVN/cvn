package model;

import utils.ObjectNotFoundException;

import java.util.LinkedList;
import java.util.List;


public class Contact{
    long contactId;
    List<ContactElement> phoneNumbers;
    List<ContactElement> emails;
    ContactElement address;
    public static enum contactTypes {PHONE, EMAIL, ADDRESS}

    public Contact() {
        this(0L, new LinkedList<ContactElement>(), new LinkedList<ContactElement>(), new ContactElement());
    }

    public Contact(long id) {
        this(id, new LinkedList<ContactElement>(), new LinkedList<ContactElement>(), new ContactElement());
        setContactId(id);
    }

    public Contact(List<ContactElement> phoneNumbers, List<ContactElement> emails,
                   ContactElement address) {
        this(0, phoneNumbers, emails, address);
    }

    public Contact(long id, List<ContactElement> phoneNumbers, List<ContactElement> emails,
                   ContactElement address) {
        contactId = id;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.address = address;
    }


    public Contact(long id, List<ContactElement> contactInfo) {
        contactId = id;
        phoneNumbers = new LinkedList<>();
        emails = new LinkedList<>();
        for(ContactElement e : contactInfo){
            if(e.getType().equals(contactTypes.PHONE.name()))
                addPhone(e);
            else if(e.getType().equals(contactTypes.EMAIL.name()))
                addEmail(e);
            else if(e instanceof Address)
                setAddress(e);
        }
    }

    public Contact(List<ContactElement> contactInfo) {
        contactId = 0L;
        phoneNumbers = new LinkedList<>();
        emails = new LinkedList<>();
        for(ContactElement e : contactInfo){
            if(e.getType().equals(contactTypes.PHONE.name()))
                addPhone(e);
            else if(e.getType().equals(contactTypes.EMAIL.name()))
                addEmail(e);
            else if(e.getType().equals(contactTypes.ADDRESS.name()))
                setAddress(e);
        }
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long id) {
        contactId = id;
    }

    public List<ContactElement> getPhoneNumbers() {
        return this.phoneNumbers == null ? new LinkedList<ContactElement>() : phoneNumbers;
    }

    public void setPhoneNumbers(List<ContactElement> phone) {
        this.phoneNumbers = phone;
    }

    public List<ContactElement> getEmails() {
        return this.emails == null ? new LinkedList<ContactElement>() : emails;
    }

    public void setEmails(List<ContactElement> email) {
        this.emails = email;
    }

    public ContactElement getAddress() {
        return this.address == null ? new ContactElement() : address;
    }

    public void setAddress(ContactElement address) {
        this.address = address;
    }

    public ContactElement getPrimaryPhone() throws ObjectNotFoundException {
        if (!this.phoneNumbers.isEmpty())
            return this.phoneNumbers.get(0);
        else
            throw new ObjectNotFoundException("Phone number ");
    }

    public ContactElement getPrimaryEmail() throws ObjectNotFoundException {
        if (!this.emails.isEmpty())
            return this.emails.get(0);
        else
            throw new ObjectNotFoundException("Email ");
    }

    public void addPhone(ContactElement phone) {
        if(!phoneNumbers.contains(phone))
            phoneNumbers.add(phone);
    }

    public void addEmail(ContactElement email) {
        if (this.emails.indexOf(email) < 0) {
            this.emails.add(email);
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

    public void removeAddress() {
       this.address = null;
    }

    public void makePrimaryPhone(ContactElement phone) {
        this.removePhone(phone);
        ((LinkedList<ContactElement>) this.phoneNumbers).addFirst(phone);
    }

    public void makePrimaryEmail(ContactElement email) {
        this.removeEmail(email);
        ((LinkedList<ContactElement>) this.emails).addFirst(email);
    }

    public List<ContactElement> getAllContactElements() {
        List<ContactElement> elements = new LinkedList<ContactElement>();
        try{
            elements.addAll(phoneNumbers);
            elements.addAll(emails);
            elements.add(address);

        }catch (NullPointerException e){
//            MainApp.printError(e);
        }
        return elements;
    }

    public ContactElement getElementById(long elementId) throws ObjectNotFoundException {
        for (ContactElement e : getAllContactElements()) {
            if (e.getElementId() == elementId)
                return e;
        }
        throw new ObjectNotFoundException("Contact Element(" + elementId + ")");
    }

    @Override
    public int hashCode() {
        return phoneNumbers.hashCode() * emails.hashCode() * address.hashCode();
    }
}
