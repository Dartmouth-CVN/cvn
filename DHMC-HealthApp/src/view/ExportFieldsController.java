package view;

import java.io.File;

import controller.CSVParsingUtils;
import controller.DatabaseHandler;
import controller.XMLParsingUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import model.MainApp;

public class ExportFieldsController {

	@FXML
	private RadioButton personalCSVRadioButton = new RadioButton();
	@FXML
	private RadioButton personalTSVRadioButton = new RadioButton();
	@FXML
	private RadioButton personalXMLRadioButton = new RadioButton();
	@FXML
	private RadioButton personalHTMLRadioButton = new RadioButton();
	@FXML
	private RadioButton healthCSVRadioButton = new RadioButton();
	@FXML
	private RadioButton healthTSVRadioButton = new RadioButton();
	@FXML
	private RadioButton preferenceCSVRadioButton = new RadioButton();
	@FXML
	private RadioButton preferenceTSVRadioButton = new RadioButton();
	@FXML
	private RadioButton preferenceXMLRadioButton = new RadioButton();
	@FXML
	private RadioButton preferenceHTMLRadioButton = new RadioButton();
	
	@FXML
	private CheckBox firstNameCheckBox = new CheckBox();
	@FXML
	private CheckBox lastNameCheckBox = new CheckBox();
	@FXML
	private CheckBox userNameCheckBox = new CheckBox();
	@FXML
	private CheckBox weightCheckBox = new CheckBox();
	@FXML
	private CheckBox heightCheckBox = new CheckBox();
	@FXML
	private CheckBox fatCheckBox = new CheckBox();
	@FXML
	private CheckBox BMICheckBox = new CheckBox();
	@FXML
	private CheckBox dateCheckBox = new CheckBox();
	@FXML
	private CheckBox distanceCheckBox = new CheckBox();
	@FXML
	private CheckBox stepsCheckBox = new CheckBox();
	@FXML
	private CheckBox floorsCheckBox = new CheckBox();
	@FXML
	private CheckBox activityCaloriesCheckBox = new CheckBox();
	@FXML
	private CheckBox caloriesBurnedCheckBox = new CheckBox();
	@FXML
	private CheckBox timeAwakeCheckBox = new CheckBox();
	@FXML
	private CheckBox timeAsleepCheckBox = new CheckBox();
	@FXML
	private CheckBox lightlyActiveTimeCheckBox = new CheckBox();
	@FXML
	private CheckBox fairlyActiveTimeCheckBox = new CheckBox();
	@FXML
	private CheckBox veryActiveTimeCheckBox = new CheckBox();
	@FXML
	private CheckBox timesWokenUpCheckBox = new CheckBox();
	@FXML
	private CheckBox bedTimeCheckBox = new CheckBox();
	@FXML
	private CheckBox sedentaryTimeCheckBox = new CheckBox();
	@FXML
	private CheckBox foodCheckBox = new CheckBox();
	@FXML
	private CheckBox petsCheckBox = new CheckBox();
	@FXML
	private CheckBox addressCheckBox = new CheckBox();
	@FXML
	private CheckBox phoneNumberCheckBox = new CheckBox();
	@FXML
	private CheckBox emailAddressCheckBox = new CheckBox();
	@FXML
	private Button personalSelectAllButton = new Button();
	@FXML
	private Button personalClearAllButton = new Button();
	@FXML
	private Button healthSelectAllButton = new Button();
	@FXML
	private Button healthClearAllButton = new Button();
	@FXML
	private Button preferenceSelectAllButton = new Button();
	@FXML
	private Button preferenceClearAllButton = new Button();
	@FXML
	private Button personalExportButton = new Button();
	@FXML
	private Button healthExportButton = new Button();
	@FXML
	private Button preferenceExportButton = new Button();

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public ExportFieldsController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

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
	 * Called when the user clicks the Go button.
	 */
	@FXML
	private void handlePersonalExportButton() {

		boolean[] fields = new boolean[12];

		fields[0] = false;
		fields[1] = firstNameCheckBox.isSelected();
		fields[2] = lastNameCheckBox.isSelected();
		fields[3] = false;
		fields[4] = false;
		fields[5] = false;
		fields[6] = addressCheckBox.isSelected();
		fields[7] = phoneNumberCheckBox.isSelected();
		fields[8] = emailAddressCheckBox.isSelected();
		fields[9] = false;
		fields[10] = false;
		fields[11] = false;

		if (personalCSVRadioButton.isSelected()) {
			CSVParsingUtils.CSVExport(createFilename("Exported", ".csv"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		} else if(personalTSVRadioButton.isSelected()){
			CSVParsingUtils.TSVExport(createFilename("Exported", ".tsv"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		}else if(personalXMLRadioButton.isSelected()){
			XMLParsingUtils.writePatientsToXML(createFilename("Exported", ".xml"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		}else if(personalHTMLRadioButton.isSelected()){
			XMLParsingUtils.writePatientsToHTML(createFilename("Exported", ".html"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		}else{
			return;
		}

	}

	/**
	 * Called when the user clicks the Go button.
	 */
	@FXML
	private void handleHealthExportButton() {

		boolean[] fields = new boolean[20];

		fields[0] = true;
		fields[1] = true;
		fields[2] = dateCheckBox.isSelected();
		fields[3] = heightCheckBox.isSelected();
		fields[4] = weightCheckBox.isSelected();
		fields[5] = BMICheckBox.isSelected();
		fields[6] = fatCheckBox.isSelected();
		fields[7] = caloriesBurnedCheckBox.isSelected();
		fields[8] = stepsCheckBox.isSelected();
		fields[9] = distanceCheckBox.isSelected();
		fields[10] = floorsCheckBox.isSelected();
		;
		fields[11] = sedentaryTimeCheckBox.isSelected();
		fields[12] = lightlyActiveTimeCheckBox.isSelected();
		fields[13] = fairlyActiveTimeCheckBox.isSelected();
		fields[14] = veryActiveTimeCheckBox.isSelected();
		fields[15] = activityCaloriesCheckBox.isSelected();
		fields[16] = timeAsleepCheckBox.isSelected();
		fields[17] = timeAwakeCheckBox.isSelected();
		fields[18] = timesWokenUpCheckBox.isSelected();
		fields[19] = bedTimeCheckBox.isSelected();

		if (personalCSVRadioButton.isSelected()) {

			// CSVExport(createFilename("Exported", ",
			// DatabaseHandler.getUniqueInstance().searchPatient(), fields);

		} else {

			// TSVExport(createFilename("Exported", ",
			// DatabaseHandler.getUniqueInstance().searchPatient(), fields);

		}
	}

	// Ignore this method, preference according WILL NOT BE IMPLEMENTED
	/**
	 * Called when the user clicks the Go button.
	 */
	@FXML
	private void handlePreferenceExportButton() {

		boolean[] fields = new boolean[12];

		fields[0] = false;
		fields[1] = firstNameCheckBox.isSelected();
		fields[2] = lastNameCheckBox.isSelected();
		fields[3] = false;
		fields[4] = false;
		fields[5] = false;
		fields[6] = addressCheckBox.isSelected();
		fields[7] = phoneNumberCheckBox.isSelected();
		fields[8] = emailAddressCheckBox.isSelected();
		fields[9] = false;
		fields[10] = false;
		fields[11] = false;

		if (preferenceCSVRadioButton.isSelected()) {
			CSVParsingUtils.CSVExport(createFilename("Exported", ".csv"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		} else if(preferenceTSVRadioButton.isSelected()){
			CSVParsingUtils.TSVExport(createFilename("Exported", ".tsv"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		}else if(preferenceXMLRadioButton.isSelected()){
			XMLParsingUtils.writePatientsToXML(createFilename("Exported", ".xml"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		}else if(preferenceHTMLRadioButton.isSelected()){
			XMLParsingUtils.writePatientsToHTML(createFilename("Exported", ".html"), DatabaseHandler.getUniqueInstance().getPTS(), fields);
		}else{
			return;
		}
	}

	@FXML
	private void handlePersonalSelectButton() {

		firstNameCheckBox.setSelected(true);
		lastNameCheckBox.setSelected(true);
		userNameCheckBox.setSelected(true);
		addressCheckBox.setSelected(true);
		phoneNumberCheckBox.setSelected(true);
		emailAddressCheckBox.setSelected(true);

	}

	@FXML
	private void handlePersonalClearButton() {

		firstNameCheckBox.setSelected(false);
		lastNameCheckBox.setSelected(false);
		userNameCheckBox.setSelected(false);
		addressCheckBox.setSelected(false);
		phoneNumberCheckBox.setSelected(false);
		emailAddressCheckBox.setSelected(false);

	}

	@FXML
	private void handleHealthSelectButton() {

		weightCheckBox.setSelected(true);
		heightCheckBox.setSelected(true);
		fatCheckBox.setSelected(true);
		BMICheckBox.setSelected(true);
		dateCheckBox.setSelected(true);
		distanceCheckBox.setSelected(true);
		stepsCheckBox.setSelected(true);
		floorsCheckBox.setSelected(true);
		activityCaloriesCheckBox.setSelected(true);
		caloriesBurnedCheckBox.setSelected(true);
		timeAwakeCheckBox.setSelected(true);
		timeAsleepCheckBox.setSelected(true);
		lightlyActiveTimeCheckBox.setSelected(true);
		fairlyActiveTimeCheckBox.setSelected(true);
		veryActiveTimeCheckBox.setSelected(true);
		bedTimeCheckBox.setSelected(true);
		timesWokenUpCheckBox.setSelected(true);
		sedentaryTimeCheckBox.setSelected(true);

	}

	@FXML
	private void handleHealthClearButton() {

		weightCheckBox.setSelected(false);
		heightCheckBox.setSelected(false);
		fatCheckBox.setSelected(false);
		BMICheckBox.setSelected(false);
		dateCheckBox.setSelected(false);
		distanceCheckBox.setSelected(false);
		stepsCheckBox.setSelected(false);
		floorsCheckBox.setSelected(false);
		activityCaloriesCheckBox.setSelected(false);
		caloriesBurnedCheckBox.setSelected(false);
		timeAwakeCheckBox.setSelected(false);
		timeAsleepCheckBox.setSelected(false);
		lightlyActiveTimeCheckBox.setSelected(false);
		fairlyActiveTimeCheckBox.setSelected(false);
		veryActiveTimeCheckBox.setSelected(false);
		bedTimeCheckBox.setSelected(false);
		timesWokenUpCheckBox.setSelected(false);
		sedentaryTimeCheckBox.setSelected(false);

	}

	@FXML
	private void handlePreferenceSelectButton() {

		foodCheckBox.setSelected(true);
		petsCheckBox.setSelected(true);

	}

	@FXML
	private void handlePreferenceClearButton() {

		foodCheckBox.setSelected(false);
		petsCheckBox.setSelected(false);

	}
	
	private String createFilename(String base, String extension){
		File toEx = new File(base+extension);
		int i=1;
		while(toEx.exists()){
			toEx = new File(base+i+extension);
			i++;
		}
		return base+i+extension;
	}

}
