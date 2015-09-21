package pso;


public class FitnessDistance implements IFitness{

	private int numDims;
	private int[] goal;
	
	public FitnessDistance () {}
	
	public FitnessDistance (int[] goal) {
		if (goal.length == 0) {
			System.out.println("goal must have at least one dimension");
			return;
		}
		this.setGoal(goal);
	}

	@Override
	public float calcFitness(Particle p) {
		float sum = 0f;
		for (int i = 0; i < this.numDims; i++) {
			sum += (float) (Math.pow(p.getPosition().getVector()[i] - this.goal[i], 2));
		}
		return (float) Math.sqrt(sum);
	}

	@Override
	public void setGoal(int[] goal) {
		this.numDims = goal.length;
		this.goal = goal;
	}

	@Override
	public int[] getGoal() {
		return this.goal;
	}
	
	@Override
	public int getGoalElement (int index) {
		if (index < 0 || index >= this.goal.length) {
			System.out.println("Fitness.getGoalElement indexOutOfBounds");
			return (int) -Math.pow(16, -1);
		}
		return this.goal[index];
	}
	
	@Override
	public double getDimensionFitness (Population p, int geneIndex) {
		double fitnessSum = p.getParticles().stream()
				.mapToDouble(individual -> {
					double difference = individual.getPosition().getVector()[geneIndex] - this.goal[geneIndex];
					return Math.abs(difference);
				})
				.sum();
		double fitnessVal = (fitnessSum) / (p.getParticles().size() * 1.0);
		return fitnessVal;
	}
	
}
