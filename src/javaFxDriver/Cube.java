package javaFxDriver;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;


public class Cube {
	
	private PhongMaterial material;
	private Box box;
	private Color color;
	private Color difuseColor = null;
	private Color specularColor = null;
	
	private Rotate rx = new Rotate(0, Rotate.X_AXIS);
	private Rotate ry = new Rotate(0, Rotate.Y_AXIS);
	private Rotate rz = new Rotate(0, Rotate.Z_AXIS);
	
	public Cube () {
		this.box = new Box(0, 0, 0);
		this.box.getTransforms().addAll(rz, ry, rx);
		this.setNewColor(Color.RED, Color.RED);
	}
	
	public Cube (int x, int y, int z) {
		this.box = new Box(x, y, z);
		this.box.getTransforms().addAll(rz, ry, rx);
		this.setNewColor(Color.RED, Color.RED);
	}
	
	public Cube (double x, double y, double z, PhongMaterial material) {
		this.box = new Box(x, y, z);
		this.box.getTransforms().addAll(rz, ry, rx);
		this.setMaterial(material);
	}
	
	
	public Cube (double x, double y, double z, Color difuseColor, Color specularColor) {
		this.box = new Box(x, y, z);
		this.box.getTransforms().addAll(rz, ry, rx);
		this.setNewColor(difuseColor, specularColor);
	}
	
	public void setNewColor (Color difuseColor, Color specularColor) {
		this.difuseColor = difuseColor;
		this.specularColor = specularColor;
		this.material = new PhongMaterial();
		this.material.setDiffuseColor(this.difuseColor);
		this.material.setSpecularColor(this.specularColor);
		this.box.setMaterial(this.material);
	}
	
	public void setMaterial (PhongMaterial material) {
		this.material = material;
		this.box.setMaterial(this.material);
	}
	
	public void translate (double x, double y, double z) {
		this.box.setTranslateX(x);
		this.box.setTranslateY(y);
		this.box.setTranslateZ(z);
	}
	
	public void setColor (double r, double g, double b) {
		if (r < 0) r = 0;
		else if (r > 1) r = 1;
		if (g < 0) g = 0;
		else if (g > 1) g = 1;
		if (b < 0) b = 0;
		else if (b > 1) b = 1;
		
		this.specularColor = Color.color(r, g, b);
		this.difuseColor = Color.color(r, g, b);
		this.material.setDiffuseColor(this.difuseColor);
		this.material.setSpecularColor(this.specularColor);
	}
	
	public Box getBox () {
		return this.box;
	}
	
	public Color getColor () {
		return this.color;
	}
	
	public void setRotate(double x, double y, double z) {
		this.rx.setAngle(x);
		this.ry.setAngle(y);
		this.rz.setAngle(z);
	}

}
