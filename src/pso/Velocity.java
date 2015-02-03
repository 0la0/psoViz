package pso;

public class Velocity {
	
	public double x;
	public double y;
	public double z;
	
	public Velocity (double x, double y) {
		this.set(x, y);
	}
	
	public Velocity (double x, double y, double z) {
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

}
