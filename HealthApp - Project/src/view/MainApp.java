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
import org.apache.derby.impl.tools.sysinfo.Main;
import utils.DBHandler;
import view.AbsDashController;
import view.AdminDashController;
import view.LoginController;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainApp extends Application {

    static DBHandler database;
    static AbsDashController dashController;
    static LoadedScene scene;
    static Scene primaryScene;
    private static Stage primaryStage;
    private static List<LoadedScene> loadedScenes;

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
//        System.err.printf("ERROR: %s\n", e.getLocalizedMessage());
        e.printStackTrace();
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
        loadedScenes = new LinkedList<>();
        loadScenes();
        database.initDB();
        showLogin();
    }

    public void loadScenes() {
        AbsController controller;
        //load login controller
        controller = new LoginController();
        setupController(controller);

        //load admin dash controller
        controller = new AdminDashController();
        setupController(controller);

        //load search controller
        controller = new SearchController();
        setupController(controller);

        //load mini patient profile controller
        controller = new MiniPatientProfileController();
        setupController(controller);

        //load add/edit patient controller
        controller = new EditPatientController();
        setupController(controller);
    }

    public void setupController(AbsController controller){
        FXMLLoader loader;
        AnchorPane pane;
        loader = controller.getLoader();
        try {
            pane = (AnchorPane) loader.load();
            controller = loader.getController();
            controller.setMainApp(this);
            loadedScenes.add(new LoadedScene(controller, loader, pane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStageDimensions() {
        primaryStage.setHeight(800);
        primaryStage.setWidth(1200);
        primaryStage.setResizable(true);
    }

    public void showLogin() {
        // Load root layout from fxml file.
        scene = getLoadedSceneOfType(new LoginController());
        primaryScene = new Scene(scene.getPane());

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void showAdminDash(Administrator admin) {
        System.out.println("Login success, loading Admin Dash...");
        setStageDimensions();

        scene = getLoadedSceneOfType(new AdminDashController());
        primaryScene = new Scene(scene.getPane());
        primaryStage.setScene(primaryScene);
        ( (AdminDashController) scene.getController()).setAdmin(admin);
        primaryStage.show();
        centerStage();
    }

    public static LoadedScene getLoadedSceneOfType(AbsController type) {
        for (LoadedScene l : loadedScenes) {
            if (l.getController().getKey().equals(type.getKey()))
                return l;
        }
        return null;
    }
}
