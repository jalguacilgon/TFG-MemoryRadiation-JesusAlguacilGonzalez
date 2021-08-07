package form;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormFX extends Application {
	

	@Override
	public void start(Stage primaryStage) {
		try {
			URL fmxlocation = getClass().getResource("/resources/FormDarkMode.fxml");
			Parent root = FXMLLoader.load(fmxlocation);
			Scene scene = new Scene(root);
			String css = getClass().getResource("/resources/FormDarkMode.css").toExternalForm();
			scene.getStylesheets().add(css);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
