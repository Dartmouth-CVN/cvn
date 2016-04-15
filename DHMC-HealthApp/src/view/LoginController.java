package view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.MainApp;

public class LoginController {
	private MainApp mainApp;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	public LoginController() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

	}

	public void handleLogin() {
		mainApp.initRootLayout();
//		String uname = username.getText();
//		String pass = password.getText();

//		if (uname.equals("") || pass.equals(""))
//			System.out.println("Must enter username and password");
//		else {
//			String user = MainApp.getDatabaseHandler().login(uname, pass);
//
//			if (user != null) {
//				mainApp.initRootLayout();
//			} else
//				System.out.println("login failure");
//		}
	}
}
