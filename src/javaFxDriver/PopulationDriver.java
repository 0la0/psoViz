package javaFxDriver;

import java.util.ArrayList;

import javafx.scene.Node;
import pso.IFitness;
import pso.Options;
import pso.Population;

/*
 * Parent class for population drivers
 */
public abstract class PopulationDriver {
	
	protected IFitness fitnessFunction;
	protected ArrayList<Options> opts = new ArrayList<Options>();
	protected ArrayList<Population> populations = new ArrayList<Population>();
	
	protected int numDimensions = 0; //Number of dimensions to represent
	protected double[] paramMult;	 //individual parameter multipliers
	protected String[] paramList;	 //parameter labels
	protected int numPopulations;	 //number of populations
	
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
	
	public abstract Node getUiNode ();
	
	public abstract void update (float elapsedTime);
	
}
