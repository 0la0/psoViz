package javaFxDriver;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;

/*
 * AlgParamController is the UI controller for the
 * PSO parameters of a given population
 */
public class AlgParamController {
	
	private GridPane mainPane = new GridPane();
	
	//---UI VARIABLES---//
	private Slider c1Slider = new Slider(0, 1, 0.1);
	private Slider c2Slider = new Slider(0, 1, 0.1);
	private Slider speedSlider = new Slider(0, 1, 0.1);
	
	private Label c1Label = new Label("c1");
	private Label c2Label = new Label("c2");
	private Label speedLabel = new Label("s-Lim");
		
	private Label c1Value = new Label(Double.toString(c1Slider.getValue()));
	private Label c2Value = new Label(Double.toString(c2Slider.getValue()));
	private Label speedValue = new Label(Double.toString(speedSlider.getValue()));
	
	public AlgParamController (PopulationManager popMngr, int index) {
		this.mainPane.setPadding(new Insets(10, 10, 10, 10));
		this.mainPane.setVgap(10);
		this.mainPane.setHgap(6);
		this.mainPane.setMinWidth(250);
        
		this.mainPane.add(this.c1Label, 0, 0);
		this.mainPane.add(this.c1Slider, 1, 0);
		this.mainPane.add(this.c1Value, 2, 0);
        
		this.mainPane.add(this.c2Label, 0, 1);
		this.mainPane.add(this.c2Slider, 1, 1);
		this.mainPane.add(this.c2Value, 2, 1);
        
		this.mainPane.add(this.speedLabel, 0, 2);
		this.mainPane.add(this.speedSlider, 1, 2);
		this.mainPane.add(this.speedValue, 2, 2);
       
		this.mainPane.setStyle("" +
			"-fx-background-color: #aaaadd;" +
			"-fx-border-color: #333333;" +
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);
        
		this.c1Slider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				popMngr.setC1(index, new_val.floatValue());
				this.c1Value.setText(String.format("%.3f", new_val));
		});
		this.c2Slider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				float val = (float) (new_val.floatValue() / 100.0);
				popMngr.setC2(index, val);
				this.c2Value.setText(String.format("%.3f", val));
		});
		this.speedSlider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				float val = (float) (new_val.floatValue() * 100.0);
				popMngr.setSpeedLimit(index, val);
				this.speedValue.setText(String.format("%.3f", val));
		});
	}
	
	public Pane getPane () {
		return this.mainPane;
	}

}
