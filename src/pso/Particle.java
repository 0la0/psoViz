package pso;

public class Particle {

	private Position position;
	private Position lastPosition1;
	private Position lastPosition2;
	private Position pBest;
	private Velocity velocity;
	private float pBestVal = 999999.9f;
	private IFitness fitnessFunction;
	private Options options;
	private int numDimensions;
	
	public Particle (Position position, Velocity velocity, IFitness fitnessFunction, Options options) {
		this.position = position;
		this.velocity = velocity;
		this.numDimensions = position.getNumDimensions();
		this.fitnessFunction = fitnessFunction;
		this.options = options;
		
		this.pBest = position.copy();
		this.lastPosition1 = position.copy();
		this.lastPosition2 = position.copy();
	}
	
	public float evaluateFitness () {
		float fitness = fitnessFunction.calcFitness(this);
		if (fitness < this.pBestVal) {
			this.pBestVal = fitness;
			this.pBest = this.position.copy();
		}
		return fitness;
	}
	
	public void update (Position gBest, double[] dimWeight) {
		if (gBest == null) {
			System.out.println("gBest is null, exiting now");
			System.exit(0);
		}
		//v[] = v[] + c1 * rand() * (pbest[] - present[]) + c2 * rand() * (gbest[] - present[])
		//double[] vel = new double[this.numDimensions];
		for (int i = 0; i < this.numDimensions; i++) {

			this.velocity.getVector()[i] += 
					(this.options.c1 * Math.random() * (this.pBest.get()[i] - this.position.get()[i])) + 
					(this.options.c2 * Math.random() * (gBest.get()[i] - this.position.get()[i]));
			this.velocity.getVector()[i] *= dimWeight[i];
		}
		this.applySpeedLimit();
		this.updateVector();
	}
	
	private void applySpeedLimit () {
		for (int i = 0; i < this.numDimensions; i++) {
			if (this.velocity.getVector()[i] > this.options.speedLimit)
				this.velocity.getVector()[i] = this.options.speedLimit;
			else if (this.velocity.getVector()[i] < -this.options.speedLimit)
				this.velocity.getVector()[i] = -this.options.speedLimit;
		}
	}
	
	private void updateVector () {
		this.lastPosition2 = this.lastPosition1.copy();
		this.lastPosition1 = this.position.copy();
		
		int[] newPos = new int[this.numDimensions];
		for (int i = 0; i < this.numDimensions; i++) {
			newPos[i] = (int) Math.round(this.position.get()[i] + this.velocity.getVector()[i]);
		}
		this.position.set(newPos);
	}
	
	public float getLocalBest () {
		return this.pBestVal;
	}
	
	public Position getLocalBestPosition () {
		return this.pBest;
	}

	public Position getPosition () {
		return this.position;
	}
	
	public Position getLastPosition1 () {
		return this.lastPosition1;
	}
	
	public Position getLastPosition2 () {
		return this.lastPosition2;
	}
	
	public void reset () {
		this.pBestVal = 999999.9f;
	}
	
	public void scatter (Velocity velocity) {
		this.pBestVal = 999999.9f;
		this.velocity = velocity;
	}
	
	public void setPosition (Position position) {
		this.position = position;
		this.lastPosition1 = position.copy();
		this.lastPosition2 = position.copy();
	}
	
	public void setVelocity (Velocity velocity) {
		this.velocity = velocity;
	}
	
}
