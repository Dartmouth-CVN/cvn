package view;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import model.MainApp;
import model.Patient;

public class ImportExportController {
	private MainApp mainApp;

	@FXML
	private Button fileButton;
	@FXML
	private Button importButton;
	@FXML
	private Button exportButton;

	private File curFile;
	private LinkedList<Patient> pts;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

	}
	@FXML
	public void chooseFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select CSV to import");
		curFile = fc.showOpenDialog(null);
		if (curFile.exists())
			importButton.setText("Import " + curFile.getName());
	}
	@FXML
	public void importCSV() {
		if (curFile != null && curFile.exists())
			pts = model.CSVParsingUtils.CSVImport(curFile);
	}
	@FXML
	public void exportCSV() {
		int curFileInt = 1;
		while (Files.exists(Paths.get("exported" + curFileInt + ".csv")))
			curFileInt++;
		model.CSVParsingUtils.CSVExport("exported" + curFileInt + ".csv", pts);
	}

}
