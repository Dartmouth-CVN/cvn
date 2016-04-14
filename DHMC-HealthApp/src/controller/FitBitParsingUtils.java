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
			if(line.substring(0,4).equals("Body")) {
				state = "Body";
				line = fileReader.nextLine();
				continue;
			} else if(line.substring(0, 10).equals("Activities")) {
				state = "Activities";
				line = fileReader.nextLine();
				i=0;
				continue;
			} else if(line.substring(0,5).equals("Sleep")) {
				state = "Sleep";
				line = fileReader.nextLine();
				i=0;
				continue;
			} else if(state.equals("No State")) {
				continue;
			} else if(!hasData(line)) {
				continue;
			}
			String [] info = CSVParsingUtils.splitSepValuesLineAndRemoveCommasFromVal(line, ",");
			
			switch(state) {
				case "Body":    	HealthInfo hi = new HealthInfo();
									hi.setDate(info[0]);
									hi.setWeight(Double.parseDouble(info[1]));
									hi.setBmi(Double.parseDouble(info[2]));
									hi.setFat(Double.parseDouble(info[3]));
									output.add(hi);
									break;
									
				case "Activities":  output.get(i).setCaloriesBurned(Double.parseDouble(info[1].replace(",", "")));   
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
				 					
				case "Sleep": 		output.get(i).setMinAsleep(Double.parseDouble(info[1]));
									output.get(i).setMinAwake(Double.parseDouble(info[2]));
									output.get(i).setNumAwakenings(Double.parseDouble(info[3]));
									output.get(i).setTimeInBed(Double.parseDouble(info[4]));
									i++;
									break;
			}
		}
		fileReader.close();
		return output;
	}
	
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
