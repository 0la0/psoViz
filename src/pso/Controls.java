package pso;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



@SuppressWarnings("serial")
public class Controls extends JPanel{

	private JPanel sliderPanel = new JPanel();
	private JSlider c1 = new JSlider(0, 100);
	private JLabel _c1 = new JLabel("c1");
	private JSlider c2 = new JSlider(0, 100);
	private JLabel _c2 = new JLabel("c2");
	private JSlider speedLimit = new JSlider(5, 50);
	private JLabel _speedLimit = new JLabel("speedLimit");
	private JSlider meanFitness = new JSlider(10, 100);
	private JLabel _meanFitness = new JLabel("meanFitness");

	private int width = 420;
	private int height = 100;
	
	private Options options;
	
	public Controls(Options options){
		this.options = options;
		
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
		
		
		
		SliderListener listener = new SliderListener();
		
		c1.addChangeListener(listener);
		c2.addChangeListener(listener);
		speedLimit.addChangeListener(listener);
		meanFitness.addChangeListener(listener);
		
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.add(sliderPanel);
		
		c1.setValue(20);
		c2.setValue(2);
		speedLimit.setValue(20);
		meanFitness.setValue(50);
		this.createFrame();
	}

	private class SliderListener implements ChangeListener{
	    public void stateChanged(ChangeEvent ce){
	    	if (ce.getSource() == c1){
	    		float val = (float) (c1.getValue() / 200.0);
	    		_c1.setText("c1: " + val);
	    		options.c1 = val;
	    	}
	    	else if (ce.getSource() == c2){
	    		float val = (float) (c2.getValue() / 200.0);
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