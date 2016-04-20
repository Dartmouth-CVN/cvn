package controller;

import java.util.LinkedList;

import model.Patient;

public interface ParsingController {
	String[] getFile(String filename);
	public LinkedList<Patient> importPatients(String filename);
	public void exportPatients(String filename, boolean[] toInclude);
	public Patient makePatient(String pt);
	public String patientToString(Patient p, boolean[] toInclude);
}
