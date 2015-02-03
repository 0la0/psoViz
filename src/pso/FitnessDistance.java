package pso;

public class FitnessDistance implements IFitness{

	private int goalX;
	private int goalY;
	private int goalZ;
	private int numDims;
	
	public FitnessDistance (int x, int y) {
		this.numDims = 2;
		this.setGoal(x, y);
	}
	
	public FitnessDistance (int x, int y, int z) {
		this.numDims = 3;
		this.setGoal(x, y, z);
	}

	@Override
	public void setGoal(int x, int y) {
		this.goalX = x;
		this.goalY = y;
	}
	
	@Override
	public void setGoal(int x, int y, int z) {
		this.goalX = x;
		this.goalY = y;
		this.goalZ = z;
	}

	@Override
	public float calcFitness(Particle p) {
		double x = Math.pow(p.getPosition().x - this.goalX, 2);
		double y = Math.pow(p.getPosition().y - this.goalY, 2);
		if (numDims == 2) {
			return (float) Math.sqrt(x + y);	
		}
		else {
			double z = Math.pow(p.getPosition().z - this.goalZ, 2);
			return (float) Math.sqrt(x + y + z);
		}
	}
	
}
