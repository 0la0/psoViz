package driver3d_javafx;
import java.util.ArrayList;

import pso.FitnessDistance;
import pso.IFitness;
import pso.Options;
import pso.Particle;
import pso.Population;
import pso.Position;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.PerspectiveCamera;
import javafx.scene.paint.PhongMaterial;
import javafx.animation.AnimationTimer;
//import javafx.scene.*;


public class Init3D extends Application {

    private Group root = new Group();
    private Group boundryGroup = new Group();
    private Xform world = new Xform();
    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private Xform cameraXform = new Xform();
    private Xform cameraXform2 = new Xform();
    private Xform cameraXform3 = new Xform();
    private double cameraDistance = 1100;
    
    private Xform particleGroup = new Xform();
    private ArrayList<Cube> particles = new ArrayList<Cube>();
    private int size = 250;
    private int halfSize = size;
    private AnimationTimer timer;
    private long lastTime = System.nanoTime();
    private boolean timerIsOn = false;
    
    private Population p;
	private IFitness fitnessFunction;
	private Options options = new Options();
	private Cube goalCube = new Cube(20, 20, 20);
    

    private void buildScene() {
        root.getChildren().add(world);
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
        //cameraXform.rx.setAngle(40);
    }

    private void buildBoundries() {
        PhongMaterial blackMaterial = new PhongMaterial();
        blackMaterial.setDiffuseColor(Color.BLACK);
        blackMaterial.setSpecularColor(Color.BLACK);        
        
        double lineWidth = 0.5;
        
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
    	
    	for (Particle particle : p.getParticles()) {
    		Color color = Color.color(
    			particle.getPosition().get()[3] / (this.size * 1.0),
    			particle.getPosition().get()[4] / (this.size * 1.0),
    			particle.getPosition().get()[5] / (this.size * 1.0)
    		);
    		Cube box = new Cube(5, 5, 5, color, color);
    		box.translate(
    			particle.getPosition().get()[0],
    			particle.getPosition().get()[1],
    			particle.getPosition().get()[2]
    		);
    		particles.add(box);
    	}
    	
        for (Cube particle : particles) {
        	this.particleGroup.getChildren().addAll(particle.getBox());
        }
        this.world.getChildren().addAll(this.particleGroup);
    }
    
    private int getPosNeg () {
    	return (Math.random() < 0.5) ? 1 : -1;
    }
    
    public void update (float elapsedTime) {
		//---CHANGE GOAL---//
		if (Math.random() < 0.01) {
			int[] newGoal = new int[6];
			for (int i = 0; i < newGoal.length; i++) {
				newGoal[i] = (int) (this.size * this.getPosNeg() * Math.random());
			}
			this.p.resetGoal(newGoal);
			this.goalCube.translate(
				this.fitnessFunction.getGoal()[0],
				this.fitnessFunction.getGoal()[1],
				this.fitnessFunction.getGoal()[2]
			);
			
			double goalR = (this.fitnessFunction.getGoal()[3] + this.size) / (this.size * 2.0);
			double goalG = (this.fitnessFunction.getGoal()[4] + this.size) / (this.size * 2.0);
			double goalB = (this.fitnessFunction.getGoal()[5] + this.size) / (this.size * 2.0);
			this.goalCube.setColor(goalR, goalG, goalB);
			
		}
		
		double meanFitness = p.update();
		
		//---set the cube position from the particle position---//
		for (int i = 0; i < this.p.getParticles().size(); i++) {
			Particle particle = this.p.getParticles().get(i);
			Cube cube = particles.get(i);
			int[] position = particle.getPosition().get();
			//---SET POSITION---//
			cube.translate(position[0], position[1], position[2]);
			//---SET COLOR---//
			double cubeR = (position[3] + this.size) / (this.size * 2.0);
			double cubeG = (position[4] + this.size) / (this.size * 2.0);
			double cubeB = (position[5] + this.size) / (this.size * 2.0);;
			cube.setColor(cubeR, cubeG, cubeB);
		}
	}

    @Override
    public void start(Stage primaryStage) {
    	
    	//swarm parameters
    	this.options.c1 = 0.1f;
    	this.options.c2 = 0.01f;
    	this.options.speedLimit = 25;
    	
    	//swarm setup
    	Position size = new Position(new int[]{
    		this.size, this.size, this.size, this.size, this.size, this.size});
    	this.fitnessFunction = new FitnessDistance(new int[]{0, 0, 0, 0, 0, 0});
    	int populationSize = 1000;
    	this.p = new Population(size, populationSize, fitnessFunction, options);
    	this.options.population = p;
    	
    	
        this.buildScene();
        this.buildCamera();
        this.buildBoundries();
        this.buildParticles();

        //Scene scene = new Scene(root, 900, 600, true, SceneAntialiasing.BALANCED);
        Scene scene = new Scene(root, 900, 600, true);
        scene.setFill(Color.GREY);
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	float elapsedTime = (float) ((now - lastTime) / 1000000.0);
            	lastTime = now;
            	update(elapsedTime);
            }
        };
        
        scene.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.SPACE)) {
        		if (timerIsOn) {
                	System.out.println("pause");
                    timer.stop();
                	timerIsOn = false;
                } else {
                	System.out.println("play");
                    timer.start();
                	timerIsOn = true;
                }
        	}
        });
        
        InteractionHelper ih = new InteractionHelper(camera, cameraXform, cameraXform2);
        ih.handleMouse(scene, world);
        
        primaryStage.setTitle("Boxes and Boxes");
        primaryStage.setScene(scene);
        primaryStage.show();
        //primaryStage.setFullScreen(true);

        scene.setCamera(camera);
        //System.out.println(scene.getAntiAliasing());
    }

    public static void main (String[] args) {
        System.setProperty("prism.dirtyopts", "false");
        launch(args);
    }
}