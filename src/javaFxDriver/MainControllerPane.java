package javaFxDriver;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;

/*
 * MainControllerPane is the left hand side of the UI 
 * that contains all the UI controllers
 */
public class MainControllerPane {
	
	private VBox mainPane = new VBox();
	private GridPane basicOptPane = new GridPane();
	private boolean timerIsRunning = false;
	private AccordionContainer accordionPane;
	private Slider numPopSlider = new Slider(1, 10, 1);
	private PopulationDriver basic2D;
	private PopulationDriver basic3D;
	private ArrayList<PopSizePane> sizePanes = new ArrayList<PopSizePane>();
	private VBox sizeParentPane = new VBox();
	private PopulationManager popMngr;
	private BorderPane activeGraphicsPane;
	
	public MainControllerPane (PopulationManager popMngr, AnimationTimer timer, BorderPane activeGraphicsPane) {
		this.popMngr = popMngr;
		this.activeGraphicsPane = activeGraphicsPane;
		
		this.accordionPane = new AccordionContainer(popMngr);
		this.mainPane.setMinWidth(250);
		
		this.sizeParentPane.setStyle("" +
			"-fx-background-color: #cccccc;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);
		
		//---2D / 3D RADIO BUTTON---//
		ToggleGroup radioButtons = new ToggleGroup();
				
		RadioButton rb2D = new RadioButton("2D");
		rb2D.setToggleGroup(radioButtons);
		rb2D.setSelected(true);

		RadioButton rb3D = new RadioButton("3D");
		rb3D.setToggleGroup(radioButtons);
		
		/*//---RADIO BUTTON CHANGE LISTENER---//
		radioButtons.selectedToggleProperty().addListener(
			(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
		});*/
		
		//---BUILD POPULATION DRIVERS == TEMP!!---//
		int[] searchSpace = new int[]{900, 675, 255, 255, 255};
		int[] initGoal = new int[]{255, 255, 255, 255, 255};
		
		Button buildButton = new Button("BUILD SCENE");
		buildButton.setOnMouseClicked((MouseEvent e) -> {
			/*
			 *	-Build accordion panel
			 * 	-Init population
			 * 	-Render initial frame
			 * TODO:
			 * 	refactor how search space and init goal
			 *  are initially represented
			 */
			popMngr.clearPopulations();
			int[] popSizes = new int[(int) numPopSlider.getValue()];
			for (int i = 0; i < popSizes.length; i++) {
				popSizes[i] = sizePanes.get(i).getSize();
			}
			
			if (radioButtons.getSelectedToggle() == rb2D) {
				basic2D = new Basic2dDriver(searchSpace, initGoal, (int) numPopSlider.getValue(), popSizes);
				popMngr.addDriver("basic2D", basic2D);
				popMngr.setActiveDriver("basic2D");
			}
			else {
				basic3D = new Basic3dDriver(searchSpace, initGoal, (int) numPopSlider.getValue(), popSizes);
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
				rebuildPane();
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
		
		this.basicOptPane.setStyle("" +
			"-fx-background-color: #ffffff;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);
		
		Label numPopLabel = new Label("# Populations: ");
		Label numPopValue = new Label("1");
		numPopSlider.setBlockIncrement(1);
		numPopSlider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				int val = (int) new_val.intValue();
				//---UPDATE UI LABEL---//
				numPopValue.setText(String.format("%d", val));
				//---TEAR DOWN POP SIZE UI AND REBUILD---//
				sizeParentPane.getChildren().clear();
				sizePanes.clear();
				for (int i = 0; i < val; i++) {
					PopSizePane psp = new PopSizePane(i);
					sizePanes.add(psp);
					sizeParentPane.getChildren().add(psp.getPane());
				}
		});
		this.basicOptPane.add(numPopLabel, 0, 2);
		this.basicOptPane.add(numPopSlider, 1, 2);
		this.basicOptPane.add(numPopValue, 2, 2);
		
		//---BUILD SCENE, START/STOP, NUM POPULATIONS---//
		this.mainPane.getChildren().add(this.basicOptPane);
		//---POPULATION SIZE PANE---//
		this.mainPane.getChildren().add(this.sizeParentPane);
		//---INDIVIDUAL POPULATION PARAMETER CONTROLS---//
		this.mainPane.getChildren().add(this.accordionPane.getPane());
	}
	
	public void rebuildPane () {
		activeGraphicsPane.setCenter(popMngr.getActiveDriver().getUiNode());
	}
	
	public Pane getPane () {
		return this.mainPane;
	}

}
