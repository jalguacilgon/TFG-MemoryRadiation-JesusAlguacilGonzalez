package form;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.FormToMemoryController;

public class FormFX extends Application {
	
	/**
	 * Displays the form and fills it with default values
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			URL fxmlocation = getClass().getResource("/resources/Form.fxml");
			FXMLLoader loader = new FXMLLoader(fxmlocation);
			
			Parent root = loader.load();
			Scene scene = new Scene(root);
			String css = getClass().getResource("/resources/Form.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			FormToMemoryController controller = loader.getController();
			controller.setMethod(MethodSelection.MD);
			controller.setSizeMultiplier(SizeSelection.MBITS);
			
			primaryStage.setTitle("False Multiple Cell Upsets (MCUs) estimator");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Main function that opens the form
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
