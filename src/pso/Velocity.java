package pso;

public class Velocity {

	public double x;
	public double y;
	public double z;
	
	public Velocity (double x, double y) {
		this.setVelocity(x, y);
	}
	
	public Velocity (double x, double y, double z) {
		this.setVelocity(x, y, z);
	}
	
	public void setVelocity (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVelocity (double x, double y, double z) {
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
