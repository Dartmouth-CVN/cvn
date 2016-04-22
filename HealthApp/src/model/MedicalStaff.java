package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MedicalStaff extends AbsUser {

	List<Patient> assignedPatients;
	int medId;

	public MedicalStaff(int userId, String firstName, String lastName, String username, String password,
			Date birthday, String room, Contact contactInfo) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
	}
	
	public MedicalStaff(int userId, String firstName, String lastName, String username, String password,
			Date birthday, String room, Contact contactInfo,int medId, List<Patient> assignedPatients) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo);
		this.medId = medId;
		this.assignedPatients = assignedPatients;
	}

	public List<Patient> getAssignedPatients() {
		return assignedPatients;
	}

	public void setAssignedPatients(List<Patient> assignedPatients) {
		this.assignedPatients = assignedPatients;
	}

	public int getMedId() {
		return medId;
	}

	public void setMedId(int medId) {
		this.medId = medId;
	}

}
