package linechart;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Memory extends Application {
	
	private double flux;
	private double sensitivity;
	private int size;
	private int D;
	
	
	
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
		
		
		data.getData().add(new XYChart.Data<Number, Number>(this.flux, this.D));
		data.getData().add(new XYChart.Data<Number, Number>(this.sensitivity, this.flux));
		data.getData().add(new XYChart.Data<Number, Number>(this.size, this.sensitivity));
		data.getData().add(new XYChart.Data<Number, Number>(this.D, this.size));
		
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

	public void setFlux(double flux) {
		this.flux = flux;
	}

	public void setSensitivity(double sensitivity) {
		this.sensitivity = sensitivity;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setD(int d) {
		D = d;
	}
}
