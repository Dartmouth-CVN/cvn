package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.MainApp;
import model.Patient;
import utils.ObjectNotFoundException;

public class LoginController extends AbsController {

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

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
			try {
				Patient p = MainApp.getDBHandler().getPatient(uname);
				if(pass.equals(p.getPassword())){
					mainApp.showAdminDash();
				}
			} catch (ObjectNotFoundException e) {
				MainApp.printError(e);
			}

		}
	}
}
