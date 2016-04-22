package view;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.MainApp;
import model.Patient;
import model.UserTableDisplay;
import utils.ObjectNotFoundException;

public class SearchController extends AbsController {

	// Integer will be replaced with Profile model
	@FXML
	private TableView<UserTableDisplay> profileTable;
	@FXML
	private TableColumn<UserTableDisplay, Number> idColumn;
	@FXML
	private TableColumn<UserTableDisplay, String> firstNameColumn;
	@FXML
	private TableColumn<UserTableDisplay, String> lastNameColumn;
	@FXML
	private TextField searchField;
	@FXML
	private TabPane profileTabPane = new TabPane();
	@FXML
	private Tab profileTab = new Tab();

	private int userId;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public SearchController() {
	}

	@FXML
	public FXMLLoader getLoader() {
		loader.setLocation(MainApp.class.getResource("../view/SearchView.fxml"));
		return loader;
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getUserIdProperty());
		profileTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			userId = newValue.getUserId();
			handleClickPatient();
		});
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Called when the user clicks the Go button.
	 */
	@FXML
	private void handleFindPatient() {
		String key = searchField.getText();

		if (key.equals("")) {
			try {
				List<Patient> patients = MainApp.getDBHandler().getPatients();
				ObservableList<UserTableDisplay> patientList = FXCollections.observableArrayList();
				for (Patient patient : patients)
					patientList.add(
							new UserTableDisplay(patient.getUserId(), patient.getFirstName(), patient.getLastName()));

				profileTable.setItems(patientList);
			} catch (ObjectNotFoundException e) {
				MainApp.printError(e);
			}
		}
	}

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		Patient patient;
		try {
			patient = MainApp.getDBHandler().getPatient(userId);
			if (patient != null)
				;
		} catch (ObjectNotFoundException e) {
			MainApp.printError(e);
		}
	}

	@FXML
	private void handleClickPatient() {
		showMiniPatientProfile();
	}

	public void showMiniPatientProfile() {
		MiniPatientProfileController controller = new MiniPatientProfileController();

		try {
			AnchorPane miniProfile = (AnchorPane) controller.getLoader().load();
			Patient patient = MainApp.getDBHandler().getPatient(userId);

			Tab profileTab = new Tab(patient.getLastName());
			profileTab.setContent(miniProfile);
			profileTabPane.getTabs().add(profileTab);

			FXMLLoader loader = controller.getLoader();
			controller = loader.getController();
			controller.setMainApp(this.mainApp);
			controller.setPatient(patient);
		} catch (IOException | ObjectNotFoundException e) {
			MainApp.printError(e);
		}
	}

}
