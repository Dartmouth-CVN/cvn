package model;

import java.io.IOException;

import controller.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.AdminDashController;
import view.EditPatientController;
import view.LoginController;
import view.SearchTabController;

public class MainApp extends Application {
	private DatabaseHandler dbHandler;
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private AnchorPane adminDash;

	@Override
	public void start(Stage primaryStage) {
		dbHandler = new DatabaseHandler();
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DHMC - Health App 3.0");

		initRootLayout();

		loadAdminDash();
		loadSearchTab();
		
		showLogin();
		
//		showAdminDash();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public DatabaseHandler getDatabaseHandler(){
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
			primaryStage.setResizable(false);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

//			primaryStage.setWidth(400);
//			primaryStage.setHeight(300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadAdminDash(){
		// Load admin overview
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("../view/AdminDash.fxml"));
        try {
			adminDash = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Give the controller access to the main.
        AdminDashController controller = loader.getController();
        controller.setMain(this);

	}
	
	public void loadSearchTab(){
		// Load admin overview
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("../view/GlobalSearch.fxml"));
        try {
			AnchorPane searchTab = (AnchorPane) loader.load();
			
			TabPane tab = (TabPane) adminDash.getChildren().get(0);
			
			tab.getTabs().get(2).setContent(searchTab);
			SearchTabController controller = loader.getController();
			controller.setMain(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Give the controller access to the main.
        SearchTabController controller = loader.getController();
        controller.setMain(this);

	}
	
	public void showAdminDash() {
        System.out.println("Login success, loading Admin Dash...");
		// Set person overview into the center of root layout.
		primaryStage.setScene(new Scene(adminDash));
	}
	
	public void showEditProfile() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/EditPatient.fxml"));
			BorderPane editProfile = (BorderPane) loader.load();
			
			Stage editProfileStage = new Stage();
			editProfileStage.setScene(new Scene(editProfile));

			EditPatientController controller = loader.getController();
			controller.setMainApp(this);
			
			editProfileStage.setTitle("Edit Profile");
			editProfileStage.show();

//			primaryStage.setWidth(400);
//			primaryStage.setHeight(300);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
