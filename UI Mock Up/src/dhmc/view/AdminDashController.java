package dhmc.view;

import dhmc.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;

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
	private ImageView scheduleImage;
	@FXML
	private ImageView searchImage;

    // Reference to the main application.
    private Main mainApp;

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
    	tabPane.getTabs().add(dashTab);
    	tabPane.getTabs().add(scheduleTab);
    	tabPane.getTabs().add(searchTab);
    	tabPane.getTabs().add(importTab);
    	//list admin's profile at the top of dashboard
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMain(Main main) {
        this.mainApp = main;

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

    	searchTab.getContent();
    	
    	
    }
    
    /**
     * Called when the user clicks on import image.
     */
    @FXML
    private void handleImportSwitch() {

    	tabPane.getSelectionModel().select(importTab);
    	
    }
}

