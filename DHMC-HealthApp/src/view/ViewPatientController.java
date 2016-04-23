package view;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import model.Caregiver;
import model.DisplayString;
import model.HealthInfo;
import model.MainApp;
import model.Meal;
import model.Patient;
import model.Pet;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ViewPatientController {

	// General functions and attributes to interact with main application

	MainApp mainApp;
	private File curCSV;
	private LinkedList<HealthInfo> info;

	public ViewPatientController() {
	};

	public void setMainApp(MainApp app) {
		this.mainApp = app;
	}

	// Functions and attributes to communicate with ViewPatient.fxml

	@FXML
	Text firstName;
	@FXML
	Text lastName;
	@FXML
	Text birthday;
	//
	@FXML
	TableView<DisplayString> patientPhoneTable;
	@FXML
	TableView<DisplayString> patientEmailTable;
	@FXML
	TableView<DisplayString> patientAddressTable;
	@FXML
	TableColumn<DisplayString, String> patientPhone;
	@FXML
	TableColumn<DisplayString, String> patientEmail;
	//
	@FXML
	TableView<Caregiver> familyTable;
	@FXML
	TableColumn<Caregiver, String> familyNames;
	@FXML
	TableColumn<Caregiver, String> familyRels;
	@FXML
	TableColumn<Caregiver, String> familyPhone;
	@FXML
	TableColumn<Caregiver, String> familyEmail;
	//
	@FXML
	TableView<Pet> petTable;
	@FXML
	TableColumn<Pet, String> petNames;
	@FXML
	TableColumn<Pet, String> petSpecies;
	@FXML
	TableColumn<Pet, Boolean> petAllergyFriendly;
	//
	@FXML
	TableView<Meal> mealTable;
	@FXML
	TableColumn<Meal, String> mealNames;
	@FXML
	TableColumn<Meal, Integer> mealCals;
	@FXML
	TableColumn<Meal, Integer> mealRatings;
	@FXML
	TableColumn<Meal, String> mealNotesCol;
	//
	@FXML
	LineChart<Number, Number> bmi;

	ObservableList<DisplayString> patientPhones;
	ObservableList<DisplayString> patientEmails;
	ObservableList<DisplayString> patientAddresses;

	ObservableList<Caregiver> familyMembers;
	ObservableList<DisplayString> familyEmails;
	ObservableList<DisplayString> familyPhones;
	ObservableList<Caregiver> famNames;
	ObservableList<Caregiver> famRels;

	@FXML
	private void initialize() {
		patientPhones = FXCollections.observableArrayList();
		patientEmails = FXCollections.observableArrayList();
		patientAddresses = FXCollections.observableArrayList();
		familyMembers = FXCollections.observableArrayList();
		famNames = FXCollections.observableArrayList();
		famRels = FXCollections.observableArrayList();
		familyPhones = FXCollections.observableArrayList();
		familyEmails = FXCollections.observableArrayList();


		
//		patientPhoneTable.setItems(patientPhones);
//		patientEmailTable.setItems(patientEmails);
//		patientAddressTable.setItems(patientAddresses);
//		familyTable.setItems(familyMembers);
//
//		patientPhone.setCellFactory(TextFieldTableCell.forTableColumn());
//		patientEmail.setCellFactory(TextFieldTableCell.forTableColumn());
//		familyPhone.setCellFactory(TextFieldTableCell.forTableColumn());
//		familyEmail.setCellFactory(TextFieldTableCell.forTableColumn());
//
//		patientPhone.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
//		patientEmail.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
//		//familyPhone.setCellValueFactory(cellData -> cellData.getValue().getContactInfo().getPrimaryPhone());
//		//familyEmail.setCellValueFactory(cellData -> cellData.getValue().getContactInfo().getPrimaryEmail());
//		familyNames.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
//		familyRels.setCellValueFactory(cellData -> cellData.getValue().relationProperty());

	}
	
	public void setPatient(Patient patient) {
		
		firstName.setText(patient.getFirstName() + " " + patient.getLastName());
		lastName.setText(patient.getUserID());
		
		XYChart.Series<Long, Double> series = new Series<Long, Double>();
		LinkedList<Long> daysSinceStart = new LinkedList<Long>();
		for (HealthInfo h : patient.getHealthInfo())
			daysSinceStart.add(ChronoUnit.DAYS.between(LocalDate.of(2000, 12, 25), LocalDate.parse(h.getDate())));
		Collections.sort(daysSinceStart);
		for (HealthInfo h : patient.getHealthInfo())
			//series.getData().add(daysSinceStart.removeFirst().intValue(), h.getBmi());
			series.getData().add(new Data<Long,Double>(daysSinceStart.removeFirst().longValue(), h.getBmi()));
		//bmi.getData().add(series);
		
		familyNames.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		familyPhone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactInfo().getPrimaryPhone()));
		familyRels.setCellValueFactory(cellData -> cellData.getValue().relationProperty());
		familyEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactInfo().getPrimaryEmail()));
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Caregiver> caregivers = MainApp.getDatabaseHandler().searchPatientCaregiver(patient);
		familyTable.setItems(caregivers);
		
		mealNames.setCellValueFactory(cellData -> cellData.getValue().foodProperty());
		mealCals.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().caloriesProperty().get()).asObject());
		mealRatings.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().ratingProperty().get()).asObject());
		mealNotesCol.setCellValueFactory(cellData -> cellData.getValue().notesProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Meal> meals = MainApp.getDatabaseHandler().searchPatientMeal(patient);
		mealTable.setItems(meals);
		
		petNames.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		petSpecies.setCellValueFactory(cellData -> cellData.getValue().speciesProperty());
		petAllergyFriendly.setCellValueFactory(cellData -> cellData.getValue().allergyFriendlyProperty());
		//assignedStaff needs to be implemented in databaseHandler
		ObservableList<Pet> pets = MainApp.getDatabaseHandler().searchPatientPet(patient);
		petTable.setItems(pets);
		
	}

	public void setMain(MainApp mainApp) {
		this.mainApp = mainApp;
		
	}

}
