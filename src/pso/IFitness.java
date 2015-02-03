package pso;

public interface IFitness {

	public void setGoal (int x, int y);
	
	public void setGoal (int x, int y, int z);
	
	public float calcFitness (Particle p);
	
}
