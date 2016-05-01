package view;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import model.MainApp;
import model.Patient;
import model.PatientWrapper;
import utils.DBHandler;

import java.io.IOException;
import java.util.List;

public class SearchController extends AbsController {

	// Integer will be replaced with Profile model
	@FXML
	private TableView<PatientWrapper> profileTable;
	@FXML
	private TableColumn<PatientWrapper, String> idColumn;
	@FXML
	private TableColumn<PatientWrapper, String> firstNameColumn;
	@FXML
	private TableColumn<PatientWrapper, String> lastNameColumn;
	@FXML
	private TableColumn<PatientWrapper, Boolean> patientSelectColumn;
	@FXML
	private TextField searchField;
	@FXML
	private TabPane profileTabPane = new TabPane();
	@FXML
	private Tab profileTab = new Tab();

	private long userId;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public SearchController() {
		key = "search";
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
			try{
				userId = newValue.getUserIdValueProperty().get();
			}catch(NullPointerException e){
//				MainApp.printError(e);
			}
		});
		profileTable.setRowFactory( tv -> {
			TableRow<PatientWrapper> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				profileTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
				if (event.getClickCount() == 2 && (! row.isEmpty()) )
					showNewMiniPatientProfile();
				else if(event.getClickCount() == 2 && (! row.isEmpty()) )
					showMiniPatientProfile();
			});
			return row ;
		});
	}

	/**
	 * Called when the user clicks the Go button.
	 */
	@FXML
	private void handleFindPatient() {
		String key = searchField.getText();

		if (key.equals("")) {
			List<Patient> patients = DBHandler.getUniqueInstance().getAllPatients();
			ObservableList<PatientWrapper> patientList = FXCollections.observableArrayList();
			for (Patient patient : patients)
                patientList.add(new PatientWrapper(patient));

			profileTable.setItems(patientList);
		} else if(!key.equals("")){
			List<Patient> patients = DBHandler.getUniqueInstance().patientNameSearch(key);
			ObservableList<PatientWrapper> patientList = FXCollections.observableArrayList();
			for (Patient patient : patients)
				patientList.add(new PatientWrapper(patient));

			profileTable.setItems(patientList);
		}
	}

	/**
	 * Called when the user clicks edit profile button.
	 */
	@FXML
	private void handleEditProfile() {
//		Patient patient;
//		try {
//			patient = MainApp.getDBHandler().getPatientByUsername(userId);
//			if (patient != null)
//				;
//		} catch (ObjectNotFoundException e) {
//			MainApp.printError(e);
//		}
	}

	@FXML
	private void handleClickPatient() {
		showMiniPatientProfile();
	}

	public void showMiniPatientProfile() {
		LoadedScene scene = MainApp.getLoadedSceneOfType(new MiniPatientProfileController());
		Patient patient = (Patient) DBHandler.getUniqueInstance().getFilledPatientById(userId);
		((MiniPatientProfileController) scene.getController()).setPatient(patient);

		if(profileTabPane.getTabs().isEmpty()){
            Tab profileTab = new Tab(patient.getLastName());
            profileTab.setContent(scene.getPane());
            profileTabPane.getTabs().add(profileTab);
        }else{
            Tab profileTab = profileTabPane.getTabs().get(0);
            profileTab.setText(patient.getLastName());
            profileTab.setContent(scene.getPane());
        }
	}

	public void showNewMiniPatientProfile() {
		Patient patient = DBHandler.getUniqueInstance().getPatientById(userId);
		LoadedScene scene = MainApp.getLoadedSceneOfType(new MiniPatientProfileController());
		((MiniPatientProfileController) scene.getController()).setPatient(patient);
		Tab profileTab = new Tab(patient.getLastName());
		profileTab.setContent(scene.getPane());
		profileTabPane.getTabs().add(profileTab);

	}

}
