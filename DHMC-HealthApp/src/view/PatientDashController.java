package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.MainApp;

public class PatientDashController {
	private MainApp mainApp;
	@FXML
	TabPane tabPane;
	@FXML
	BorderPane layout;

	public PatientDashController() {
	}

	@FXML
	private void initialize() {
		tabPane.getSelectionModel().selectedIndexProperty()
				.addListener((observable, oldValue, newValue) -> handleTabSwitch(newValue));
	}

	public void handleTabSwitch(Number newValue) {
//		if (newValue.intValue() == 1) {
//			mainApp.setHorizontalDimensions();
//			showMySchedule();
//		}
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

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
