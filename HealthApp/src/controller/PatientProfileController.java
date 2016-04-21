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
	private TableColumn<Caregiver, String> birthdayColumn;
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
	private TableColumn<Meal, Number> caloriesColumn;
	@FXML
	private TableColumn<Meal, Number> ratingColumn;
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
	@FXML
	private Label nameLabel = new Label();
	@FXML
	private Label idLabel = new Label();
	@FXML
	private Label phoneLabel = new Label();
	@FXML
	private Label emailLabel = new Label();

	// Reference to the main application.
	private MainApp mainApp;
	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize(Patient patient) {
		caregiverNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
		relationColumn.setCellValueFactory(cellData -> cellData.getValue().relationProperty());
		inFamilyColumn.setCellValueFactory(cellData -> cellData.getValue().inFamilyProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Caregiver> caregivers = MainApp.getDatabaseHandler().searchPatientCaregiver(patient);
		careGiversTable.setItems(caregivers);
		
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<MedicalStaff> personData = MainApp.getDatabaseHandler().searchPatientAssignedStaff(patient);
		assignedStaffTable.setItems(personData);
		
		foodColumn.setCellValueFactory(cellData -> cellData.getValue().foodProperty());
		caloriesColumn.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty());
		ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
		notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Meal> meals = MainApp.getDatabaseHandler().searchPatientMeal(patient);
		menuTable.setItems(meals);
		
		petNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		speciesColumn.setCellValueFactory(cellData -> cellData.getValue().speciesProperty());
		allergyFriendlyColumn.setCellValueFactory(cellData -> cellData.getValue().allergyFriendlyProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Pet> pets = MainApp.getDatabaseHandler().searchPatientPet(patient);
		petTable.setItems(pets);
		
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		
		// set table listener
		careGiversTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showCareGiversContactInfo(newValue));
	}
	
	public PatientProfileController(IDisplayable user) {
	}
	
	/**
	 * Method that displays med staff mini profile
	 * @param user
	 */
	private void showCareGiversContactInfo(Caregiver caregiver) {
	
		//databasehandler needs to make getMedicalStaff(MedicalStaff staff) method
		Caregiver cg = MainApp.getDatabaseHandler().getCaregiver(caregiver.getCaregiverID());
		//nameLabel.setText(ms.getFirstName() + " " + ms.getLastName());	
		Contact contactInfo = cg.getContactInfo();
		
	}
}
