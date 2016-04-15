package controller;

import java.util.LinkedList;

import model.Patient;

public class XMLTest {

	public static void main(String[] args) {
		LinkedList<Patient> pts = CSVParsingUtils.CSVImport("TestingCSV.csv");
		
		boolean[] test =null;// {false,false,false,false};
		System.out.println(XMLParsingUtils.writePatientsToXML(null, pts, test));
		//CSVParsingUtils.CSVExport("exported420.csv", pts, test);
		
		System.out.println(XMLParsingUtils.writePatientsToHTML("patients.html", pts));

	}

}
