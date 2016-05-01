package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import utils.ObjectNotFoundException;

public class MiniPatientProfileController extends AbsController {
	
	private Patient patient;

	// Integer will be replaced with Profile model
	@FXML
	private TableView<MedicalStaffWrapper> assignedStaffTable;
	@FXML
	private TableColumn<MedicalStaffWrapper, String> positionColumn;
	@FXML
	private TableColumn<MedicalStaffWrapper, String> firstNameColumn;
	@FXML
	private TableColumn<MedicalStaffWrapper, String> lastNameColumn;
	@FXML
	private Button viewPatientProfileButton = new Button();
	@FXML
	private Button viewMedicalStaffProfileButton = new Button();
	@FXML
	private Button editProfileButton = new Button();
	@FXML
	private Button removeProfileButton = new Button();
	@FXML
	private ImageView patientProfilePic;
	@FXML
	private ImageView staffProfilePic;
	@FXML
	private Label nameLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label staffNameLabel;
	@FXML
	private AnchorPane miniProfilePane;


	private int userId;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}
	
	@Override
	public FXMLLoader getLoader(){
		loader.setLocation(MainApp.class.getResource("/view/MiniPatientProfileView.fxml"));
		return loader;
	}
	
	public MiniPatientProfileController() {
		key = "mini patient profile";
	}

	/**
	 * Method that displays med staff mini profile
	 * @param
	 */
	private void showMedStaffDetails(MedicalStaff staff) {	
	}
	
	public void setPatient(Patient patient){
		this.patient = patient;
		loadPatientDetails();
	}

	private int staffIndex;
	ObservableList<MedicalStaffWrapper> medStaff;
	
	public void loadPatientDetails(){
		patientProfilePic.setImage(new Image("file:" + patient.getPicture()));
		nameLabel.setText(patient.getFirstName() + " " + patient.getLastName());
		idLabel.setText(String.valueOf(patient.getUserId()));

		try {
			phoneLabel.setText(patient.getContactInfo().getPrimaryPhone().getValue());
			emailLabel.setText(patient.getContactInfo().getPrimaryEmail().getValue());
		} catch (ObjectNotFoundException e) {
			MainApp.printError(e);
		}

		medStaff  = FXCollections.observableArrayList();
		for(MedicalStaff med : patient.getAssignedStaff())
			medStaff.add(new MedicalStaffWrapper(med));

		assignedStaffTable.setItems(medStaff);
		assignedStaffTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			staffIndex = assignedStaffTable.getSelectionModel().getSelectedIndex();
			handleStaffSwitch();
		});
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		positionColumn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());
	}

	private void handleStaffSwitch(){
		MedicalStaff med = medStaff.get(staffIndex).getMedicalStaff();
		staffNameLabel.setText(med.getFirstName() + " " + med.getLastName());
		staffProfilePic.setImage(new Image("file:" + med.getPicture()));
	}
	

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		( (AdminDashController) MainApp.getLoadedSceneOfType(
				new AdminDashController()).getController()).loadEditPatientTab(patient);
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
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("/view/PatientProfile.fxml"));
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
