package javaFxDriver;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;

public class Init extends Application {
	
	private long lastTime;
	private BorderPane mainBorderPane = new BorderPane();
	private BorderPane activeGraphicsPane = new BorderPane();
	private PopulationManager popMngr = new PopulationManager();

	@Override
	public void start (Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 1154, 680, Color.WHITE);
		stage.setScene(scene);
		stage.setTitle("Particle Swarm Optimization Visualization");
		
		//---BUILD POPULATION DRIVERS---//
		/*
		 * Right now the graphics pane needs a live populationManager
		 * uiPane ... need to refactor
		 */
		int[] searchSpace = new int[]{900, 675, 255, 255, 255};
		int[] initGoal = new int[]{255, 255, 255, 255, 255};
		PopulationDriver basic2D = new Basic2dDriver(searchSpace, initGoal, -1, new int[100]);
		this.popMngr.addDriver("basic2D", basic2D);
		this.popMngr.setActiveDriver("basic2D");
		
		//---SET MAIN GRAPHICS PANE---//
		this.activeGraphicsPane.setCenter(this.popMngr.getActiveDriver().getUiNode());
		this.activeGraphicsPane.setStyle("" +
			"-fx-background-color: #ffffff;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);

		//---CREATE TIMER AND START---//
		this.lastTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				float elapsedTime = (float) ((now - lastTime) / 1000000.0);
				lastTime = now;
				//if (Math.random() < 0.01) swapGraphicsPanes();
				popMngr.update(elapsedTime);
			}
		};
		
		//---FINISH BUILDING UI---//
		//---CONTROLLER PANEL ON THE LEFT---//
		MainControllerPane mpc = new MainControllerPane(this.popMngr, timer, this.activeGraphicsPane);
		
		mainBorderPane.setLeft(mpc.getPane());
		mainBorderPane.setCenter(this.activeGraphicsPane);
		scene.setRoot(mainBorderPane);
		stage.show();
	}

	public static void main (String[] args) {
		launch(args);
	}

}