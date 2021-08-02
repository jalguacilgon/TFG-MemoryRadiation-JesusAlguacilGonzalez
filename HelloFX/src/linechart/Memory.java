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
	
	private double [] errors;
	
	
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
		
		
		this.calculateErrors(data);
		
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
	
	private void calculateErrors(XYChart.Series<Number, Number> data) {
		this.errors = new double [20];
		double Np, Nbf, Nfm2;
		for(int i = 1; i <= 20; ++i) {
			Nbf = this.flux * i * this.sensitivity;
			Np =  (Nbf * (Nbf - 1))/2;
			Nfm2 = Math.pow(this.size, -1) * Np * 2 * this.D * (this.D + 1);
			this.errors[i-1] = 1 - Math.pow(Math.E, -Nfm2);
			System.out.println(i + ": Nbf: " + Nbf + ", Np: " + Np + ", Nfm2: " + Nfm2);
			data.getData().add(new XYChart.Data<Number, Number>(i, this.errors[i-1]));
		}
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
