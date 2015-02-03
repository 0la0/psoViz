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
	
	public Particle (Position Vector, Velocity velocity, IFitness fitnessFunction, Options options) {
		this.position = Vector;
		this.velocity = velocity;
		this.fitnessFunction = fitnessFunction;
		this.options = options;
		
		this.pBest = new Position(Vector.x, Vector.y);
		this.lastPosition1 = new Position(Vector.x, Vector.y);
		this.lastPosition2 = new Position(Vector.x, Vector.y);
	}
	
	public float evaluateFitness () {
		float fitness = fitnessFunction.calcFitness(this);
		if (fitness < this.pBestVal) {
			this.pBestVal = fitness;
			this.pBest.x = this.position.x;
			this.pBest.y = this.position.y;
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
		
		this.applySpeedLimit();
		this.updateVector();
	}
	
	private void applySpeedLimit () {
		if (this.velocity.x > this.options.speedLimit) {
			this.velocity.x = this.options.speedLimit;
		}
		else if (this.velocity.x < -this.options.speedLimit) {
			this.velocity.x = -this.options.speedLimit;
		}
		if (this.velocity.y > this.options.speedLimit) {
			this.velocity.y = this.options.speedLimit;
		}
		else if (this.velocity.y < -this.options.speedLimit) {
			this.velocity.y = -this.options.speedLimit;
		}
	}
	
	private void updateVector () {
		this.lastPosition2.x = this.lastPosition1.x;
		this.lastPosition2.y = this.lastPosition1.y;
		this.lastPosition1.x = this.position.x;
		this.lastPosition1.y = this.position.y;
		this.position.x = (int) Math.floor(this.position.x + this.velocity.x);
		this.position.y = (int) Math.floor(this.position.y + this.velocity.y);
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
		//chage to clone
		this.position = position;
		this.lastPosition1 = new Position(position.x, position.y);
		this.lastPosition2 = new Position(position.x, position.y);
	}
	
	public void setVelocity (Velocity velocity) {
		this.velocity = velocity;
	}
	
}
