package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	public AdminDashController(MainApp mainApp) {
		super(mainApp);
	}

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	// private void showProfileDetails(Profile profile) {
	// fills the profile space in the admin dash
	/*
	 * if (profile != null) { // Fill the labels with info from the person
	 * object. firstNameLabel.setText(person.getFirstName());
	 * lastNameLabel.setText(person.getLastName());
	 * streetLabel.setText(person.getStreet());
	 * postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
	 * cityLabel.setText(person.getCity());
	 * birthdayLabel.setText(DateUtil.format(person.getBirthday()));
	 * 
	 * } else { // Profile is null, remove all the text.
	 * firstNameLabel.setText(""); lastNameLabel.setText("");
	 * streetLabel.setText(""); postalCodeLabel.setText("");
	 * cityLabel.setText(""); birthdayLabel.setText(""); } }
	 */

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
		controller.setMainApp(mainApp);
		controller.setPatient(patient);
		controller.loadFields();
	}

}
