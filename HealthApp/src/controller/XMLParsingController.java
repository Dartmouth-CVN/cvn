package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import model.MainApp;
import model.Patient;

public class XMLParsingController implements ParsingController {

	@Override
	public LinkedList<Patient> importData(String filename) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setIgnoringElementContentWhitespace(true);
		Document doc;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			File file = new File(filename);
			doc = builder.parse(file); 
			// Do something with the document here.
		} catch (Exception e) {
			return null;
		}
		doc.getDocumentElement().normalize();
		return null;
	}

	@Override
	public void exportData(String filename, LinkedList<Patient> patients, boolean[] toInclude) {
		// TODO Auto-generated method stub

	}

	@Override
	public Patient makePatient(String pt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String patientToString(Patient p, boolean[] toInclude) {
		// TODO Auto-generated method stub
		return null;
	}

}
