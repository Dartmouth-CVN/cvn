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
import utils.ObjectNotFoundException;

public class SearchTabController extends AbsController {

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
	
	@FXML
	public FXMLLoader getLoader(){
		loader.setLocation(MainApp.class.getResource("../view/SearchView.fxml"));
		return loader;
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
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
		
	}

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
		Patient patient;
		try {
			patient = MainApp.getDBHandler().getPatient(userID);
			if (patient != null)
				mainApp.showEditProfile(patient);
		} catch (ObjectNotFoundException e) {
			// TODO handleEditProfile catch block
			e.printStackTrace();
		}

	}
	

	@FXML
	private void handleClickPatient() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/MiniPatientProfile.fxml"));
			AnchorPane miniProfile = (AnchorPane) loader.load();

			MiniPatientProfileController controller = loader.getController();
			controller.setMainApp(mainApp);
			profileTab.setContent(miniProfile);
			profileTabPane.getTabs().add(profileTab);
			
		} catch (IOException e) {
			MainApp.printError(e);
		}	
	}
}
