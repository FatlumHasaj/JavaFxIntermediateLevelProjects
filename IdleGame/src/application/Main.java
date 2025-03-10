package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Ensure the path matches your project structure
            Parent root = FXMLLoader.load(getClass().getResource("/view/IdleGame.fxml"));
            Scene scene = new Scene(root, 640, 400);
            primaryStage.setTitle("Idle Clicker Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
