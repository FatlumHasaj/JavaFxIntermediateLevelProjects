package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DButil {
	
	private static final String url = "jdbc:mysql://localhost:0000/schemaName";
	private static final String user = "myworkbenchuser";
	private static final String dbPassword = "password";
	
	
	public void changeSceneOld(String file, Stage stage) {
		try {
			URL resource = getClass().getResource(file);
			
			if(resource == null) {
				System.out.println("The file " + file + " doesn't exist or cant be accessed.");
				return;
			}
			
			FXMLLoader loader = new FXMLLoader(resource);
			
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			
			stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void changescene(ActionEvent event, String file, String title) {
		Parent root = null;
		
		try {
			FXMLLoader loader = new FXMLLoader(DButil.class.getResource(file));
			root = loader.load();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root));
		
	}
	
	
	public static void registerUser(ActionEvent event, String email, String username, String password) {
		Connection conn = null;
		PreparedStatement psInsert = null;
		PreparedStatement psCheck = null;
		ResultSet result = null;
		
		try {
			conn = DriverManager.getConnection(url,user,dbPassword);
			psCheck = conn.prepareStatement("SELECT * FROM users2 WHERE email = ? OR username = ?");
			psCheck.setString(1, email);
			psCheck.setString(2, username);
			result = psCheck.executeQuery();
			
			if(result.isBeforeFirst()) {
				System.out.println("Username or email already exists.");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Use diffrent email/username");
				alert.show();
			}else {
				psInsert =  conn.prepareStatement("INSERT INTO users2 (email, username, password) VALUES (?, ?, ?)");
				psInsert.setString(1, email);
				psInsert.setString(2, username);
				psInsert.setString(3, password);
				psInsert.executeUpdate();
				System.out.println("Username or email already exists.");
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setContentText("Account created successfully.");
				alert.show();
				changescene(event, "/view/loggedin.fxml","Task manager");
			}
			
			
		}catch(SQLException e) {
			
		}finally {
			if(result != null) {
				try {
					result.close();
				}catch(SQLException e) {
					
				}
			}
			
			if(psCheck != null) {
				try {
					psCheck.close();
				}catch(SQLException e) {
					
				}
			}
			
			if(psInsert != null) {
				try {
					psInsert.close();
				}catch(SQLException e) {
					
				}
			}
			
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException e) {
					
				}
			}
		}
		
	}
	

	
	public static void checkCredentials(ActionEvent event, String username, String email, String password1) {
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(url,user,dbPassword);
			prep = con.prepareStatement("SELECT * FROM users2 WHERE username = ? OR email = ?");
			prep.setString(1, username);
			prep.setString(2, email);
			rs = prep.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				System.out.println("User doesnt exist");
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setContentText("User doesnt exist.");
				a.show();
			}else {
				while(rs.next()) {
					String password2 = rs.getNString("password");
					if(password1.equals(password2)) {
						DButil.changescene(event, "/view/loggedin.fxml", "Logged in");
					}else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("Invalid password");
						alert.show();
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					
				}
			}
			
			if(prep!= null) {
				try {
					prep.close();
				}catch(SQLException e) {
					
				}
			}
			
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e) {
					
				}
			}
		}
	}
}

	