package javaFxDriver;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import pso.IFitness;
import pso.Options;
import pso.Population;

/*
 * Parent class for population drivers
 */
public abstract class PopulationDriver {
	
	protected Options options = new Options();
	protected Population p;
	protected IFitness fitnessFunction;
	
	protected int goalRadius = 20;
	protected GraphicsContext g2d;
	protected Color goalColor = Color.BLACK;
	protected int numDimensions = 0;
	protected double[] paramMult;
	
	public PopulationDriver (int[] searchSpaceDimensions, int[] initGoal, int numPopulations) {}
	
	protected double getColor (float val, int size) {
		if (val < 0) return 0;
		if (val > size) return 1;
		return val / (size * 1.0);
	}
	
	public Options getOptions () {
		return this.options;
	}
	
	public int getNumDims () {
		return this.numDimensions;
	}
	
	public void setParamMult (int index, double val) {
		if (index < 0 || index >= this.paramMult.length) {
			System.out.println("PopulationDriver.setParamMult indexOutOfBounds");
			return;
		}
		this.paramMult[index] = val;
		System.out.println("param index: " + index + ", val: " + this.paramMult[index]);
	}
	
	public void addPopulation (int populationSize) {}
	
	public abstract Node getUiNode ();
	
	public abstract void update (float elapsedTime);
	
}
