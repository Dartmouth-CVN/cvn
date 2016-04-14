package controller;

import java.io.File;
import java.util.LinkedList;

import model.Patient;

public class XMLTest {

	public static void main(String[] args) {
		LinkedList<Patient> pts = CSVParsingUtils.CSVImport("TestingCSV.csv");
		/*
		XMLParsingUtils.writePatientsToXML("test.xml", pts);
		CSVParsingUtils.CSVExport("exported420.csv", pts);
		*/
		System.out.println(XMLParsingUtils.writePatientsToHTML(null, pts));

	}

}
