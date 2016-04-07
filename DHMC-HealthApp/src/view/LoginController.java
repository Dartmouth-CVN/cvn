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
	
	public LoginController(){
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	@FXML
	private void initialize(){
		
	}
	
	public void handleLogin(){
		String uname = username.getText();
		String pass = password.getText();
		
		String user = mainApp.getDatabaseHandler().login(uname, pass);
		
		if(user != null){
			System.out.println(user);
			mainApp.showAdminDash();
		}
		else
			System.out.println("login failure");
	}
}
