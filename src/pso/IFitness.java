package pso;

public interface IFitness {

	public void setGoal (int x, int y);
	
	public void setGoal (int x, int y, int z);
	
	public int getGoalX ();
	
	public int getGoalY ();
	
	public int getGoalZ ();
	
	public float calcFitness (Particle p);
	
}
