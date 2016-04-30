package model;

import utils.ObjectNotFoundException;

import java.util.LinkedList;
import java.util.List;


public class Contact{
    long contactId;
    List<ContactElement> phoneNumbers;
    List<ContactElement> emails;
    List<ContactElement> addresses;

    public Contact() {
        this(0L, new LinkedList<ContactElement>(), new LinkedList<ContactElement>(), new LinkedList<ContactElement>());
    }

    public Contact(long id) {
        this(id, new LinkedList<ContactElement>(), new LinkedList<ContactElement>(), new LinkedList<ContactElement>());
        setContactId(id);
    }

    public Contact(List<ContactElement> phoneNumbers, List<ContactElement> emails,
                   List<ContactElement> addresses) {
        this(0, phoneNumbers, emails, addresses);
    }

    public Contact(long id, List<ContactElement> phoneNumbers, List<ContactElement> emails,
                   List<ContactElement> addresses) {
        contactId = id;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.addresses = addresses;
    }


    public Contact(long id, List<ContactElement> contactInfo) {
        contactId = id;
        phoneNumbers = new LinkedList<>();
        emails = new LinkedList<>();
        addresses = new LinkedList<>();
        for(ContactElement e : contactInfo){
            if(e instanceof Phone)
                addPhone(e);
            else if(e instanceof Email)
                addEmail(e);
            else if(e instanceof Address)
                addAddress(e);
        }
    }

    public Contact(List<ContactElement> contactInfo) {
        contactId = 0L;
        phoneNumbers = new LinkedList<>();
        emails = new LinkedList<>();
        addresses = new LinkedList<>();

        for(ContactElement e : contactInfo){
            if(e.getContactType().equals("PHONE"))
                addPhone(e);
            else if(e.getContactType().equals("EMAIL"))
                addEmail(e);
            else if(e.getContactType().equals("ADDRESS"))
                addAddress(e);
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

    public List<ContactElement> getAddresses() {
        return this.addresses == null ? new LinkedList<ContactElement>() : addresses;
    }

    public void setAddresses(List<ContactElement> address) {
        this.addresses = address;
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

    public ContactElement getPrimaryAddress() throws ObjectNotFoundException {
        if (!this.addresses.isEmpty())
            return this.addresses.get(0);
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

    public List<ContactElement> getAllContactElements() {
        List<ContactElement> elements = new LinkedList<ContactElement>();
        try{
            elements.addAll(phoneNumbers);
            elements.addAll(emails);
            elements.addAll(addresses);

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
        return phoneNumbers.hashCode() * emails.hashCode() * addresses.hashCode();
    }
}
