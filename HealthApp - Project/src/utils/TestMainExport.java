package utils;

import model.Patient;
import java.util.LinkedList;

/**
 * Created by Sean on 4/27/2016.
 */
public class TestMainExport {

    public static void main(String[] args){
        LinkedList<Patient> pats = patList(100);
        PatientExportWrapper fieldManager = new PatientExportWrapper(randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB(),randB());
        (new CSVParsingUtils()).exportData("exported.csv", pats, fieldManager);
    }
    public static LinkedList<Patient> patList(int num) {
        LinkedList<Patient> output = new LinkedList<Patient>();
        while (num > 0) {
            output.add(RandomGenerator.getRandomPatient());
            num--;
        }
        return output;
    }

    public static boolean randB(){
        return Math.random() > 0.5;
    }
}