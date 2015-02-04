package pso;

public class Position {

	public int x;
	public int y;
	public int z;
	private int numDimensions;
	
	public Position (int x, int y) {
		this.numDimensions = 2;
		this.set(x, y);
	}
	
	public Position (int x, int y, int z) {
		this.numDimensions = 3;
		this.set(x, y, z);
	}
	
	public void set (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set (int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX () {
		return this.x;
	}
	
	public int getY () {
		return this.y;
	}
	
	public int getZ () {
		return this.z;
	}
	
	public int getNumDimensions () {
		return this.numDimensions;
	}
	
	public Position copy () {
		if (this.numDimensions == 2)
			return new Position(this.x, this.y);
		else
			return new Position(this.x, this.y, this.z);
	}
	
}
