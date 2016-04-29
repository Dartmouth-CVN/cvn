package utils;

import model.Patient;
import java.util.LinkedList;
import java.io.File;
/**
 * Created by Sean on 4/27/2016.
 */
public class TestMainExport {

    public static void main(String[] args){
        LinkedList<Patient> pats = patList(100);
        PatientExportWrapper fieldManager = new PatientExportWrapper(randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB());
        (new XMLParsingUtils()).exportData(new File("exported.xml"), pats, fieldManager);
        (new XMLParsingUtils()).importData("exported.xml");
        (new CSVParsingUtils()).exportData("exported.csv", pats, fieldManager);
        (new CSVParsingUtils()).importData("exported.csv");

    }
    public static LinkedList<Patient> patList(int num) {
        LinkedList<Patient> output = new LinkedList<Patient>();
        while (num > 0) {
            Patient p = RandomGenerator.getRandomPatient();
            RandomGenerator.fillPatient(p);
            output.add(p);
            num--;
        }
        return output;
    }

    public static boolean randB(){
        return true;
    }
}