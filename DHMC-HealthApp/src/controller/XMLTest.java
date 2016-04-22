package controller;

import java.util.LinkedList;

import model.Patient;

public class XMLTest {

	public static void main(String[] args) {
		LinkedList<Patient> pts = CSVParsingUtils.CSVImport("TestingCSV.csv");
		XMLParsingUtils.writePatientsToHTML("Exported.html", pts);
	}

}
