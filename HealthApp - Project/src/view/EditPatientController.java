package view;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;
import model.*;
import org.controlsfx.control.Rating;
import utils.FitBitParsingUtils;
import utils.ObjectNotFoundException;

public class EditPatientController extends AbsController {

    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    ImageView profilePic;
    @FXML
    TextArea patientAddress;
    @FXML
    TextField relationFirstName;
    @FXML
    TextField relationLastName;
    @FXML
    TextField relationshipField;
    @FXML
    ComboBox<String> relationType;
    @FXML
    TextField mealName;
    @FXML
    TextField mealCalories;
    @FXML
    TextField petName;
    @FXML
    TextField petSpecies;
    @FXML
    CheckBox allergyFriendlyCheckBox;
    @FXML
    AnchorPane mealRating;
    @FXML
    TextArea mealNotes;
    @FXML
    DatePicker patientBirthday;
    @FXML
    DatePicker relationBirthday;
    @FXML
    TableView<ContactElementWrapper> patientPhoneTable;
    @FXML
    TableView<ContactElementWrapper> patientEmailTable;
    @FXML
    TableView<ContactElementWrapper> relationPhoneTable;
    @FXML
    TableView<ContactElementWrapper> relationEmailTable;
    @FXML
    TableColumn<ContactElementWrapper, String> patientPhoneColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> patientEmailColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> patientPhoneLabelColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> patientEmailLabelColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationPhoneColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationEmailColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationPhoneLabelColumn;
    @FXML
    TableColumn<ContactElementWrapper, String> relationEmailLabelColumn;
    @FXML
    TableView<AbsRelationWrapper> relationTable;
    @FXML
    TableColumn<AbsRelationWrapper, String> relationNameColumn;
    @FXML
    TableColumn<AbsRelationWrapper, String> relationRelationshipColumn;
    @FXML
    TableColumn<AbsRelationWrapper, Boolean> isFamilyColumn;
    @FXML
    TableColumn<AbsRelationWrapper, Boolean> isCaregiverColumn;
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

    DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Patient patient;

    public EditPatientController() {
        key = "edit patient";
    }

    @FXML
    public FXMLLoader getLoader() {
        loader.setLocation(MainApp.class.getResource("../view/EditPatientView.fxml"));
        return loader;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient p) {
        this.patient = p;
        loadFields();
    }

    @FXML
    private void initialize() {
        initializePersonalInfo();
        initializeRelationInfo();
        initializePetInfo();
        initializeDietaryPreferences();
    }

    public void initializePersonalInfo() {
        patientPhones = FXCollections.observableArrayList();
        patientEmails = FXCollections.observableArrayList();
        contactLabelValues = FXCollections.observableArrayList();
        for(int i = 0; i < ContactElement.contactLabel.values().length; i++){
            contactLabelValues.add(ContactElement.contactLabel.values()[i].name());
        }

        patientPhoneTable.setItems(patientPhones);
        patientEmailTable.setItems(patientEmails);
        patientPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientPhoneLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        patientPhoneLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        patientEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        patientEmailLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        patientEmailLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        patientPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patientEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        patientPhoneColumn.setOnEditCommit((event) -> {
            int phoneIndex = patientPhoneTable.getSelectionModel().getSelectedIndex();
            long id = patientPhones.get(phoneIndex).getElementIdProperty().get();
            try {
                patientPhones.set(phoneIndex, new ContactElementWrapper(patient.getContactInfo().getElementById(id)));
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
        });

        patientEmailColumn.setOnEditCommit((event) -> {
            try {
                int emailIndex = patientEmailTable.getSelectionModel().getSelectedIndex();
                long id = patientEmails.get(emailIndex).getElementIdProperty().get();
                patientEmails.set(emailIndex, new ContactElementWrapper(patient.getContactInfo().getElementById(id)));
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    int relationIndex = 0;
    public void initializeRelationInfo() {
        relations = FXCollections.observableArrayList();
        relationPhones = FXCollections.observableArrayList();
        relationEmails = FXCollections.observableArrayList();
        relationTable.setItems(relations);
        relationPhoneTable.setItems(relationPhones);
        relationEmailTable.setItems(relationEmails);
        relationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            relationIndex = relationTable.getSelectionModel().getSelectedIndex();
            handleRelationChange();
        });

        relationTypeValues = FXCollections.observableArrayList();
        for(int i = 0; i < AbsRelation.relationType.values().length; i++){
            relationTypeValues.add(AbsRelation.relationType.values()[i].name());
        }
        relationNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        relationRelationshipColumn.setCellValueFactory(cellData -> cellData.getValue().getRelationshipProperty());
        isFamilyColumn.setCellValueFactory(cellData -> cellData.getValue().getIsFamilyProperty());
        isCaregiverColumn.setCellValueFactory(cellData -> cellData.getValue().getIsCaregiverProperty());

        relationPhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationPhoneLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        relationEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationEmailLabelColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        relationPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationPhoneLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
        relationEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getValueProperty());
        relationEmailLabelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), contactLabelValues));
    }

    private void handleRelationChange(){
    }

    int petIndex = 0;
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

    @FXML
    private void handleAddPet(){

    }

    private void handlePetChange(){
        Pet p = pets.get(petIndex).toPet();
        petName.setText(p.getName());
        petSpecies.setText(p.getSpecies());
        allergyFriendlyCheckBox.setSelected(p.isAllergyFriendly());
    }

    int mealIndex = 0;
    Rating stars;
    public void initializeDietaryPreferences() {
        meals = FXCollections.observableArrayList();
        mealTable.setItems(meals);
        mealTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mealIndex = mealTable.getSelectionModel().getSelectedIndex();
            handleMealChange();
        });

        mealNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFoodProperty());
        mealCaloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getCaloriesProperty());
        mealRatingColumn.setCellValueFactory(cellData -> cellData.getValue().getRatingProperty());
        mealNotesColumn.setCellValueFactory(cellData -> cellData.getValue().getNotesProperty());
        stars = new Rating(10, 0);
        stars.setPartialRating(true);
        mealRating.getChildren().add(stars);
    }

    private void handleMealChange(){
        Meal m = meals.get(mealIndex).toMeal();
        mealName.setText(m.getFood());
        mealCalories.setText(String.valueOf(m.getCalories()));
        mealNotes.setText(m.getNotes());
        stars.setRating(m.getRating());
    }

    public void loadFields() {
        profilePic.setImage(new Image("file:" + patient.getPicture()));
        firstName.setText(patient.getFirstName());
        lastName.setText(patient.getLastName());
        patientBirthday.setValue(patient.getBirthday());

        for (ContactElement e : patient.getContactInfo().getPhoneNumbers())
            patientPhones.add(new ContactElementWrapper(e));

        for (ContactElement e : patient.getContactInfo().getEmails())
            patientEmails.add(new ContactElementWrapper(e));

        String addresses = "";
        for (ContactElement e : patient.getContactInfo().getAddresses())
            addresses += e.getValue() + "\n";
        patientAddress.setText(addresses);

        for (AbsRelation relation : patient.getRelations())
            relations.add(new AbsRelationWrapper(relation));

        for (Pet p : patient.getPets())
            pets.add(new PetWrapper(p));

        for (Meal m : patient.getMeals())
            meals.add(new MealWrapper(m));
    }

    @FXML
    private void addPatientPhone() {
        patientPhones.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void removePatientPhone() {
        int index = patientPhoneTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            patientPhones.remove(index);
    }

    @FXML
    private void addPatientEmail() {
        patientEmails.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void removePatientEmail() {
        int index = patientEmailTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            patientEmails.remove(index);
    }

    @FXML
    private void addRelationPhone() {
        relationPhones.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void removeRelationPhone() {
        int index = relationPhoneTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            relationPhones.remove(index);
    }

    @FXML
    private void addRelationEmail() {
        relationEmails.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void removeRelationEmail() {
        int index = relationEmailTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            relationEmails.remove(index);
    }

    @FXML
    private void addPet() {
        petTable.getItems().add(new PetWrapper(new Pet()));
    }

    @FXML
    private void removePet() {
        int selectionIndex = petTable.getSelectionModel().getSelectedIndex();

        if (selectionIndex >= 0) {
            petTable.getItems().remove(selectionIndex);
        }
    }

    @FXML
    private void handleAddMeal() {
        mealTable.getItems().add(new MealWrapper(new Meal()));
    }

    @FXML
    private void removeMeal() {
        int selectionIndex = mealTable.getSelectionModel().getSelectedIndex();

        if (selectionIndex >= 0) {
            mealTable.getItems().remove(selectionIndex);
        }
    }

    @FXML
    private void handleFitBitImport() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            patient.getHealthProfile().setHealthInfo(FitBitParsingUtils.fitBitImport(selectedFile));
            MainApp.showAlert("FitBit import done");
        }

    }

    @FXML
    private void save() {
        patient.setFirstName(firstName.getText());
        patient.setLastName(lastName.getText());
        patient.setBirthday(patient.getBirthday());

        for (MealWrapper m : meals)
            patient.addMeal(m.toMeal());

        for (PetWrapper p : pets)
            patient.addPet(p.toPet());

        for (AbsRelationWrapper relation : relations)
            patient.addRelation(relation.toAbsRelation());

        if (patient.getIsNewPatient() && patient.savePatient() ||
                !patient.getIsNewPatient() && patient.updatePatient()) {
            MainApp.showAlert("Update successful!");
        }
    }
}
