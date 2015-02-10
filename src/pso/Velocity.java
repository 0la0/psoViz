package pso;

public class Velocity {
	
	private int numDimensions;
	private double[] velocity;
	
	public Velocity (double[] velocity) {
		this.numDimensions = velocity.length;
		this.set(velocity);
	}
	
	public void set (double[] velocity) {
		this.velocity = velocity;
	}
	
	public double[] get () {
		return this.velocity;
	}
	
	public int getNumDimensions () {
		return this.numDimensions;
	}
	
	public Velocity copy () {
		return new Velocity(this.velocity);
	}

}
