package view;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.IDisplayable;
import model.MainApp;
import model.Patient;

public class SearchTabController {

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
	private TabPane profileTabPane = new TabPane();

	private String userID;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public SearchTabController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty());

		ObservableList<IDisplayable> personData = MainApp.getDatabaseHandler().searchPatient();
		profileTable.setItems(personData);
		// set table listener
		profileTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> { 
					
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
		String name = searchField.getText();
		if (!name.equals("")) {
			ObservableList<IDisplayable> personData = MainApp.getDatabaseHandler().searchPatient(name);
			profileTable.setItems(personData);
		} else {
			ObservableList<IDisplayable> personData = MainApp.getDatabaseHandler().searchPatient();
			profileTable.setItems(personData);
		}
	}

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		Patient patient = MainApp.getDatabaseHandler().getPatientAlgorithm(userID);
		if (patient != null)
			mainApp.showEditProfile(patient);
	}
	
	private void handleClickPatient() {
		try {
			userID = profileTable.getSelectionModel().getSelectedItem().getUserID();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/MiniPatientProfile.fxml"));
			AnchorPane miniProfile = (AnchorPane) loader.load();

			MiniPatientProfileController controller = loader.getController();
			controller.setMain(mainApp);
			Patient patient = MainApp.getDatabaseHandler().getFilledPatient(userID);

			
			Tab profileTab = new Tab(patient.getLastName());
			profileTab.setContent(miniProfile);
			controller.setPatient(patient);
			profileTabPane.getTabs().add(profileTab);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
