package view;

import java.io.File;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import model.Caregiver;
import model.DisplayString;
import model.HealthInfo;
import model.MainApp;
import model.Meal;
import model.Patient;
import model.Pet;

public class EditPatientController {

	// General functions and attributes to interact with main application

	private Patient p;
	MainApp mainApp;
	private File curCSV;
	private LinkedList<HealthInfo> info;

	public EditPatientController(Patient p) {
		this.p = p;

	}

	public EditPatientController() {
	};

	public void setMainApp(MainApp app) {
		this.mainApp = app;
	}

	public void setPatient(Patient p) {
		this.p = p;
	}

	@FXML
	public void importFitBitCSV() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select FitBit CSV to import");
		curCSV = fc.showOpenDialog(null);
		if (curCSV != null && curCSV.exists()) {
			info = controller.FitBitParsingUtils.fitBitImport(curCSV);
			p.addHealthInfoList(info);
		}
	}

	// Functions and attributes to communicate with EditPatient.fxml

	@FXML
	TextField firstName;
	@FXML
	TextField lastName;
	@FXML
	TextArea patientAddress;
	@FXML
	TextField familyMemName;
	@FXML
	TextField familyMemRel;
	@FXML
	TextField mealName;
	@FXML
	TextField mealCal;
	@FXML
	TextArea mealNotes;
	@FXML
	DatePicker patientBirthday;
	@FXML
	DatePicker familyMemBirthday;
	@FXML
	TableView<DisplayString> patientPhoneTable;
	@FXML
	TableView<DisplayString> patientEmailTable;
	@FXML
	TableView<DisplayString> familyPhoneTable;
	@FXML
	TableView<DisplayString> familyEmailTable;
	@FXML
	TableColumn<DisplayString, String> patientPhone;
	@FXML
	TableColumn<DisplayString, String> patientEmail;
	@FXML
	TableColumn<DisplayString, String> familyPhone;
	@FXML
	TableColumn<DisplayString, String> familyEmail;
	@FXML
	TableView<Caregiver> familyTable;
	@FXML
	TableColumn<Caregiver, String> familyNames;
	@FXML
	TableColumn<Caregiver, String> familyRels;
	@FXML
	TableView<Pet> petTable;
	@FXML
	TableColumn<Pet, String> petNames;
	@FXML
	TableColumn<Pet, String> petSpecies;
	@FXML
	TableColumn<Pet, Boolean> petAllergyFriendly;
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
	@FXML
	RadioButton mealLikeButton;
	@FXML
	RadioButton mealDislikeButton;
	@FXML
	RadioButton mealNeutralButton;

	ObservableList<DisplayString> patientPhones;
	ObservableList<DisplayString> patientEmails;

	ObservableList<Caregiver> familyMembers;
	ObservableList<DisplayString> familyEmails;
	ObservableList<DisplayString> familyPhones;
	ObservableList<Caregiver> famNames;
	ObservableList<Caregiver> famRels;

	@FXML
	private void initialize() {
		patientPhones = FXCollections.observableArrayList();
		patientEmails = FXCollections.observableArrayList();
		familyMembers = FXCollections.observableArrayList();
		famNames = FXCollections.observableArrayList();
		famRels = FXCollections.observableArrayList();
		familyPhones = FXCollections.observableArrayList();
		familyEmails = FXCollections.observableArrayList();

		patientPhoneTable.setItems(patientPhones);
		patientEmailTable.setItems(patientEmails);
		familyTable.setItems(familyMembers);

		patientPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		patientEmail.setCellFactory(TextFieldTableCell.forTableColumn());
		familyPhone.setCellFactory(TextFieldTableCell.forTableColumn());
		familyEmail.setCellFactory(TextFieldTableCell.forTableColumn());

		patientPhone.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		patientEmail.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		familyPhone.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		familyEmail.setCellValueFactory(cellData -> cellData.getValue().getStringProperty());
		familyNames.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		familyRels.setCellValueFactory(cellData -> cellData.getValue().getRelationProperty());

		patientPhone.setOnEditCommit(new EventHandler<CellEditEvent<DisplayString, String>>() {
			@Override
			public void handle(CellEditEvent<DisplayString, String> t) {
				int phoneIndex = patientPhoneTable.getSelectionModel().getSelectedIndex();
				patientPhones.set(phoneIndex, new DisplayString(t.getNewValue()) );
			}
		});

		patientEmail.setOnEditCommit(new EventHandler<CellEditEvent<DisplayString, String>>() {
			@Override
			public void handle(CellEditEvent<DisplayString, String> t) {
				int emailIndex = patientEmailTable.getSelectionModel().getSelectedIndex();
				patientEmails.set(emailIndex, new DisplayString(t.getNewValue()) );
			}
		});

	}

	public void loadFields() {
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		patientBirthday.setValue(p.getBirthday());
		patientAddress.setText(p.getContactInfo().getPrimaryAddress());

		for (String phoneNum : p.getContactInfo().getPhone())
			patientPhones.add(new DisplayString(phoneNum));

		for (String email : p.getContactInfo().getEmail())
			patientEmails.add(new DisplayString(email));

		// for(Caregiver family : p.)

		// familyNames.setCellValueFactory(cellData
		// ->cellData.getValue().getStringProperty());
		// familyRels.setCellValueFactory(new PropertyValueFactory<Caregiver,
		// String>("relation"));
		// familyTable.setItems(FXCollections.observableArrayList(p.getPreferences().getCaregiver()));
		//
		// familyTable.getSelectionModel().selectedItemProperty().addListener(new
		// ChangeListener<Caregiver>() {
		//
		// petNames.setCellValueFactory(new PropertyValueFactory<Pet,
		// String>("name"));
		// petSpecies.setCellValueFactory(new PropertyValueFactory<Pet,
		// String>("species"));
		// petAllergyFriendly.setCellValueFactory(new PropertyValueFactory<Pet,
		// Boolean>("allergyFriendly"));
		// petTable.setItems(FXCollections.observableArrayList(p.getPreferences().getPets()));
		//
		// mealNames.setCellValueFactory(new PropertyValueFactory<Meal,
		// String>("name"));
		// mealCals.setCellValueFactory(new PropertyValueFactory<Meal,
		// Integer>("calories"));
		// mealLiked.setCellValueFactory(new PropertyValueFactory<Meal,
		// Boolean>("like"));
		// mealDisliked.setCellValueFactory(new PropertyValueFactory<Meal,
		// Boolean>("dislike"));
		// mealNotesCol.setCellValueFactory(new PropertyValueFactory<Meal,
		// String>("notes"));
		// mealTable.setItems(FXCollections.observableArrayList(p.getPreferences().getMenu()));
		//
		// mealTable.getSelectionModel().selectedItemProperty().addListener(new
		// ChangeListener<Meal>() {
		//
		// @Override
		// public void changed(ObservableValue<? extends Meal> observable, Meal
		// oldValue, Meal newValue) {
		// switchMeal(oldValue, newValue);
		// }
		// });
	}

	@FXML
	private void addPatientPhone() {
		patientPhones.add(new DisplayString("Enter New Number..."));
	}

	@FXML
	private void removePatientPhone() {
		patientPhones.remove(patientPhoneTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addPatientEmail() {
		patientEmails.add(new DisplayString("Enter new email..."));
	}

	@FXML
	private void removePatientEmail() {
		patientEmails.remove(patientEmailTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMemPhone() {
		familyPhones.add(new DisplayString("Enter New Number..."));
	}

	@FXML
	private void removeFamilyMemPhone() {
		familyPhones.remove(familyPhoneTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMemEmail() {
		familyEmails.add(new DisplayString("Enter new email..."));
	}

	@FXML
	private void removeFamilyMemEmail() {
		familyEmails.remove(familyEmailTable.getSelectionModel().getSelectedIndex());
	}

	@FXML
	private void addFamilyMember() {
	}

	@FXML
	private void removeFamilyMember() {
		int selectionIndex = familyTable.getSelectionModel().getSelectedIndex();

		if (selectionIndex >= 0) {
			familyTable.getItems().remove(selectionIndex);
		}
	}

	@FXML
	private void addPet() {
		petTable.getItems().add(new Pet());
	}

	@FXML
	private void removePet() {
		int selectionIndex = petTable.getSelectionModel().getSelectedIndex();

		if (selectionIndex >= 0) {
			petTable.getItems().remove(selectionIndex);
		}
	}

	@FXML
	private void addMeal() {
		mealTable.getItems().add(new Meal());
	}

	@FXML
	private void removeMeal() {
		int selectionIndex = mealTable.getSelectionModel().getSelectedIndex();

		if (selectionIndex >= 0) {
			mealTable.getItems().remove(selectionIndex);
		}
	}

	@FXML
	private void likeMeal() {
		mealDislikeButton.setSelected(false);
		mealNeutralButton.setSelected(false);
	}

	@FXML
	private void dislikeMeal() {
		mealLikeButton.setSelected(false);
		mealNeutralButton.setSelected(false);
	}

	@FXML
	private void unlikeMeal() {
		mealDislikeButton.setSelected(false);
		mealLikeButton.setSelected(false);
	}

	private void switchFamilyMember(Caregiver oldValue, Caregiver newValue) {
		// Save previously selected family member
		// Set fields on right of screen with info for new selected family
		// member
	}

	private void switchMeal(Meal oldValue, Meal newValue) {
		// Save previously selected meal
		// Set fields on right of screen with info for new selected meal
	}

	@FXML
	private void save() {
		p.setFirstName(firstName.getText());
		p.setLastName(lastName.getText());
		p.setBirthday(patientBirthday.getValue());
		p.getContactInfo().makePrimaryAddress(patientAddress.getText());

		for (DisplayString phoneNumber : patientPhones)
			p.getContactInfo().addPhone(phoneNumber.getString());

		for (DisplayString email : patientEmails)
			p.getContactInfo().addEmail(email.getString());
		
		p.getContactInfo().makePrimaryAddress(patientAddress.getText());

		if ( (p.getNewPatient() && p.save()) || (!p.getNewPatient() && p.update())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("General Information");
			alert.setHeaderText("Info");
			alert.setContentText("Update successful!");

			alert.showAndWait();
		}
	}
}
