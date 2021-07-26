package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.lang.Math;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		init(primaryStage);
	}
	
	private void init(Stage primaryStage) {
		// TODO Auto-generated method stub		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Time");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("False MBUs");
		
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Memory Radiation");
		
		XYChart.Series<Number, Number> data = new XYChart.Series<>();
		data.setName("False MBUs / Time");
		
		for(int t = -5; t <= 5; ++t) {
			data.getData().add(new XYChart.Data<Number, Number>(t*10, Math.pow(t, 3)));
		}
		
		lineChart.getData().add(data);
		
		VBox vb = new VBox();
        vb.setFillWidth(true);
        HBox hb = new HBox();
        hb.getChildren().add(lineChart);
        hb.setFillHeight(true);
        vb.getChildren().add(hb);

        HBox.setHgrow(lineChart, Priority.ALWAYS);
        VBox.setVgrow(hb, Priority.ALWAYS);

        Scene scene = new Scene(vb);
		
		primaryStage.setTitle("LineChart TFG");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
