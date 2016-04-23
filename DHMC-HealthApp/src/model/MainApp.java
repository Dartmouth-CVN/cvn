package model;

import java.io.IOException;

import controller.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.AdminDashController;
import view.LoginController;
import view.RootController;

public class MainApp extends Application {
	private static DatabaseHandler dbHandler;
	private Stage primaryStage;
	private BorderPane rootLayout;

	private AnchorPane adminDash;
	private AdminDashController dashController;

	@Override
	public void start(Stage primaryStage) {
		dbHandler = DatabaseHandler.getUniqueInstance();
		dbHandler.initDB();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DHMC - Health App v1.0");

		showLogin();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static DatabaseHandler getDatabaseHandler() {
		if(dbHandler == null)
			dbHandler = DatabaseHandler.getUniqueInstance();
		
		
		return dbHandler;
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			RootController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.setScene(scene);
			setStageDimensions(scene);

			showAdminDash();

		} catch (IOException e) {
			printError(e);
		}
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
			adminDash = (AnchorPane) loader.load();
			// Give the controller access to the main.
			 dashController = loader.getController();
			dashController.setMainApp(this);
			rootLayout.setCenter(adminDash);
			
		} catch (IOException e) {
			printError(e);
		}
	}
	
	public void showEditProfile(Patient patient){
		dashController.showEditPatientTab(patient);
	}
	
	public void showAddPatient(){
		dashController.getAddPatientTab();
	}
	
	public void changeScreenSize(Scene s){
	}

	public static void printError(Exception e) {
		System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
		e.printStackTrace();
	}
	
	public static void showAlert(String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("General Information");
		alert.setHeaderText("Info");
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	
}
