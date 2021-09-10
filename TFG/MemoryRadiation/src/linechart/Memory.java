package linechart;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import form.MethodSelection;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import resources.FormToMemoryController;
import resources.MemoryToWindowController;
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
	private boolean generateUntilMax;
	
	private List<Double> errors;
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
		yAxis.setLabel("Probability of observing false MCUs");
		
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Size: " + this.size + " bits   Distance: " + this.D);
		
		XYChart.Series<Number, Number> data = new XYChart.Series<>();
		data.setName("Probability of False MCUs / Bitflip");
		
		if(this.method == MethodSelection.MD)
			this.calculateMD(data);
		else
			this.calculateIND(data);
		
		lineChart.getData().add(data);
		
        
        StackPane spLineChart = new StackPane();
        spLineChart.getChildren().add(lineChart);

        Button formButton = new Button("New search");
        formButton.setOnMouseClicked((event)->{
            FormToMemoryController controller = new FormToMemoryController();
            try {
            	controller.switchToForm(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        formButton.setStyle("-fx-text-fill: white;-fx-background-color: #f37e53;-fx-cursor: hand; -fx-border-color: #707070;");
        Font font = new Font("Candara", 15);
        formButton.setFont(font);
        
        Button windowButton = new Button("Lookup desired result");
        windowButton.setOnMouseClicked((event)->{
            MemoryToWindowController controller = new MemoryToWindowController();
            try {
            	controller.switchToWindow(event, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        windowButton.setStyle("-fx-text-fill: white;-fx-background-color: #f37e53;-fx-cursor: hand; -fx-border-color: #707070;");
        windowButton.setFont(font);
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(formButton, windowButton);
        hbox.setAlignment(Pos.CENTER);
        
        StackPane spButton = new StackPane();
        spButton.getChildren().add(hbox);

        VBox vbox = new VBox();
        VBox.setVgrow(spLineChart, Priority.ALWAYS);
        vbox.getChildren().addAll(spButton, spLineChart);

        HBox.setHgrow(spLineChart, Priority.ALWAYS);
        VBox.setVgrow(spLineChart, Priority.ALWAYS);

        Scene scene = new Scene(vbox);
		
		primaryStage.setTitle("False Multiple Cell Upsets (MCUs) estimator");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	private void calculateMD(XYChart.Series<Number, Number> data) {
		this.errors = new ArrayList<Double>();
		double Np, Nbf, Nfm2;
		int i = 0;
		double currentError = 0;
		
		if(this.generateUntilMax) {			
			while(currentError < 0.99) {
				Nbf = i;
				Np =  (Nbf * (Nbf - 1))/2;
				Nfm2 = Math.pow(this.size, -1) * Np * 2 * this.D * (this.D + 1);
				currentError = 1 - Math.pow(Math.E, -Nfm2);
				this.errors.add(currentError);
				data.getData().add(new XYChart.Data<Number, Number>(i, currentError));
				++i;
			}
			
		}
		else {
			for(i = 0; i <= this.nBitflips; ++i) {
				Nbf = i;
				Np =  (Nbf * (Nbf - 1))/2;
				Nfm2 = Math.pow(this.size, -1) * Np * 2 * this.D * (this.D + 1);
				this.errors.add(1 - Math.pow(Math.E, -Nfm2));
				data.getData().add(new XYChart.Data<Number, Number>(i, this.errors.get(i)));
			}
		}
		
	}
	
	private void calculateIND(XYChart.Series<Number, Number> data) {
		this.errors = new ArrayList<Double>();
		double Np, Nbf, Nfm2;
		
		int i = 0;
		double currentError = 0;
		
		if(this.generateUntilMax) {
			while(currentError < 0.99) {
				Nbf = i;
				Np =  (Nbf * (Nbf - 1))/2;
				Nfm2 = Math.pow(this.size, -1) * Np * 4 * this.D * (this.D + 1);
				currentError = 1 - Math.pow(Math.E, -Nfm2);
				this.errors.add(currentError);
				data.getData().add(new XYChart.Data<Number, Number>(i, currentError));
				++i;
			}
		}
		else {
			for(i = 0; i <= this.nBitflips; ++i) {
				Nbf = i;
				Np =  (Nbf * (Nbf - 1))/2;
				Nfm2 = Math.pow(this.size, -1) * Np * 4 * this.D * (this.D + 1);
				currentError = 1 - Math.pow(Math.E, -Nfm2);
				this.errors.add(currentError);
				data.getData().add(new XYChart.Data<Number, Number>(i, currentError));
			}
		}
		
	}
	
	public String findDesiredFalseMCUs(double desiredFalseMCUs) {
		for (int i = 0; i < this.errors.size() ; ++i) {
			if(this.errors.get(i) >= desiredFalseMCUs)
				return "" + i;
		}
		return "Could not find expected result";
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

	public void setGenerateUntilMax(boolean generateUntilMax) {
		this.generateUntilMax = generateUntilMax;
	}
}
