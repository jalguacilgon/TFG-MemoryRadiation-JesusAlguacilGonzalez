package resources;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import linechart.Memory;

public class SceneController {

	private Stage stage;
	
	@FXML
	TextField fluxTextField;
	@FXML
	TextField sensitivityTextField;
	@FXML
	TextField sizeTextField;
	@FXML
	TextField distanceTextField;
	
	@FXML
	Label fluxError;
	@FXML
	Label sensitivityError;
	@FXML
	Label sizeError;
	@FXML
	Label distanceError;
	
	public void switchToGraph(ActionEvent event) {
		boolean dataOk = true;
		fluxError.setText(null);
		sensitivityError.setText(null);
		sizeError.setText(null);
		distanceError.setText(null);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Memory mem = new Memory();
		try {
			mem.setFlux(Double.parseDouble(fluxTextField.getText()));
		}catch (Exception e) {
			fluxError.setText("Please enter a number");
			dataOk = false;
		}
		try {
			mem.setSensitivity(Double.parseDouble(sensitivityTextField.getText()));
		}catch (Exception e) {
			sensitivityError.setText("Please enter a number");
			dataOk = false;
		}
		try {
			mem.setSize(Integer.parseInt(sizeTextField.getText()));
		}catch (Exception e) {
			sizeError.setText("Please enter a number");
			dataOk = false;
		}
		try {
			mem.setD(Integer.parseInt(distanceTextField.getText()));
		}catch (Exception e) {
			distanceError.setText("Please enter a number");
			dataOk = false;
		}
		if(dataOk)
			mem.start(stage);
	}
}
