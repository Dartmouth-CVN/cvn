package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import model.MainApp;
import model.Patient;

@SuppressWarnings("unused")
public abstract class GeneralParsingUtils {
	
	/**
	 * Returns s if b is true, "" otherwise
	 * 
	 * @param s
	 *            the String to return
	 * @param b
	 *            whether or not to return the given String
	 * @return either s or an empty String
	 */
	protected static String stringIfTrue(String s, boolean b) {
		return (b) ? s : "";
	}
	
	/**
	 * Given a filename and a delimiter, create a LinkedList of 
	 * @param f
	 * @param delimiter
	 * @return
	 */
	public static LinkedList<String[]> ImportSepValuesFile(File f, String delimiter) {
		LinkedList<String[]> output = new LinkedList<String[]>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(f);
		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
			System.out.println("File not Found");
			MainApp.printError(e1);
			return null;
		}

		while (fileReader.hasNextLine()) // Parses the file line by line
			output.add((splitSepValuesLine(fileReader.nextLine(), delimiter)));
		fileReader.close();
		return output;
	}

	/**
	 * 
	 */
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
					removeCommas(toAdd);
				output.add(toAdd);
				curVal = "";
			} else {
				curVal += curChar;
			}
		}
		if (curVal.length() > 0) {
			String toAdd = curVal.trim();
			if (remCommas)
				removeCommas(toAdd);
			output.add(toAdd);
		}
		String[] outputArr = new String[output.size()];
		output.toArray(outputArr);
		return outputArr;
	}

	public static String[] splitSepValuesLine(String s, String delimiter) {
		return splitSepValuesLine(s, delimiter, false);
	}

	public static String[] splitSepValuesLineAndRemoveCommasFromVal(String s, String delimiter) {
		return splitSepValuesLine(s, delimiter, true);
	}

}
