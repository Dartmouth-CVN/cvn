package view;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import model.MainApp;
import model.Patient;

public class RootController {
	private MainApp mainApp;
	private File curCSV;
	private File curTSV;
	private LinkedList<Patient> pts;
	
	public RootController(){
	}

	@FXML
	private void initialize() {
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public void importCSV() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select CSV to import");
		curCSV = fc.showOpenDialog(null);
		if (curCSV != null && curCSV.exists()) {
			pts = controller.CSVParsingUtils.CSVImport(curCSV);
			mainApp.getDatabaseHandler().insertPatients(pts);
		}
	}

	public void importTSV() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select TSV to import");
		curTSV = fc.showOpenDialog(null);
		if (curTSV != null && curTSV.exists()) {
			pts = controller.CSVParsingUtils.TSVImport(curTSV);
			mainApp.getDatabaseHandler().insertPatients(pts);
		}
	}

	public void exportCSV() {
		int curFileInt = 1;
//		pts = mainApp.getDatabaseHandler().getPTS();
		while (Files.exists(Paths.get("exported" + curFileInt + ".csv")))
			curFileInt++;
		controller.CSVParsingUtils.CSVExport("exported" + curFileInt + ".csv", pts);
	}

	public void exportTSV() {
		int curFileInt = 1;
//		pts = mainApp.getDatabaseHandler().getPTS();
		while (Files.exists(Paths.get("exported" + curFileInt + ".tsv")))
			curFileInt++;
		controller.CSVParsingUtils.TSVExport("exported" + curFileInt + ".tsv", pts);

	}
}
