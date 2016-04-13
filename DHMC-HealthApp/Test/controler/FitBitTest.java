package controler;

import static org.junit.Assert.*;
import java.io.File;
import java.util.LinkedList;

import controller.FitBitParsingUtils;
import model.HealthInfo;
import org.junit.Test;

public class FitBitTest {

	@Test
	public void test() {
		File fitbitData = new File("/Users/Luke/Desktop/fitbitExport.csv");
		//LinkedList<HealthInfo> hi = new LinkedList<HealthInfo>();
		//hi = FitBitParsingUtils.fitBitImport(fitbitData);
		assertEquals(FitBitParsingUtils.fitBitImport(fitbitData).size(), 31);
		//System.out.println(hi.get(0).getDate() + "\n" + hi.get(0).getWeight() + "\n" + hi.get(0).getCaloriesBurned() + "\n" + hi.get(0).getMinAsleep());
	}

}
