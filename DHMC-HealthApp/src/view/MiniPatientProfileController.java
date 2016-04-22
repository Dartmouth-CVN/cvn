package view;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
	private Label patientNameLabel = new Label();
	@FXML
	private Label medicalStaffNameLabel = new Label();
	@FXML
	private Label idLabel = new Label();
	@FXML
	private Label phoneLabel = new Label();
	@FXML
	private Label emailLabel = new Label();
	
	private Patient patient;
	

	private String userID;

	// Reference to the main application.
	private MainApp mainApp;
	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// set table listener
		assignedStaffTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedStaffDetails(newValue));
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
		
		patientNameLabel.setText(patient.getFirstName() + " " + patient.getLastName());
		idLabel.setText(patient.getUserID());
		phoneLabel.setText(patient.getContactInfo().getPrimaryPhone());
		emailLabel.setText(patient.getContactInfo().getPrimaryEmail());
		
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<MedicalStaff> assignedStaff = MainApp.getDatabaseHandler().searchPatientAssignedStaff(patient.getUserID());
		assignedStaffTable.setItems(assignedStaff);
		
	}
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	
	public MiniPatientProfileController() {
	}
	
	/**
	 * Method that displays med staff mini profile
	 * @param user
	 */
	private void showMedStaffDetails(MedicalStaff staff) {
	
		//databasehandler needs to make getMedicalStaff(MedicalStaff staff) method
		MedicalStaff ms = MainApp.getDatabaseHandler().getMedicalStaff(staff.getUserID());
		medicalStaffNameLabel.setText(ms.getFirstName() + " " + ms.getLastName());		
		
	}
	

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		
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
		
	        try {
	           // Parent root = FXMLLoader.load(MainApp.class.getResource("../view/ViewPatient.fxml"));
	            
	            FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("../view/ViewPatient.fxml"));
				ScrollPane viewPatient = (ScrollPane) loader.load();

				ViewPatientController controller = loader.getController();
				controller.setMain(mainApp);
				
	            Stage stage = new Stage();
	            stage.setTitle("Patient Profile");
	            stage.setScene(new Scene(viewPatient, 1214, 700));
	            
	            controller.setPatient(patient);
	            
	            stage.show();

	            //hide this current window (if this is whant you want
	            //((Node)(event.getSource())).getScene().getWindow().hide();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	   
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
