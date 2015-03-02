package javaFxDriver;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/*
 * PopSize Pane is a horizontal pane that contains
 * a label and a population size
 */
public class PopSizePane {
	
	private Label label = new Label();
	private TextField textField = new TextField("100");
	
	public PopSizePane (int index) {
		this.label.setText("Population " + index + " size: ");
		this.textField.setMaxWidth(50);
		
		this.label.setStyle("" +
			"-fx-margin-left: 10px;"
		);
	}
	
	public int getSize () {
		return Integer.parseInt(this.textField.getText());
	}
	
	public void setSize (int size) {
		this.textField.setText(size + "");
	}
	
	public Pane getPane () {
		HBox parentPane = new HBox(this.label, this.textField);
		parentPane.setStyle("" +
			"-fx-padding: 2px 0px 2px 20px;" +
			"-fx-background-color: #cccccc;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);
		return parentPane;
	}

}
