import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;

public class CSVParsingUtils {

	public static LinkedList<String[]> CSVImport(String str) {
		return CSVImport(new File(str));
	}

	public static LinkedList<String[]> CSVImport(File f) {
		LinkedList<String[]> output = new LinkedList<String[]>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(f);
		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
			System.out.println("File not Found");
			e1.printStackTrace();
			return null;
		}

		while (fileReader.hasNextLine()) // Parses the file line by line
			output.add(fileReader.nextLine().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
		fileReader.close();
		return output;
	}

	public static void CSVExport(String output, LinkedList<String[]> src) {
		PrintWriter toFile;
		try {
			toFile = new PrintWriter(output, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		for (String[] line : src) {
			int i = 0;
			while (i < line.length - 1) {
				toFile.print(line[i] + ",");
				i++;
			}
			toFile.println(line[i]);
		}

		toFile.close();
	}

}
