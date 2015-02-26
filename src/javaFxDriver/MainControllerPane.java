package javaFxDriver;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;

public class MainControllerPane {
	
	//private PopulationManager popMngr;
	private VBox mainPane = new VBox();
	private GridPane basicOptPane = new GridPane();
	private AlgParamController apc;
	private boolean timerIsRunning = false;
	
	public MainControllerPane (PopulationManager popMngr, AnimationTimer timer, BorderPane activeGraphicsPane) {
		//this.popMngr = popMngr;
		this.apc = new AlgParamController(popMngr);
		
		this.mainPane.setMinWidth(250);
		
		//---2D / 3D RADIO BUTTON---//
		ToggleGroup radioButtons = new ToggleGroup();
				
		RadioButton rb2D = new RadioButton("2D");
		rb2D.setToggleGroup(radioButtons);
		rb2D.setSelected(true);

		RadioButton rb3D = new RadioButton("3D");
		rb3D.setToggleGroup(radioButtons);
		
		radioButtons.selectedToggleProperty().addListener(
			(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
				
		});
		
		
		
		//---START / STOP BUTTON---//
		Button startStop = new Button("START");
		startStop.setOnMouseClicked((MouseEvent e) -> {
			if (!this.timerIsRunning) {
				startStop.setText("STOP");
				if (radioButtons.getSelectedToggle() == rb2D) {
					popMngr.setActiveDriver("basic2D");
				}
				else {
					popMngr.setActiveDriver("basic3D");
				}
				activeGraphicsPane.setCenter(popMngr.getActiveDriver().getUiNode());
				timer.start();
				this.timerIsRunning = true;
				//System.out.println(radioButtons.getSelectedToggle());
			}
			else {
				startStop.setText("START");
				timer.stop();
				this.timerIsRunning = false;
			}
		});
		this.basicOptPane.add(startStop, 0, 0);
		
		this.basicOptPane.add(new Label("NumDims: "), 0, 1);
		this.basicOptPane.add(rb2D, 1, 1);
		this.basicOptPane.add(rb3D, 2, 1);
		
		
		mainPane.getChildren().add(this.basicOptPane);
		mainPane.getChildren().add(this.apc.getPane());
	}
	
	public Pane getPane () {
		return this.mainPane;
	}

}
