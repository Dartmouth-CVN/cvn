package view.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	protected Tab scheduleTab;
	@FXML
	protected Tab searchTab;
	@FXML
	protected Tab exportTab;
	@FXML
	protected Tab editPatientTab;
	@FXML
	protected Tab addPatientTab;
	@FXML
	protected ImageView scheduleImage;
	@FXML
	protected ImageView searchImage;

	@FXML
	private void initialize() {
	}

	public void initializeTabs(){
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab.equals(searchTab)) {
				loadSearchTab();
				tabPane.getSelectionModel().select(searchTab);
			}
			else if(newTab.equals(addPatientTab)) {
				loadAddPatientTab();
				tabPane.getSelectionModel().select(addPatientTab);
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
		} catch (IOException e) {
			MainApp.printError(e);
		}
	}

	public void loadAddPatientTab(){
		EditPatientController controller = new EditPatientController();

		try {
			AnchorPane editPatientView = (AnchorPane) controller.getLoader().load();

			FXMLLoader loader = controller.getLoader();
			controller = loader.getController();
			controller.setMainApp(this.mainApp);
			controller.setPatient(new Patient());
			addPatientTab.setContent(editPatientView);
		} catch (IOException e) {
			MainApp.printError(e);
			e.printStackTrace();
		}
	}

	public void loadEditPatientTab(Patient p) {
		EditPatientController controller = new EditPatientController();

		try {
			AnchorPane editPatientView = (AnchorPane) controller.getLoader().load();

			FXMLLoader loader = controller.getLoader();
			controller = loader.getController();
			controller.setMainApp(this.mainApp);
			controller.setPatient(p);
			editPatientTab.setContent(editPatientView);
			tabPane.getSelectionModel().select(editPatientTab);

		} catch (IOException e) {
			MainApp.printError(e);
			e.printStackTrace();
		}
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
