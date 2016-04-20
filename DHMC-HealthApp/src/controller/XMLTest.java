package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import model.Patient;

public class XMLTest {

	public static void main(String[] args) {
	//	LinkedList<Patient> pts = CSVParsingUtils.CSVImport("TestingCSV.csv");
		
		boolean[] test =null;// {false,false,false,false};
		//System.out.println(XMLParsingUtils.writePatientsToXML("patients.xml", pts, test));
		//CSVParsingUtils.CSVExport("exported420.csv", pts, test);
		
		//System.out.println(XMLParsingUtils.writePatientsToHTML("patients.html", pts));
		LinkedList<String> testy = new LinkedList<String>();
		String[] test2 = {"qewr","rawr,we,qw","lel"};
		testy.addAll(Arrays.asList(test2));
		System.out.println(String.join(",",testy));

	}

}
