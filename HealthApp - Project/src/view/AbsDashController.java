package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Patient;
import sun.applet.Main;

public abstract class AbsDashController extends AbsController {

	public AbsDashController() {
		key = "abs dash";
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
		LoadedScene scene = MainApp.getLoadedSceneOfType(new SearchController());
		searchTab.setContent(scene.getPane());
	}

	public void loadAddPatientTab(){
		LoadedScene scene = MainApp.getLoadedSceneOfType(new EditPatientController());
		((EditPatientController) scene.getController()).setPatient(new Patient());
		addPatientTab.setContent(scene.getPane());
	}

	public void loadEditPatientTab(Patient p) {
		LoadedScene scene = MainApp.getLoadedSceneOfType(new EditPatientController());
		((EditPatientController) scene.getController()).setPatient(p);
		editPatientTab.setContent(scene.getPane());
		tabPane.getSelectionModel().select(editPatientTab);
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
