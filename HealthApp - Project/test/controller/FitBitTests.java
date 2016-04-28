package controller;


import java.io.File;

import javax.swing.JFileChooser;

import org.junit.Test;

public class FitBitTests {

	@Test
	public void test() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File fitbitData = fc.getSelectedFile();
//    		System.out.println(hi.get(0).getDate() + "\n" + hi.get(0).getWeight() + "\n" + hi.get(0).getCaloriesBurned() + "\n" + hi.get(0).getMinAsleep());
        } 
	}

}
