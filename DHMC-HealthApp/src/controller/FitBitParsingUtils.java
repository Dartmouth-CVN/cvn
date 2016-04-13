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
		} catch (FileNotFoundException e1) { // If the file doesn't exist, abort
			System.out.println("File not Found");
			e1.printStackTrace();
			return null;
		}

		while(fileReader.hasNextLine()) {// Parses the file line by line
			//if fileReader.nextLine() == "A" or "a" or "B" or "b" ....etc {reader.skip(1);} //skips any heading characters; implement if other method doesnt work
			if (fileReader.nextLine().equals("Body") && fileReader.hasNextLine()) {
				fileReader.nextLine();
				String line = fileReader.nextLine();
				while(!line.equals("")) {
					HealthInfo hi = new HealthInfo();
					String [] info = line.split(",");
					hi.setDate(info[0]);
					hi.setWeight(Double.parseDouble(info[1]));
					hi.setBmi(Double.parseDouble(info[2]));
					hi.setFat(Double.parseDouble(info[3]));
					output.add(hi);
					line = fileReader.nextLine();
				}
				fileReader.nextLine();
			} //skips the headings in the body data fields, skips 5 values(may need differnet skip functions to work right)
			else if(fileReader.nextLine().equals("Activities") && fileReader.hasNextLine()) {
				fileReader.nextLine();
				String line = fileReader.nextLine();
				while(!line.equals("")) {
					int i = 0;
					String [] info = line.split(",");
					System.out.println("floors: " + info[3]);
					output.get(i).setCaloriesBurned(Double.parseDouble(info[0]));
					output.get(i).setSteps(Double.parseDouble(info[1]));
					output.get(i).setDistance(Double.parseDouble(info[2]));
					output.get(i).setFloors(Double.parseDouble(info[3]));
					output.get(i).setMinSedentary(Double.parseDouble(info[4]));
					output.get(i).setMinLightlyActive(Double.parseDouble(info[5]));
					output.get(i).setMinFairlyActive(Double.parseDouble(info[6]));
					output.get(i).setMinVeryActive(Double.parseDouble(info[7]));
					output.get(i).setActivityCalories(Double.parseDouble(info[8]));
					i++;
					line = fileReader.nextLine();
				}
				fileReader.nextLine();
			}  //skips the headings in the activities data fields (skips 11 values)
			else if(fileReader.nextLine().equals("Sleep") && fileReader.hasNextLine()) {
				fileReader.nextLine();                
				String line = fileReader.nextLine();  
				while(!line.equals("")) {
					int i = 0;
					String [] info = line.split(",");
					output.get(i).setMinAsleep(Double.parseDouble(info[0]));
					output.get(i).setMinAwake(Double.parseDouble(info[1]));
					output.get(i).setNumAwakenings(Double.parseDouble(info[2]));
					output.get(i).setTimeInBed(Double.parseDouble(info[3]));
					i++;
					line = fileReader.nextLine();
				}
				fileReader.nextLine();
			}  //skips the headings in the sleep data fields (skips 6 values)
		}	
		fileReader.close();
		return output;
	}
}
