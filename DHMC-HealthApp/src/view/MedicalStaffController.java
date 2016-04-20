package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Caregiver;
import model.Contact;
import model.IDisplayable;
import model.MainApp;
import model.Meal;
import model.MedicalStaff;
import model.Patient;
import model.Pet;

public class MedicalStaffController {

	// Integer will be replaced with Profile model
	@FXML
	private TableView<Patient> assignedPatientTable;
	@FXML
	private TableColumn<MedicalStaff, String> firstNameColumn;
	@FXML
	private TableColumn<MedicalStaff, String> lastNameColumn;
	@FXML
	private Label nameLabel = new Label();
	@FXML
	private Label roleLabel = new Label();
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
	private void initialize(MedicalStaff staff) {
		
		
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

		ObservableList<Patient> patientData = MainApp.getDatabaseHandler().searchMedStaffAssignedPatient(staff);
		assignedPatientTable.setItems(patientData);
		
		

	}
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	
	public MedicalStaffController(IDisplayable user) {
	}
	
	
	
}
