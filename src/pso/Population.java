package pso;

import java.util.ArrayList;
import java.util.Arrays;


public class Population {

	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int velMultiplier = 5;
	private double gBestVal = 9999.9f;
	private Position gBest;
	private IFitness fitnessFunction;
	private int size;
	private Position searchSpaceSize;
	private int numDimensions;
	private double[] dimWeight;
	
	public Population (Position searchSpaceSize, int numParticles, IFitness fitnessFunction, PsoConfigOptions options) {
		this.searchSpaceSize = searchSpaceSize;
		this.numDimensions = this.searchSpaceSize.getNumDimensions();
		this.dimWeight = new double[this.numDimensions];
		Arrays.fill(this.dimWeight, 1);
		this.size = numParticles;
		this.fitnessFunction = fitnessFunction;
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(
				new Position( getRandomVector() ),
				this.getRandomVelocity(false),
				fitnessFunction,
				options
			));
		}
	}
	
	//TODO: move a lot of this logic from the population to a population dirver
	public double update () {
		
		//evaluate fitness and sum all particle fitness values
		double fitnessSum = particles.stream()
				.mapToDouble(particle -> particle.evaluateFitness())
				.sum();
		
		
		//search for global best
		particles.forEach(particle -> {
			if (particle.getLocalBest() < gBestVal) {
				this.gBestVal = particle.getLocalBest();
				gBest = particle.getLocalBestPosition().copy();
			}
		});
		
		//update positions
		particles.forEach(particle -> {
			particle.update(gBest, dimWeight);
		});
		
		//return mean fitness
		return fitnessSum / (this.size * 1.0);
	}
	
	public ArrayList<Particle> getParticles () {
		return this.particles;
	}
	
	private int getPosNeg () {
		return Math.random() < 0.5 ? -1 : 1;
	}
	
	public void resetGoal (int[] goal) {
		this.fitnessFunction.setGoal(goal);
		this.gBestVal = 9999.9f;
		this.particles.forEach(particle -> particle.reset());
	}
	
	public void resetPosAndVel () {
		this.fitnessFunction.setGoal(getRandomVector());
		this.gBestVal = 9999.9f;
		this.particles.forEach(particle -> {
			particle.reset();
			particle.setPosition( new Position( getRandomVector() ) );
			particle.setVelocity(this.getRandomVelocity(false));
		});
	}
	
	private int[] getRandomVector () {
		int[] randVector = new int[this.numDimensions]; 
		for (int i = 0; i < this.numDimensions; i++) {
			randVector[i] = (int) Math.floor(this.searchSpaceSize.getElement(i) * Math.random());
		}
		return randVector;
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
		this.gBestVal = 9999.9f;
		this.particles.forEach(particle -> {
			particle.scatter(getRandomVelocity(true));
		});
	}
	
	public Position getGlobalBest () {
		return this.gBest;
	}
	
	public void setGlobalBest (Position gBest) {
		this.gBest = gBest;
	}
	
	public void setDimWeight (int index, double val) {
		if (index < 0 || index >= this.dimWeight.length) {
			System.out.println("Population.setDimWeight outOfBounds");
			return;
		}
		this.dimWeight[index] = val;
	}
	
	
}
