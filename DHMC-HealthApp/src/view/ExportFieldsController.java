package view;

import java.time.LocalTime;
import java.util.ArrayList;

import controller.CSVParsingUtils;
import controller.XMLParsingUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import model.MainApp;

public class ExportFieldsController {

	@FXML
	private RadioButton CSVRadioButton = new RadioButton();
	@FXML
	private RadioButton TSVRadioButton = new RadioButton();
	@FXML
	private RadioButton XMLRadioButton = new RadioButton();
	@FXML
	private RadioButton CSVImportButton = new RadioButton();
	@FXML
	private RadioButton TSVImportButton = new RadioButton();
	@FXML
	private RadioButton XMLImportButton = new RadioButton();
	@FXML
	private RadioButton HTMLRadioButton = new RadioButton();
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
	@FXML
	private TitledPane personalPane = new TitledPane();
	@FXML
	private TitledPane healthPane = new TitledPane();
	@FXML
	private TitledPane preferencePane = new TitledPane();
	
	CheckBox[] personal = new CheckBox[6];
	CheckBox[] health = new CheckBox[18];
	CheckBox[] preference = new CheckBox[2];
	
	
	// Reference to the main application.
	@SuppressWarnings("unused")
	private MainApp mainApp;
	
	private ArrayList<Boolean> fieldCheck;

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
		fieldCheck = new ArrayList<Boolean>();
		loadCheckBoxArrays();
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
	public void handlePersonalExport() {

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
		
		for(boolean bool : fields )
			fieldCheck.add(bool);
	}

	/**
	 * Called when the user clicks the Go button.
	 */
	public void handleHealthExport() {
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
		fields[11] = sedentaryTimeCheckBox.isSelected();
		fields[12] = lightlyActiveTimeCheckBox.isSelected();
		fields[13] = fairlyActiveTimeCheckBox.isSelected();
		fields[14] = veryActiveTimeCheckBox.isSelected();
		fields[15] = activityCaloriesCheckBox.isSelected();
		fields[16] = timeAsleepCheckBox.isSelected();
		fields[17] = timeAwakeCheckBox.isSelected();
		fields[18] = timesWokenUpCheckBox.isSelected();
		fields[19] = bedTimeCheckBox.isSelected();

		for(boolean bool : fields )
			fieldCheck.add(bool);
	}

	// Ignore this method, preference according WILL NOT BE IMPLEMENTED
	/**
	 * Called when the user clicks the Go button.
	 */
	public void handlePreferenceExport() {

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
	}

	@FXML
	private void handlePersonalSelectButton() {

		for (int i = 0; i < personal.length; i++) {
			
			personal[i].setSelected(true);
		}
	}

	@FXML
	private void handlePersonalClearButton() {

		for (int i = 0; i < personal.length; i++) {
			
			personal[i].setSelected(false);
		}
	}


	@FXML
	private void handleHealthSelectButton() {

		for (int i = 0; i < health.length; i++) {
			
			health[i].setSelected(true);
		}
	}


	@FXML
	private void handleHealthClearButton() {

		for (int i = 0; i < health.length; i++) {
			
			health[i].setSelected(false);
		}
	}

	@FXML
	private void handlePreferenceSelectButton() {

		for (int i = 0; i < preference.length; i++) {
			
			preference[i].setSelected(true);
		}
	}

	@FXML
	private void handlePreferenceClearButton() {

		for (int i = 0; i < preference.length; i++) {
			
			preference[i].setSelected(false);
		}
	}


	@FXML
	private void handleExport(){
		handlePersonalExport();
		handleHealthExport();
		handlePreferenceExport();
		
		boolean[] fields = new boolean[fieldCheck.size()];
		for(int i = 0; i < fields.length; i++){
			fields[i] = fieldCheck.get(i);
		}
		String name = "Exported" + LocalTime.now(); 
		if (CSVRadioButton.isSelected()) {
			 CSVParsingUtils.CSVExport(name + ".csv", MainApp.getDatabaseHandler().getPatientList(), fields);
			 MainApp.showAlert("Export CSV done");
		} else if(TSVRadioButton.isSelected())  {
			 CSVParsingUtils.TSVExport(name + ".tsv", MainApp.getDatabaseHandler().getPatientList(), fields);
			 MainApp.showAlert("Export TSV done");
		}else if(XMLRadioButton.isSelected())  {
			XMLParsingUtils.writePatientsToXML(name + ".xml", MainApp.getDatabaseHandler().getPatientList());
			 MainApp.showAlert("Export XML done");
		}else if(HTMLRadioButton.isSelected())  {
			XMLParsingUtils.writePatientsToHTML(name + ".html", MainApp.getDatabaseHandler().getPatientList());
			 MainApp.showAlert("Export HTML done");
		}
	}
	
	private void loadCheckBoxArrays(){
		
		int i = 0;
		
		for(Node m : personalPane.getChildrenUnmodifiable()) {
			
			if (m instanceof CheckBox) {
				
				personal[i] = (CheckBox) m;
				i++;
			}
		}
		
		i = 0;
		
		for(Node n : healthPane.getChildrenUnmodifiable()) {
			
			if (n instanceof CheckBox) {
				
				health[i] = (CheckBox) n;
				i++;
			}
		}
		
		i = 0;
		
		for(Node o : preferencePane.getChildrenUnmodifiable()) {
			
			if (o instanceof CheckBox) {
				
				preference[i] = (CheckBox) o;
				i++;
			}
		}
	}
	
}
