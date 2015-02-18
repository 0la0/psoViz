package javaFxDriver;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pso.FitnessDistance;
import pso.Particle;
import pso.Population;
import pso.Position;

public class Basic2dDriver extends PopulationDriver {

	private Canvas canvas = new Canvas(900, 675);
	
	public Basic2dDriver(int[] searchSpaceDimensions, int[] initGoal, int numPopulations) {
		super(searchSpaceDimensions, initGoal, numPopulations);
		
		//---BUILD PSO---//
		this.options.c1 = 0.01f;
		this.options.c2 = 0.001f;
		this.options.speedLimit = 10.0f;
    	
		int populationSize = 1000;
		Position size = new Position(searchSpaceDimensions.clone());
		this.fitnessFunction = new FitnessDistance(initGoal.clone());
		this.p = new Population(size, populationSize, fitnessFunction, options);
		this.options.population = p;
		this.setUpUi();
	}
	
	@Override
	public void update (float elapsedTime) {
		p.update();
		g2d.clearRect(0, 0, g2d.getCanvas().getWidth(), g2d.getCanvas().getHeight());
		//---RENDER GOAL STATE---//
		g2d.fillOval(
			this.fitnessFunction.getGoal()[0] - this.goalRadius, 
			this.fitnessFunction.getGoal()[1] - this.goalRadius, 
			this.goalRadius * 2, this.goalRadius * 2
		);
		//---RENDER PARTICLES---//
		for (Particle particle : p.getParticles()) {
			g2d.setStroke(Color.color(
				this.getColor(particle.getPosition().get()[2], 255), 
				this.getColor(particle.getPosition().get()[3], 255),
				this.getColor(particle.getPosition().get()[4], 255)
			));
			g2d.strokeLine(
				particle.getPosition().get()[0], particle.getPosition().get()[1],
				particle.getLastPosition1().get()[0], particle.getLastPosition1().get()[1]
			);
			g2d.strokeLine(
				particle.getLastPosition1().get()[0], particle.getLastPosition1().get()[1],
				particle.getLastPosition2().get()[0], particle.getLastPosition2().get()[1]
			);
		}	
	}
	
	private void setUpUi () {
		this.canvas = new Canvas(900, 675);
		this.g2d = canvas.getGraphicsContext2D();
		
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			p.resetGoal(new int[]{
				(int) e.getX(), (int) e.getY(), 
				(int) (255 * Math.random()),
				(int) (255 * Math.random()),
				(int) (255 * Math.random())
			});
			goalColor = Color.color(
				fitnessFunction.getGoal()[2] / 255.0,
				fitnessFunction.getGoal()[3] / 255.0,
				fitnessFunction.getGoal()[4] / 255.0
			);
			g2d.setFill(goalColor);
		});
	}
	
	@Override
	public Node getUiNode () {
		return this.canvas;
	}
	
	public String toString () {
		return "basic2D";
	}

}
