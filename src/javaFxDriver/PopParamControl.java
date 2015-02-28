package javaFxDriver;

import pso.Population;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/*
 * PopParamControl contains the UI controls for
 * each population's dimension representation and controller
 */
public class PopParamControl {
	
	private GridPane mainPane = new GridPane();
	private PopulationManager popMngr;
	
	public PopParamControl (PopulationManager popMngr, int index) {
		this.popMngr = popMngr;
		this.mainPane.setPadding(new Insets(10, 10, 10, 10));
		this.mainPane.setVgap(10);
		this.mainPane.setHgap(6);
		this.mainPane.setMinWidth(250);
		
		this.mainPane.setStyle("" +
			"-fx-background-color: #aaaadd;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);
		this.rebuildPane(index);
	}
	
	public Pane rebuildPane (int index) {
		this.mainPane.getChildren().clear();
		Population pop = popMngr.getActiveDriver().getPopulation(index);
		for (int i = 0; i < this.popMngr.getActiveDriver().getNumDims(); i++) {
			Param p = new Param(i, this.popMngr.getActiveDriver().getParamLabel(i));
			p.addToPane(this.mainPane, i);
			p.slider.valueProperty().addListener( 
				(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					double val = (double) new_val.doubleValue();
					pop.setDimWeight(p.index, val);
					p.value.setText(String.format("%.2f", val));
			});
		}
		return this.mainPane;
	}
	
	public Pane getPane () {
		return this.mainPane;
	}
	
	private class Param {
		
		public Label label = new Label();
		public Slider slider = new Slider(0, 1, 1);
		public Label value = new Label();
		public int index;
		
		public Param (int index, String label) {
			this.index = index;
			this.label.setText(label);
			this.value.setText(1 + "");
		}
		
		public void addToPane (GridPane gp, int row) {
			gp.add(this.label, 0, row);
			gp.add(this.slider, 1, row);
			gp.add(this.value, 2, row);
		}
	
	}
	

}
