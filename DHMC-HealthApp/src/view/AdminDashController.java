package view;

import model.MainApp;
import model.Patient;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class AdminDashController {

	@FXML
	private TabPane tabPane;
	@FXML
	private Tab dashTab = new Tab();
	@FXML
	private Tab scheduleTab = new Tab();
	@FXML
	private Tab importTab = new Tab();
	@FXML
	private Tab searchTab = new Tab();
	@FXML
	private ImageView scheduleImage = new ImageView();
	@FXML
	private ImageView searchImage = new ImageView();
	@FXML
	private ImageView importImage = new ImageView();
	@FXML
	private AnchorPane adminDash;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public AdminDashController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// list admin's profile at the top of dashboard
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(MainApp mainApp) {
		this.mainApp = mainApp;

		// get the list of profiles from another class
		// personTable.setItems(mainApp.getPersonData());
	}

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	// private void showProfileDetails(Profile profile) {
	// fills the profile space in the admin dash
	/*
	 * if (profile != null) { // Fill the labels with info from the person
	 * object. firstNameLabel.setText(person.getFirstName());
	 * lastNameLabel.setText(person.getLastName());
	 * streetLabel.setText(person.getStreet());
	 * postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
	 * cityLabel.setText(person.getCity());
	 * birthdayLabel.setText(DateUtil.format(person.getBirthday()));
	 * 
	 * } else { // Profile is null, remove all the text.
	 * firstNameLabel.setText(""); lastNameLabel.setText("");
	 * streetLabel.setText(""); postalCodeLabel.setText("");
	 * cityLabel.setText(""); birthdayLabel.setText(""); } }
	 */

	/**
	 * Called when the user clicks on the calendar image.
	 */
	@FXML
	private void handleScheduleSwitch() {

		tabPane.getSelectionModel().select(scheduleTab);

	}

	/**
	 * Called when the user clicks on search image.
	 */
	@FXML
	private void handleSearchSwitch() {

		mainApp.setHorizontalLayout();
		setHorizontalLayout();
		mainApp.loadSearchTab();
		tabPane.getSelectionModel().select(searchTab);
	}

	public void setHorizontalLayout() {
		System.out.println("changing dash size...");
		adminDash.setPrefWidth(600);
		adminDash.setPrefHeight(450);
	}

	/**
	 * Called when the user clicks on import image.
	 */
	@FXML
	private void handleImportSwitch() {
		tabPane.getSelectionModel().select(importTab);
	}

	// ImportandExportThings

	@FXML
	private Button csvButton;
	@FXML
	private Button importCSVButton;
	@FXML
	private Button exportCSVButton;

	@FXML
	private Button tsvButton;
	@FXML
	private Button importTSVButton;
	@FXML
	private Button exportTSVButton;

	private File curCSV;
	private File curTSV;
	private LinkedList<Patient> pts;

	@FXML
	public void chooseCSV() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select CSV to import");
		curCSV = fc.showOpenDialog(null);
		if (curCSV.exists())
			importCSVButton.setText("Import " + curCSV.getName());
	}

	@FXML
	public void importCSV() {
		if (curCSV != null && curCSV.exists()){
			pts = model.CSVParsingUtils.CSVImport(curCSV);
			mainApp.getDatabaseHandler().insertPatients(pts);
		}
	}
	
	@FXML
	public void chooseTSV() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select TSV to import");
		curTSV = fc.showOpenDialog(null);
		if (curTSV.exists())
			importTSVButton.setText("Import " + curTSV.getName());
	}

	@FXML
	public void importTSV() {
		if (curTSV != null && curTSV.exists()){
			pts = model.CSVParsingUtils.TSVImport(curTSV);
		}
	}
	
	@FXML
	public void exportCSV() {
		int curFileInt = 1;
		while (Files.exists(Paths.get("exported" + curFileInt + ".csv")))
			curFileInt++;
		model.CSVParsingUtils.CSVExport("exported" + curFileInt + ".csv", pts);
	}
	@FXML
	public void exportTSV(){
		int curFileInt = 1;
		while (Files.exists(Paths.get("exported" + curFileInt + ".tsv")))
			curFileInt++;
		model.CSVParsingUtils.TSVExport("exported" + curFileInt + ".tsv", pts);
	
	}
}
