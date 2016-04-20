package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import model.MainApp;
import model.MedicalStaff;
import model.Medication;
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

	/**
	 * This function will check to see if a given string contains any nonwhitespace or noncomma characters
	 * 
	 * 
	 * @param s: the string to check
	 * @return a boolean that will be true if any valid characters are detected
	 */
	static boolean hasData(String s) {
		int counter = 0;
		for(int i = 0; i < s.length(); i++) {
			if(Character.isWhitespace(s.charAt(i)) || s.charAt(i) == ',') {
				counter++;
			}
		}
		return counter != s.length();
	}

	/**
	 * Writes a String of XML in the format < tag >content< /tag >
	 * 
	 * @param tag
	 *            the tag to envelop the content
	 * @param content
	 *            the content to be enveloped
	 * @return the String in XML format
	 */
	protected static String XMLLine(String tag, String content) {
		return XMLLine(tag, content, false);
	}

	/**
	 * Writes a String of XML in the format < tag >content< /tag > with newlines
	 * if major is true
	 * 
	 * @param tag
	 *            the tag to envelop the content
	 * @param content
	 *            the content to be enveloped
	 * @param major
	 *            whether or not the XML should be formatted such that
	 *            multi-line structures look nicer, by adding newlines
	 * @return the XMLified String
	 */
	protected static String XMLLine(String tag, String content, boolean major) {
		String output = (tag != null) ? "<" + tag + ">" : "";
		if (major)
			output += "\n\n";
		output += content;
		if (major)
			output += "\n";
		if (tag == null)
			output += "\n";
		else if (tag.split(" ").length > 1)
			output += "</" + tag.split(" ")[0] + ">\n";
		else
			output += "</" + tag + ">\n";
		return output;
	}

	/**
	 * Given an array of Strings to each be parsed by one sub-tag, surround that
	 * list with a main tag
	 * 
	 * @param tag
	 *            the tag to surround the whole list
	 * @param subtag
	 *            the tag to surround each entry in the list
	 * @param content
	 *            the entries for the list
	 * @return the String in XML format
	 */
	protected static String XMLList(String tag, String subtag, Iterable<String> content) {
		String inner = "";
		for (String c : content)
			inner += XMLLine(subtag, c) + "<br>";
		return XMLLine(tag, inner, true);
	}

	protected static String XMLList(String tag, String subtag, String[] content) {
		return XMLList(tag, subtag, Arrays.asList(content));
	}

	/**
	 * Given a LinkedList of MedicalStaff, produce the appropriate XML String
	 * 
	 * @param staffMembers
	 *            the LinkedList to provide
	 * @return the String in XML format
	 */
	protected static String[] XMLParseMedStaff(LinkedList<MedicalStaff> staffMembers) {
		String[] output = new String[staffMembers.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = staffMembers.get(i).getUserID();
	
		return output;
	}

	/**
	 * Given a LinkedList of Medication, produce the appropriate XML String
	 * 
	 * @param meds
	 *            the LinkedList to provide
	 * @return the String in XML format
	 */
	private static String[] XMLParseMedication(LinkedList<Medication> meds) {
		String[] output = new String[meds.size()];
		for (int i = 0; i < output.length; i++)
			output[i] = meds.get(i).getName();
		return output;
	}

}
