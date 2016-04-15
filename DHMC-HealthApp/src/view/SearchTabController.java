package view;

import java.util.Random;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

		/*
		 * Listen for selection changes and show the person details when
		 * changed. unsure if we need this
		 * profileTable.getSelectionModel().selectedItemProperty().addListener(
		 * (observable, oldValue, newValue) -> showPersonDetails(newValue));
		 */
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getUserIDProperty());

		// set table listener
		profileTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
	}

	private void showUserDetails(IDisplayable user) {
		nameLabel.setText(user.getFirstName() + " " + user.getLastName());
		Random rand = new Random();
		String[] phoneNumbers = { "(508) 737-3661", "(857) 250-5168", "(703) 309-3778" };
		doctorLabel.setText("Doctor " + (rand.nextInt(10) + 1));
		roomLabel.setText("Room " + (rand.nextInt(10) + 1));
		nurseLabel.setText("Nurse " + (rand.nextInt(10) + 1));
		phoneLabel.setText(phoneNumbers[rand.nextInt(2)]);
		userID = idColumn.getCellData(profileTable.getSelectionModel().getSelectedIndex());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(MainApp mainApp) {
		this.mainApp = mainApp;
		// get the list of profiles from another class
		// personTable.setItems(mainApp.getPersonData());
	}

	/**
	 * Called when the user clicks the Go button.
	 */
	@FXML
	private void handleFindPatient() {
		String name = searchField.getText();
		if (!name.equals("")) {
			ObservableList<IDisplayable> personData = mainApp.getDatabaseHandler().searchPatient(name);
			profileTable.setItems(personData);
		} else {
			ObservableList<IDisplayable> personData = mainApp.getDatabaseHandler().searchPatient();
			profileTable.setItems(personData);
		}
		// search through the database with the given name

		// display into TableView

	}

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		Patient patient = mainApp.getDatabaseHandler().getPatient(userID);
		if (patient != null)
			mainApp.showEditProfile(patient);

	}
}
