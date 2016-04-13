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

//	public static LinkedList<HealthInfo> fitBitImport(File f) {
//		LinkedList<HealthInfo> output = new LinkedList<HealthInfo>();
//		Scanner fileReader;
//		try {
//			fileReader = new Scanner(f);
//		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
//			System.out.println("File not Found");
//			e1.printStackTrace();
//			return null;
//		}
//
//		while(fileReader.hasNextLine()) {// Parses the file line by line
//			//if fileReader.nextLine() == "A" or "a" or "B" or "b" ....etc {reader.skip(1);} //skips any heading characters; implement if other method doesnt work
//			
//
//			
//			
//			if (fileReader.nextLine().equals("Body") && fileReader.hasNextLine()) {
//				fileReader.nextLine();
//				String line = fileReader.nextLine();
//				while(!line.equals("")) {
//					HealthInfo hi = new HealthInfo();
//					String [] info = line.split(",");
//					hi.setDate(info[0]);
//					hi.setWeight(Double.parseDouble(info[1]));
//					hi.setBmi(Double.parseDouble(info[2]));
//					hi.setFat(Double.parseDouble(info[3]));
//					output.add(hi);
//					line = fileReader.nextLine();
//				}
//			} //skips the headings in the body data fields, skips 5 values(may need differnet skip functions to work right)
//			else if(fileReader.nextLine().equals("Activities") && fileReader.hasNextLine()) {
//				fileReader.nextLine();
//				String line = fileReader.nextLine();
//				while(!line.equals("")) {
//					int i = 0;
//					String [] info = line.split(",");
//					System.out.println("floors: " + info[3]);
//					output.get(i).setCaloriesBurned(Double.parseDouble(info[0]));
//					output.get(i).setSteps(Double.parseDouble(info[1]));
//					output.get(i).setDistance(Double.parseDouble(info[2]));
//					output.get(i).setFloors(Double.parseDouble(info[3]));
//					output.get(i).setMinSedentary(Double.parseDouble(info[4]));
//					output.get(i).setMinLightlyActive(Double.parseDouble(info[5]));
//					output.get(i).setMinFairlyActive(Double.parseDouble(info[6]));
//					output.get(i).setMinVeryActive(Double.parseDouble(info[7]));
//					output.get(i).setActivityCalories(Double.parseDouble(info[8]));
//					i++;
//					line = fileReader.nextLine();
//				}
//			}  //skips the headings in the activities data fields (skips 11 values)
//			else if(fileReader.nextLine().equals("Sleep") && fileReader.hasNextLine()) {
//				fileReader.nextLine();                
//				String line = fileReader.nextLine();  
//				while(!line.equals("")) {
//					int i = 0;
//					String [] info = line.split(",");
//					output.get(i).setMinAsleep(Double.parseDouble(info[0]));
//					output.get(i).setMinAwake(Double.parseDouble(info[1]));
//					output.get(i).setNumAwakenings(Double.parseDouble(info[2]));
//					output.get(i).setTimeInBed(Double.parseDouble(info[3]));
//					i++;
//					line = fileReader.nextLine();
//				}
//			}  //skips the headings in the sleep data fields (skips 6 values)
//		}	
//		fileReader.close();
//		return output;
//	}
	
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
			String [] info = line.split(",");
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
									output.get(i).setCaloriesBurned(Double.parseDouble(info[1]));
				 					output.get(i).setSteps(Double.parseDouble(info[2]));
				 					output.get(i).setDistance(Double.parseDouble(info[3]));
				 					output.get(i).setFloors(Double.parseDouble(info[4]));
				 					output.get(i).setMinSedentary(Double.parseDouble(info[5]));
				 					output.get(i).setMinLightlyActive(Double.parseDouble(info[6]));
				 					output.get(i).setMinFairlyActive(Double.parseDouble(info[7]));
				 					output.get(i).setMinVeryActive(Double.parseDouble(info[8]));
				 					output.get(i).setActivityCalories(Double.parseDouble(info[9]));
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
