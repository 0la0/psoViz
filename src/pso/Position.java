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
	
	public int getNumDimensions () {
		return this.numDimensions;
	}
	
	public Position copy () {
		return new Position(this.position);
	}
	
}
