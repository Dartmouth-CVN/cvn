package utils;

import model.AbsUser;
import model.Patient;

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
            toFile.println("<html><head><title>Patient List</title>");
            toFile.println(getStyleString());
            toFile.println("</head><body>\n<table>\n");
            for (Patient p : patients) {
                exportWrapper.setPatient(p);
                toFile.println(exportWrapper.toHTMLString());
            }
            toFile.println("</table></body></html>");
            toFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("The file selected is either inaccessible or does not exist.");
        }

    }

    private String getStyleString() {
        String output = "";
        output += "<style>\n";
        output += "table{\n border-collapse: collapse\n}\n";
        output += "table, th, td {\n border: 1px solid black;\n}\n";
        output += "tr:hover {\nbackground-color: #f5fff5\n}\n";
        output += "</style>";
        return output;
    }

    public static void main(String[] args) {
        System.out.println(new HTMLParsingUtils().getStyleString());
    }
}
