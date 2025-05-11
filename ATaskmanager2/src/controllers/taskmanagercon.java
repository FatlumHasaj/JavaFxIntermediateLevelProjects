package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class taskmanagercon {

	@FXML
	private Button Add;

	@FXML
	private TableView<Tasks> Taskview;

	@FXML
	private TableColumn<Tasks, String> clmprogress;

	@FXML
	private TableColumn<Tasks, String> clmpriority;

	@FXML
	private TableColumn<Tasks, String> clmtask;

	@FXML
	private Button delete;

	@FXML
	private Label taskid;

	@FXML
	private TextField txtask;

	@FXML
	private TextField txtpriority;

	@FXML
	private TextField txtprogress;

	@FXML
	private Button update;

	@FXML
	private Button logout;

	ObservableList<Tasks> listoftasks = FXCollections.observableArrayList();

	private int userid;

	@SuppressWarnings("unused")
	public void initialize() throws SQLException {
		clmtask.setCellValueFactory(new PropertyValueFactory<Tasks, String>("taskName"));
		clmprogress.setCellValueFactory(new PropertyValueFactory<Tasks, String>("taskProgress"));
		clmpriority.setCellValueFactory(new PropertyValueFactory<Tasks, String>("Priority"));

		Taskview.setOnMouseClicked(event -> {
			Tasks t = Taskview.getSelectionModel().getSelectedItem();
			if (t != null) {
				txtask.setText(t.getTaskName());
				txtprogress.setText(t.getTaskProgress());
				txtpriority.setText(t.getPriority());

				taskid.setText(String.valueOf(t.getId()));
			}
		});

		populate();

		Add.setOnAction(event -> add(event));
		update.setOnAction(event -> update(event));
		delete.setOnAction(event -> delete(event));
		logout.setOnAction(event -> DBUtil.changeScene(event, "/view/login.fxml", "Login", null, 0));
	}

	// DONE: Finished the populate method
	public void populate() {

		listoftasks.clear();

		try {
			Connection con = dbconnection.doObj();
			PreparedStatement p = con.prepareStatement("SELECT * FROM tasks WHERE userid = ?");
			p.setInt(1, userid);
			ResultSet rs = p.executeQuery();

			while (rs.next()) {
				Tasks t = new Tasks(rs.getInt("taskid"), rs.getString("taskName"), rs.getString("progress"),
						rs.getString("priority"), rs.getInt("userid"));
				listoftasks.add(t);
			}
			Taskview.setItems(listoftasks);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clear() {
		txtask.clear();
		txtprogress.clear();
		txtpriority.clear();
	}

	// TODO: Finished the add method
	public void add(ActionEvent event) {

		if (txtask.getText().isEmpty() || txtprogress.getText().isEmpty() || txtpriority.getText().isEmpty()) {
			showError("All fields must be filled to add a task.");
			return;
		}

		try {
			Connection conn = dbconnection.doObj();
			PreparedStatement prepst = conn
					.prepareStatement("insert into tasks (taskName, progress, priority, userid) values (?, ?, ?, ?)");
			prepst.setString(1, txtask.getText());
			prepst.setString(2, txtprogress.getText());
			prepst.setString(3, txtpriority.getText());
			prepst.setInt(4, userid);
			System.out.println("Current user ID: " + userid);
			prepst.executeUpdate();
			populate();
			clear();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO: Finished the update method
	public void update(ActionEvent event) {

		if (taskid.getText().isEmpty()) {
			showError("No task selected to update.");
			return;
		}

		int nrId = Integer.parseInt(taskid.getText());
		try {
			Connection conn = dbconnection.doObj();
			PreparedStatement prepst = conn
					.prepareStatement("update tasks set taskName = ?, progress = ?, priority = ? where taskid = ?");
			prepst.setString(1, txtask.getText());
			prepst.setString(2, txtprogress.getText());
			prepst.setString(3, txtpriority.getText());
			prepst.setInt(4, nrId);
			prepst.executeUpdate();
			populate();
			clear();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO: Finish the delete method
	public void delete(ActionEvent event) {

		if (taskid.getText().isEmpty()) {
			showError("No task selected to delete.");
			return;
		}

		int nrId = Integer.parseInt(taskid.getText());
		try {
			Connection conn = dbconnection.doObj();
			PreparedStatement prepst = conn.prepareStatement("DELETE FROM tasks WHERE taskid = ?");
			prepst.setInt(1, nrId);
			prepst.executeUpdate();
			populate();
			clear();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method for dynamic change of user id so table populates only with tasks for
	// one users not all users
	public void setUserid(int userid) {
		this.userid = userid;
		populate();
	}

	public void showError(String msg) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText(msg);
		alert.show();
	}
}
