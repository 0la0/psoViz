package pso;

public interface IFitness {

	public void setGoal (int[] goal);
	
	public int[] getGoal ();
	
	public int getGoalElement (int index);
	
	public float calcFitness (Particle p);
	
	public double getDimensionFitness (Population p, int index);
	
}
