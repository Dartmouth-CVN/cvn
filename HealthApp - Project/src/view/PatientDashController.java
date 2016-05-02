package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.AbsUser;
import model.MainApp;
import model.Patient;

import java.io.IOException;

import static model.MainApp.getLoadedSceneOfType;

public class PatientDashController extends AbsDashController {
    @FXML
    TabPane tabPane;
    @FXML
    BorderPane layout;
    @FXML
    private Tab statsTab;
    @FXML
    private ImageView statsImage;

    @FXML
    private ImageView profilePic;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label room;
    @FXML
    private Label number;

    public PatientDashController() {
        key = "patient dash";
    }

    @FXML
    private void initialize() {
        loadPatientFields();
        //super.initializeTabs();
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if(newTab.equals(searchTab)) {
                loadSearchTab();
                tabPane.getSelectionModel().select(searchTab);
            } else if(newTab.equals(addPatientTab)) {
                loadAddPatientTab();
                tabPane.getSelectionModel().select(addPatientTab);
            } else if(newTab.equals(editPatientTab)){
                tabPane.getSelectionModel().select(editPatientTab);
            } else if (newTab.equals(statsTab)) {
                loadStatsTab(user);
                tabPane.getSelectionModel().select(statsTab);
            }
        });
    }

    @Override
    public FXMLLoader getLoader(){
        loader.setLocation(MainApp.class.getResource("/view/PatientDash.fxml"));
        return loader;
    }

    public void setPatient(Patient p){
        user = p;
        loadPatientFields();
    }

    public void loadPatientFields(){
        super.loadUserFields();
    }

    public Patient getPatient(){
        return (Patient) user;
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

    @FXML
    public void handleStatsImage() {
        loadStatsTab(user);
        tabPane.getSelectionModel().select(statsTab);
    }

    public void loadStatsTab(AbsUser user) {
        LoadedScene scene = getLoadedSceneOfType(new StatsController());
        ((StatsController) scene.getController()).setPatient((Patient)user);
        statsTab.setContent(scene.getPane());
    }



}
