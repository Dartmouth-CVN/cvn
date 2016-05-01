package view;

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
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;
import model.*;
import org.controlsfx.control.Rating;
import utils.FitBitParsingUtils;

import java.io.File;
import java.time.format.DateTimeFormatter;

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
        loader.setLocation(MainApp.class.getResource("/view/EditPatientView.fxml"));
        return loader;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient p) {
        this.patient = p;
        if(!p.getIsNewPatient())
            loadFields();

    }

    @FXML
    private void initialize() {
        initializePersonalInfo();
        initializeRelationInfo();
        initializePetInfo();
        initializeDietaryPreferences();
    }

    int phoneIndex = 0;
    int emailIndex = 0;
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
            setPhoneIndex();
            element.setValue(event.getNewValue());
            patientPhones.set(phoneIndex, new ContactElementWrapper(element));
        });
        patientPhoneLabelColumn.setOnEditCommit((event) -> {
            setPhoneIndex();
            element.setType(event.getNewValue());
            patientPhones.set(phoneIndex, new ContactElementWrapper(element));
        });

        patientEmailColumn.setOnEditCommit((event) -> {
            setEmailIndex();
            element.setValue(event.getNewValue());
            patientEmails.set(emailIndex, new ContactElementWrapper(element));
        });
        patientEmailLabelColumn.setOnEditCommit((event) -> {
            setEmailIndex();
            element.setType(event.getNewValue());
            patientEmails.set(emailIndex, new ContactElementWrapper(element));
        });

    }

    ContactElement element;
    private void setPhoneIndex(){
        phoneIndex = patientPhoneTable.getSelectionModel().getSelectedIndex();
        element = patientPhones.get(phoneIndex).toContactElement();
    }

    private void setEmailIndex(){
        emailIndex = patientEmailTable.getSelectionModel().getSelectedIndex();
        element = patientEmails.get(emailIndex).toContactElement();
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

    private void handlePetChange(){
        Pet p;
        if(pets.size() > 0) {
            p = pets.get(petIndex).toPet();
            petName.setText(p.getName());
            petSpecies.setText(p.getSpecies());
            allergyFriendlyCheckBox.setSelected(p.isAllergyFriendly());
        } else {
            petName.clear();
            petSpecies.clear();
            allergyFriendlyCheckBox.setSelected(false);
        }
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
        stars.setUpdateOnHover(true);
        mealRating.getChildren().add(stars);
    }

    private void handleMealChange(){
        Meal m;
        if(meals.size() > 0) {
            m = meals.get(mealIndex).toMeal();
            mealName.setText(m.getFood());
            mealCalories.setText(String.valueOf(m.getCalories()));
            mealNotes.setText(m.getNotes());
            stars.setRating(m.getRating());
        } else {
            mealName.clear();
            mealCalories.clear();
            mealNotes.clear();
            stars.setRating(0);
        }
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
    private void handleAddPatientPhone() {
        patientPhones.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemovePatientPhone() {
        int index = patientPhoneTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            patientPhones.remove(index);
    }

    @FXML
    private void handleAddPatientEmail() {
        patientEmails.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemovePatientEmail() {
        int index = patientEmailTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            patientEmails.remove(index);
    }

    @FXML
    private void handleAddRelationPhone() {
        relationPhones.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemoveRelationPhone() {
        int index = relationPhoneTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            relationPhones.remove(index);
    }

    @FXML
    private void handleAddRelationEmail() {
        relationEmails.add(new ContactElementWrapper(new ContactElement()));
    }

    @FXML
    private void handleRemoveRelationEmail() {
        int index = relationEmailTable.getSelectionModel().getSelectedIndex();
        if(index >= 0)
            relationEmails.remove(index);
    }

    @FXML
    private void handleAddPet() {
        String name = petName.getText();
        String species = petSpecies.getText();
        Boolean allergyFriendly = allergyFriendlyCheckBox.isSelected();

        Pet pet = new Pet(name, species, allergyFriendly);
        pets.add(new PetWrapper(pet));
    }

    @FXML
    private void handleRemovePet() {
        int selectionIndex = petTable.getSelectionModel().getSelectedIndex();

        if(pets.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Remove pet");
            alert.setContentText("Are you sure you want to remove this pet?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.CANCEL) {
                    return;
                } else if (rs == ButtonType.OK) {
                    if (selectionIndex >= 0) {
                        petTable.getItems().remove(selectionIndex);
                    }

                    petName.clear();
                    petSpecies.clear();
                    allergyFriendlyCheckBox.setSelected(false);
                }
            });
        }
    }

    @FXML
    private void handleAddMeal() {
        String name = mealName.getText();
        int calories;
        int rating = (int) stars.getRating();
        String notes = mealNotes.getText();

        try{
            calories = Integer.parseInt(mealCalories.getText());
        } catch (NumberFormatException e) {
            calories = 0;
        }

        Meal meal = new Meal(name, calories, rating, notes);
        meals.add(new MealWrapper(meal));
    }

    @FXML
    private void handleRemoveMeal() {
        int selectionIndex = mealTable.getSelectionModel().getSelectedIndex();

        if(meals.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Remove meal");
            alert.setContentText("Are you sure you want to remove this meal?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.CANCEL) {
                    return;
                } else if (rs == ButtonType.OK) {
                    if (selectionIndex >= 0) {
                        mealTable.getItems().remove(selectionIndex);
                    }
                    mealName.clear();
                    mealCalories.clear();
                    stars.setRating(0);
                    mealNotes.clear();
                }
            });
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

        for(ContactElementWrapper phone : patientPhones)
            patient.getContactInfo().addPhone(phone.toContactElement());

        for(ContactElementWrapper email : patientEmails)
            patient.getContactInfo().addEmail(email.toContactElement());

//        System.out.printf("patient phones size: %d patient emails size: %d\n", patientPhones.size(), patientEmails.size());

        if (patient.getIsNewPatient() && patient.savePatient() ||
                !patient.getIsNewPatient() && patient.updatePatient()) {
            MainApp.showAlert("Update successful!");
        }
    }
}
