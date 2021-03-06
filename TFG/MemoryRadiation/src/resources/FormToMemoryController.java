package resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import form.MethodSelection;
import form.SizeSelection;
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
	private ChoiceBox<SizeSelection> sizeChoice;
	private SizeSelection[] sizeMultipliers = {SizeSelection.BITS, SizeSelection.KBITS, SizeSelection.MBITS};
	@FXML
	private CheckBox checkbox;	
	@FXML
	private Label sizeError;
	@FXML
	private Label distanceError;
	
	/**
	 * Retrieves the data from the form and creates a {@code Memory} object to display the results
	 * 
	 * @param event The event that triggered this action
	 */
	public void switchToGraph(ActionEvent event) {
		boolean dataOk = true;
		sizeError.setText(null);
		distanceError.setText(null);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Memory mem = new Memory();
		
		try {
			mem.setSize(Integer.parseInt(sizeTextField.getText()), sizeChoice.getValue());
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
		mem.setMultiplier(sizeChoice.getValue());
		mem.setGenerateUntilMax(checkbox.isSelected());
		
		if(dataOk)
			mem.start(stage);
	}
	
	/**
	 * Switches the scene back to the form and fills the fields with the previous memory specifications
	 * 
	 * @param event The event that triggered this action
	 * @param mem {@code Memory} object from which will fill the form's fields
	 * @throws IOException If the {@code FXML} file provided was not found
	 */
	public void switchToForm(MouseEvent event, Memory mem) throws IOException {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		URL fxmlocation = getClass().getResource("/resources/Form.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlocation);
		
		Parent root = loader.load();
		Scene scene = new Scene(root);
		String css = getClass().getResource("/resources/Form.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		FormToMemoryController controller = loader.getController();
		controller.setMemorySize(Integer.toString(mem.getMemorySize()/mem.getMultiplier().getMultiplier()));
		controller.setDistance(Integer.toString(mem.getD()));
		controller.setMethod(mem.getMethod());
		controller.setSizeMultiplier(mem.getMultiplier());
		controller.setGeneration(mem.isGenerateUntilMax());
		
		stage.setTitle("False Multiple Cell Upsets (MCUs) estimator");
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dropdown.getItems().addAll(method);
		sizeChoice.getItems().addAll(sizeMultipliers);
	}

	public void setMemorySize(String memorySize) {
		this.sizeTextField.setText(memorySize);
	}

	public void setDistance(String distance) {
		this.distanceTextField.setText(distance);
	}

	public void setMethod(MethodSelection method) {
		this.dropdown.setValue(method);
	}
	
	public void setSizeMultiplier(SizeSelection sizeMultiplier) {
		this.sizeChoice.setValue(sizeMultiplier);
	}

	public void setGeneration(boolean isChecked) {
		this.checkbox.setSelected(isChecked);
	}

	
}
