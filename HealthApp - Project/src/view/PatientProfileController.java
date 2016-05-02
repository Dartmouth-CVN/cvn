package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.*;
import org.controlsfx.control.Rating;

import java.time.format.DateTimeFormatter;

public class PatientProfileController extends AbsController {

	@FXML
	ImageView profilePic;
	@FXML
	private Label nameLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label idLabel;
	@FXML
	private Label emailLabel;
	@FXML
	ComboBox<String> relationType;
	@FXML
	AnchorPane mealRating;
	@FXML
	DatePicker patientBirthday;
	@FXML
	DatePicker relationBirthday;
	@FXML
	TableView<PetWrapper> petTable;
	@FXML
	TableColumn<PetWrapper, String> petNameColumn;
	@FXML
	TableColumn<PetWrapper, String> petSpeciesColumn;
	@FXML
	TableColumn<PetWrapper, Boolean> petAllergyFriendlyColumn;
	@FXML
	TableView<MealWrapper> mealTable;
	@FXML
	TableColumn<MealWrapper, String> mealNameColumn;
	@FXML
	TableColumn<MealWrapper, Number> mealCaloriesColumn;
	@FXML
	TableColumn<MealWrapper, Number> mealRatingColumn;
	@FXML
	TableColumn<MealWrapper, String> mealNotesColumn;

	ObservableList<ContactElementWrapper> patientPhones;
	ObservableList<ContactElementWrapper> patientEmails;
	ObservableList<AbsRelationWrapper> relations;
	ObservableList<ContactElementWrapper> relationEmails;
	ObservableList<ContactElementWrapper> relationPhones;
	ObservableList<PetWrapper> pets;
	ObservableList<MealWrapper> meals;
	ObservableList<String> contactLabelValues;
	ObservableList<String> relationTypeValues;
	int relationIndex = 0;
	int petIndex = 0;
	int mealIndex = 0;
	Rating stars;
	int phoneIndex = 0;
	int emailIndex = 0;
	int relationPhoneIndex = 0;
	int relationEmailIndex = 0;
	ContactElement element;
	Callback<TableColumn<ContactElementWrapper, String>, TableCell<ContactElementWrapper, String>> cellFactory;

	DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private Patient patient;

	public PatientProfileController() {
		//key = "edit patient";
	}

	@FXML
	public FXMLLoader getLoader() {
		loader.setLocation(MainApp.class.getResource("/view/PatientProfileView.fxml"));
		return loader;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient p) {

		this.patient = p;

	}

	@FXML
	private void initialize() {
		initializePetInfo();
		initializeDietaryPreferences();

	}

	public void initializePetInfo() {
		pets = FXCollections.observableArrayList();
		petTable.setItems(pets);
		petTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			petIndex = petTable.getSelectionModel().getSelectedIndex();
			handlePetChange();
		});

		petNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		petSpeciesColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeciesProperty());
		petAllergyFriendlyColumn.setCellValueFactory(cellData -> cellData.getValue().getAllergyFriendlyProperty());
	}

	private void handlePetChange(){
		Pet p;
		if(pets.size() > 0) {
			p = pets.get(petIndex).toPet();

		} else {

		}
	}

	public void initializeDietaryPreferences() {
		meals = FXCollections.observableArrayList();
		mealTable.setItems(meals);
		mealTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			mealIndex = mealTable.getSelectionModel().getSelectedIndex();
		});

		mealNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFoodProperty());
		mealCaloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getCaloriesProperty());
		mealRatingColumn.setCellValueFactory(cellData -> cellData.getValue().getRatingProperty());
		mealNotesColumn.setCellValueFactory(cellData -> cellData.getValue().getNotesProperty());
		stars = new Rating(10, 0);
		stars.setUpdateOnHover(true);
		mealRating.getChildren().add(stars);
	}

}
