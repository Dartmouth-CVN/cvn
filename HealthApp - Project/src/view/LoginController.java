package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.AbsUser;
import model.Administrator;
import model.MainApp;
import utils.DBHandler;

public class LoginController extends AbsController {

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;
	@FXML
	private AnchorPane loginPane;

	public LoginController() {
	}
	
	@Override
	public FXMLLoader getLoader() {
		loader.setLocation(MainApp.class.getResource("../view/LoginView.fxml"));
		return loader;
	}

	@FXML
	private void initialize() {

	}

	public void handleLogin() {
		String uname = username.getText();
		String pass = password.getText();

		if (uname.equals("") || pass.equals(""))
			System.out.println("Must enter username and password");
		else {
			AbsUser user = DBHandler.getUniqueInstance().getAbsUser(uname);
			if(pass.equals(user.getPassword())){
				if(user instanceof Administrator){
					mainApp.showAdminDash((Administrator)user);
				}
			}
		}
	}
}
