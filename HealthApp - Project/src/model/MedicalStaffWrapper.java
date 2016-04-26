package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mrampiah on 4/24/16.
 */
public class MedicalStaffWrapper {
    List<PatientWrapper> patientWrapperList;

    public MedicalStaffWrapper(MedicalStaff m) {
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

}
