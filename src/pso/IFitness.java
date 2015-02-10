package pso;

public interface IFitness {

	public void setGoal (int[] goal);
	
	public int[] getGoal ();
	
	public float calcFitness (Particle p);
	
}
