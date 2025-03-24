package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController implements Initializable{
	DButil db = new DButil();
	@FXML
	private Button LoginButton2;
	
	@FXML
	private Button signupButton;
	
	@FXML
	private TextField username1;
	
	@FXML
	private TextField email1;
	
	@FXML
	private TextField password1;

	public void loginButton(ActionEvent event) {
		DButil.changescene(event, "/view/login.fxml", "Login");
	}
	
	public void signUpButton(ActionEvent event) {
		String user = username1.getText();
		String email = email1.getText();
		String password = password1.getText();
		
		if(user.isEmpty() || email.isEmpty() || password.isEmpty()) {
		}else {
		DButil.registerUser(event, email,user, password);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		signupButton.setOnAction(event -> signUpButton(event));
		LoginButton2.setOnAction(event -> loginButton(event));
	}
	
	
	
	
	
} 
