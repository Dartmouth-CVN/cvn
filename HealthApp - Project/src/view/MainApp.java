package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Administrator;
import utils.DBHandler;
import view.AbsDashController;
import view.AdminDashController;
import view.LoginController;

import java.io.IOException;

public class MainApp extends Application {

    static DBHandler database;
    static AbsDashController dashController;
    static Scene primaryScene;
    private static Stage primaryStage;

    public static AbsDashController getDashController() {
        return dashController;
    }

    public static void setDashController(AbsDashController controller) {
        dashController = controller;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void centerStage() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - primaryScene.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryScene.getHeight()) / 2);
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

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DHMC - Health App v2.0");
        database = DBHandler.getUniqueInstance();
        database.connect();
        database.initDB();
        showLogin();
    }

    public void setStageDimensions() {
        primaryStage.setHeight(800);
        primaryStage.setWidth(1200);
        primaryStage.setResizable(true);
    }

    public void showLogin() {
        try {
            // Load root layout from fxml file.
            LoginController controller = new LoginController();
            FXMLLoader loader = controller.getLoader();
            AnchorPane login = (AnchorPane) controller.getLoader().load();

            controller = loader.getController();
            controller.setMainApp(this);
            primaryScene = new Scene(login);

            primaryStage.setScene(primaryScene);
            primaryStage.show();
        } catch (IOException e) {
            printError(e);
        }
    }

    public void showAdminDash(Administrator admin) {
        System.out.println("Login success, loading Admin Dash...");
        AdminDashController controller = new AdminDashController();
        setStageDimensions();

        try {
            AnchorPane adminDash = (AnchorPane) controller.getLoader().load();

            FXMLLoader loader = controller.getLoader();
            controller = loader.getController();
            controller.setMainApp(this);
            controller.setAdmin(admin);
            dashController = controller;
            primaryScene = new Scene(adminDash);
            primaryStage.setScene(primaryScene);
            primaryStage.show();
            centerStage();
        } catch (IOException e) {
            printError(e);
        }
    }
}
