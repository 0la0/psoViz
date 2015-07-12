package pso;

public class Velocity {
	
	private int numDimensions;
	private double[] velocity;
	
	public Velocity (double[] velocity) {
		this.numDimensions = velocity.length;
		this.setVector(velocity);
	}
	
	public void setVector (double[] velocity) {
		this.velocity = velocity;
	}
	
	public double[] getVector () {
		return this.velocity;
	}
	
	public void setElement (int index, double value) {
		if (index < 0 || index >= this.velocity.length) {
			System.out.println("Velocity.setElement indexOutOfBounds");
			return;
		}
		this.velocity[index] = value;
	}
	
	public double getElement (int index) {
		if (index < 0 || index >= this.velocity.length) {
			System.out.println("Velocity.getElement indexOutOfBounds");
			return 0;
		}
		return this.velocity[index];
	}
	
	public int getNumDimensions () {
		return this.numDimensions;
	}
	
	public Velocity copy () {
		return new Velocity(this.velocity);
	}

}
