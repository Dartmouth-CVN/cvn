package view;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.HealthAttribute;
import model.MainApp;
import model.Patient;


/**
 * Created by Matt on 4/27/2016.
 */
public class StatsController  extends AbsController{

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
    public XYChart<Integer, Double> xyChart;
    @FXML
    private NumberAxis xAxis = new NumberAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private ComboBox<String> myCombobox;
    @FXML
    private Label selectedField;
    @FXML
    private AnchorPane statsPane;
    @FXML
    public LineChart lineChart = new LineChart<Number, Number>(xAxis, yAxis);


    // Reference to the main application.
    private MainApp mainApp;
    private Patient myPatient;

    public StatsController (){
        key = "stats";
    }

    @FXML
    public FXMLLoader getLoader(){
        loader.setLocation(MainApp.class.getResource("/view/StatsView.fxml"));
        return loader;
    }

    public void setPatient(Patient p){
        this.myPatient = p;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // populate the combo box with health attribute choices.
        myCombobox.getItems().setAll("<Select A Category>","Weight","BMI","Fat","Calories Burned", "Steps", "Distance", "Floors", "Minutes Sedentary", "Minutes Lightly Active", "Minutes Fairly Active", "Minutes Very Active", "Activity Calories", "Minutes Asleep", "Minutes Awake", "Number of Awakenings", "Time in Bed");

        // bind the selected attribute label to the selected attribute text in the combo box.
        //selectedField.textProperty().bind(myCombobox.getSelectionModel().selectedItemProperty());
        // listen for changes to the combo box selection and update the displayed chart and stat fields accordingly.
        myCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldAtt, String newAtt) {
//                if (oldAtt != null) {
//                    switch(oldAtt) {
//                        case "caloriesBurned": updateChartAxis("Year","caloriesBurned"); break;
//                        case "test2":  updateChartAxis("Year","test2"); break;
//                    }
//                }
                if (newAtt != null) {
                    XYChart.Series series = new XYChart.Series<>();
                    int i = 0;
                    double average = 0;
                    Integer r;

                    switch(newAtt) {
                        case "weight":
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getWeights()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Pounds");
                            return;
                        case "BMI":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getBMIs()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (double) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("N/A");
                            return;
                        case "Fat":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getFats()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("%");
                            return;
                        case "Calories Burned":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getCals()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            return;
                        case "Steps":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getSteps()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Steps");
                            return;
                        case "Distance":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getDistances()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (double) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Miles");
                            return;
                        case "Floors":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getFloors()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Floors");
                            return;
                        case "Minutes Sedentary":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getMinSed()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Minutes");
                            return;
                        case "Minutes Lightly Active":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getMinLight()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Minutes");
                            return;
                        case "Minutes Fairly Active":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getMinFairly()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Minutes");
                            return;
                        case "Minutes Very Active":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getMinVery()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Minutes");
                            return;
                        case "Activity Calories":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getActiveCal()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Calories");
                            return;
                        case "Minutes Asleep":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getMinAsleep()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Minutes");
                            return;
                        case "Minutes Awake":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getMinAwake()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Minutes");
                            return;
                        case "Number of Awakenings":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getNumAwakening()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Awakenings");
                            return;
                        case "Time in Bed":
                            i = 0;
                            lineChart.getData().clear();
                            lineChart.setTitle(newAtt + " Over Time");
                            for(HealthAttribute hi : myPatient.getHealthProfile().getTimeInBed()) {
                                r = i;
                                series.getData().add(new XYChart.Data<>(r.toString(), hi.getValue()));
                                average += (int) hi.getValue();
                                i++;
                            }
                            lineChart.getData().add(series);
                            averageResult.setText(new Double(((double) average)/i).toString());
                            unitResult.setText("Minutes");
                            return;
                    }
                }
            }
        });
    }

//    /**
//     * Updates chart axis boundaries based on type of passed in axis
//     * @param xAxisType, timeframe either day, week, month, year
//     * @param yAxisType, health attribute
//     */
//    private void updateChartAxis(String xAxisType, String yAxisType){
//        //xyChart
//        switch(xAxisType) {
//            case "Day":
//                //per hour
//                xAxis.setLowerBound(0);
//                xAxis.setUpperBound(24);
//                xAxis.setTickUnit(1);
//                break;
//            case "Week":
//                //per day
//                xAxis.setLowerBound(0);
//                xAxis.setUpperBound(7);
//                xAxis.setTickUnit(1);
//                break;
//            case "Month":
//                //per week
//                xAxis.setLowerBound(0);
//                xAxis.setUpperBound(4);
//                xAxis.setTickUnit(1);
//                break;
//            case "Year":
//                //per month
//                xAxis.setLowerBound(0);
//                xAxis.setUpperBound(12);
//                xAxis.setTickUnit(1);
//                break;
//
//        }
//
//        switch(yAxisType){
//            case "caloriesBurned":
//                yAxis.setLowerBound(0);
//                yAxis.setUpperBound(10);
//                yAxis.setTickUnit(2);
//                break;
//            case "test2":
//                yAxis.setLowerBound(0);
//                yAxis.setUpperBound(1);
//                yAxis.setTickUnit(0.1);
//                break;
//        }
//
//
//    }

    /**
     * Clears current data from chart and updates with new data to display
     * @param series
     */
    private void updateChart(XYChart.Series series){
        lineChart.getData().clear();
        lineChart.getData().add(series);
    }

    /**
     * Sorts health info attributes into lists
     */

}



