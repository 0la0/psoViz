package javaFxDriver;

import java.util.ArrayList;

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
	
	//protected Options options = new Options();
	//protected Population p; //change to collection of populations
	protected IFitness fitnessFunction;
	protected ArrayList<Options> opts = new ArrayList<Options>();
	protected ArrayList<Population> populations = new ArrayList<Population>();
	
	protected int goalRadius = 20;
	protected GraphicsContext g2d;
	protected Color goalColor = Color.BLACK;
	protected int numDimensions = 0;
	protected double[] paramMult;
	protected String[] paramList;
	protected int numPopulations;
	
	public PopulationDriver (int[] searchSpaceDimensions, int[] initGoal, int numPopulations, int[] popSizes) {
		this.numPopulations = numPopulations;
	}
	
	protected double getColor (float val, int size) {
		if (val < 0) return 0;
		if (val > size) return 1;
		return val / (size * 1.0);
	}
	
	public Options getOptions (int index) {
		if (index < 0 || index >= this.opts.size()) {
			System.out.println("PopulationDriver.getOptions indexOutOfBounds");
		}
		return this.opts.get(index);
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
	
	public Population getPopulation (int index) {
		if (index < 0 || index >= this.populations.size()) {
			System.out.println("PopulationDriver.getPopulation indexOutOfBounds");
		}
		return this.populations.get(index);
	}
	
	public String getParamLabel (int index) {
		if (index < 0 || index >= this.paramList.length) {
			System.out.println("PopulationDriver.getParamLabel indexOutOfBounds");
			return null;
		}
		return this.paramList[index];
	}
	
	public void addPopulation (int populationSize) {}
	
	public abstract Node getUiNode ();
	
	public abstract void update (float elapsedTime);
	
}
