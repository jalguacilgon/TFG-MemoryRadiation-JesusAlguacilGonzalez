package linechart;
	
import java.io.IOException;

import form.MethodSelection;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.SceneController;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Memory extends Application {
	
	private int size;
	private int D;
	private MethodSelection method;
	
	private double [] errors;
	private int nBitflips = 2000;
	
	
	@Override
	public void start(Stage primaryStage) {
		init(primaryStage);
	}
	
	private void init(Stage primaryStage) {
		// TODO Auto-generated method stub		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Number of Bitflips");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("False MCUs");
		
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Memory Radiation");
		
		XYChart.Series<Number, Number> data = new XYChart.Series<>();
		data.setName("False MBUs / Bitflip");
		
		if(this.method == MethodSelection.MD)
			this.calculateMD(data);
		else
			this.calculateIND(data);
		
		lineChart.getData().add(data);
        
        StackPane spLineChart = new StackPane();
        spLineChart.getChildren().add(lineChart);

        Button button = new Button("New search");
        button.setOnMouseClicked((event)->{
            SceneController sc = new SceneController();
            try {
				sc.switchToForm(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        button.setStyle("-fx-text-fill: white;-fx-background-color: #f3802d;-fx-cursor: hand;");
        Font font = new Font("Cantara", 15);
        button.setFont(font);
        StackPane spButton = new StackPane();
        spButton.getChildren().add(button);

        VBox vbox = new VBox();
        VBox.setVgrow(spLineChart, Priority.ALWAYS);
        vbox.getChildren().addAll(spButton, spLineChart);

        HBox.setHgrow(spLineChart, Priority.ALWAYS);
        VBox.setVgrow(spLineChart, Priority.ALWAYS);

        Scene scene = new Scene(vbox);
		
		primaryStage.setTitle("Memory Radiation");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	private void calculateMD(XYChart.Series<Number, Number> data) {
		this.errors = new double [this.nBitflips + 1];
		double Np, Nbf, Nfm2;
		for(int i = 0; i <= this.nBitflips; ++i) {
			Nbf = i;
			Np =  (Nbf * (Nbf - 1))/2;
			Nfm2 = Math.pow(this.size, -1) * Np * 2 * this.D * (this.D + 1);
			this.errors[i] = 1 - Math.pow(Math.E, -Nfm2);
			System.out.println(i + ": Nbf: " + Nbf + ", Np: " + Np + ", Nfm2: " + Nfm2);
			data.getData().add(new XYChart.Data<Number, Number>(i, this.errors[i]));
		}
	}
	
	private void calculateIND(XYChart.Series<Number, Number> data) {
		this.errors = new double [this.nBitflips + 1];
		double Np, Nbf, Nfm2;
		for(int i = 1; i <= this.nBitflips; ++i) {
			Nbf = i;
			Np =  (Nbf * (Nbf - 1))/2;
			Nfm2 = Math.pow(this.size, -1) * Np * 4 * this.D * (this.D + 1);
			this.errors[i] = 1 - Math.pow(Math.E, -Nfm2);
			System.out.println(i + ": Nbf: " + Nbf + ", Np: " + Np + ", Nfm2: " + Nfm2);
			data.getData().add(new XYChart.Data<Number, Number>(i, this.errors[i]));
		}
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setD(int d) {
		D = d;
	}

	public void setMethod(MethodSelection method) {
		this.method = method;
	}
}
