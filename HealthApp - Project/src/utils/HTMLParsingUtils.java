package utils;

import model.AbsUser;
import model.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
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
            toFile.println("</head><body>\n");
            toFile.println("<img src=\"src\\view\\images\\dh-logo.png\" alt=\"Dartmouth-Hitchcock\" style=\"width:50%;\">");
            toFile.println("<table>\n");

            toFile.println(getHeaderRow(exportWrapper));
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
        output += "body{\n background-color: #edebe5;\n}\n";
        output += "table{\n border-collapse: collapse;\n}\n";
        output += "table, th, td {\n border: 1px solid black;\n}\n";
        output += "th, td {\n padding: 15px;\n}\n";
        output += "tr {\nbackground-color: #B1B1B1;\n color: #000000;\n}\n";
        output += "tr:hover {\nbackground-color: #353535;\n color: #FFFFFF;\n}\n";
        output += "tr.headline{\nbackground-color: #005A58;\ncolor: #edebe5;\nfont-size:36px;\ntext-align:center;\nfont-weight:bold;\n}\n";
        output += "td {\n white-space:nowrap;\n}\n";
        output += "</style>";
        return output;
    }

    private String getHeaderRow(PatientExportWrapper exportWrapper) {
        String[] fields = {"First Name", "Last Name", "Username", "Birthday", "Picture", "Room", "Addresses",
                "Phone Numbers", "Emails", "Relationships", "Pets", "Meals", "Health Info", "Allergies", "Dietary Restrictions"};
        List<String> fieldsList = Arrays.asList(fields);
        List<Boolean> toExport = exportWrapper.getFieldsPractical();
        for (int i = 0; i < fieldsList.size(); i++) {
            if (toExport.get(i))
                fieldsList.set(i, XMLLine(fieldsList.get(i).replace(";", "<br>"), "td"));
            else
                fieldsList.set(i, "");
        }
        return XMLLine(String.join("\n", fieldsList), "tr class=\"headline\"", true);

    }

    private String XMLLine(String content, String tag, boolean major) {
        if (major)
            content = "\n" + content + "\n";
        tag = tag.trim();
        return "<" + tag + ">" + content + "</" + tag.split(" ")[0] + ">";
    }

    private String XMLLine(String content, String tag) {
        return XMLLine(content, tag, false);
    }

    public static void main(String[] args) {
        PatientExportWrapper fieldManager = new PatientExportWrapper(true, true, true, true, true, true, true, true, true, true, true, true, true);
        (new HTMLParsingUtils()).exportData(new File("exported.html"), patList(10), fieldManager);
        System.out.println(new HTMLParsingUtils().getStyleString());
        System.out.println(new HTMLParsingUtils().getHeaderRow(fieldManager));

    }

    private static LinkedList<Patient> patList(int num) {
        LinkedList<Patient> output = new LinkedList<Patient>();
        while (num > 0) {
            Patient p = RandomGenerator.getRandomPatient();
            RandomGenerator.fillPatient(p);
            output.add(p);
            num--;
        }
        return output;
    }
}
