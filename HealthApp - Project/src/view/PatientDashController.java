package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.ContactElement;
import model.MainApp;
import model.Patient;
import utils.ObjectNotFoundException;

public class PatientDashController extends AbsDashController {
	@FXML
	TabPane tabPane;
	@FXML
	BorderPane layout;
	@FXML
	private Tab statsTab;

	@FXML
	private ImageView profilePic;
	@FXML
	private Label name;
	@FXML
	private Label email;
	@FXML
	private Label room;
	@FXML
	private Label number;

	private Patient patient;

	public PatientDashController() {
		key = "patient dash";
	}

	@FXML
	private void initialize() {
		loadPatientFields();
		super.initializeTabs();
	}

	@Override
	public FXMLLoader getLoader(){
		loader.setLocation(MainApp.class.getResource("../view/PatientDash.fxml"));
		return loader;
	}

	public void setPatient(Patient p){
		this.patient = p;
		loadPatientFields();
	}

	public void loadPatientFields(){
		try {
			if(patient != null) {
				profilePic.setImage(new Image("file:" + patient.getPicture()));
				name.setText(patient.getFirstName() + " " + patient.getLastName());
				ContactElement mail = patient.getContactInfo().getPrimaryEmail();
				ContactElement phone = patient.getContactInfo().getPrimaryPhone();

				email.setText(mail.getValue() + " (" + mail.getType() + ")");
				room.setText(patient.getRoom());
				number.setText(phone.getValue() + " (" + phone.getType() + ")");
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Patient getPatient(){
		return patient;
	}

	public void showMySchedule() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MySchedule.fxml"));
			AnchorPane mySchedule = (AnchorPane) loader.load();

			tabPane.getTabs().get(1).setContent(mySchedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showCalendar(Scene calendar) {
		// primaryStage.setScene(calendar);
	}

	public void loadStatsTab() {
		StatsController controller = new StatsController();

		try {
			AnchorPane statsView = (AnchorPane) controller.getLoader().load();

			FXMLLoader loader = controller.getLoader();
			controller = loader.getController();
			controller.setMainApp(this.mainApp);
			statsTab.setContent(statsView);
		} catch (IOException e) {
			MainApp.printError(e);
		}
	}

}
