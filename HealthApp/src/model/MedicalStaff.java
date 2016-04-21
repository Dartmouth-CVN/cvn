package model;

import java.time.LocalDate;
import java.util.Set;

public class MedicalStaff extends AbsUser {

	Set<Patient> assignedPatients;
	int medId;

	public MedicalStaff(int userId, String firstName, String lastName, String username, String password,
			LocalDate birthday, String room, Contact contactInfo, Schedule schedule) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
	}
	
	public MedicalStaff(int userId, String firstName, String lastName, String username, String password,
			LocalDate birthday, String room, Contact contactInfo, Schedule schedule, int medId, Set<Patient> assignedPatients) {
		super(userId, firstName, lastName, username, password, birthday, room, contactInfo, schedule);
		this.medId = medId;
		this.assignedPatients = assignedPatients;
	}

	public Set<Patient> getAssignedPatients() {
		return assignedPatients;
	}

	public void setAssignedPatients(Set<Patient> assignedPatients) {
		this.assignedPatients = assignedPatients;
	}

	public int getMedId() {
		return medId;
	}

	public void setMedId(int medId) {
		this.medId = medId;
	}

}
