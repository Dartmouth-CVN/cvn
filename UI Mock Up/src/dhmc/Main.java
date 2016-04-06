package dhmc;

import java.io.IOException;

import dhmc.view.LoginController;
import dhmc.view.PatientDashController;
import dhmc.view.AdminDashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DHMC - Health App 3.0");

		initRootLayout();

		showLogin();
		//showAdminDash();
		// showPatientDash();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showLogin() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Login.fxml"));
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

	public void showPatientDash() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PatientDash.fxml"));
			AnchorPane patientDash = (AnchorPane) loader.load();

			rootLayout.setCenter(patientDash);

			PatientDashController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showAdminDash() {
        try {
            // Load admin overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AdminDash.fxml"));
            AnchorPane adminDash = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(adminDash);

            // Give the controller access to the main.
            AdminDashController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showMySchedule(){
		
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void setVerticalDimensions(){
		primaryStage.setWidth(600);
		primaryStage.setHeight(700);
	}

	public void setHorizontalDimensions() {
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
	}
}
