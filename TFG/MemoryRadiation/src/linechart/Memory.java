package linechart;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import form.MethodSelection;
import form.SizeSelection;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import resources.FormToMemoryController;
import resources.MemoryToLookupController;
import resources.MemoryToZoomController;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Memory extends Application {
	
	private int memorySize;
	private SizeSelection multiplier;
	private int D;
	private MethodSelection method;
	private boolean generateUntilMax;
	
	private List<Double> errors;
	private int nBitflips = 2000;
	private XYChart.Series<Number, Number> data = new XYChart.Series<>();
	LineChart<Number, Number> lineChart;
	
	private boolean isZoomed = false;
	
	/**
	 * Initializes the graph and buttons to display, then it calculates the data and adds it to the chart.
	 * Lastly, it shows the scene with all the content that has been generated.
	 * 
	 * Must be used after setting the parameters retrieved by the form.
	 * 
	 * @param primaryStage the stage which will be used to display the linechart
	 */
	@Override
	public void start(Stage primaryStage) {
		init(primaryStage);
	}
	
	/**
	 * Initializes the graph and buttons to display, then it calculates the data and adds it to the chart.
	 * Lastly, it shows the scene with all the content that has been generated.
	 * 
	 * Must be used after setting the parameters retrieved by the form.
	 * 
	 * @param primaryStage the stage which will be used to display the linechart
	 */	
	private void init(Stage primaryStage) {
		// TODO Auto-generated method stub		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Number of Bitflips");
		xAxis.setForceZeroInRange(false);
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Probability of observing false MCUs");
		yAxis.setForceZeroInRange(false);
		
		this.lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		this.lineChart.setTitle("Size: " + this.memorySize + " bits   Distance: " + this.D + "   Method: " + this.method);
		
		this.data.setName("Probability of False MCUs / Bitflip");
		
		if(this.method == MethodSelection.MD)
			this.calculateMD(this.data);
		else
			this.calculateIND(this.data);
		
		this.lineChart.getData().add(this.data);
		
		addTooltips(this.data);
		
		this.lineChart.getStyleClass().add("thick-chart");
        
        StackPane spLineChart = new StackPane();
        spLineChart.getChildren().add(this.lineChart);
        
        Font font = new Font("Candara", 15);

        Button formButton = new Button("New search");
        formButton.setOnMouseClicked((event)->{
            FormToMemoryController controller = new FormToMemoryController();
            try {
            	controller.switchToForm(event, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        formButton.getStyleClass().add("button-menu");
        formButton.setFont(font);
        
        Button windowButton = new Button("Lookup desired result");
        windowButton.setOnMouseClicked((event)->{
            MemoryToLookupController controller = new MemoryToLookupController();
            try {
            	controller.switchToWindow(event, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        windowButton.getStyleClass().add("button-menu");
        windowButton.setFont(font);
        
        Button zoomButton = new Button("Zoom in");
        zoomButton.setOnMouseClicked((event)->{
        	MemoryToZoomController controller = new MemoryToZoomController();
        	try {
            	controller.switchToZoom(event, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
        zoomButton.getStyleClass().add("button-menu");
        zoomButton.setFont(font);
        
        Button resetButton = new Button("Reset Zoom");
        resetButton.setOnMouseClicked((event)->{
        	this.linechartResetZoom();
        });
        resetButton.getStyleClass().add("button-menu");
        resetButton.setFont(font);
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(formButton, windowButton, zoomButton, resetButton);
        hbox.setAlignment(Pos.CENTER);
        
        StackPane spButton = new StackPane();
        spButton.getChildren().add(hbox);

        VBox vbox = new VBox();
        VBox.setVgrow(spLineChart, Priority.ALWAYS);
        vbox.getChildren().addAll(spButton, spLineChart);

        HBox.setHgrow(spLineChart, Priority.ALWAYS);
        VBox.setVgrow(spLineChart, Priority.ALWAYS);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("/resources/Linechart.css");
		
		primaryStage.setTitle("False Multiple Cell Upsets (MCUs) estimator");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	
	/**
	 * Calculates the expected results using the Manhattan Distance (MD) method.
	 * 
	 * @param data XYChart Series in which the data will be stored
	 */
	private void calculateMD(XYChart.Series<Number, Number> data) {
		this.errors = new ArrayList<Double>();
		double Np, Nbf, Nfm2;
		int i = 0;
		double currentError = 0;
		
		if(this.generateUntilMax) {			
			while(currentError < 0.99) {
				Nbf = i;
				Np =  (Nbf * (Nbf - 1))/2;
				Nfm2 = Math.pow(this.memorySize, -1) * Np * 2 * this.D * (this.D + 1);
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
				Nfm2 = Math.pow(this.memorySize, -1) * Np * 2 * this.D * (this.D + 1);
				this.errors.add(1 - Math.pow(Math.E, -Nfm2));
				data.getData().add(new XYChart.Data<Number, Number>(i, this.errors.get(i)));
			}
		}
		
	}
	
	/**
	 * Calculates the expected results using the Infinity Norm Distance (MD) method.
	 * 
	 * @param data XYChart Series in which the data will be stored
	 */	
	private void calculateIND(XYChart.Series<Number, Number> data) {
		this.errors = new ArrayList<Double>();
		double Np, Nbf, Nfm2;
		
		int i = 0;
		double currentError = 0;
		
		if(this.generateUntilMax) {
			while(currentError < 0.99) {
				Nbf = i;
				Np =  (Nbf * (Nbf - 1))/2;
				Nfm2 = Math.pow(this.memorySize, -1) * Np * 4 * this.D * (this.D + 1);
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
				Nfm2 = Math.pow(this.memorySize, -1) * Np * 4 * this.D * (this.D + 1);
				currentError = 1 - Math.pow(Math.E, -Nfm2);
				this.errors.add(currentError);
				data.getData().add(new XYChart.Data<Number, Number>(i, currentError));
			}
		}
		
	}
	
	/**
	 * Returns the expected number of bitflips required to obtain a certain probability of false MCUs appearance.
	 * 
	 * @param desiredFalseMCUs The probability of false MCUs appearance
	 * @return Expected number of bitflips to obtain desired probability in {@code String} format
	 */
	public String findDesiredFalseMCUs(double desiredFalseMCUs) {
		for (int i = 0; i < this.errors.size() ; ++i) {
			if(this.errors.get(i) >= desiredFalseMCUs)
				return "" + i;
		}
		return "Could not find expected result";
	}
	
	/**
	 * Zooms in the chart between the values provided
	 * 
	 * @param low Lower value of the range
	 * @param high Higher value of the range
	 */
	
	public void linechartZoomIn(int low, int high) {
		XYChart.Series<Number, Number> zoomedData = new XYChart.Series<Number, Number>();
		for(int i = low; i <= high; ++i) {
			zoomedData.getData().add(new XYChart.Data<Number, Number>(i, this.errors.get(i)));
		}
		this.lineChart.getData().clear();
		this.lineChart.getData().add(zoomedData);
		zoomedData.setName("Probability of False MCUs / Bitflip");
		addTooltips(zoomedData);
		this.isZoomed = true;
	}
	
	/**
	 * Resets zoom on the chart
	 */
	public void linechartResetZoom() {
		if(this.isZoomed) {
			this.lineChart.getData().clear();
			this.lineChart.getData().add(this.data);
			this.isZoomed = false;
		}
	}
	
	private void addTooltips(XYChart.Series<Number, Number> series) {
		for (XYChart.Data<Number, Number> entry : series.getData()) {
            Tooltip t = new Tooltip("p: " + (double)Math.round(entry.getYValue().doubleValue() * 1000d) / 1000d + " / b: " + entry.getXValue().toString());
            t.getStyleClass().add("tooltip-node");
            Tooltip.install(entry.getNode(), t);
		}
	}

	public void setSize(int size, SizeSelection sizeMultiplier) {
		this.memorySize = size*sizeMultiplier.getMultiplier();
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

	public List<Double> getErrors() {
		return errors;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public int getD() {
		return D;
	}

	public MethodSelection getMethod() {
		return method;
	}

	public boolean isGenerateUntilMax() {
		return generateUntilMax;
	}

	public SizeSelection getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(SizeSelection multiplier) {
		this.multiplier = multiplier;
	}
}
