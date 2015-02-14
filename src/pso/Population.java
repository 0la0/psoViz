package pso;

import java.util.ArrayList;


public class Population {

	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int velMultiplier = 5;
	private float gBestVal = 9999.9f;
	private Position gBest;
	private IFitness fitnessFunction;
	private int size;
	private Position canvasSize;
	private int numDimensions;
	
	public Population (Position canvasSize, int numParticles, IFitness fitnessFunction, Options options) {
		this.canvasSize = canvasSize;
		this.numDimensions = this.canvasSize.getNumDimensions();
		this.size = numParticles;
		this.fitnessFunction = fitnessFunction;
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(
				this.getRandomPosition(),
				this.getRandomVelocity(false),
				fitnessFunction,
				options
			));
		}
	}
	
	public double update () {
		double fitnessSum = 0;
		for (Particle p : particles) {
			fitnessSum += p.evaluateFitness();
		}
		//search for global best
		for (Particle p : particles) {
			if (p.getLocalBest() < this.gBestVal) {
				//new global best
				this.gBestVal = p.getLocalBest();
				gBest = p.getLocalBestPosition().copy();
				
			}
		}
		//update positions
		for (Particle p : particles) {
			p.update(gBest);
		}
		//return mean fitness
		return fitnessSum / (this.size * 1.0);
	}
	
	public ArrayList<Particle> getParticles () {
		return this.particles;
	}
	
	private int getPosNeg () {
		if (Math.random() < 0.5) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public void resetGoal (int[] goal) {
		this.fitnessFunction.setGoal(goal);
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.reset();
		}
	}
	
	public void resetPosAndVel () {
		int[] newGoal = new int[this.numDimensions];
		for (int i = 0; i < this.numDimensions; i++) {
			newGoal[i] = (int) (this.canvasSize.get()[i] * Math.random());
		}
		this.fitnessFunction.setGoal(newGoal);
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.reset();
			p.setPosition(this.getRandomPosition());
			p.setVelocity(this.getRandomVelocity(false));
		}
	}
	
	private Position getRandomPosition () {
		int[] randPos = new int[this.numDimensions]; 
		for (int i = 0; i < this.numDimensions; i++) {
			randPos[i] = (int) Math.floor(this.canvasSize.get()[i] * Math.random());
		}
		return new Position(randPos);
	}
	
	private Velocity getRandomVelocity (boolean isScatter) {
		double[] velocity = new double[this.numDimensions];
		int scatterMultiplier = 1;
		if (isScatter) scatterMultiplier = 5;
		for (int i = 0; i < this.numDimensions; i++) {
			velocity[i] = this.getPosNeg() * velMultiplier * scatterMultiplier * Math.random();
		}
		return new Velocity(velocity);
	}
	
	public void scatter () {
		System.out.println("population scatter");
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.scatter(this.getRandomVelocity(true));
		}
	}
	
}
