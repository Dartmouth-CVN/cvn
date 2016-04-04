import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class CSVParsingUtils {

	public static LinkedList<LinkedList<String>> CSVImport(String str) {
		return CSVImport(new File(str));
	}

	public static LinkedList<LinkedList<String>> CSVImport(File f) {
		LinkedList<LinkedList<String>> output = new LinkedList<LinkedList<String>>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(f);
		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
			System.out.println("File not Found");
			e1.printStackTrace();
			return null;
		}

		while (fileReader.hasNextLine()) // Parses the file line by line
			output.add(new LinkedList<String>(Arrays.asList(fileReader.nextLine().split(","))));
		fileReader.close();
		return output;
	}
	
	public static void CSVExport(String output, LinkedList<LinkedList<String>> src) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(output, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		} 
		for (LinkedList<String> line : src) {
			String toWrite = line.toString();
			toWrite = toWrite.substring(1, toWrite.length() - 1); //line.toString returns "[x, y, z]", this line truncates that to "x, y, z"
			toFile.println(toWrite);	
		}

		toFile.close();
	}

}
