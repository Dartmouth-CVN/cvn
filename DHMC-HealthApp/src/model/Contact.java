package model;

import java.io.Serializable;
import java.util.LinkedList;

import javafx.beans.property.SimpleStringProperty;

public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2789588534495622114L;
	private String id;
	private LinkedList<SimpleStringProperty> phone;
	private LinkedList<SimpleStringProperty> email;
	private LinkedList<SimpleStringProperty> address;

	public Contact(String id) {
		this.id = id;
		this.phone = new LinkedList<SimpleStringProperty>();
		this.email = new LinkedList<SimpleStringProperty>();
		this.address = new LinkedList<SimpleStringProperty>();
	}

	public Contact() {
		this.id = "Unassigned";
		this.phone = new LinkedList<SimpleStringProperty>();
		this.email = new LinkedList<SimpleStringProperty>();
		this.address = new LinkedList<SimpleStringProperty>();
	}

	public String getID() {
		return this.id;
	}
	
	public LinkedList<SimpleStringProperty> phoneProperty() {
		return phone;
	}
	
    public final LinkedList<String> getPhoneList() {
    	
    	LinkedList<String> phoneList = new LinkedList<String>();
    	
    	for (int i = 0; i < emailProperty().size(); i++) {
    		
    		phoneList.add(emailProperty().get(i).get());
    		
    	}
    	
        return phoneList;
    }
    
    public void addPhoneList(LinkedList<String> phoneList) {
    	
    	for(String num : phoneList) {
    		
    		phoneProperty().add(new SimpleStringProperty(num));
    	}

    }
    
    public final String getPrimaryPhone() {
    	
        return phoneProperty().get(0).get();
    }

    public final void setPhone(String phone) {
    	
    	SimpleStringProperty simplePhone = new SimpleStringProperty(phone);
    	
        phoneProperty().add(simplePhone);
    }
    
    public final void removePhone(String phone) {
    	
    	SimpleStringProperty simplePhone = new SimpleStringProperty(phone);
    	
    	for (int i = 0; i < phoneProperty().size(); i++) {
    		
    		if (phoneProperty().get(i).get() == simplePhone.get()) {
    			
    			phoneProperty().remove(i);
    		}
    	}
    }
    
	public LinkedList<SimpleStringProperty> emailProperty() {
		return email;
	}
	
    public final LinkedList<String> getEmailList() {
    	
    	LinkedList<String> emailList = new LinkedList<String>();
    	
    	for (int i = 0; i < emailProperty().size(); i++) {
    		
    		emailList.add(emailProperty().get(i).get());
    		
    	}
    	
        return emailList;
    }
    
    public void addEmailList(LinkedList<String> emailList) {
    	
    	for(String num : emailList) {
    		
    		emailProperty().add(new SimpleStringProperty(num));
    	}

    }
    
    public final String getPrimaryEmail() {
    	
        return emailProperty().get(0).get();
    }

    public final void setEmail(String email) {
    	
    	SimpleStringProperty simpleEmail = new SimpleStringProperty(email);
    	
        phoneProperty().add(simpleEmail);
    }
    
    public final void removeEmail(String email) {
    	
    	SimpleStringProperty simpleEmail = new SimpleStringProperty(email);
    	
    	for (int i = 0; i < emailProperty().size(); i++) {
    		
    		if (emailProperty().get(i).get() == simpleEmail.get()) {
    			
    			emailProperty().remove(i);
    		}
    	}
    }
    
	public LinkedList<SimpleStringProperty> addressProperty() {
		return address;
	}
	
    public final LinkedList<String> getAddressList() {
    	
    	LinkedList<String> addressList = new LinkedList<String>();
    	
    	for (int i = 0; i < emailProperty().size(); i++) {
    		
    		addressList.add(emailProperty().get(i).get());
    		
    	}
    	
        return addressList;
    }
    
    public void addAddressList(LinkedList<String> addressList) {
    	
    	for(String num : addressList) {
    		
    		addressProperty().add(new SimpleStringProperty(num));
    	}

    }
    
    public final String getPrimaryAddress() {
    	
        return addressProperty().get(0).get();
    }

    public final void setAddress(String address) {
    	
    	SimpleStringProperty simpleAddress = new SimpleStringProperty(address);
    	
        phoneProperty().add(simpleAddress);
    }
    
    public final void removeAddress(String address) {
    	
    	SimpleStringProperty simpleAddress = new SimpleStringProperty(address);
    	
    	for (int i = 0; i < addressProperty().size(); i++) {
    		
    		if (addressProperty().get(i).get() == simpleAddress.get()) {
    			
    			addressProperty().remove(i);
    		}
    	}
    }

}