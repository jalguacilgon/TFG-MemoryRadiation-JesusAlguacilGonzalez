module HelloFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens linechart to javafx.graphics, javafx.fxml;
	opens form to javafx.graphics, javafx.fxml;
	opens resources to javafx.graphics, javafx.fxml;
}
