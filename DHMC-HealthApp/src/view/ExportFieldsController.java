package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import model.MainApp;

public class ExportFieldsController {

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
	private Button exportButton = new Button();
	


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
	private void handleExportButton() {

		

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

}

