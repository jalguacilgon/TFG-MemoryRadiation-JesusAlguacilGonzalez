package resources;

import java.net.URL;
import java.util.ResourceBundle;

import form.MethodSelection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import linechart.Memory;

public class SceneController implements Initializable{

	private Stage stage;
	
	@FXML
	private TextField fluxTextField;
	@FXML
	private TextField sensitivityTextField;
	@FXML
	private TextField sizeTextField;
	@FXML
	private TextField distanceTextField;
	
	@FXML
	private ChoiceBox<MethodSelection> dropdown;
	private MethodSelection[] method = {MethodSelection.MD, MethodSelection.IND};
	
	
	@FXML
	private Label fluxError;
	@FXML
	private Label sensitivityError;
	@FXML
	private Label sizeError;
	@FXML
	private Label distanceError;
	
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
		}catch (NumberFormatException e) {
			fluxError.setText("Please enter a number");
			dataOk = false;
		}
		
		try {
			mem.setSensitivity(Double.parseDouble(sensitivityTextField.getText()));
		}catch (NumberFormatException e) {
			sensitivityError.setText("Please enter a number");
			dataOk = false;
		}
		
		try {
			mem.setSize(Integer.parseInt(sizeTextField.getText()));
		}catch (NumberFormatException e) {
			sizeError.setText("Please enter a number");
			dataOk = false;
		}
		
		try {
			mem.setD(Integer.parseInt(distanceTextField.getText()));
		}catch (NumberFormatException e) {
			distanceError.setText("Please enter a number");
			dataOk = false;
		}
		
		mem.setMethod(dropdown.getValue());
		
		if(dataOk)
			mem.start(stage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dropdown.getItems().addAll(method);
		dropdown.setValue(method[0]);
	}
}
