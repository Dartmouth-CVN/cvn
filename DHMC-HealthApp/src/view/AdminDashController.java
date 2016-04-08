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

    // Reference to the main application.
    @SuppressWarnings("unused")
	private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public AdminDashController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	//list admin's profile at the top of dashboard
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMain(MainApp mainApp) {
        this.mainApp = mainApp;

        //get the list of profiles from another class
        //personTable.setItems(mainApp.getPersonData());
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
   // private void showProfileDetails(Profile profile) {
    	//fills the profile space in the admin dash
        /*if (profile != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            
        } else {
            // Profile is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }*/
    
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

    	tabPane.getSelectionModel().select(searchTab);	
    }
    
    /**
     * Called when the user clicks on import image.
     */
    @FXML
    private void handleImportSwitch() {

    	tabPane.getSelectionModel().select(importTab);
    	
    }
    
    
    //ImportandExportThings
    
	@FXML
	private Button fileButton;
	@FXML
	private Button importButton;
	@FXML
	private Button exportButton;

	private File curFile;
	private LinkedList<Patient> pts;


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

