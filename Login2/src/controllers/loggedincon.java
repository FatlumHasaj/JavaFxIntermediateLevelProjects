package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class loggedincon implements Initializable{
	@FXML
	private Button logout;
	
	@FXML
	public void logout(ActionEvent event) {
		try {
			DBUtil.changeScene(event, "/view/login.fxml", "Login", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logout.setOnAction(event -> logout1(event));
		
	}
	
	
	
}
