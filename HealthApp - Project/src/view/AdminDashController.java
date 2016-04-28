package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
<<<<<<< HEAD
import javafx.scene.layout.AnchorPane;
=======
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
>>>>>>> f88a3051f151a85fd647267e20b59d859dcea7db
import model.Administrator;
import model.MainApp;

public class AdminDashController extends MedicalStaffDashController {

	@FXML
<<<<<<< HEAD
=======
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
>>>>>>> f88a3051f151a85fd647267e20b59d859dcea7db
	protected Tab addPatientTab;

	private Administrator admin;

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
		return admin;
	}

	public void setAdmin(Administrator admin){
		this.admin = admin;
		loadAdminFields();
	}

	public void loadAdminFields(){
		super.loadUserFields(admin);
	}

	@Override
	public FXMLLoader getLoader(){
		loader.setLocation(MainApp.class.getResource("../view/AdminDash.fxml"));
		return loader;
	}
}
