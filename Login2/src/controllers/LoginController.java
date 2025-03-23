package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController implements Initializable{
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Button signupButton;
	
	@FXML
	private TextField q;
	
	@FXML
	private PasswordField w;
	
	public void signupScene(ActionEvent event) {
		DButil.changescene(event, "/view/signup.fxml", "Sign up");
	}
	
	public void loginButtonW(ActionEvent event) {
		String a = q.getText();
		String b = w.getText();
		
		DButil.checkCredentials(event, a, a, b);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginButton.setOnAction(event -> loginButtonW(event));
		
		signupButton.setOnAction(event ->signupScene(event));
		
	}
	
	
	
}
