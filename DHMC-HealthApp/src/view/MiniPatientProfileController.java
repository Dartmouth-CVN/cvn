package view;

import java.util.Random;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.IDisplayable;
import model.MainApp;
import model.MedicalStaff;
import model.Patient;

public class MiniPatientProfileController {

	// Integer will be replaced with Profile model
	@FXML
	private TableView<MedicalStaff> assignedStaffTable;
	@FXML
	private TableColumn<MedicalStaff, String> positionColumn;
	@FXML
	private TableColumn<MedicalStaff, String> firstNameColumn;
	@FXML
	private TableColumn<MedicalStaff, String> lastNameColumn;
	@FXML
	private Button viewPatientProfileButton = new Button();
	@FXML
	private Button viewMedicalStaffProfileButton = new Button();
	@FXML
	private Button editProfileButton = new Button();
	@FXML
	private Button removeProfileButton = new Button();
	@FXML
	private Label nameLabel = new Label();
	@FXML
	private Label idLabel = new Label();
	@FXML
	private Label phoneLabel = new Label();
	@FXML
	private Label emailLabel = new Label();
	

	private String userID;

	// Reference to the main application.
	private MainApp mainApp;
	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize(Patient patient) {
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<MedicalStaff> personData = MainApp.getDatabaseHandler().searchAssignedStaff(patient);
		assignedStaffTable.setItems(personData);
		// set table listener
		assignedStaffTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedStaffDetails(newValue));
	}
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	
	public MiniPatientProfileController(IDisplayable user) {
	}
	
	/**
	 * Method that displays med staff mini profile
	 * @param user
	 */
	private void showMedStaffDetails(MedicalStaff staff) {
	
		//databasehandler needs to make getMedicalStaff(MedicalStaff staff) method
		MedicalStaff ms = MainApp.getDatabaseHandler().getMedicalStaff(staff.getMedID());
		nameLabel.setText(ms.getFirstName() + " " + ms.getLastName());		
		
	}
	

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		Patient patient = MainApp.getDatabaseHandler().getPatient(userID);
		if (patient != null)
			mainApp.showEditProfile(patient);

	}
	
	/**
	 * TODO make a new view to see the patient's data without editing it
	 * 
	 * This function will open a patient to view their information
	 * 
	 */
	@FXML
	public void viewPatientProfile() {
		//Will open a new view to look at a given patient
	}
	
	/**
	 * TODO make a new view to see the patient's data without editing it
	 * 
	 * This function will open a patient to view their information
	 * 
	 */
	@FXML
	public void viewMedicalStaffProfile() {
		//Will open a new view to look at a given patient
	}
	
	/**
	 * TODO call the database to remove the patient
	 * 
	 * This function will remove a patient from the database
	 * 
	 */
	@FXML
	public void removePatient() {
		//Database will be called

	}
	
}
