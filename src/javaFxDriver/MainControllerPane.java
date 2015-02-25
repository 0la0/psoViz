package javaFxDriver;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;

public class MainControllerPane {
	
	//private PopulationManager popMngr;
	private VBox mainPane = new VBox();
	private GridPane basicOptPane = new GridPane();
	private AlgParamController apc;
	
	public MainControllerPane (PopulationManager popMngr, AnimationTimer timer) {
		//this.popMngr = popMngr;
		this.apc = new AlgParamController(popMngr);
		
		this.mainPane.setMinWidth(250);
		
		
		Button startButton = new Button("START");
		startButton.setOnMouseClicked((MouseEvent e) -> {
			timer.start();
		});
		Button stopButton = new Button("STOP");
		stopButton.setOnMouseClicked((MouseEvent e) -> {
			timer.stop();
		});
		
		this.basicOptPane.add(startButton, 0, 0);
		this.basicOptPane.add(stopButton, 1, 0);
		
		mainPane.getChildren().add(this.basicOptPane);
		mainPane.getChildren().add(this.apc.getPane());
	}
	
	public Pane getPane () {
		return this.mainPane;
	}

}
