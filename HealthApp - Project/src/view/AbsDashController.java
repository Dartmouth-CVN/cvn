package view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AbsUser;
import model.ContactElement;
import model.Patient;
import utils.ObjectNotFoundException;

import static model.MainApp.getLoadedSceneOfType;
import static model.MainApp.main;

public abstract class AbsDashController extends AbsController {

	@FXML
	protected TabPane tabPane;
	@FXML
	protected Tab scheduleTab;
	@FXML
	protected Tab searchTab;
	@FXML
	protected Tab exportTab;
	@FXML
	protected Tab editPatientTab;
	@FXML
	protected Tab addPatientTab;
	@FXML
	protected ImageView scheduleImage;
	@FXML
	protected ImageView searchImage;
	@FXML
	protected ImageView addPatientImage;
	@FXML
	protected ImageView exportImage;
	@FXML
	protected Button logoutButton;

	@FXML
	protected ImageView profilePic;
	@FXML
	protected Label name;
	@FXML
	protected Label email;
	@FXML
	protected Label room;
	@FXML
	protected Label number;

	@FXML
	private AnchorPane iconPane;

	static LoadedScene scene;
	static Scene primaryScene;
	AbsUser user;

	public AbsDashController() {
		key = "abs dash";
	}

	@FXML
	private void initialize() {
	}

	public void setIconPane(AnchorPane pane){
		iconPane = pane;
	}

	public void initializeTabs(){
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab.equals(searchTab)) {
				loadSearchTab();
				tabPane.getSelectionModel().select(searchTab);
			} else if(newTab.equals(addPatientTab)) {
				loadAddPatientTab();
				tabPane.getSelectionModel().select(addPatientTab);
			}
		});
	}

	public void loadUserFields(){
		try {
			if(user != null) {
				profilePic.setImage(new Image("file:" + user.getPicture()));
				name.setText(user.getFirstName() + " " + user.getLastName());
				ContactElement mail = user.getContactInfo().getPrimaryEmail();
				ContactElement phone = user.getContactInfo().getPrimaryPhone();
				email.setText(mail.getValue() + " (" + mail.getType() + ")");
				room.setText(user.getRoom());
				number.setText(phone.getValue() + " (" + phone.getType() + ")");
				loadScheduleTab(user);
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void loadSearchTab() {
		LoadedScene scene = getLoadedSceneOfType(new SearchController());
		searchTab.setContent(scene.getPane());
	}

	public void loadAddPatientTab(){
		LoadedScene scene = getLoadedSceneOfType(new EditPatientController());
		Patient p = new Patient();
		p.setNewPatient();
		((EditPatientController) scene.getController()).setPatient(p);
		addPatientTab.setContent(scene.getPane());
	}

	public void loadEditPatientTab(Patient p) {
		LoadedScene scene = getLoadedSceneOfType(new EditPatientController());
		((EditPatientController) scene.getController()).setPatient(p);
		editPatientTab.setContent(scene.getPane());
		tabPane.getSelectionModel().select(editPatientTab);
	}

	public void loadExportTab(){
		LoadedScene scene = getLoadedSceneOfType(new ExportController());
		exportTab.setContent(scene.getPane());

	}

	public void loadScheduleTab(AbsUser user) {
		LoadedScene scene = getLoadedSceneOfType(new ScheduleController());
		((ScheduleController) scene.getController()).setUser(user);
		scheduleTab.setContent(scene.getPane());
	}

	/**
	 * Clicking search image opens the search tab.
	 */
	@FXML
	protected void handleSearchImage() {
		loadSearchTab();
		tabPane.getSelectionModel().select(searchTab);
	}

	/**
	 * Clicking schedule image opens the schedule tab.
	 */
	@FXML
	protected void handleScheduleImage() {
		loadScheduleTab(new Patient());
		tabPane.getSelectionModel().select(scheduleTab);
	}

	@FXML
	public void handleExportImage() {
		loadExportTab();
		tabPane.getSelectionModel().select(exportTab);
	}

	@FXML
	public void handleAddPatientImage() {
		loadAddPatientTab();
		tabPane.getSelectionModel().select(addPatientTab);
	}

	@FXML
	public void handleSwipeLeft(){

		int currentTab = tabPane.getSelectionModel().getSelectedIndex() - 1;

		if (currentTab <= 0 ) {


		} else {

			tabPane.getSelectionModel().select(currentTab);

		}
	}

	@FXML
	public void handleSwipeRight(){

		int currentTab = tabPane.getSelectionModel().getSelectedIndex() + 1;

		if (currentTab >= tabPane.getTabs().size() - 1) {


		} else {

			tabPane.getSelectionModel().select(currentTab);

		}

	}

	@FXML
	public void handleLogoutButton() {
		user = null;
		mainApp.showLogin();
	}

}
