package controller;


import java.io.File;

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
			FitBitParsingUtils.fitBitImport(fitbitData);
        }
	}

}
