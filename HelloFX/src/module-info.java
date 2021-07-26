module HelloFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens linechart to javafx.graphics, javafx.fxml;
	opens form to javafx.graphics, javafx.fxml;
}
