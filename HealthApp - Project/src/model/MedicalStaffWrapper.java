package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrampiah on 4/24/16.
 */
public class MedicalStaffWrapper extends AbsUserWrapper{
    List<PatientWrapper> patientWrapperList;
    StringProperty roleProperty;
    MedicalStaff medicalStaff;

    public MedicalStaffWrapper(MedicalStaff m) {
        super(m);
        setRoleProperty(MedicalStaff.getRole());
        medicalStaff = m;
        setPatientWrapperList(m.getAssignedPatients());
    }

    public List<PatientWrapper> getPatientWrapperList() {
        return patientWrapperList;
    }

    public void setPatientWrapperList(List<Patient> patientList) {
        patientWrapperList = new LinkedList<PatientWrapper>();
        for (Patient p : patientList)
            patientWrapperList.add(new PatientWrapper(p));
    }

    public StringProperty getRoleProperty(){
        return roleProperty;
    }

    public void setRoleProperty(String role){
        roleProperty = new SimpleStringProperty(role);
    }

    public void setMedicalStaff(MedicalStaff med){
        this.medicalStaff = med;
    }

    public MedicalStaff getMedicalStaff(){
        return medicalStaff;
    }

}
