package javaFxDriver;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pso.FitnessDistance;
import pso.PsoConfigOptions;
import pso.Particle;
import pso.Population;
import pso.Position;

public class Basic2dDriver extends PopulationDriver {

	protected int goalRadius = 20;
	protected GraphicsContext g2d;
	protected Color goalColor = Color.BLACK;
	private Canvas canvas = new Canvas(width, height);
	
	public Basic2dDriver(int[] searchSpaceDimensions, int[] initGoal, int numPopulations, int[] popSizes) {
		super(searchSpaceDimensions, initGoal, numPopulations, popSizes);
		
		this.paramList = new String[]{"X", "Y", "R", "G", "B"};
		this.numDimensions = searchSpaceDimensions.length;
		this.paramMult = new double[this.numDimensions]; 
		Arrays.fill(this.paramMult, 1);
		
		Position size = new Position(searchSpaceDimensions.clone());
		this.fitnessFunction = new FitnessDistance(initGoal.clone());
		
		for (int i = 0; i < this.numPopulations; i++) {
			PsoConfigOptions options = new PsoConfigOptions();
			options.c1 = 0.01f;
			options.c2 = 0.001f;
			options.speedLimit = 10.0f;
			Population pop = new Population(size, popSizes[i], fitnessFunction, options);
			options.population = pop;
			this.opts.add(options);
			this.populations.add(pop);
		}
		
		this.setUpUi();
		this.g2d.setLineWidth(3);
	}
	
	@Override
	public void update (float elapsedTime) {
		//g2d.clearRect(0, 0, g2d.getCanvas().getWidth(), g2d.getCanvas().getHeight());
		for (Population p : this.populations) {
			p.update();
			//---RENDER GOAL STATE---//
			/*
			g2d.fillOval(
				this.fitnessFunction.getGoal()[0] - this.goalRadius, 
				this.fitnessFunction.getGoal()[1] - this.goalRadius, 
				this.goalRadius * 2, this.goalRadius * 2
			);
			*/
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
	}
	
	private void setUpUi () {
		this.canvas = new Canvas(900, 675);
		this.g2d = canvas.getGraphicsContext2D();
		
		//---SET INITIAL GOAL STATE COLOR---//
		goalColor = Color.color(
			fitnessFunction.getGoal()[2] / 255.0,
			fitnessFunction.getGoal()[3] / 255.0,
			fitnessFunction.getGoal()[4] / 255.0
		);
		
		//---MOUSE LISTENER TO CHANGE GOAL STATE---//
		canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			for (Population p : populations) {
				p.resetGoal(new int[]{
					(int) e.getX(), (int) e.getY(), 
					(int) (255 * Math.random()),
					(int) (255 * Math.random()),
					(int) (255 * Math.random())
				});
			}
			goalColor = Color.color(
				fitnessFunction.getGoal()[2] / 255.0,
				fitnessFunction.getGoal()[3] / 255.0,
				fitnessFunction.getGoal()[4] / 255.0
			);
			g2d.setFill(goalColor);
		});
	}
	
	public void setFullscreen (boolean isFullscreen, double w, double h) {
		if (isFullscreen) {
			this.canvas.setWidth(w);
			this.canvas.setHeight(h);
		}
		else {
			this.canvas.setWidth(width);
			this.canvas.setHeight(height);
		}
	}
	
	@Override
	public Node getUiNode () {
		return this.canvas;
	}
	
	public String toString () {
		return "basic2D";
	}

}
