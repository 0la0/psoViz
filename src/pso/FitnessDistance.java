package pso;

public class FitnessDistance implements IFitness{

	private int numDims;
	private int[] goal;
	
	public FitnessDistance (int[] goal) {
		if (goal.length == 0) {
			System.out.println("goal must have at least one dimension");
			return;
		}
		this.numDims = goal.length;
		this.setGoal(goal);
	}

	@Override
	public float calcFitness(Particle p) {
		float sum = 0f;
		for (int i = 0; i < this.numDims; i++) {
			sum += (float) (Math.pow(p.getPosition().get()[i] - this.goal[i], 2));
		}
		return (float) Math.sqrt(sum);
	}

	@Override
	public void setGoal(int[] goal) {
		this.goal = goal;
	}

	@Override
	public int[] getGoal() {
		return this.goal;
	}
	
}
