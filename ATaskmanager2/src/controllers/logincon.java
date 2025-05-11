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

public class logincon implements Initializable {

	@FXML
	private Button signup;

	@FXML
	private Button login;

	@FXML
	private TextField user;

	@FXML
	private PasswordField passkey;

	public void signupScene(ActionEvent event) {
		DBUtil.changeScene(event, "/view/signup.fxml", "Sign up", null, 1);
	}

	public void loginButton(ActionEvent event) {
		String user1 = user.getText();
		String password = passkey.getText();

		if (user1 != null || password != null) {
			DBUtil.loginUser(event, user1, user1, password);
		} else {
			System.out.println("You cannot leace the fields empty");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("You cannot leave one or both fields empty");
			alert.show();
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resource) {
		signup.setOnAction(event -> {
			signupScene(event);
		});

		login.setOnAction(event -> {
			loginButton(event);
		});
	}
}
