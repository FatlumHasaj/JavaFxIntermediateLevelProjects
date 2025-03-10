module IdleGame {
	requires javafx.controls;
    requires javafx.fxml;
    
    opens controller to javafx.fxml; // Allow JavaFX to access the controller package
    exports application; //
	opens application to javafx.graphics, javafx.fxml;
}
