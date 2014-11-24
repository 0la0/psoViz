
public class FitnessDistance implements IFitness{

	private int goalX;
	private int goalY;
	
	public FitnessDistance (int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}

	@Override
	public void setGoal(int x, int y) {
		this.goalX = x;
		this.goalY = y;
	}

	@Override
	public float calcFitness(Particle p) {
		double x = Math.pow(p.getPosition().x - this.goalX, 2);
		double y = Math.pow(p.getPosition().y - this.goalY, 2);
		return (float) Math.sqrt(x + y);
	}
	
}
