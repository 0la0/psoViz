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
			this.pBest.x = this.position.x;
			this.pBest.y = this.position.y;
			if (this.numDimensions == 3)
				this.pBest.z = this.position.z;
		}
		return fitness;
	}
	
	public void update (Position gBest) {
		if (gBest == null) {
			System.out.println("gBest is null, exiting now");
			System.exit(0);
		}
		//v[] = v[] + c1 * rand() * (pbest[] - present[]) + c2 * rand() * (gbest[] - present[])
		this.velocity.x += (this.options.c1 * Math.random() * (this.pBest.x - this.position.x)) + (this.options.c2 * Math.random() * (gBest.x - this.position.x));
		this.velocity.y += (this.options.c1 * Math.random() * (this.pBest.y - this.position.y)) + (this.options.c2 * Math.random() * (gBest.y - this.position.y));
		if (this.numDimensions == 3)
			this.velocity.z += (this.options.c1 * Math.random() * (this.pBest.z - this.position.z)) + (this.options.c2 * Math.random() * (gBest.z - this.position.z));
		
		this.applySpeedLimit();
		this.updateVector();
	}
	
	private void applySpeedLimit () {
		if (this.velocity.x > this.options.speedLimit)
			this.velocity.x = this.options.speedLimit;
		else if (this.velocity.x < -this.options.speedLimit)
			this.velocity.x = -this.options.speedLimit;
		
		if (this.velocity.y > this.options.speedLimit)
			this.velocity.y = this.options.speedLimit;
		else if (this.velocity.y < -this.options.speedLimit)
			this.velocity.y = -this.options.speedLimit;
		
		if (this.numDimensions == 3) {
			if (this.velocity.z > this.options.speedLimit)
				this.velocity.z = this.options.speedLimit;
			else if (this.velocity.z < -this.options.speedLimit)
				this.velocity.z = -this.options.speedLimit;
		}
	}
	
	private void updateVector () {
		this.lastPosition2.x = this.lastPosition1.x;
		this.lastPosition2.y = this.lastPosition1.y;
		if (this.numDimensions == 3)
			this.lastPosition2.z = this.lastPosition1.z;
		this.lastPosition1.x = this.position.x;
		this.lastPosition1.y = this.position.y;
		if (this.numDimensions == 3)
			this.lastPosition1.z = this.position.z;
		this.position.x = (int) Math.floor(this.position.x + this.velocity.x);
		this.position.y = (int) Math.floor(this.position.y + this.velocity.y);
		if (this.numDimensions == 3)
			this.position.z = (int) Math.floor(this.position.z + this.velocity.z);
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
	
	public void setPosition (Position position) {
		this.position = position;
		this.lastPosition1 = position.copy();
		this.lastPosition2 = position.copy();
	}
	
	public void setVelocity (Velocity velocity) {
		this.velocity = velocity;
	}
	
}
