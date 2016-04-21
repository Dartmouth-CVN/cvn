package controller;

import java.time.LocalTime;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import model.MainApp;
import utils.SVParsingUtils;
import utils.XMLParsingUtils;

public class ExportController extends AbsController {

	@FXML
	private RadioButton CSVRadioButton = new RadioButton();
	@FXML
	private RadioButton TSVRadioButton = new RadioButton();
	@FXML
	private RadioButton XMLRadioButton = new RadioButton();
	@FXML
	private RadioButton HTMLRadioButton = new RadioButton();
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
	
	private ArrayList<Boolean> fieldCheck;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public ExportController(MainApp mainApp) {
		super(mainApp);
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
	 * Called when the user clicks the Go button.
	 */
	public void handlePersonalExport() {

		boolean[] fields = new boolean[12];
		
		for(boolean bool : fields )
			fieldCheck.add(bool);
	}

	/**
	 * Called when the user clicks the Go button.
	 */
	public void handleHealthExport() {
		boolean[] fields = new boolean[20];

		for(boolean bool : fields )
			fieldCheck.add(bool);
	}

	// Ignore this method, preference according WILL NOT BE IMPLEMENTED
	/**
	 * Called when the user clicks the Go button.
	 */
	public void handlePreferenceExport() {

		//boolean[] fields = new boolean[12];

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
			 SVParsingUtils.exportData(name + ".csv", MainApp.getDatabaseHandler().getPatientList(), fields);
			 MainApp.showAlert("Export CSV done");
		} else if(TSVRadioButton.isSelected())  {
			 SVParsingUtils.exportData(name + ".tsv", MainApp.getDatabaseHandler().getPatientList(), fields);
			 MainApp.showAlert("Export TSV done");
		}else if(XMLRadioButton.isSelected())  {
			XMLParsingUtils.exportData(name + ".xml", MainApp.getDatabaseHandler().getPatientList());
			 MainApp.showAlert("Export XML done");
		}else if(HTMLRadioButton.isSelected())  {
			XMLParsingUtils.exportsData(name + ".html", MainApp.getDatabaseHandler().getPatientList());
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
