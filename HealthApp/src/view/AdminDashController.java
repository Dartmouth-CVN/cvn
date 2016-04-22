package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import model.MainApp;
import model.Patient;

public class AdminDashController extends MedicalStaffDashController {

	@FXML
	private AnchorPane adminDash;

	private AnchorPane editPatient;
	private FXMLLoader editPatientLoader;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public AdminDashController() {
	}
	
	@Override
	public FXMLLoader getLoader(){
		loader.setLocation(MainApp.class.getResource("../view/AdminDash.fxml"));
		return loader;
	}

	/**
	 * Loads and sets content of the schedule tab.
	 */
	@FXML
	private void loadScheduleTab() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/MySchedule.fxml"));
			AnchorPane mySchedule = (AnchorPane) loader.load();

			scheduleTab.setContent(mySchedule);

			MyScheduleController controller = loader.getController();
			controller.setMainApp(mainApp);

		} catch (IOException e) {
			MainApp.printError(e);
		}
	}
	
	public void showEditPatientTab(Patient p) {
		editPatientTab.setContent(getEditPatientTab(p));
		tabPane.getSelectionModel().select(editPatientTab);
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
//		controller.setMainApp();
		controller.setPatient(patient);
		controller.loadFields();
	}

}
