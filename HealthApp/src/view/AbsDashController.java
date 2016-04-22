package view;

import java.io.IOException;

import javax.swing.event.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.MainApp;
import model.Patient;

public abstract class AbsDashController extends AbsController {

	public AbsDashController() {
	}

	@FXML
	protected TabPane tabPane;

	@FXML
	protected Tab scheduleTab = new Tab();

	@FXML
	protected Tab searchTab = new Tab();

	@FXML
	protected Tab exportTab = new Tab();

	@FXML
	protected Tab editPatientTab = new Tab();

	@FXML
	protected ImageView scheduleImage = new ImageView();

	@FXML
	protected ImageView searchImage = new ImageView();

	@FXML
	protected AnchorPane editPatient;

	protected FXMLLoader editPatientLoader;

	@FXML
	protected void initialize() {
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
	        if(newTab.equals(searchTab)){
	        	loadSearchTab();
	        }
	    });
	}

	public void loadSearchTab() {
		SearchController controller = new SearchController();

		try {
			AnchorPane searchView = (AnchorPane) controller.getLoader().load();

			FXMLLoader loader = controller.getLoader();
			controller = loader.getController();
			controller.setMainApp(this.mainApp);
			searchTab.setContent(searchView);
			tabPane.getSelectionModel().select(searchTab);
		} catch (IOException e) {
			MainApp.printError(e);
		}
	}

	public void showEditPatientTab(Patient p) {
		editPatientTab.setContent(getEditPatientTab(p));
		tabPane.getSelectionModel().select(editPatientTab);
	}

	protected FXMLLoader getEditPatientLoader() {
		FXMLLoader editPatientLoader = new FXMLLoader();
		editPatientLoader.setLocation(MainApp.class.getResource("../view/EditPatient.fxml"));
		try {
			editPatient = (AnchorPane) editPatientLoader.load();
		} catch (IOException e) {
			MainApp.printError(e);
		}
		return editPatientLoader;
	}

	/**
	 * Loads and sets the contents of the export tab.
	 */
	protected void loadExportTab() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/ExportFields.fxml"));
			AnchorPane export = (AnchorPane) loader.load();

			ExportController controller = loader.getController();
			controller.setMainApp(mainApp);
			exportTab.setContent(export);
		} catch (IOException e) {
			MainApp.printError(e);
		}
	}

	public AnchorPane getEditPatientTab(Patient p) {
		try {
			loadEditPatient(p);
		} catch (IOException e) {
			MainApp.printError(e);
		}
		return editPatient;
	}

	public void loadEditPatient(Patient patient) throws IOException {
		editPatientLoader = getEditPatientLoader();
		EditPatientController controller = editPatientLoader.getController();
		controller.setMainApp(mainApp);
		controller.setPatient(patient);
		controller.loadFields();
	}

	/**
	 * Clicking search image opens the search tab.
	 */
	@FXML
	protected void handleSearchImage() {
		loadSearchTab();
		tabPane.getSelectionModel().select(searchTab);
	}

	/**
	 * Clicking schedule image opens the schedule tab.
	 */
	@FXML
	protected void handleScheduleImage() {
		tabPane.getSelectionModel().select(scheduleTab);
	}

}
