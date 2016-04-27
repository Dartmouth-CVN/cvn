package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import view.MainApp;
import model.Patient;
import model.PatientExportWrapper;

public abstract class SVParsingUtils implements ParsingUtils {
	String delimiter;

	public SVParsingUtils(String delimiter){
		this.delimiter = delimiter;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter){
		this.delimiter = delimiter;
	}

	/**
	 * getFile takes in the name of a file and returns its lines as an array of
	 * Strings
	 *
	 * @param filename
	 *            the name of the file to import
	 * @return the lines of the file as an array of Strings
	 */
	@Override
	public File getFile(String filename) {
		return new File(filename);
	}

	@Override
	public List<String> getFileContents(File file){
		List<String> lines = null;
		try {
			Scanner fileReader = new Scanner(file);
			lines = new LinkedList<String>();
			while (fileReader.hasNextLine())
				lines.add(fileReader.nextLine());
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * importData takes in the name of a separated values file and uses those
	 * fields to construct a LinkedList of Patients
	 *
	 * @param filename
	 *            the name of the separated values file
	 * @return a LinkedList of the imported Patients
	 */
	public List<Patient> importData(String filename) {
		List<String> lines = getFileContents(getFile(filename));
		List<Patient> output = new LinkedList<Patient>();
		Patient placeholder = new Patient();
		for (String patient : lines)
			output.add((Patient)placeholder.fromSVString(patient));
		return output;
	}

	/**
	 * Outputs all patients from the database to the specified file
	 *
	 * @param filename
	 *            the file to write to
	 */
	public  void exportData(String filename, List<Patient> patients, PatientExportWrapper exportWrapper) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			MainApp.printError(e);
			return;
		}
		for (Patient patient : patients) {
			exportWrapper.setPatient(patient);
			exportWrapper.toSVString(delimiter);
			toFile.println();
		}
		toFile.close();
	}

	/**
	 * splitLine takes a line of Separated Values and returns it as an array of
	 * Strings
	 *
	 * @param toSplit
	 *            the line to split
	 * @return the list of values
	 */
	public List<String> splitLine(String toSplit) {
		LinkedList<String> output = new LinkedList<String>();
		String regex = delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		String[] split = toSplit.split(regex);
		return Arrays.asList(split);

//		String curVal = "";
//		boolean inQuotes = false;
//		for (int i = 0; i < toSplit.length(); i++) {
//			char curChar = toSplit.charAt(i);
//			if (curChar == '\"')
//				inQuotes = !inQuotes;
//			else if (curChar == getDelimiter().charAt(0) && !inQuotes) {
//				String toAdd = curVal.trim();
//				output.add(toAdd);
//				curVal = "";
//			} else {
//				curVal += curChar;
//			}
//		}
//		if (curVal.length() > 0) {
//			String toAdd = curVal.trim();
//			output.add(toAdd);
//		}
	}
}
