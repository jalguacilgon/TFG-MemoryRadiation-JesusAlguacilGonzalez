package resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import form.MethodSelection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import linechart.Memory;

public class FormToMemoryController implements Initializable{
	
	// Form parameters	
	@FXML
	private TextField sizeTextField;
	@FXML
	private TextField distanceTextField;	
	@FXML
	private ChoiceBox<MethodSelection> dropdown;
	private MethodSelection[] method = {MethodSelection.MD, MethodSelection.IND};	
	@FXML
	private CheckBox checkbox;	
	@FXML
	private Label sizeError;
	@FXML
	private Label distanceError;
	
	public void switchToGraph(ActionEvent event) {
		boolean dataOk = true;
		sizeError.setText(null);
		distanceError.setText(null);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Memory mem = new Memory();
		
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
		mem.setGenerateUntilMax(checkbox.isSelected());
		
		if(dataOk)
			mem.start(stage);
	}
	
	public void switchToForm(MouseEvent event) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		URL fmxlocation = getClass().getResource("/resources/Form.fxml");
		Parent root = FXMLLoader.load(fmxlocation);
		Scene scene = new Scene(root);
		String css = getClass().getResource("/resources/Form.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dropdown.getItems().addAll(method);
		dropdown.setValue(method[0]);
	}
}