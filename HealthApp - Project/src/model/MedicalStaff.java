package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MedicalStaff extends AbsUser {
    List<Patient> assignedPatients;


    public MedicalStaff() {

    }

    public MedicalStaff(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
                        Date birthday, java.lang.String room, String picture, Contact contactInfo) {
        this(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo, new LinkedList<Patient>());
    }

    public MedicalStaff(java.lang.String userId, java.lang.String firstName, java.lang.String lastName, java.lang.String username, java.lang.String password,
                        Date birthday, java.lang.String room, String picture, Contact contactInfo, List<Patient> assignedPatients) {
        super(userId, firstName, lastName, username, password, birthday, room, picture, contactInfo);
        this.assignedPatients = assignedPatients;
    }

    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(List<Patient> assignedPatients) {
        this.assignedPatients = assignedPatients;
    }

    public java.lang.String getRole() {
        return "Medical Staff";
    }

    public void addPatient(Patient p) {
        assignedPatients.add(p);
    }

}
