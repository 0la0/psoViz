package pso;

public class Position {
	
	private int numDimensions;
	private int[] position;
	
	public Position (int[] pos) {
		this.numDimensions = pos.length;
		this.set(pos);
	}
	
	public void set (int[] pos) {
		this.position = pos;
	}
	
	public int[] get () {
		return this.position;
	}
	
	public void setElement (int index, int value) {
		if (index < 0 || index >= this.position.length) {
			System.out.println("Velocity.setElement indexOutOfBounds");
			return;
		}
		this.position[index] = value;
	}
	
	public double getElement (int index) {
		if (index < 0 || index >= this.position.length) {
			System.out.println("Velocity.getElement indexOutOfBounds");
			return 0;
		}
		return this.position[index];
	}
	
	public int getNumDimensions () {
		return this.numDimensions;
	}
	
	public Position copy () {
		return new Position(this.position);
	}
	
}
