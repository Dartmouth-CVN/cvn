package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class PatientDashController extends AbsDashController {
	@FXML
	TabPane tabPane;
	@FXML
	BorderPane layout;
	@FXML
	protected Tab statsTab;

	public PatientDashController() {
		super();
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
