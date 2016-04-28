package controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;

import org.junit.Test;

import utils.FitBitParsingUtils;


public class FitBitTests {

	@Test
	public void test() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File fitbitData = fc.getSelectedFile();
<<<<<<< HEAD
    		Set<HealthInfo> hi = new HashSet<HealthInfo>();
//    		hi = FitBitParsingUtils.fitBitImport(fitbitData);
    		assertEquals(FitBitParsingUtils.fitBitImport(fitbitData).size(), 31);
=======
//    		Set<HealthInfo> hi = new HashSet<HealthInfo>();
//    		assertEquals(FitBitParsingUtils.fitBitImport(fitbitData).size(), 31);
>>>>>>> dbwork
//    		System.out.println(hi.get(0).getDate() + "\n" + hi.get(0).getWeight() + "\n" + hi.get(0).getCaloriesBurned() + "\n" + hi.get(0).getMinAsleep());
        } 
	}

}
