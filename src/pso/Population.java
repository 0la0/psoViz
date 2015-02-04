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
		System.out.println("numDims: " + this.numDimensions);
		this.size = numParticles;
		this.fitnessFunction = fitnessFunction;
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(
				this.getRandomPosition(),
				this.getRandomVelocity(),
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
				if (this.numDimensions == 2)
					gBest = new Position(p.getLocalBestPosition().x, p.getLocalBestPosition().y);
				else
					gBest = new Position(
							p.getLocalBestPosition().x, p.getLocalBestPosition().y, p.getLocalBestPosition().z);
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
	
	public void resetGoal (int x, int y) {
		this.fitnessFunction.setGoal(x, y);
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.reset();
		}
	}
	
	public void resetGoal (int x, int y, int z) {
		this.fitnessFunction.setGoal(x, y, z);
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.reset();
		}
	}
	
	public void resetPosAndVel () {
		int newGoalX = (int) Math.floor(this.canvasSize.x * Math.random());
		int newGoalY = (int) Math.floor(this.canvasSize.y * Math.random());
		if (this.numDimensions == 2) {
			this.fitnessFunction.setGoal(newGoalX, newGoalY);
		}
		else {
			int newGoalZ = (int) Math.floor(this.canvasSize.z * Math.random());
			this.fitnessFunction.setGoal(newGoalX, newGoalY, newGoalZ);
		}
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.reset();
			p.setPosition(this.getRandomPosition());
			p.setVelocity(this.getRandomVelocity());
		}
	}
	
	private Position getRandomPosition () {
		if (this.numDimensions == 2) {
			return new Position (
					(int) Math.floor(this.canvasSize.x * Math.random()),
					(int) Math.floor(this.canvasSize.y * Math.random()));
		}
		else {
			return new Position (
					(int) Math.floor(this.canvasSize.x * Math.random()),
					(int) Math.floor(this.canvasSize.y * Math.random()),
					(int) Math.floor(this.canvasSize.z * Math.random()));
		}
	}
	
	private Velocity getRandomVelocity () {
		if (this.numDimensions == 2) {
			return new Velocity(
					(double) (this.getPosNeg() * (velMultiplier * Math.random())),
					(double) (this.getPosNeg() * (velMultiplier * Math.random())));
		}
		else {
			return new Velocity(
					(double) (this.getPosNeg() * (velMultiplier * Math.random())),
					(double) (this.getPosNeg() * (velMultiplier * Math.random())),
					(double) (this.getPosNeg() * (velMultiplier * Math.random())));
		}
	}
	
}
