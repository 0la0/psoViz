package pso;

public class Velocity {
	
	public double x;
	public double y;
	public double z;
	private int numDimensions;
	
	public Velocity (double x, double y) {
		this.numDimensions = 2;
		this.set(x, y);
	}
	
	public Velocity (double x, double y, double z) {
		this.numDimensions = 3;
		this.set(x, y, z);
	}
	
	public void set (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void set (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX () {
		return this.x;
	}
	
	public double getY () {
		return this.y;
	}
	
	public double getZ () {
		return this.z;
	}
	
	public int getNumDimensions () {
		return this.numDimensions;
	}
	
	public Velocity copy () {
		if (this.numDimensions == 2)
			return new Velocity(this.x, this.y);
		else
			return new Velocity(this.x, this.y, this.z);
	}

}
