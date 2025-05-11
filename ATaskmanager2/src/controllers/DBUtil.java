package controllers;

import java.io.IOException; // File handling errors (FXML, config files)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Database errors (if login uses a database)

// Event handling for button clicks
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
// Scene graph and nodes
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
// UI controls (Button, TextField, PasswordField, Label)
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
// Window (Stage) handling
import javafx.stage.Stage;

public class DBUtil {

	private static Stage loginStage = null;
	private static Stage signupStage = null;
	private static Stage taskStage = null;

	public static void changeScene(ActionEvent event, String file, String title, String username, int cid) {
		try {
			FXMLLoader loader = new FXMLLoader(DBUtil.class.getResource(file));
			Parent root = loader.load();

			// Pass user ID to controller if needed
			Object controller = loader.getController();
			if (username != null && controller instanceof taskmanagercon) {
				((taskmanagercon) controller).setUserid(cid);
			}

			// Determine which stage this FXML belongs to
			Stage targetStage = null;
			if (file.contains("login")) {
				if (loginStage == null)
					loginStage = new Stage();
				targetStage = loginStage;
			} else if (file.contains("signup")) {
				if (signupStage == null)
					signupStage = new Stage();
				targetStage = signupStage;
			} else if (file.contains("taskmanager")) {
				if (taskStage == null)
					taskStage = new Stage();
				targetStage = taskStage;
			}

			// Load scene into target stage
			targetStage.setTitle(title);
			targetStage.setScene(new Scene(root));
			targetStage.show();

			// Hide current stage (but only if it's not the same as target)
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			if (currentStage != targetStage) {
				currentStage.hide();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void signupUser(ActionEvent event, String username, String email, String password) {
		Connection conn = null;
		PreparedStatement psCheckUserExists = null;
		PreparedStatement psInsert = null;
		ResultSet result = null;

		try {
			conn = dbconnection.doObj();
			psCheckUserExists = conn.prepareStatement("SELECT * FROM users101 WHERE username = ? or email = ?");
			psCheckUserExists.setString(1, username);
			psCheckUserExists.setString(2, email);
			result = psCheckUserExists.executeQuery();

			if (result.isBeforeFirst()) {
				System.out.println("User already exists");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("User already exists");
				alert.show();
			} else {
				psInsert = conn.prepareStatement("INSERT INTO users101 (username, email, password) VALUES (?, ?, ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, email);
				psInsert.setString(3, password);
				psInsert.executeUpdate();

				psCheckUserExists = conn.prepareStatement("SELECT * FROM users101 WHERE username = ? or email = ?");
				psCheckUserExists.setString(1, username);
				psCheckUserExists.setString(2, email);
				result = psCheckUserExists.executeQuery();

				int currentUser = -1;

				while (result.next()) {

					currentUser = result.getInt("user_id");
				}

				System.out.println("User registered.");
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("User was registered.");
				alert.show();
				changeScene(event, "/view/taskmanager.fxml", "Task Manager", username, currentUser);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (psCheckUserExists != null) {
				try {
					psCheckUserExists.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (psInsert != null) {
				try {
					psInsert.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void loginUser(ActionEvent event, String username, String email, String password) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet result = null;

		try {
			conn = dbconnection.doObj();
			prep = conn.prepareStatement("SELECT * FROM users101 WHERE username = ? OR email = ?");
			prep.setString(1, username);
			prep.setString(2, email);
			result = prep.executeQuery();
			if (!result.isBeforeFirst()) {
				System.out.println("User doesnt exist");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("User doesnt exist.");
				alert.show();
				return;
			} else {
				while (result.next()) {
					String pass = result.getString("password");
					int currentUser = result.getInt("user_id");

					if (pass.equals(password)) {
						changeScene(event, "/view/taskmanager.fxml", "Task Manager", username, currentUser);
					} else {
						System.out.println("Password didnt match");
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Invalid password");
						alert.show();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (prep != null) {
				try {
					prep.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * public void getUserId(String user, String password) {
	 * 
	 * ResultSet rs; try { Connection conn = dbconnection.doObj(); String sql =
	 * "SELECT * FROM users101 WHERE username = ? AND password = ?";
	 * PreparedStatement pst = conn.prepareStatement(sql); pst.setString(1, user);
	 * pst.setString(2, password); rs = pst.executeQuery(); while (rs.next()) { int
	 * userId = rs.getInt("user_id");
	 * 
	 * // Load task manager and pass the userId FXMLLoader loader = new
	 * FXMLLoader(getClass().getResource("/view/taskmanager.fxml"));
	 * 
	 * Parent root = loader.load(); taskmanagercon controller =
	 * loader.getController(); controller.setUserid(userId); } } catch (Exception e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
}
