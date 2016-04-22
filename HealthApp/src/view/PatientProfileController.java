package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Caregiver;
import model.Contact;
import model.MainApp;
import model.Meal;
import model.MedicalStaff;
import model.Patient;
import model.Pet;

public class PatientProfileController extends AbsController {
	
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

	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		caregiverNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		birthdayColumn.setCellValueFactory(cellData -> cellData.getValue().getBirthdayProperty());
		relationColumn.setCellValueFactory(cellData -> cellData.getValue().getRelationProperty());
		inFamilyColumn.setCellValueFactory(cellData -> cellData.getValue().getInFamilyProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Caregiver> caregivers = MainApp.getDatabaseHandler().searchPatientCaregiver(patient);
		careGiversTable.setItems(caregivers);
		
//		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
//		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
//		positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<MedicalStaff> personData = MainApp.getDatabaseHandler().searchPatientAssignedStaff(patient);
		assignedStaffTable.setItems(personData);
		

//		foodColumn.setCellValueFactory(cellData -> cellData.getValue().foodProperty());
//		caloriesColumn.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty());
//		ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
//		notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());

		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Meal> meals = MainApp.getDatabaseHandler().searchPatientMeal(patient);
		menuTable.setItems(meals);
		
		petNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		speciesColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeciesProperty());
		allergyFriendlyColumn.setCellValueFactory(cellData -> cellData.getValue().getAllergyFriendlyProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Pet> pets = MainApp.getDatabaseHandler().searchPatientPet(patient);
		petTable.setItems(pets);
		
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneProperty());
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
		
		// set table listener
		careGiversTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showCareGiversContactInfo(newValue));
	}

	public PatientProfileController(MainApp mainApp, Patient patient) {
		super(mainApp);
		this.patient = patient;
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
		// TODO: Show caregiver contact info
		
	}
}
