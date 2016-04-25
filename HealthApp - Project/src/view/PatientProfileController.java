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
		// TODO: Manage Caregiver data
	}

	public PatientProfileController() {
		super();
	}
	
	/**
	 * Method that displays med staff mini profile
	 * @param
	 */
	private void showCareGiversContactInfo(Caregiver caregiver) {
		
	}
}
