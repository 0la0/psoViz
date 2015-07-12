package driver2d;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pso.PsoConfigOptions;



@SuppressWarnings("serial")
public class UiControls2D extends JPanel{

	private JPanel mainPanel = new JPanel();
	private JPanel sliderPanel = new JPanel();
	private JSlider c1 = new JSlider(0, 100);
	private JLabel _c1 = new JLabel("c1");
	private JSlider c2 = new JSlider(0, 100);
	private JLabel _c2 = new JLabel("c2");
	private JSlider speedLimit = new JSlider(5, 50);
	private JLabel _speedLimit = new JLabel("speedLimit");
	private JSlider meanFitness = new JSlider(10, 100);
	private JLabel _meanFitness = new JLabel("meanFitness");
	private JButton scatterButton = new JButton("scatter");

	private int width = 420;
	private int height = 130;
	
	private PsoConfigOptions options;
	
	public UiControls2D(final PsoConfigOptions options){
		this.options = options;
		
		//---CREATE SLIDER PANEL---//
		sliderPanel.setLayout(new GridLayout(4, 2));
		sliderPanel.add(c1);
		sliderPanel.add(_c1);
		sliderPanel.add(c2);
		sliderPanel.add(_c2);
		sliderPanel.add(speedLimit);
		sliderPanel.add(_speedLimit);
		sliderPanel.add(meanFitness);
		sliderPanel.add(_meanFitness);
        TitledBorder sliderBorder = BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.black),
			"Options"
		);
		sliderBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
		sliderPanel.setBorder(sliderBorder);
		
		//---SLIDER LISTENERS---//
		SliderListener listener = new SliderListener();
		c1.addChangeListener(listener);
		c2.addChangeListener(listener);
		speedLimit.addChangeListener(listener);
		meanFitness.addChangeListener(listener);
		
		//---BUTTON LISTENER---//
		scatterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (options.population != null) {
					options.population.scatter();
				}
			}
		});
		
		//---SET INITIAL VALUES---//
		c1.setValue(6);
		c2.setValue(1);
		speedLimit.setValue(20);
		meanFitness.setValue(50);
		
		//---CREATE PANEL---//
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(sliderPanel);
		mainPanel.add(scatterButton);
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.add(mainPanel);
		this.createFrame();
	}

	private class SliderListener implements ChangeListener{
	    public void stateChanged(ChangeEvent ce){
	    	if (ce.getSource() == c1){
	    		float val = (float) (c1.getValue() / 1000.0);
	    		_c1.setText("c1: " + val);
	    		options.c1 = val;
	    	}
	    	else if (ce.getSource() == c2){
	    		float val = (float) (c2.getValue() / 1000.0);
	    		_c2.setText("c2: " + val);
	    		options.c2 = val;
	    	}
	    	else if (ce.getSource() == speedLimit){
	    		_speedLimit.setText("speedLimit: " + speedLimit.getValue());
	    		options.speedLimit = speedLimit.getValue();
	    	}
	    	else if (ce.getSource() == meanFitness){
	    		_meanFitness.setText("meanFitness: " + meanFitness.getValue());
	    		options.convergenceThresh = meanFitness.getValue();
	    	}
	    }
	}
	
	private void createFrame () {
		JFrame imgFrame = new JFrame();
		imgFrame.add(this);
		imgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imgFrame.setSize(width, height);
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		imgFrame.pack();
		imgFrame.setLocation(0, 10);
		imgFrame.setVisible(true);
	}
}