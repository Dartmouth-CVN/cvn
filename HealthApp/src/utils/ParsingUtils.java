package utils;

import java.util.LinkedList;
import java.util.Set;

import model.Patient;

public interface ParsingUtils {
	public static Set<Patient> importData(String filename) {
		return null;
	}
	public static void exportData(String filename, LinkedList<Patient> patients, boolean[] toInclude) {
	}
	public static Patient makePatient(String pt) {
		return null;
	}
	public static String patientToString(Patient p, boolean[] toInclude) {
		return null;
	}
}
