package pso;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Driver extends JPanel implements MouseListener{

	private Population p;
	private Timer timer;
	private int animationStepTime = 33;
	private IFitness fitnessFunction = new FitnessDistance(255, 255);
	private int iterationCnt = 0;
	private int convergenceThresh = 50;
	private Options options;
	
	public Driver (int width, int height, int populationSize, Options options) {
		this.addMouseListener(this);
		this.setBackground(new Color(255, 255, 255));
		this.options = options;
		this.p = new Population(width, height, populationSize, fitnessFunction, options);
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
		this.p.render((Graphics2D) g); 
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
		this.p.resetGoal(arg0.getX(), arg0.getY());
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	//-------------------------------------------//
	
}
