package pso;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;


public class Population {

	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int velMultiplier = 5;
	private float gBestVal = 9999.9f;
	private VectorI gBest;
	private IFitness fitnessFunction;
	private boolean drawTails = true;
	private int size;
	private VectorI dimension;
	
	public Population (int x, int y, int numParticles, IFitness fitnessFunction, Options options) {
		this.dimension = new VectorI(x, y);
		this.size = numParticles;
		this.fitnessFunction = fitnessFunction;
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(
				this.getRandomPosition(),
				this.getRandomVelocity(),
				fitnessFunction,
				options
			));
		}
	}
	
	public double update () {
		double fitnessSum = 0;
		for (Particle p : particles) {
			fitnessSum += p.evaluateFitness();
		}
		//search for global best
		for (Particle p : particles) {
			if (p.getLocalBest() < this.gBestVal) {
				//new global best
				this.gBestVal = p.getLocalBest();
				gBest = new VectorI(p.getLocalBestPosition().x, p.getLocalBestPosition().y);
			}
		}
		//update positions
		for (Particle p : particles) {
			p.update(gBest);
		}
		//return mean fitness
		return fitnessSum / (this.size * 1.0);
	}
	
	public void render (Graphics2D g) {
		g.setPaint(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(2));
		for (Particle p : particles) {
			if (this.drawTails) {
				g.drawLine(p.getPosition().x, p.getPosition().y, p.getLastPosition1().x, p.getLastPosition1().y);
				g.drawLine(p.getLastPosition1().x, p.getLastPosition1().y, p.getLastPosition2().x, p.getLastPosition2().y);
			}
			//g.fillRect(p.getPosition().x, p.getPosition().y, 4, 4);
		}
	}
	
	private int getPosNeg () {
		if (Math.random() < 0.5) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public void resetGoal (int x, int y) {
		this.fitnessFunction.setGoal(x, y);
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.reset();
		}
	}
	
	public void resetPosAndVel () {
		//int newGoalX = (int) Math.floor(this.width * Math.random());
		//int newGoalY = (int) Math.floor(this.height * Math.random());
		int newGoalX = (int) Math.floor(this.dimension.x * Math.random());
		int newGoalY = (int) Math.floor(this.dimension.y * Math.random());
		this.fitnessFunction.setGoal(newGoalX, newGoalY);
		this.gBestVal = 9999.9f;
		for (Particle p : this.particles) {
			p.reset();
			p.setPosition(this.getRandomPosition());
			p.setVelocity(this.getRandomVelocity());
		}
	}
	
	private VectorI getRandomPosition () {
		return new VectorI (
			//(int) Math.floor(this.width * Math.random()),
			//(int) Math.floor(this.height * Math.random())
			(int) Math.floor(this.dimension.x * Math.random()),
			(int) Math.floor(this.dimension.y * Math.random())
		);
	}
	
	private VectorD getRandomVelocity () {
		return new VectorD(
			(double) (this.getPosNeg() * (velMultiplier * Math.random())),
			(double) (this.getPosNeg() * (velMultiplier * Math.random()))
		);
	}
	
}
