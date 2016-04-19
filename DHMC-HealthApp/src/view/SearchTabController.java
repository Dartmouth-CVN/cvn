package view;

import java.io.IOException;
import java.util.Random;

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
	@FXML
	private Tab profileTab = new Tab();

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
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getUserIDProperty());

		ObservableList<IDisplayable> personData = MainApp.getDatabaseHandler().searchPatient();
		profileTable.setItems(personData);
		// set table listener
		profileTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> { 
					
					handleClickPatient();
					
				});
	}

	private void showUserDetails(IDisplayable user) {
		Patient p = MainApp.getDatabaseHandler().getPatient(user.getUserID());
		nameLabel.setText(p.getFirstName() + " " + p.getLastName());
		Random rand = new Random();
		doctorLabel.setText("Doctor " + (rand.nextInt(10) + 1));
		roomLabel.setText("Room " + (rand.nextInt(10) + 1));
		nurseLabel.setText("Nurse " + (rand.nextInt(10) + 1));
		phoneLabel.setText(p.getContactInfo().getPrimaryPhone());
		userID = p.getUserID();
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
		Patient patient = MainApp.getDatabaseHandler().getPatient(userID);
		if (patient != null)
			mainApp.showEditProfile(patient);

	}
	

	@FXML
	private void handleClickPatient() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/MiniPatientProfile.fxml"));
			AnchorPane miniProfile = (AnchorPane) loader.load();

			MiniPatientProfileController controller = loader.getController();
			controller.setMain(mainApp);
			profileTab.setContent(miniProfile);
			profileTabPane.getTabs().add(profileTab);
			
		} catch (IOException e) {
			MainApp.printError(e);
		}	
	}
}
