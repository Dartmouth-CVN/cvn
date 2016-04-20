package model;
import java.util.LinkedList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MedicalStaff extends User {
	
	private StringProperty position = new SimpleStringProperty();
	
	protected LinkedList<Patient> assignedPatients;
	private int medID;
	
	public MedicalStaff(String firstName, String lastName, String position, String userID, int medID) {
		super(firstName, lastName, userID);
		this.setPosition(position);;
		this.setMedID(medID);
	}
	
	public MedicalStaff(String userID) {
		super("Enter First Name", "Enter Last Name", userID);
		this.setMedID(0);
	}
	
	public int addPatient (Patient patient) {
		if(this.assignedPatients.contains(patient)){
			System.out.println("The patient already exists");
			return 0;
		} else {
			this.assignedPatients.add(patient);
			return 1; //Will be changed to return 1 upon successfully updating the database
		}
	}
	
	public int removePatient (Patient patient) {
		if(this.assignedPatients.contains(patient)){
			this.assignedPatients.remove(patient);
			return 1; //Will be changed to return 1 upon successfully updating the database
		} else {
			System.out.println("The patient requested doesn't seem to exist");
			return 0;
		}
	}
	
	public int editPatient (Patient patient) {
		if(this.assignedPatients.contains(patient)) {
			//edit the patient
			return 1;
		} else {
			System.out.println("The patient requested doesn't seem to exist");
			return 0;
		}
	}
	
	public int exportPatient (Patient patient) {
		if(this.assignedPatients.contains(patient)) {
			//export the patient
			return 1;
		} else {
			System.out.println("The patient requested doesn't seem to exist");
			return 0;
		}
	}
	
	public int prescribe(Patient patient, Medication medication) {
		return 0;
	}

	public int getMedID() {
		return medID;
	}

	public void setMedID(int medID) {
		this.medID = medID;
	}

	@Override
	public StringProperty getFirstNameProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringProperty getLastNameProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringProperty getUserIDProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	public StringProperty positionProperty() {
		return position;
	}
	
    public final String getPosition() {
        return positionProperty().get();
    }

    public final void setPosition(String position) {
        positionProperty().set(position);
    }

	@Override
	public void initObservers() {
		// TODO Auto-generated method stub
		
	}
}
