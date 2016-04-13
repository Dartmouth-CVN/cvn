package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.MainApp;
import model.Patient;

public class EditPatientController {
	
	private Patient p;
	
	public EditPatientController(Patient p) {this.p=p;}
	public EditPatientController(){};
	
	@FXML
	private TextField firstNameTF;

	@FXML
	private TextField lastNameTF;

	@FXML
	private TextField emailTF;

	@FXML
	private TextField phoneTF;
	
	@FXML
	private TextArea savedGeneralInfo;
	
	@FXML
	private TextArea savedFamilyInfo;
	
	@FXML
	private TextArea savedFitnessInfo;
	
	@FXML
	private TextArea savedDietInfo;
	
	@FXML
	private ScrollPane familySP;

	@FXML
	private ScrollPane petSP;
	
	@FXML
	private ScrollPane exerciseSP;
	
	@FXML
	private ScrollPane likedMealSP;
	
	@FXML
	private ScrollPane dislikedMealSP;

	@FXML
	private VBox familyVB;

	@FXML
	private VBox petVB;

	@FXML
	private VBox exerciseVB;

	@FXML
	private VBox likedMealVB;

	@FXML
	private VBox dislikedMealVB;
	
	@FXML
	private Button familyAdd;

	@FXML
	private Button familyDelete;
	
	@FXML
	private Button petAdd;

	@FXML
	private Button petDelete;
	
	@FXML
	private Button exerciseAdd;

	@FXML
	private Button exerciseDelete;
	
	@FXML
	private Button likedMealAdd;

	@FXML
	private Button likedMealDelete;
	
	@FXML
	private Button dislikedMealAdd;

	@FXML
	private Button dislikedMealDelete;
	
	@FXML
	private Button saveGeneral;
	
	@FXML
	private Button saveFamily;
	
	@FXML
	private Button saveFitness;
	
	@FXML
	private Button saveDiet;
	
	MainApp mainApp;
	
	
	
	@FXML
	public void initialize(){

	}
	
	public void setPatient(Patient p){
		System.out.println("Setting patient: " + p.getFirstName());
		firstNameTF.setText(p.getFirstName());
		lastNameTF.setText(p.getLastName());
		this.p = p;
	}

	@FXML
	public void pressEnter(KeyEvent event){

		if(event.getCode().equals(KeyCode.ENTER)) {
			saveGeneralInfo();
		}
	}
	
	public void saveGeneralInfo() {
		String first = firstNameTF.getText();
		String last = lastNameTF.getText();
		String email = emailTF.getText();
		String phone = phoneTF.getText();
		
		p.setFirstName(first);
		p.setLastName(last);
		p.getContactInfo().makePrimaryEmail(email);
		p.getContactInfo().makePrimaryPhone(phone);
		
		savedGeneralInfo.setText("Saved Information:\n" +
				"First Name: " + p.getFirstName() + "\n" +
				"Last Name: " + p.getLastName() + "\n" +
				"Email: " + p.getContactInfo().getPrimaryEmail() + "\n" +
				"Phone: " + p.getContactInfo().getPrimaryPhone()); 
		mainApp.getDatabaseHandler().updatePatient(p);
	}
	
	public void saveFamilyInfo() {
		int i;
		TextField field;
		String familyList = "";
		String petList = "";
		String newFamily;
		String newPet;
		
		for (i = 0; i < (familyVB.getChildren().size() - 1); i++) {
			field = (TextField) familyVB.getChildren().get(i);
//			this.p.getPreferences().addFamily(field.getText());
		}
		
		for (i = 0; i < (petVB.getChildren().size() - 1); i++) {
			field = (TextField) petVB.getChildren().get(i);
//			this.p.getPreferences().addPet(field.getText());
		}
		
//		for(i = 0; i < this.p.getPreferences().getFamily().size(); i++) {
//			newFamily = this.p.getPreferences().getFamily().get(i);
//			familyList = familyList + newFamily + "\n";
//		}
		
		for(i = 0; i < this.p.getPreferences().getPets().size(); i++) {
//			newPet = this.p.getPreferences().getPets().get(i);
//			petList = petList + newPet + "\n";
		}
		
		savedFamilyInfo.setText("Saved Family Information:\n\nFamily Members:\n" + familyList + "\nPets:\n" + petList);
		mainApp.getDatabaseHandler().updatePatient(p);
	}
	
	
	
	public void saveFitnessInfo() {
		int i;
		TextField field;
		String exerciseList = "";
		String newExercise;
		
		for (i = 0; i < (exerciseVB.getChildren().size() - 1); i++) {
			field = (TextField) exerciseVB.getChildren().get(i);
			this.p.getPreferences().addFitness(field.getText());
		}
		
		for(i = 0; i < this.p.getPreferences().getFitness().size(); i++) {
			newExercise = this.p.getPreferences().getFitness().get(i);
			exerciseList = exerciseList + newExercise + "\n";
		}
		
		
		savedFitnessInfo.setText("Saved Fitness Information:\n\nExercises:\n" + exerciseList);
		mainApp.getDatabaseHandler().updatePatient(p);
	}
	
	public void saveDietInfo() {
		int i;
		TextField field;
		String likedMealList = "";
		String dislikedMealList = "";
		String newLikedMeal;
		String newDislikedMeal;
//		
//		for (i = 0; i < (likedMealVB.getChildren().size() - 1); i++) {
//			field = (TextField) likedMealVB.getChildren().get(i);
//			this.p.getPreferences().addLikedMeal(field.getText());
//		}
//		
//		for (i = 0; i < (dislikedMealVB.getChildren().size() - 1); i++) {
//			field = (TextField) dislikedMealVB.getChildren().get(i);
//			this.p.getPreferences().addDislikedMeal(field.getText());
//		}
//		
//		for(i = 0; i < this.p.getPreferences().getLikedMeals().size(); i++) {
//			newLikedMeal = this.p.getPreferences().getLikedMeals().get(i);
//			likedMealList = likedMealList + newLikedMeal + "\n";
//		}
//		
//		for(i = 0; i < this.p.getPreferences().getDislikedMeals().size(); i++) {
//			newDislikedMeal = this.p.getPreferences().getDislikedMeals().get(i);
//			dislikedMealList = dislikedMealList + newDislikedMeal + "\n";
//		}
		
		savedDietInfo.setText("Saved Diet Information:\n\nLiked Meals:\n" + likedMealList + "\nDisliked Meals:\n" + dislikedMealList);
		mainApp.getDatabaseHandler().updatePatient(p);
	}
	
	public void setMainApp(MainApp app){
		this.mainApp = app;
	}
	
	@FXML
	public void addFamily() {
		TextField newFamily = new TextField();
		newFamily.setPromptText("Family Member Name");
		familyVB.getChildren().add(familyVB.getChildren().size()-2, newFamily);
	}
	
	@FXML
	public void deleteFamily() {
		if(familyVB.getChildren().size() > 2) {
			familyVB.getChildren().remove(familyVB.getChildren().size()-2);
		}
	}
	
	@FXML
	public void addPet() {
		TextField newPet = new TextField();
		newPet.setPromptText("Pet Name - Species - Breed");
		petVB.getChildren().add(petVB.getChildren().size()-2, newPet);
	}
	
	@FXML
	public void deletePet() {
		if(petVB.getChildren().size() > 2) {
			petVB.getChildren().remove(petVB.getChildren().size()-2);
		}
	}
	
	@FXML
	public void addExercise() {
		TextField newExercise = new TextField();
		newExercise.setPromptText("Exercise");
		exerciseVB.getChildren().add(exerciseVB.getChildren().size()-2, newExercise);
	}
	
	@FXML
	public void deleteExercise() {
		if(exerciseVB.getChildren().size() > 2) {
			exerciseVB.getChildren().remove(exerciseVB.getChildren().size()-2);
		}
	}
	
	@FXML
	public void addLikedMeal() {
		TextField newLikedMeal = new TextField();
		newLikedMeal.setPromptText("Liked Meal Item");
		likedMealVB.getChildren().add(likedMealVB.getChildren().size()-2, newLikedMeal);
	}
	
	@FXML
	public void deleteLikedMeal() {
		if(likedMealVB.getChildren().size() > 2) {
			likedMealVB.getChildren().remove(likedMealVB.getChildren().size()-2);
		}
	}
	
	@FXML
	public void addDislikedMeal() {
		TextField newDislikedMeal = new TextField();
		newDislikedMeal.setPromptText("Disliked Meal Item");
		dislikedMealVB.getChildren().add(dislikedMealVB.getChildren().size()-2, newDislikedMeal);
	}
	
	@FXML
	public void deleteDislikedMeal() {
		if(dislikedMealVB.getChildren().size() > 2) {
			dislikedMealVB.getChildren().remove(dislikedMealVB.getChildren().size()-2);
		}
	}
	
	public void update() {
		mainApp.getDatabaseHandler().updatePatient(p);
	}
}
