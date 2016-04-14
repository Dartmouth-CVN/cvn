package controller;

import java.util.LinkedList;

import model.Patient;

public class XMLTest {

	public static void main(String[] args) {
		LinkedList<Patient> pts = CSVParsingUtils.CSVImport("TestingCSV.csv");
		
		//System.out.println(XMLParsingUtils.writePatientsToXML(null, pts));
		//CSVParsingUtils.CSVExport("exported420.csv", pts);
		
		System.out.println(XMLParsingUtils.writePatientsToHTML(null, pts));

	}

}
