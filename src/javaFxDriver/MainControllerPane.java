package javaFxDriver;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
	//private AlgParamController apc;
	private boolean timerIsRunning = false;
	//private PopParamControl ppc;
	private AccordionContainer accordionPane;
	private Slider numPopSlider = new Slider(1, 10, 1);
	private PopulationDriver basic2D;
	private PopulationDriver basic3D;
	
	public MainControllerPane (PopulationManager popMngr, AnimationTimer timer, BorderPane activeGraphicsPane) {
		//this.popMngr = popMngr;
		this.accordionPane = new AccordionContainer(popMngr);
		
		//this.apc = new AlgParamController(popMngr);
		//this.ppc = new PopParamControl(popMngr);
		this.mainPane.setMinWidth(250);
		
		//---2D / 3D RADIO BUTTON---//
		ToggleGroup radioButtons = new ToggleGroup();
				
		RadioButton rb2D = new RadioButton("2D");
		rb2D.setToggleGroup(radioButtons);
		rb2D.setSelected(true);

		RadioButton rb3D = new RadioButton("3D");
		rb3D.setToggleGroup(radioButtons);
		
		/*
		radioButtons.selectedToggleProperty().addListener(
			(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
		});
		*/
		
		//---BUILD POPULATION DRIVERS == TEMP!!---//
		int[] searchSpace = new int[]{900, 675, 255, 255, 255};
		int[] initGoal = new int[]{255, 255, 255, 255, 255};
		
		Button buildButton = new Button("BUILD SCENE");
		buildButton.setOnMouseClicked((MouseEvent e) -> {
			/*
			 * TODO: 
			 * 		*Build accordian panel
			 * 		*Init population
			 * 		*Render initial frame
			 */
			popMngr.clearPopulations();
			if (radioButtons.getSelectedToggle() == rb2D) {
				basic2D = new Basic2dDriver(searchSpace, initGoal, (int) numPopSlider.getValue());
				popMngr.addDriver("basic2D", basic2D);
				popMngr.setActiveDriver("basic2D");
			}
			else {
				basic3D = new Basic3dDriver(searchSpace, initGoal, (int) numPopSlider.getValue());
				popMngr.addDriver("basic3D", basic3D);
				popMngr.setActiveDriver("basic3D");
			}
			accordionPane.rebuildAccordion((int) numPopSlider.getValue());
		});
		
		//---START / STOP BUTTON---//
		Button startStop = new Button("START");
		startStop.setOnMouseClicked((MouseEvent e) -> {
			if (!this.timerIsRunning) {
				startStop.setText("STOP");
				activeGraphicsPane.setCenter(popMngr.getActiveDriver().getUiNode());
				timer.start();
				this.timerIsRunning = true;
			}
			else {
				startStop.setText("START");
				timer.stop();
				this.timerIsRunning = false;
			}
		});
		
		this.basicOptPane.add(buildButton, 0, 0);
		this.basicOptPane.add(startStop, 2, 0);
		
		this.basicOptPane.add(new Label("NumDims: "), 0, 1);
		this.basicOptPane.add(rb2D, 1, 1);
		this.basicOptPane.add(rb3D, 2, 1);
		
		
		Label numPopLabel = new Label("# Populations: ");
		Label numPopValue = new Label("1");
		numPopSlider.setBlockIncrement(1);
		numPopSlider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				numPopValue.setText(String.format("%d", (int) new_val.intValue()));
		});
		this.basicOptPane.add(numPopLabel, 0, 2);
		this.basicOptPane.add(numPopSlider, 1, 2);
		this.basicOptPane.add(numPopValue, 2, 2);
		
		Label activePopLabel = new Label("Select Population");
		
		
		mainPane.getChildren().add(this.basicOptPane);
		
		mainPane.getChildren().add(this.accordionPane.getPane());
		
		//====EACH SWARM SHOULD HAVE THEIR OWN CONTROLS===//
		///...create an accordion for each
		//mainPane.getChildren().add(this.apc.getPane());
		//mainPane.getChildren().add(ppc.getPane());
	}
	
	public Pane getPane () {
		return this.mainPane;
	}

}
