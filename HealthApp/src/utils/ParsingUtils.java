package utils;

import java.util.LinkedList;
import java.util.Set;

import model.Patient;

public interface ParsingUtils {
	public Set<Patient> importData(String filename);
	public void exportData(String filename, LinkedList<Patient> patients, boolean[] toInclude);
	public Patient makePatient(String pt);
	public String patientToString(Patient p, boolean[] toInclude);
}
