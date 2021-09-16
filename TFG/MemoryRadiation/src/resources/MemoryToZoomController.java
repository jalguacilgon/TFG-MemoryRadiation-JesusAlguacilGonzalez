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

public class MemoryToZoomController {
	
		@FXML
		private TextField lowZoomTextField;
		@FXML
		private TextField highZoomTextField;
		
		@FXML
		private Label zoomError;
		
		private int lowZoom;
		private int highZoom;
		
		public void switchToZoom(MouseEvent event, Memory mem) throws IOException {
			Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			URL fmxlocation = getClass().getResource("/resources/Zoom.fxml");
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
		
		public void zoomGraph(ActionEvent event) {
			boolean dataOk = true;
			zoomError.setText(null);
			
			try {
				this.lowZoom = Integer.parseInt(this.lowZoomTextField.getText());
				this.highZoom = Integer.parseInt(this.highZoomTextField.getText());
			}catch(NumberFormatException e) {
				this.zoomError.setText("Please introduce a valid number");
				dataOk = false;
			}
			
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Memory mem = (Memory) stage.getUserData();
			
			if(dataOk) {
				if(this.lowZoom >= this.highZoom || this.lowZoom < 0 || this.highZoom >= mem.getErrors().size()) {
					this.zoomError.setText("Please introduce a valid range");
					dataOk = false;
				}
				else {
					mem.linechartZoomIn(this.lowZoom, this.highZoom);
				}
			}
			
			if(dataOk)
				stage.close();			
		}
}
