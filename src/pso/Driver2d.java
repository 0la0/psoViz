package pso;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Driver2d extends JPanel implements MouseListener{

	private Population p;
	private Timer timer;
	private int animationStepTime = 33;
	//private IFitness fitnessFunction = new FitnessDistance(255, 255);
	private IFitness fitnessFunction = new FitnessDistance(new int[]{255, 255});
	private int iterationCnt = 0;
	//private int convergenceThresh = 50;
	private Options options;
	private int goalRadius = 10;
	private int numDimensions;
	
	public Driver2d (int width, int height, int populationSize, Options options) {
		this.addMouseListener(this);
		this.setBackground(new Color(255, 255, 255));
		this.options = options;
		Position size = new Position(new int[]{width, height});
		this.p = new Population(size, populationSize, fitnessFunction, options);
		this.options.population = p;
		this.timer = new Timer(animationStepTime, new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	    		iterate();
	        }
	    });
	}
	
	public void start () {
		this.timer.start();
	}
	
	private void iterate () {
		this.iterationCnt++;
		double meanFitness = p.update();
		if (meanFitness < this.options.convergenceThresh) {
			this.p.resetPosAndVel();
			System.out.println("Num steps to convergence: " + this.iterationCnt);
			this.iterationCnt = 0;
		}
		repaint();
	}
	
	
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		//render goal
		g2d.setColor(new Color(50, 200, 50));
		g2d.fillOval(
				this.fitnessFunction.getGoal()[0] - this.goalRadius, 
				this.fitnessFunction.getGoal()[1] - this.goalRadius, 
				this.goalRadius * 2, this.goalRadius * 2);
		//render particles
		g2d.setColor(new Color(50, 50, 50));
		g2d.setStroke(new BasicStroke(2));
		for (Particle particle : p.getParticles()) {
			g.drawLine(
					particle.getPosition().get()[0], particle.getPosition().get()[1],
					particle.getLastPosition1().get()[0], particle.getLastPosition1().get()[1]
			);
			g.drawLine(
					particle.getLastPosition1().get()[0], particle.getLastPosition1().get()[1],
					particle.getLastPosition2().get()[0], particle.getLastPosition2().get()[1]
			);
		}
	}
	
	//-------------MOUSE LISTENERS-------------//
	@Override
	public void mouseClicked (MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {
		this.p.resetGoal(new int[]{arg0.getX(), arg0.getY()});
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	//-------------------------------------------//
	
}
