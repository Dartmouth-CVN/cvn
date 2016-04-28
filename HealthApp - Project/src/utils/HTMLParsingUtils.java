package utils;

import model.AbsUser;
import model.Patient;
import model.PatientExportWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Sean on 4/27/2016.
 */
public class HTMLParsingUtils implements ParsingUtils {
    @Override
    public File getFile(String name) {
        return new File(name);
    }

    @Override
    public List<String> getFileContents(File file) {
        return null;
    }

    @Override
    public AbsUser getUserFromContents(String contents) {
        return null;
    }

    public void exportData(File f, List<Patient> patients, PatientExportWrapper exportWrapper) {
        try {
            PrintWriter toFile = new PrintWriter(f);
            toFile.println("<html><head><title>Patient List</title></head><body>\n<table>\n");
            for (Patient p : patients) {
                exportWrapper.setPatient(p);
                toFile.println(exportWrapper.toHTMLString());
            }
            toFile.println("</table></body></html>");
        } catch (FileNotFoundException e) {
            System.err.println("The file selected is either inaccessible or does not exist.");
        }

    }
}
