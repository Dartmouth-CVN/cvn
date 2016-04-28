package view;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.AnchorPane;
import model.Administrator;
import javafx.stage.Stage;
import model.Patient;
import model.PatientWrapper;
import utils.DBHandler;

import java.io.IOException;


/**
 * Created by Matt on 4/27/2016.
 */
public class StatsController  extends AbsController{

    @FXML // fx:id="myChoices"
    private ChoiceBox<String> myChoices; // Value injected by FXMLLoader
    @FXML
    private Label unitResult = new Label();
    @FXML
    private Label averageResult = new Label();
    @FXML
    private Label maxResult = new Label();
    @FXML
    private Label minResult = new Label();
    @FXML
    private Label standDevResult = new Label();
    @FXML
    private Label titleLabel = new Label();
    @FXML
    public LineChart chart;
    @FXML
    public XYChart<Integer, Double> xyChart;
    @FXML
    private NumberAxis xAxis ;
    @FXML
    private NumberAxis yAxis ;
    @FXML
    private ComboBox<String> myCombobox;
    @FXML
    private Label selectedField; // Value injected by FXMLLoader

    // Reference to the main application.
    private MainApp mainApp;


    public StatsController (){

    }
    @FXML
    public FXMLLoader getLoader(){
        loader.setLocation(MainApp.class.getResource("../view/StatsView.fxml"));
        return loader;
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // populate the combo box with health attribute choices.
        myCombobox.getItems().setAll("test","test1");

        // bind the selected attribute label to the selected attribute text in the combo box.
        selectedField.textProperty().bind(myCombobox.getSelectionModel().selectedItemProperty());
        // listen for changes to the combo box selection and update the displayed chart and stat fields accordingly.
        myCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldAtt, String newAtt) {
                if (oldAtt != null) {
                    switch(oldAtt) {
                        case "test": updateChartAxis("Year","test"); break;
                        case "test1":  updateChartAxis("Year","test1"); break;
                    }
                }
                if (newAtt != null) {
                    switch(newAtt) {
                        case "test":  break;
                        case "test1":  break;
                    }
                }
            }
        });
    }

    private void updateChartAxis(String xAxisType, String yAxisType){
        //xyChart
        switch(xAxisType) {
            case "Day":
                break;
            case "Week":
                break;
            case "Month":
                break;
            case "Year":
                xAxis.setLowerBound(0);
                xAxis.setUpperBound(12);
                xAxis.setTickUnit(1);
                break;

        }

        switch(yAxisType){
            case "test":
                yAxis.setLowerBound(0);
                yAxis.setUpperBound(10);
                yAxis.setTickUnit(2);
                break;
            case "test2":
                yAxis.setLowerBound(0);
                yAxis.setUpperBound(1);
                yAxis.setTickUnit(0.1);
                break;
        }


    }
    
}



