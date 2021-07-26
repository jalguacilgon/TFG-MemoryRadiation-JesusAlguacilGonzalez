package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;


public class LineChartSimple extends Application {
	@Override
	public void start(Stage primaryStage) {
		init(primaryStage);
	}
	
	private void init(Stage primaryStage) {
		// TODO Auto-generated method stub
		HBox root = new HBox();
		Scene scene = new Scene(root, 450, 330);
		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Time");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("False MBUs");
		
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Memory Radiation");
		
		XYChart.Series<Number, Number> data = new XYChart.Series<>();
		data.setName("False MBUs / Time");
		
		for(int t = 1; t <= 10; ++t) {
			data.getData().add(new XYChart.Data<Number, Number>(t, t*100));
		}
		
		lineChart.getData().add(data);
		root.getChildren().add(lineChart);
		
		primaryStage.setTitle("LineChart TFG");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
