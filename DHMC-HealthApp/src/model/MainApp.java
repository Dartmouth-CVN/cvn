package model;

import java.io.IOException;

import controller.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.AdminDashController;
import view.EditPatientController;
import view.LoginController;

public class MainApp extends Application {
	private static DatabaseHandler dbHandler;
	private Stage primaryStage;
	private BorderPane rootLayout;

	private AnchorPane adminDash;

	@Override
	public void start(Stage primaryStage) {
		dbHandler = DatabaseHandler.getUniqueInstance();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DHMC - Health App v1.0");

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

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);

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
			AdminDashController controller = loader.getController();
			controller.setMainApp(this);
			rootLayout.setCenter(adminDash);
			
		} catch (IOException e) {
			printError(e);
		}
	}

	public void showEditProfile(Patient patient) {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/EditPatient.fxml"));
			BorderPane editProfile = (BorderPane) loader.load();

			Stage editProfileStage = new Stage();
			editProfileStage.setScene(new Scene(editProfile));

			EditPatientController controller = loader.getController();
			controller.setMainApp(this);
			controller.setPatient(patient);

			editProfileStage.setTitle("Edit Profile");
			editProfileStage.show();
		} catch (IOException e) {
			printError(e);
		}
	}
	
	public void changeScreenSize(Scene s){
	}

	public static void printError(Exception e) {
		System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
	}
	
	
}
