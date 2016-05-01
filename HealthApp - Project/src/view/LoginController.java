package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.AbsUser;
import model.Administrator;
import model.MainApp;
import model.Patient;
import utils.DBHandler;

public class LoginController extends AbsController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane loginPane;

    public LoginController() {
        key = "login";
    }

    @Override
    public FXMLLoader getLoader() {
        loader.setLocation(MainApp.class.getResource("/view/LoginView.fxml"));
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
            AbsUser user = DBHandler.getUniqueInstance().getFilledUserByUsername(uname);
            if (user == null);
            else{
                if (pass.equals(user.getPassword())) {
                    username.setText("");
                    password.setText("");
                    username.requestFocus();
                    System.out.printf("user contact size: %d\n", user.getContactInfo().getAllContactElements().size());

                    if (user instanceof Administrator)
                        mainApp.showAdminDash((Administrator) user);
                    else if (user instanceof Patient)
                        mainApp.showPatientDash((Patient) user);
                }
            }

        }
    }
}
