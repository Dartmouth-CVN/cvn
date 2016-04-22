package model;

import java.io.IOException;

import view.AdminDashController;
import view.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utils.DatabaseHandler;

public class MainApp extends Application {

	private Stage primaryStage;
	private AdminDashController dashController;
	static DatabaseHandler dbHandler;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DHMC - Health App v2.0");
		dbHandler = new DatabaseHandler();

		showLogin();
	}

	public static DatabaseHandler getDBHandler() {
		return dbHandler;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setStageDimensions(Scene s) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((screenBounds.getWidth() - s.getWidth()) / 2);
		primaryStage.setY((screenBounds.getHeight() - s.getHeight()) / 2);
		primaryStage.setResizable(true);
		// primaryStage.setFullScreen(true);
	}

	public void showLogin() {
		try {
			// Load root layout from fxml file.
			LoginController controller = new LoginController();
			FXMLLoader loader = controller.getLoader();
			AnchorPane login = (AnchorPane) controller.getLoader().load();

			controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.setScene(new Scene(login));
			primaryStage.show();
		} catch (IOException e) {
			printError(e);
		}
	}

	public void showAdminDash() {
		System.out.println("Login success, loading Admin Dash...");
		AdminDashController controller = new AdminDashController();
		
		try {
			AnchorPane adminDash = (AnchorPane) controller.getLoader().load();
			
			FXMLLoader loader = controller.getLoader();
			controller = loader.getController();
			controller.setMainApp(this);
			
			primaryStage.setScene(new Scene(adminDash));
			primaryStage.show();
		} catch (IOException e) {
			printError(e);
		}
	}

	public void showEditProfile(Patient patient) {
		dashController.showEditPatientTab(patient);
	}

	public void showAddPatient() {
		// dashController.getAddPatientTab();
	}

	public void changeScreenSize(Scene s) {
	}

	public static void printError(Exception e) {
		System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
	}

	public static void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("General Information");
		alert.setHeaderText("Info");
		alert.setContentText(message);

		alert.showAndWait();
	}
}
