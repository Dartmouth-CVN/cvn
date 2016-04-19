package view;

import java.time.LocalDate;
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
import model.Caregiver;
import model.Contact;
import model.IDisplayable;
import model.MainApp;
import model.Meal;
import model.MedicalStaff;
import model.Patient;
import model.Pet;

public class PatientProfileController {

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
	private TableView<Caregiver> careGiversTable;
	@FXML
	private TableColumn<Caregiver, String> caregiverNameColumn;
	@FXML
	private TableColumn<Caregiver, LocalDate> birthdayColumn;
	@FXML
	private TableColumn<Caregiver, String> relationColumn;
	@FXML
	private TableColumn<Caregiver, Boolean> inFamilyColumn;
	@FXML
	private TableView<Contact> contactInfoTable;
	@FXML
	private TableColumn<Contact, String> addressColumn;
	@FXML
	private TableColumn<Contact, String> phoneColumn;
	@FXML
	private TableColumn<Contact, String> emailColumn;
	@FXML
	private TableView<Meal> menuTable;
	@FXML
	private TableColumn<Meal, String> foodColumn;
	@FXML
	private TableColumn<Meal, Integer> caloriesColumn;
	@FXML
	private TableColumn<Meal, Integer> ratingColumn;
	@FXML
	private TableColumn<Meal, String> notesColumn;
	@FXML
	private TableView<Pet> petTable;
	@FXML
	private TableColumn<Pet, String> petNameColumn;
	@FXML
	private TableColumn<Pet, String> speciesColumn;
	@FXML
	private TableColumn<Pet, Boolean> allergyFriendlyColumn;

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
}
