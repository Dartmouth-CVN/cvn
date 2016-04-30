package model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class MedicalStaff extends AbsUser {
    List<Patient> assignedPatients;


    public MedicalStaff() {

    }

    public MedicalStaff(long userIdValue, String firstName, String lastName, String username, String password,
                        LocalDate birthday, String room, String picture) {
        this(userIdValue, firstName, lastName, username, password, birthday, room, picture, new Contact(), new LinkedList<Patient>());
    }

    public MedicalStaff(long userIdValue, String firstName, String lastName, String username, String password,
                        LocalDate birthday, String room, String picture, Contact contactInfo) {
        this(userIdValue, firstName, lastName, username, password, birthday, room, picture, contactInfo, new LinkedList<Patient>());
    }

    @Override
    public String getUserId() {
        return "MS" + userIdValue;
    }

    public MedicalStaff(long userIdValue, String firstName, String lastName, String username, String password,
                        LocalDate birthday, String room, String picture, Contact contactInfo, List<Patient> assignedPatients) {
        super(userIdValue, firstName, lastName, username, password, birthday, room, picture, contactInfo);
        this.assignedPatients = assignedPatients;
    }

    public List<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(List<Patient> assignedPatients) {
        this.assignedPatients = assignedPatients;
    }

    public void addPatient(Patient p) {
        if(!assignedPatients.contains(p))
            assignedPatients.add(p);
    }

    @Override
    public AbsUser fromXMLString() {
        return null;
    }

    @Override
    public AbsUser fromCSVString() {
        return null;
    }

    @Override
    public AbsUser fromTSVString() {
        return null;
    }

    @Override
    public AbsUser fromSVString(String delimiter) {
        return null;
    }

    public static String getRole(){
        return "MED_STAFF";
    }
}
