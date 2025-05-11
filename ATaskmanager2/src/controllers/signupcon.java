package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class signupcon implements Initializable{

    @FXML
    private Button login;

    @FXML
    private PasswordField password;
    
    @FXML
    private Button signup;

    @FXML
    private TextField username;
    
    @FXML
    private TextField email;
    
    public void signupButton(ActionEvent event) {
    	String user = username.getText();
    	String pass = password.getText();
    	String emal = email.getText();
    	
    	if(user != null || pass != null) {
    		DBUtil.signupUser(event, user,emal, pass);
    	}else {
    		System.out.println("You cannot leace the fields empty");
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setContentText("You cannot leave one or both fields empty");
    		alert.show();
    	}
    }
    
    public void loginButton(ActionEvent event) {
    	DBUtil.changeScene(event, "/view/login.fxml", "Login", null,1);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		signup.setOnAction(event->signupButton(event));
		login.setOnAction(event -> loginButton(event));
		
	}

}
