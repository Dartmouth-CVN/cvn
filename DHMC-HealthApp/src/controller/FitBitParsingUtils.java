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
		System.out.println("Something Happened!\n");
		String state = "No State";
		while(fileReader.hasNextLine()) {
			System.out.println("Something Better Happened!\n");
			String line = fileReader.nextLine();
			if(line.substring(0,4).equals("Body")) {
				state = "Body";
				fileReader.nextLine();
				line = fileReader.nextLine();
				System.out.println("State is Body!\n");
			} else if(line.substring(0, 10).equals("Activities")) {
				state = "Activities";
				fileReader.nextLine();
				line = fileReader.nextLine();
				System.out.println("State is Activities!\n");
				i=0;
			} else if(line.substring(0,5).equals("Sleep")) {
				state = "Sleep";
				fileReader.nextLine();
				line = fileReader.nextLine();
				System.out.println("State is Sleep!\n");
				i=0;
			} else {
				state = "No State";
			}
			String [] info = CSVParsingUtils.splitSepValuesLine(line, ",");
			switch(state) {
				case "Body":    	System.out.println(line);
									HealthInfo hi = new HealthInfo();
									hi.setDate(info[0]);
									hi.setWeight(Double.parseDouble(info[1]));
									hi.setBmi(Double.parseDouble(info[2]));
									hi.setFat(Double.parseDouble(info[3]));
									output.add(hi);
									break;
									
				case "Activities":  System.out.println(line);
									output.get(i).setCaloriesBurned(Double.parseDouble(info[1].replace(",", "")));    //quotes
				 					output.get(i).setSteps(Double.parseDouble(info[2].replace(",", "")));             //quotes
				 					output.get(i).setDistance(Double.parseDouble(info[3]));
				 					output.get(i).setFloors(Double.parseDouble(info[4]));
				 					output.get(i).setMinSedentary(Double.parseDouble(info[5]));
				 					output.get(i).setMinLightlyActive(Double.parseDouble(info[6]));
				 					output.get(i).setMinFairlyActive(Double.parseDouble(info[7]));
				 					output.get(i).setMinVeryActive(Double.parseDouble(info[8]));
				 					output.get(i).setActivityCalories(Double.parseDouble(info[9].replace(",", "")));  //quotesu7
				 					i++;
				 					break;
				 					
				case "Sleep": 		System.out.println(line);
									output.get(i).setMinAsleep(Double.parseDouble(info[1]));
									output.get(i).setMinAwake(Double.parseDouble(info[2]));
									output.get(i).setNumAwakenings(Double.parseDouble(info[3]));
									output.get(i).setTimeInBed(Double.parseDouble(info[4]));
									i++;
									break;
				case "No State":    break;
			}
		}
		fileReader.close();
		return output;
	}
}
