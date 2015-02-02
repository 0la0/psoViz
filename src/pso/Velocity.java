package pso;

public class Velocity {

	public double x;
	public double y;
	
	public Velocity (double x, double y) {
		this.setVelocity(x, y);
	}
	
	public void setVelocity (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX () {
		return this.x;
	}
	
	public double getY () {
		return this.y;
	}
	
}
