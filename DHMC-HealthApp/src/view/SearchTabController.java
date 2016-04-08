package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.MainApp;
import model.SimpleUser;

public class SearchTabController {

	// Integer will be replaced with Profile model
	@FXML
	private TableView<SimpleUser> profileTable;
	@FXML
	private TableColumn<SimpleUser, Integer> idColumn;
	@FXML
	private TableColumn<SimpleUser, String> firstNameColumn;
	@FXML
	private TableColumn<SimpleUser, String> lastNameColumn;
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

		if (!name.equals("")){
			profileTable.getItems().setAll(mainApp.getDatabaseHandler().searchPatient(name));
		}
		// search through the database with the given name

		// display into TableView

	}

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {

		mainApp.showEditProfile();

	}
}
