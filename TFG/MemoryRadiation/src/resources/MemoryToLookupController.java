package resources;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import linechart.Memory;

public class MemoryToLookupController {
	
	// Window parameters
		@FXML
		private TextField falsemcusTextField;
		@FXML
		private Label resultLabel;
		@FXML
		private Label falseMCUsError;
		private double desiredFalseMCUs;
		
		public void switchToWindow(MouseEvent event, Memory mem) throws IOException {
			Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			URL fmxlocation = getClass().getResource("/resources/Lookup.fxml");
			Parent root = FXMLLoader.load(fmxlocation);
			Scene scene = new Scene(root);
			String css = getClass().getResource("/resources/Form.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			Stage newStage = new Stage();
			newStage.setUserData(mem);
			newStage.initOwner(primaryStage);
			newStage.setTitle("False Multiple Cell Upsets (MCUs) estimator");
			newStage.setScene(scene);
			newStage.showAndWait();
		}
		
		public void calculateDesiredFalseMCUs(ActionEvent event) throws IOException {
			boolean dataOk = true;
			resultLabel.setText(null);
			falseMCUsError.setText(null);
			
			try {
				this.desiredFalseMCUs = Double.parseDouble(falsemcusTextField.getText());
			}catch(NumberFormatException e) {
				falseMCUsError.setText("Please enter a number");
				dataOk = false;
			}
			
			if(dataOk) {
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Memory mem = (Memory) stage.getUserData();
				this.resultLabel.setText(mem.findDesiredFalseMCUs(desiredFalseMCUs));
			}
			
		}

}