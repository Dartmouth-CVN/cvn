package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
//import model.mainApp;

public class ImportExportController {
	// private MainApp mainApp;

	@FXML
	private Button fileButton;
	@FXML
	private Button importButton;
	@FXML
	private Button exportButton;

	private File curFile;
	//	private LinkedList<Patient> pts;
	
	
	/*
	 * public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; }
	 */

	@FXML
	private void initialize() {

	}

	public void chooseFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select CSV to import");
		curFile = fc.showOpenDialog(null);
		if (curFile.exists())
			importButton.setText("Import " + curFile.getName());
	}
/*
	public void importCSV() {
		if (curFile != null && curFile.exists())
			pts = model.CSVParsingUtils.CSVImport(curFile);
	}

	public void exportCSV(){
		int curFileInt = 1;
		while (	Files.exists(Paths.get("exported"+1+".csv")))
			curFileInt++;
		model.CSVParsingUtils.CSVExport("exported"+1+".csv", pts);
	}
	*/
}
