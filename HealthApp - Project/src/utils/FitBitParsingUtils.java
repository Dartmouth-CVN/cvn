package utils;

import model.HealthAttribute;
import model.MainApp;
import model.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class FitBitParsingUtils {
	public static List<HealthAttribute<?>> fitBitImport(String str) {
		return fitBitImport(new File(str));
	}

	/**
	 * Splits a line of values delimited by a given delimiter, treating quoted
	 * sections as a whole, removing commas if remCommas is true
	 * 
	 * @param s
	 *            the line to split
	 * @param delimiter
	 *            the delimiter with with to split it
	 * @param remCommas
	 *            if internal commas should be removed
	 * @return an array of String containing all the individual values
	 */
	public static String[] splitSepValuesLine(String s, String delimiter, boolean remCommas) {
		LinkedList<String> output = new LinkedList<String>();
		String curVal = "";
		boolean inQuotes = false;
		for (int i = 0; i < s.length(); i++) {
			char curChar = s.charAt(i);
			if (curChar == '\"')
				inQuotes = !inQuotes;
			else if (curChar == delimiter.charAt(0) && !inQuotes) {
				String toAdd = curVal.trim();
				if (remCommas)
					toAdd = removeCommas(toAdd);
				output.add(toAdd);
				curVal = "";
			} else {
				curVal += curChar;
			}
		}
		if (curVal.length() > 0) {
			String toAdd = curVal.trim();
			if (remCommas)
				toAdd = removeCommas(toAdd);
			output.add(toAdd);
		}
		String[] outputArr = new String[output.size()];
		output.toArray(outputArr);
		return outputArr;
	}

	public static String[] splitSepValuesLineAndRemoveCommasFromVal(String s, String delimiter) {
		return splitSepValuesLine(s, delimiter, true);
	}

	static List<HealthAttribute<?>> healthInfo;
	/**
	 * 
	 * @param f:
	 *            a CSV file containing fitbit data
	 * @return a LinkedList of HealthInfo that contains all of the FitBit data
	 */
	public static List<HealthAttribute<?>> fitBitImport(File f) {
		healthInfo = new LinkedList<>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(f);
		} catch (FileNotFoundException e1) {
			System.out.println("File not Found");
			e1.printStackTrace();
			return null;
		}

		String state = "No State";
		List<String> titles = new ArrayList<String>();
		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();

			if (!hasData(line)) {
				continue;
			}
			String firstItem = line.split(",")[0];
			if (firstItem.equals("Body")) {
				state = "Body";
				titles = Arrays.asList(fileReader.nextLine().split(","));
				line = fileReader.nextLine();
			} else if (firstItem.equals("Activities")) {
				state = "Activities";
				titles = Arrays.asList(fileReader.nextLine().split(","));
				line = fileReader.nextLine();
			} else if (firstItem.equals("Sleep")) {
				state = "Sleep";
				titles = Arrays.asList(fileReader.nextLine().split(","));
				line = fileReader.nextLine();
			} else if (state.equals("No State")) {
				continue;
			}
			String[] info = splitSepValuesLineAndRemoveCommasFromVal(line, ",");

			HealthAttribute<?> hi;
			switch (state) {
			case "Body":
				System.out.println("Importing Body Info");
				addAttributesToInfo(info, titles);
				break;

			case "Activities":
				System.out.println("Importing Activity Info");
				addAttributesToInfo(info, titles);
				break;

			case "Sleep":
				System.out.println("Importing Sleep Info");
				addAttributesToInfo(info, titles);
				break;
			}
		}
		fileReader.close();
		System.out.println("health size: " + healthInfo.size());
		return healthInfo;
	}

	public static void addAttributesToInfo(String info[], List<String> titles){
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/dd/yyyy");
		DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("MM/d/yyyy");
		DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate t;
		t = LocalDate.now();
		HealthAttribute<?> hi;
		for (int i = 1; i < info.length-5; i++) {

			try {
				t = LocalDate.parse(info[0], formatter1);
			} catch (DateTimeParseException e) {}

			try {
				t = LocalDate.parse(info[0], formatter2);
			} catch (DateTimeParseException e) {}

			try {
				t = LocalDate.parse(info[0], formatter3);
			} catch (DateTimeParseException e) {}

			try {
				t = LocalDate.parse(info[0], formatter4);
			} catch (DateTimeParseException e) {}


			if(RandomGenerator.isInteger(info[i])) {
				hi = new HealthAttribute<Integer>(RandomGenerator.getRandomId(), t
						, titles.get(i), Integer.parseInt(info[i]));
			}else if(RandomGenerator.isDouble(info[i])){
				hi = new HealthAttribute<Double>(RandomGenerator.getRandomId(),
						t, titles.get(i), Double.parseDouble(info[i]));
			}else{
				hi = new HealthAttribute<>(RandomGenerator.getRandomId(),
						t, titles.get(i), info[i]);
			}
			healthInfo.add(hi);
		}
	}

	/**
	 * Writes fitbit data for all patients as a CSV
	 * 
	 * @param pts
	 *            the patients to write
	 */
	public static void fitbitExport(List<Patient> pts) {
		String filename = "fitbitExported";
		int i = 1;
		File exFile = new File("fitbitExported");
		while (exFile.exists()) {
			exFile = new File(filename + i);
			i++;
		}

		PrintWriter toWrite;
		try {
			toWrite = new PrintWriter(exFile);
		} catch (FileNotFoundException e) {
			MainApp.printError(e);
			return;
		}
		for (Patient p : pts) {

			toWrite.println(p.getFirstName() + " " + p.getLastName());
			for (HealthAttribute h : p.getHealthProfile().getHealthInfo()) {
				String toPrint = h.toString();
				toWrite.println(toPrint);
			}
		}
		toWrite.close();
	}

	public static String removeCommas(String s) {
		String retVal = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ',') {
				retVal += s.charAt(i);
			}
		}
		return retVal;
	}

	/**
	 * This function will check to see if a given string contains any
	 * nonwhitespace or noncomma characters
	 *
	 *
	 * @param s:
	 *            the string to check
	 * @return a boolean that will be true if any valid characters are detected
	 */
	static boolean hasData(String s) {
		int counter = 0;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isWhitespace(s.charAt(i)) || s.charAt(i) == ',') {
				counter++;
			}
		}
		return counter != s.length();
	}
}
