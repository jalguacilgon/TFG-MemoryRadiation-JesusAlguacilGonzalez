module HelloFX {
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires transitive javafx.base;
	
	opens linechart;
	opens form;
	opens resources;
	
	exports linechart;
	exports form;
	exports resources;
}
