package model;

import java.io.IOException;

import controller.AdminDashController;
import controller.LoginController;
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

	private static DatabaseHandler dbHandler;
	private Stage primaryStage;
	private AdminDashController dashController;

	@Override
	public void start(Stage primaryStage) {
		dbHandler = DatabaseHandler.getUniqueInstance();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DHMC - Health App v2.0");

		showLogin();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static DatabaseHandler getDatabaseHandler() {
		if(dbHandler == null)
			return DatabaseHandler.getUniqueInstance();
		return dbHandler;
	}

	public void setStageDimensions(Scene s){
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    primaryStage.setX((screenBounds.getWidth() - s.getWidth() ) / 2); 
	    primaryStage.setY((screenBounds.getHeight() - s.getHeight()) / 2);  
		primaryStage.setResizable(true);
//		primaryStage.setFullScreen(true);
	}

	public void showLogin() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/Login.fxml"));
			AnchorPane login = (AnchorPane) loader.load();

			primaryStage.setScene(new Scene(login));

			LoginController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
		} catch (IOException e) {
			printError(e);
		}
	}

	public void showAdminDash() {
		try {
			System.out.println("Login success, loading Admin Dash...");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/AdminDash.fxml"));
			loader.load();
			// Give the controller access to the main.
			 dashController = loader.getController();
			dashController.setMainApp(this);
			
		} catch (IOException e) {
			printError(e);
		}
	}
	
	public void showEditProfile(Patient patient){
		dashController.showEditPatientTab(patient);
	}
	
	public void showAddPatient(){
//		dashController.getAddPatientTab();
	}
	
	public void changeScreenSize(Scene s){
	}

	public static void printError(Exception e) {
		System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
	}
	
	public static void showAlert(String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("General Information");
		alert.setHeaderText("Info");
		alert.setContentText(message);

		alert.showAndWait();
	}
}
