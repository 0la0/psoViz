package pso;

public interface IFitness {

	public void setGoal (int x, int y);
	
	public float calcFitness (Particle p);
	
}
