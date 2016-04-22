package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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

	private Patient p;
	MainApp mainApp;
	private File curCSV;
	private LinkedList<HealthInfo> info;

	public ViewPatientController(Patient p) {
		this.p = p;
	}

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
	TableColumn<Meal, Boolean> mealLiked;
	@FXML
	TableColumn<Meal, Boolean> mealDisliked;
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

		XYChart.Series series = new XYChart.Series();
		LinkedList<Long> daysSinceStart = new LinkedList<Long>();
		for (HealthInfo h : p.getHealthInfo())
			daysSinceStart.add(ChronoUnit.DAYS.between(LocalDate.of(2000, 12, 25), LocalDate.parse(h.getDate())));
		Collections.sort(daysSinceStart);
		for (HealthInfo h : p.getHealthInfo())
			series.getData().add(daysSinceStart.removeFirst().intValue(), h.getBmi());
		bmi.getData().add(series);
		
		patientPhoneTable.setItems(patientPhones);
		patientEmailTable.setItems(patientEmails);
		patientAddressTable.setItems(patientAddresses);
		familyTable.setItems(familyMembers);

		patientPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		patientEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		familyPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		familyEmail.setCellFactory(TextFieldTableCell.forTableColumn());

		patientPhone.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		patientEmail.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		familyPhone.setCellValueFactory(cellData -> cellData.getValue().getContactInfo().getPhoneList());
		familyEmail.setCellValueFactory(cellData -> cellData.getValue().getContactInfo().getEmailList());
		familyNames.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		familyRels.setCellValueFactory(cellData -> cellData.getValue().relationProperty());

	}

}
