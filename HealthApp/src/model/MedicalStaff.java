package model;

import org.hibernate.annotations.IndexColumn;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
public class MedicalStaff extends AbsUser {
	List<Patient> assignedPatients;


	public MedicalStaff(){

	}

	public MedicalStaff(String userId, String firstName, String lastName, String username, String password,
						Date birthday, String room, Contact contactInfo) {
		this(userId, firstName, lastName, username, password, birthday, room, contactInfo, new LinkedList<Patient>());
	}
	
	public MedicalStaff(String userId, String firstName, String lastName, String username, String password,
						Date birthday, String room, Contact contactInfo,List<Patient> assignedPatients) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
		this.assignedPatients = assignedPatients;
	}

	public List<Patient> getAssignedPatients() {
		return assignedPatients;
	}

	public void setAssignedPatients(List<Patient> assignedPatients) {
		this.assignedPatients = assignedPatients;
	}

	public String getRole(){
		return "Medical Staff";
	}

	public void addPatient(Patient p){
		assignedPatients.add(p);
	}

}
