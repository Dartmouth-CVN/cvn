package model;

import java.io.IOException;
import java.util.List;

import view.AdminDashController;
import view.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.DatabaseHandler;
import utils.ObjectNotFoundException;

public class MainApp extends Application {

	private Stage primaryStage;
	static DatabaseHandler dbHandler;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DHMC - Health App v2.0");
		getDBHandler().populateDatabase(10);
		
		List<Patient> patients;
		try {
			patients = getDBHandler().getPatients();
			System.out.println(patients.get(0).getUsername() + " " + patients.get(0).getPassword());
		} catch (ObjectNotFoundException e) {
			MainApp.printError(e);
		}
		showLogin();
	}

	public static DatabaseHandler getDBHandler() {
		  if(dbHandler == null)
			  dbHandler = new DatabaseHandler();
		  return dbHandler;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setStageDimensions() {
		primaryStage.setHeight(800);
		primaryStage.setWidth(1200);
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
		setStageDimensions();
		
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
