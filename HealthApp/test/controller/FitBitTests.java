package controller;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.LinkedList;

import org.junit.Test;

import model.HealthInfo;
import utils.FitBitParsingUtils;


public class FitBitTests {

	@Test
	public void test() {
		File fitbitData = new File("/Users/Luke/Desktop/fitbitExport.csv");
		LinkedList<HealthInfo> hi = new LinkedList<HealthInfo>();
		hi = FitBitParsingUtils.fitBitImport(fitbitData);
		assertEquals(FitBitParsingUtils.fitBitImport(fitbitData).size(), 31);
		System.out.println(hi.get(0).getDate() + "\n" + hi.get(0).getWeight() + "\n" + hi.get(0).getCaloriesBurned() + "\n" + hi.get(0).getMinAsleep());
	}

}
