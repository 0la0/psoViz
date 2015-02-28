package javaFxDriver;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PopSizePane {
	
	//private int size = 100;
	private Label label = new Label();
	private TextField textField = new TextField("100");
	
	public PopSizePane (int index) {
		label.setText("Population " + index + " size: ");
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
			"-fx-background-color: #ffffff;" +
			"-fx-border-color: #333333;" + 
			"-fx-border-width: 2px;" +
			"-fx-border-radius: 4px;"
		);
		return parentPane;
	}

}
