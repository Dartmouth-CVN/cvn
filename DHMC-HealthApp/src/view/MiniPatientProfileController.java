package view;

import java.util.Random;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.IDisplayable;
import model.MainApp;
import model.Patient;

public class MiniPatientProfileController {

	// Integer will be replaced with Profile model
	@FXML
	private TableView<IDisplayable> profileTable;
	@FXML
	private TableColumn<IDisplayable, String> idColumn;
	@FXML
	private TableColumn<IDisplayable, String> firstNameColumn;
	@FXML
	private TableColumn<IDisplayable, String> lastNameColumn;
	@FXML
	private TextField searchField;
	@FXML
	private Label roomLabel = new Label();
	@FXML
	private Label nameLabel = new Label();
	@FXML
	private Label doctorLabel = new Label();
	@FXML
	private Label nurseLabel = new Label();
	@FXML
	private Label phoneLabel = new Label();
	@FXML
	private Label idLabel = new Label();
	@FXML
	private Label emailLabel = new Label();
	@FXML
	private TabPane profileTabPane = new TabPane();
	@FXML
	private Tab profileTab = new Tab();
	

	private String userID;

	// Reference to the main application.
	private MainApp mainApp;
	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getUserIDProperty());
		//medstaff data
		ObservableList<IDisplayable> personData = MainApp.getDatabaseHandler().searchPatient();
		profileTable.setItems(personData);
		// set table listener
		profileTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showMedStaffDetails(newValue));
	}

	
	public MiniPatientProfileController(IDisplayable user) {
		
		Patient p = MainApp.getDatabaseHandler().getPatient(user.getUserID());
		nameLabel.setText(p.getFirstName() + " " + p.getLastName());
		Random rand = new Random();
		//doctorLabel.setText("Doctor " + (rand.nextInt(10) + 1));
		//roomLabel.setText("Room " + (rand.nextInt(10) + 1));
		//nurseLabel.setText("Nurse " + (rand.nextInt(10) + 1));
		phoneLabel.setText(p.getContactInfo().getPrimaryPhone());
		idLabel.setText("ID: " + p.getUserID());
		emailLabel.setText("Email: " + p.getContactInfo().getPrimaryEmail());
		userID = p.getUserID();
	}
	
	/**
	 * Method that displays med staff mini profile
	 * @param user
	 */
	private void showMedStaffDetails(IDisplayable staff) {
	
		//MedicalStaff m = MainApp.getDatabaseHandler().getMedicalStaff(staff.getUserID());
		
		
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
	

	@FXML
	private void handleClickPatient() {
		
		// profileTab.setContent();
		 profileTabPane.getTabs().add(profileTab);
		
	}
	/**
	 * TODO make a new view to see the patient's data without editing it
	 * 
	 * This function will open a patient to view their information
	 * 
	 */
	@FXML
	public void viewPatient() {
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
