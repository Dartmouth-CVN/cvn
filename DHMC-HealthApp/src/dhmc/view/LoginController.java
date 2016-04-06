package dhmc.view;

import dhmc.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
		
		boolean login = mainApp.getDatabseHandler().login(uname, pass);
		
		if(login == true)
			System.out.println("login success");
		else
			System.out.println("login failure");
	}
}
