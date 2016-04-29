package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import model.Administrator;
import model.MainApp;

public class AdminDashController extends MedicalStaffDashController {

	@FXML
	private ImageView profilePic;
	@FXML
	private Label name;
	@FXML
	private Label email;
	@FXML
	private Label room;
	@FXML
	private Label number;


	@FXML
	protected Tab addPatientTab;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public AdminDashController() {
		key = "admin dash";
	}

	@FXML
	private void initialize() {
		loadAdminFields();
		super.initializeTabs();
		System.out.println("loading this stuff");
		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab.equals(exportTab)) {
				loadExportTab();
				tabPane.getSelectionModel().select(exportTab);
			}
			});
	}

	public Administrator getAdmin(){
		return (Administrator) user;
	}

	public void setAdmin(Administrator admin){
		user =  admin;
		loadAdminFields();
	}

	public void loadAdminFields(){
		super.loadUserFields();
	}

	@Override
	public FXMLLoader getLoader(){
		loader.setLocation(MainApp.class.getResource("../view/AdminDash.fxml"));
		return loader;
	}
}
