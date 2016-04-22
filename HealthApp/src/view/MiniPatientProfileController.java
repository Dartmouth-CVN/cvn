package view;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.MainApp;
import model.MedicalStaff;
import model.Patient;
import utils.ObjectNotFoundException;

public class MiniPatientProfileController extends AbsController {
	
	private Patient patient;

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
	@FXML
	private Label assignedStaffLabel = new Label();
	

	private String userID;
	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
//		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
//		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
//		positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<MedicalStaff> personData = MainApp.getDatabaseHandler().searchPatientAssignedStaff(patient);
		assignedStaffTable.setItems(personData);
		// set table listener
		assignedStaffTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedStaffDetails(newValue));
		assignedStaffLabel.setId("assignedStaff");
	}
	
	public MiniPatientProfileController(MainApp mainApp, Patient patient) {
		super(mainApp);
		this.patient = patient;
	}
	
	/**
	 * Method that displays med staff mini profile
	 * @param user
	 */
	private void showMedStaffDetails(MedicalStaff staff) {
	
		//databasehandler needs to make getMedicalStaff(MedicalStaff staff) method
		MedicalStaff ms = MainApp.getDatabaseHandler().getMedicalStaff(staff.getUserID());
		nameLabel.setText(ms.getFirstName() + " " + ms.getLastName());		
		
	}
	

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		Patient patient;
		try {
			patient = MainApp.getDatabaseHandler().getPatient(userID);
			if (patient != null)
				mainApp.showEditProfile(patient);
		} catch (ObjectNotFoundException e) {
			// TODO handleEditProfile catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * 
	 * This function will open a patient to view their information
	 * 
	 */
	@FXML
	public void viewPatientProfile() {
		//Will open a new view to look at a given patient
		
	        Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("../view/PatientProfile.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("My New Stage Title");
	            stage.setScene(new Scene(root, 450, 450));
	            stage.show();

	            //hide this current window (if this is whant you want
	            //((Node)(event.getSource())).getScene().getWindow().hide();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	   
	}
	
	/**
	 *
	 * 
	 * This function will open a patient to view their information
	 * 
	 */
	@FXML
	public void viewMedicalStaffProfile() {
		//Will open a new view to look at a given patient
	}
	
	/**
	 * TODO removePatient
	 * 
	 * This function will remove a patient from the database
	 * 
	 */
	@FXML
	public void removePatient() {
		//Database will be called

	}
	
}
