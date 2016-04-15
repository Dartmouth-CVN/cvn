package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import model.HealthInfo;

public class FitBitParsingUtils {

	public static LinkedList<HealthInfo> fitBitImport(String str) {
		return fitBitImport(new File(str));
	}


	/**
	 * 
	 * @param f: a CSV file containing fitbit data
	 * @return a LinkedList of HealthInfo that contains all of the FitBit data
	 */
	public static LinkedList<HealthInfo> fitBitImport(File f) {
		LinkedList<HealthInfo> output = new LinkedList<HealthInfo>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(f);
		} catch (FileNotFoundException e1) {
			System.out.println("File not Found");
			e1.printStackTrace();
			return null;
		}

		int i = 0;
		String state = "No State";
		while(fileReader.hasNextLine()) {
			String line = fileReader.nextLine();

			if(!hasData(line)) {
				continue;
			} 
			String firstItem = line.split(",")[0];
			if(firstItem.equals("Body")) {
				state = "Body";
				line = fileReader.nextLine();
				continue;
			} else if(firstItem.equals("Activities")) {
				state = "Activities";
				line = fileReader.nextLine();
				i=0;
				continue;
			} else if(firstItem.equals("Sleep")) {
				state = "Sleep";
				line = fileReader.nextLine();
				i=0;
				continue;
			} else if(state.equals("No State")) {
				continue;
			} 
			String [] info = CSVParsingUtils.splitSepValuesLineAndRemoveCommasFromVal(line, ",");

			switch(state) {
			case "Body":    	
				HealthInfo hi = new HealthInfo();
				System.out.println("Importing Body Info");
				hi.setDate(info[0]);
				hi.setWeight(Double.parseDouble(info[1]));
				hi.setBmi(Double.parseDouble(info[2]));
				hi.setFat(Double.parseDouble(info[3]));
				output.add(hi);
				break;

			case "Activities":  
				System.out.println("Importing Activity Info");
				output.get(i).setCaloriesBurned(Double.parseDouble(info[1].replace(",", "")));   
				output.get(i).setSteps(Double.parseDouble(info[2].replace(",", "")));            
				output.get(i).setDistance(Double.parseDouble(info[3]));
				output.get(i).setFloors(Double.parseDouble(info[4]));
				output.get(i).setMinSedentary(Double.parseDouble(info[5]));
				output.get(i).setMinLightlyActive(Double.parseDouble(info[6]));
				output.get(i).setMinFairlyActive(Double.parseDouble(info[7]));
				output.get(i).setMinVeryActive(Double.parseDouble(info[8]));
				output.get(i).setActivityCalories(Double.parseDouble(info[9].replace(",", ""))); 
				i++;
				break;

			case "Sleep": 	
				System.out.println("Importing Sleep Info");	
				output.get(i).setMinAsleep(Double.parseDouble(info[1]));
				output.get(i).setMinAwake(Double.parseDouble(info[2]));
				output.get(i).setNumAwakenings(Double.parseDouble(info[3]));
				output.get(i).setTimeInBed(Double.parseDouble(info[4]));
				i++;
				break;
			}
		}
		fileReader.close();
		System.out.println("FitBit Import Complete!");
		return output;
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
}
