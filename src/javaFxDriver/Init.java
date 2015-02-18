package testDriver;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

public class Init extends Application {
	
	private long lastTime;
	
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
	
	private GridPane navigation = new GridPane();
	private BorderPane mainBorderPane = new BorderPane();
	private BorderPane activePane = new BorderPane();
	private PopulationManager popMngr = new PopulationManager();
	
	
	private void buildNavigationPane () {
		this.navigation = new GridPane();
		this.navigation.setPadding(new Insets(10, 10, 10, 10));
		this.navigation.setVgap(10);
		this.navigation.setHgap(6);
		this.navigation.setMinWidth(250);
        
		this.navigation.add(this.c1Label, 0, 0);
		this.navigation.add(this.c1Slider, 1, 0);
		this.navigation.add(this.c1Value, 2, 0);
        
		this.navigation.add(this.c2Label, 0, 1);
		this.navigation.add(this.c2Slider, 1, 1);
		this.navigation.add(this.c2Value, 2, 1);
        
		this.navigation.add(this.speedLabel, 0, 2);
		this.navigation.add(this.speedSlider, 1, 2);
		this.navigation.add(this.speedValue, 2, 2);
       
		this.navigation.setStyle("" +
			"-fx-background-color: #aaaadd;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);
        
		this.c1Slider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				this.popMngr.setC1(new_val.floatValue());
				this.c1Value.setText(String.format("%.5f", new_val));
		});
		this.c2Slider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				float val = (float) (new_val.floatValue() / 100.0);
				this.popMngr.setC2(val);
				this.c2Value.setText(String.format("%.5f", val));
		});
		this.speedSlider.valueProperty().addListener( 
			(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
				float val = (float) (new_val.floatValue() * 100.0);
				this.popMngr.setSpeedLimit(val);
				this.speedValue.setText(String.format("%.5f", val));
		});
        
	}
	
	private void swapGraphicsPanes () {
		if (this.popMngr.getActiveDriver().toString().equals("basic2D")) {
			this.popMngr.setActiveDriver("basic3D");
		}
		else {
			this.popMngr.setActiveDriver("basic2D");
		}
		this.activePane.setCenter(this.popMngr.getActiveDriver().getUiNode());
	}

	@Override
	public void start (Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 1154, 680, Color.WHITE);
		stage.setScene(scene);
		stage.setTitle("Particle Swarm Optimization Visualization");

		this.buildNavigationPane();
		
		//---BUILD POPULATION DRIVERS---//
		int[] searchSpace = new int[]{900, 675, 255, 255, 255};
		int[] initGoal = new int[]{255, 255, 255, 255, 255};

		PopulationDriver basic2D = new Basic2dDriver(searchSpace, initGoal, -1);
		this.popMngr.addDriver("basic2D", basic2D);
		//this.popMngr.setActiveDriver("basic2D");

		PopulationDriver basic3D = new Basic3dDriver(searchSpace, initGoal, -1);
		this.popMngr.addDriver("basic3D", basic3D);
		this.popMngr.setActiveDriver("basic3D");

		this.activePane.setCenter(this.popMngr.getActiveDriver().getUiNode());
		this.activePane.setStyle("" +
			"-fx-background-color: #ffffff;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);

		//---BUILD MAIN UI---//
		mainBorderPane.setLeft(navigation);
		mainBorderPane.setCenter(this.activePane);
		scene.setRoot(mainBorderPane);
		stage.show();

		//---CREATE TIMER AND START---//
		this.lastTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				float elapsedTime = (float) ((now - lastTime) / 1000000.0);
				lastTime = now;
				if (Math.random() < 0.01) swapGraphicsPanes();
				popMngr.update(elapsedTime);
			}
		};
		timer.start();
	}

	public static void main (String[] args) {
		launch(args);
	}

}