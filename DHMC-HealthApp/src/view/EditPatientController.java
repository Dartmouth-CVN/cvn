package view;

import java.io.File;
import java.util.LinkedList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Caregiver;
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

	public void update() {
		MainApp.getDatabaseHandler().updatePatient(p);
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
	VBox patientPhone;
	@FXML
	VBox patientEmail;
	@FXML
	VBox familyMemPhone;
	@FXML
	VBox familyMemEmail;
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
	RadioButton mealNeutralButton;;

	@FXML
	private void initialize() {

	}

	public void loadFields() {
		TextField temp;
		int i;
		System.out.println("Patient: " + p.getFirstName());
		LinkedList<String> phoneNums = p.getContactInfo().getPhone();
		LinkedList<String> emails = p.getContactInfo().getEmail();
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		patientBirthday.setValue(p.getBirthday());
//		patientAddress.setText(p.getContactInfo().getPrimaryAddress());

		for (i = 0; i < phoneNums.size(); i++) {
			if (i > 0) {
				addPatientPhone();
			}
			temp = (TextField) patientPhone.getChildren().get(i);
			temp.setText(phoneNums.get(i));
		}

		for (i = 0; i < emails.size(); i++) {
			if (i > 0) {
				addPatientEmail();
			}
			temp = (TextField) patientEmail.getChildren().get(i);
			temp.setText(emails.get(i));

		}
		// Readying the tables
//		familyNames.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("name"));
//		familyRels.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("relation"));
//		familyTable.setItems(FXCollections.observableArrayList(p.getPreferences().getCaregiver()));
//
//		familyTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Caregiver>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Caregiver> observable, Caregiver oldValue,
//					Caregiver newValue) {
//				switchFamilyMember(oldValue, newValue);
//			}
//		});

//		petNames.setCellValueFactory(new PropertyValueFactory<Pet, String>("name"));
//		petSpecies.setCellValueFactory(new PropertyValueFactory<Pet, String>("species"));
//		petAllergyFriendly.setCellValueFactory(new PropertyValueFactory<Pet, Boolean>("allergyFriendly"));
//		petTable.setItems(FXCollections.observableArrayList(p.getPreferences().getPets()));
//
//		mealNames.setCellValueFactory(new PropertyValueFactory<Meal, String>("name"));
//		mealCals.setCellValueFactory(new PropertyValueFactory<Meal, Integer>("calories"));
//		mealLiked.setCellValueFactory(new PropertyValueFactory<Meal, Boolean>("like"));
//		mealDisliked.setCellValueFactory(new PropertyValueFactory<Meal, Boolean>("dislike"));
//		mealNotesCol.setCellValueFactory(new PropertyValueFactory<Meal, String>("notes"));
//		mealTable.setItems(FXCollections.observableArrayList(p.getPreferences().getMenu()));
//
//		mealTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Meal> observable, Meal oldValue, Meal newValue) {
//				switchMeal(oldValue, newValue);
//			}
//		});
	}

	@FXML
	private void addPatientPhone() {
		TextField newPhone = new TextField();
		newPhone.setPromptText("Phone Number");
		patientPhone.getChildren().add(newPhone);
	}

	@FXML
	private void removePatientPhone() {
		int size = patientPhone.getChildren().size();

		if (size > 1) {
			patientPhone.getChildren().remove(size - 1);
		}
	}

	@FXML
	private void addPatientEmail() {
		TextField newEmail = new TextField();
		newEmail.setPromptText("Email Address");
		patientEmail.getChildren().add(newEmail);
	}

	@FXML
	private void removePatientEmail() {
		int size = patientEmail.getChildren().size();

		if (size > 1) {
			patientEmail.getChildren().remove(size - 1);
		}
	}

	@FXML
	private void addFamilyMemPhone() {
		TextField newPhone = new TextField();
		newPhone.setPromptText("Phone Number");
		familyMemPhone.getChildren().add(newPhone);
	}

	@FXML
	private void removeFamilyMemPhone() {
		int size = familyMemPhone.getChildren().size();

		if (size > 1) {
			familyMemPhone.getChildren().remove(size - 1);
		}
	}

	@FXML
	private void addFamilyMemEmail() {
		TextField newEmail = new TextField();
		newEmail.setPromptText("Email Address");
		familyMemEmail.getChildren().add(newEmail);
	}

	@FXML
	private void removeFamilyMemEmail() {
		int size = familyMemEmail.getChildren().size();

		if (size > 1) {
			familyMemEmail.getChildren().remove(size - 1);
		}
	}

	@FXML
	private void addFamilyMember() {
		familyTable.getItems().add(new Caregiver());
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
		int i;
		TextField field;
		String text;
		LinkedList<String> list = new LinkedList<String>();
		LinkedList<String> phone = p.getContactInfo().getPhone();
		LinkedList<String> email = p.getContactInfo().getEmail();

		p.setFirstName(firstName.getText());
		p.setLastName(lastName.getText());
		p.setBirthday(patientBirthday.getValue());
		p.getContactInfo().makePrimaryAddress(patientAddress.getText());

		for (i = 0; i < patientPhone.getChildren().size(); i++) {
			field = (TextField) patientPhone.getChildren().get(i);
			text = field.getText();
			if (!text.equals("")) {
				list.add(text);
				p.getContactInfo().addPhone(text);
			}
		}

		p.getContactInfo().makePrimaryPhone(list.getFirst());

		for (i = 0; i < phone.size(); i++) {
			text = phone.get(i);
			if (!list.contains(text)) {
				p.getContactInfo().removePhone(text);
			}
		}

		list = new LinkedList<String>();

		for (i = 0; i < patientEmail.getChildren().size(); i++) {
			field = (TextField) patientEmail.getChildren().get(i);
			text = field.getText();
			if (!text.equals("")) {
				list.add(text);
				p.getContactInfo().addEmail(text);
			}
		}

		p.getContactInfo().makePrimaryEmail(list.getFirst());

		for (i = 0; i < email.size(); i++) {
			text = email.get(i);
			if (!list.contains(text)) {
				p.getContactInfo().removeEmail(text);
			}
		}

		// Save family information
		// Save pet information
		// Save meal information

		update();
	}
}
