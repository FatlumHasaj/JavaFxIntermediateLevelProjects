module Login2 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	
	
	exports controllers; // Add this line to export the controllers package to other modules, including javafx.fxml
    opens controllers to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
}
