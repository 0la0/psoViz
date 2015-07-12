package javaFxDriver;

import java.util.ArrayList;
import java.util.Arrays;

import pso.FitnessDistance;
import pso.PsoConfigOptions;
import pso.Particle;
import pso.Population;
import pso.Position;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.SceneAntialiasing;

public class Basic3dDriver extends PopulationDriver {

	private Group root = new Group();
	private Group boundryGroup = new Group();
	private Xform world = new Xform();
	private PerspectiveCamera camera = new PerspectiveCamera(true);
	private Xform cameraXform = new Xform();
	private Xform cameraXform2 = new Xform();
	private Xform cameraXform3 = new Xform();
	private double cameraDistance = 2500;

	private Xform particleGroup = new Xform();
	private ArrayList<Cube> particles = new ArrayList<Cube>();
	private int size = 500;
	private int halfSize = size;

	private SubScene scene;
	private Cube goalCube = new Cube(20, 20, 20);
	private int particleSize = 70;
	
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;
	
	
	public Basic3dDriver(int[] searchSpaceDimensions, int[] initGoal, int numPopulations, int[] popSizes) {
		super(searchSpaceDimensions, initGoal, numPopulations, popSizes);
	
		//---swarm setup---//
		this.paramList = new String[]{"X", "Y", "Z", "Red", "Green", "Blue", "Alpha", "Beta", "Gamma"};
		this.numDimensions = 9; //TODO: Set dynamically
		this.paramMult = new double[this.numDimensions];
		Arrays.fill(this.paramMult, 1);
		
		Position size = new Position(new int[]{
			this.size, this.size, this.size, 
			this.size, this.size, this.size,
			this.size, this.size, this.size
		});
		this.fitnessFunction = new FitnessDistance(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0});
		
		//---POPULATION SETUP---//
		for (int i = 0; i < this.numPopulations; i++) {
			PsoConfigOptions options = new PsoConfigOptions();
			options.c1 = 0.006f;
			options.c2 = 0.001f;
			options.speedLimit = 25;
			Population p = new Population(size, popSizes[i], fitnessFunction, options);
			options.population = p;
			this.opts.add(options);
			this.populations.add(p);
		}
		
		//---UI SETUP---//
		root.getChildren().add(world);
		this.buildCamera();
		this.buildBoundries();
		this.buildParticles();

		this.scene = new SubScene(root, width, height, true, SceneAntialiasing.BALANCED);
		
		this.scene.setFill(Color.color(0.85, 0.85, 1.0));
		this.scene.setCamera(camera);
		this.handleMouse();
	}

	private void buildCamera() {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		cameraXform3.setRotateZ(180.0);

		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-cameraDistance);
		cameraXform.ry.setAngle(320.0);
	}

	//---BUILDS CUBE SKELETON FOR VISUAL SPATIAL REFERENCE---//
	private void buildBoundries() {
		PhongMaterial blackMaterial = new PhongMaterial();
		blackMaterial.setDiffuseColor(Color.BLACK);
		blackMaterial.setSpecularColor(Color.BLACK);

		double lineWidth = 1.0;
		ArrayList<Cube> bounds = new ArrayList<Cube>();

		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(size * 2, lineWidth, lineWidth, blackMaterial));
		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(lineWidth, size * 2, lineWidth, blackMaterial));
		for (int i = 0; i < 4; i++)
			bounds.add(new Cube(lineWidth, lineWidth, size * 2, blackMaterial));

		bounds.get(0).translate(0, halfSize, halfSize);
		bounds.get(1).translate(0, halfSize, -halfSize);
		bounds.get(2).translate(0, -halfSize, halfSize);
		bounds.get(3).translate(0, -halfSize, -halfSize);

		bounds.get(4).translate(halfSize, 0, halfSize);
		bounds.get(5).translate(halfSize, 0, -halfSize);
		bounds.get(6).translate(-halfSize, 0, halfSize);
		bounds.get(7).translate(-halfSize, 0, -halfSize);

		bounds.get(8).translate(halfSize, halfSize, 0);
		bounds.get(9).translate(halfSize, -halfSize, 0);
		bounds.get(10).translate(-halfSize, halfSize, 0);
		bounds.get(11).translate(-halfSize, -halfSize, 0);

		for (Cube bound : bounds) {
			boundryGroup.getChildren().add(bound.getBox());
		}
		boundryGroup.getChildren().add(this.goalCube.getBox());
		world.getChildren().addAll(boundryGroup);
	}

	private void buildParticles () {
		for (Population p : populations) {
			for (Particle particle : p.getParticles()) {
				Color color = Color.color(
					particle.getPosition().getVector()[3] / (this.size * 1.0),
					particle.getPosition().getVector()[4] / (this.size * 1.0),
					particle.getPosition().getVector()[5] / (this.size * 1.0)
				);
				Cube box = new Cube(this.particleSize, this.particleSize, this.particleSize, color, color);
				box.translate(
					particle.getPosition().getVector()[0],
					particle.getPosition().getVector()[1],
					particle.getPosition().getVector()[2]
				);
				particles.add(box);
			}
		}
	
		for (Cube particle : particles) {
			this.particleGroup.getChildren().addAll(particle.getBox());
		}
		this.world.getChildren().addAll(this.particleGroup);
	}

	private int getPosNeg () {
		return (Math.random() < 0.5) ? 1 : -1;
	}

	@Override
	public void update (float elapsedTime) {
		//---CHANGE GOAL---//
		if (Math.random() < 0.01) {
			int[] newGoal = new int[this.numDimensions];
			for (int i = 0; i < newGoal.length; i++) {
				newGoal[i] = (int) (this.size * this.getPosNeg() * Math.random());
			}
			for (Population p : populations) {
				p.resetGoal(newGoal);
			}
			this.goalCube.translate(
				this.fitnessFunction.getGoal()[0],
				this.fitnessFunction.getGoal()[1],
				this.fitnessFunction.getGoal()[2]
			);
			
			double goalR = (this.fitnessFunction.getGoal()[3] + this.size) / (this.size * 2.0);
			double goalG = (this.fitnessFunction.getGoal()[4] + this.size) / (this.size * 2.0);
			double goalB = (this.fitnessFunction.getGoal()[5] + this.size) / (this.size * 2.0);
			this.goalCube.setColor(goalR, goalG, goalB);
			
			double rotX = ((this.fitnessFunction.getGoal()[6] + this.size) / (this.size * 2.0) * 360);
			double rotY = ((this.fitnessFunction.getGoal()[7] + this.size) / (this.size * 2.0) * 360);
			double rotZ = ((this.fitnessFunction.getGoal()[8] + this.size) / (this.size * 2.0) * 360);
			this.goalCube.setRotate(rotX, rotY, rotZ);
		}
		
		int totParticleCnt = 0;
		for (Population p : populations) {
			p.update();
			
			for (int i = totParticleCnt; i < totParticleCnt + p.getParticles().size(); i++) {
				Particle particle = p.getParticles().get(i - totParticleCnt);
				Cube cube = particles.get(i);
				int[] position = particle.getPosition().getVector();
				//---SET POSITION---//
				cube.translate(position[0], position[1], position[2]);
				//---SET COLOR---//
				double cubeR = ((position[3] + this.size) / (this.size * 2.0));
				double cubeG = ((position[4] + this.size) / (this.size * 2.0));
				double cubeB = ((position[5] + this.size) / (this.size * 2.0));;
				cube.setColor(cubeR, cubeG, cubeB);
				
				double rotX = (((position[6] + this.size) / (this.size * 2.0) * 360));
				double rotY = (((position[7] + this.size) / (this.size * 2.0) * 360));
				double rotZ = (((position[8] + this.size) / (this.size * 2.0) * 360));
				cube.setRotate(rotX, rotY, rotZ);
			}
			
			totParticleCnt += p.getParticles().size();
		}
		
	}
	
	@Override
	public Node getUiNode () {
		return this.scene;
	}
	
	public void setFullscreen (boolean isFullscreen, double w, double h) {
		if (isFullscreen) {
			this.scene.setWidth(w);
			this.scene.setHeight(h);
		}
		else {
			this.scene.setWidth(width);
			this.scene.setHeight(height);
		}
	}
	
	public String toString () {
		return "basic3D";
	}
	
	/*
	 * 3D camera rotate on mouse drag
	 * from http://docs.oracle.com/javafx/8/3d_graphics/sampleapp.htm
	 */
	public void handleMouse() {
		
		scene.setOnMousePressed((MouseEvent e) -> {
			mousePosX = e.getSceneX();
			mousePosY = e.getSceneY();
			mouseOldX = e.getSceneX();
			mouseOldY = e.getSceneY();
		});
		
		scene.setOnMouseDragged((MouseEvent e) -> {
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			mousePosX = e.getSceneX();
			mousePosY = e.getSceneY();
			mouseDeltaX = (mousePosX - mouseOldX);
			mouseDeltaY = (mousePosY - mouseOldY);

			double modifier = 1.0;
			double modifierFactor = 0.1;

			if (e.isControlDown()) {
				modifier = 0.1;
			}
			if (e.isShiftDown()) {
				modifier = 10.0;
			}
			if (e.isPrimaryButtonDown()) {
				cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
				cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
			} else if (e.isSecondaryButtonDown()) {
				double z = camera.getTranslateZ();
				double newZ = z + mouseDeltaX * modifierFactor * modifier;
				camera.setTranslateZ(newZ);
			} else if (e.isMiddleButtonDown()) {
				cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
				cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
			}
		});
		
	}

}