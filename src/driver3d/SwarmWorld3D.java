package driver3d;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import pso.FitnessDistance;
import pso.IFitness;
import pso.PsoConfigOptions;
import pso.Particle;
import pso.Population;
import pso.Position;


public class SwarmWorld3D {
	
	private ArrayList<RenderObject> gameObjs = new ArrayList<RenderObject>();
	private Vector3f cameraPosition = new Vector3f(-500, 0, -500);
	private double totElapsedTime = 0;
	private Cube goalCube = new Cube(new Vector3f (0f, 0f, 0f), 50, 0, 0, 0);
	private ArrayList<Cube> cubes = new ArrayList<Cube>();
	private Population p;
	private IFitness fitnessFunction;
	private PsoConfigOptions options = new PsoConfigOptions();
	private int searchSpaceSize = 500;
	private int doubleSpace = 1000;
	
	public SwarmWorld3D (PsoConfigOptions options) {
		//swarm parameters
		this.options = options;
		
		Position size = new Position(new int[]{
			this.searchSpaceSize, this.searchSpaceSize, this.searchSpaceSize,
			this.searchSpaceSize, this.searchSpaceSize, this.searchSpaceSize
		});
		this.fitnessFunction = new FitnessDistance(new int[]{0, 0, 0, 0, 0, 0});
		int populationSize = 300;
		this.p = new Population(size, populationSize, fitnessFunction, options);
		this.options.population = p;
		this.createBoundingBox ();
		
		//initialize cubes
		for (Particle particle : p.getParticles()) {
			cubes.add(new Cube(
				new Vector3f(
					(float) particle.getPosition().getVector()[0],
					(float) particle.getPosition().getVector()[1],
					(float) particle.getPosition().getVector()[2]),
					10.0,
					particle.getPosition().getVector()[3],
					particle.getPosition().getVector()[4],
					particle.getPosition().getVector()[5]
			));
		}

	}
	
	private int getPosNeg () {
		if (Math.random() < 0.5)
			return -1;
		else 
			return 1;
	}
	
	public void update (int elapsedTime) {
		totElapsedTime += elapsedTime / 600.0;
		
		if (Math.random() < 0.01) {
			int[] newGoal = new int[6];
			for (int i = 0; i < newGoal.length; i++) {
				newGoal[i] = (int) (this.searchSpaceSize * this.getPosNeg() * Math.random());
			}
			this.p.resetGoal(newGoal);
			this.goalCube.setPosition( 
				(float) this.fitnessFunction.getGoal()[0],
				(float) this.fitnessFunction.getGoal()[1],
				(float) this.fitnessFunction.getGoal()[2]
			);
			
			double goalR = (this.fitnessFunction.getGoal()[3] + this.searchSpaceSize) / (this.doubleSpace * 1.0);
			double goalG = (this.fitnessFunction.getGoal()[4] + this.searchSpaceSize) / (this.doubleSpace * 1.0);
			double goalB = (this.fitnessFunction.getGoal()[5] + this.searchSpaceSize) / (this.doubleSpace * 1.0);
			this.goalCube.setColor(goalR, goalG, goalB);
			
		}
		
		double meanFitness = p.update();
		/*
		if (meanFitness < this.options.convergenceThresh) {
			this.p.resetPosAndVel();
		}
		*/
		//---set the cube position from the particle position---//
		for (int i = 0; i < this.p.getParticles().size(); i++) {
			Particle particle = this.p.getParticles().get(i);
			Cube cube = cubes.get(i);
			int[] position = particle.getPosition().getVector();
			//---SET POSITION---//
			cube.setPosition(
				(float) position[0], 
				(float) position[1], 
				(float) position[2]
			);
			//---SET COLOR---//
			double cubeR = (position[3] + this.searchSpaceSize) / (this.doubleSpace * 1.0);
			double cubeG = (position[4] + this.searchSpaceSize) / (this.doubleSpace * 1.0);
			double cubeB = (position[5] + this.searchSpaceSize) / (this.doubleSpace * 1.0);;
			cube.setColor(cubeR, cubeG, cubeB);
		}
	}

	public void render () {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0.75f, 0.8f, 0.9f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        
        this.updateCameraPos();
        for (RenderObject go : gameObjs){
			go.render();
		}
        this.goalCube.render();
        for (Cube c : cubes) {
        	c.render();
        }
	}
	
	private void updateCameraPos(){
		cameraPosition.x = (float) (900 * Math.sin(totElapsedTime / 4));
		//cameraPosition.y = (float) (900 * Math.cos(totElapsedTime / 4));
		cameraPosition.z = (float) (900 * Math.cos(totElapsedTime / 4));
        GLU.gluLookAt(
        		cameraPosition.x, cameraPosition.y, cameraPosition.z,
        		0, 0, 0,
                0, 1, 0
        );
	}
	
	private void createBoundingBox () {
		//bottom
		gameObjs.add(new Line(
			new Vector3f((float) this.searchSpaceSize, (float) this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) this.searchSpaceSize, (float) -this.searchSpaceSize, (float) -this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) this.searchSpaceSize, (float) -this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) -this.searchSpaceSize, (float) -this.searchSpaceSize, (float) -this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) -this.searchSpaceSize, (float) -this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) -this.searchSpaceSize, (float) this.searchSpaceSize, (float) -this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) -this.searchSpaceSize, (float) this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) this.searchSpaceSize, (float) this.searchSpaceSize, (float) -this.searchSpaceSize)
		));
		
		//top
		gameObjs.add(new Line(
			new Vector3f((float) this.searchSpaceSize, (float) this.searchSpaceSize, (float) this.searchSpaceSize),
			new Vector3f((float) this.searchSpaceSize, (float) -this.searchSpaceSize, (float) this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) this.searchSpaceSize, (float) -this.searchSpaceSize, (float) this.searchSpaceSize),
			new Vector3f((float) -this.searchSpaceSize, (float) -this.searchSpaceSize, (float) this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) -this.searchSpaceSize, (float) -this.searchSpaceSize, (float) this.searchSpaceSize),
			new Vector3f((float) -this.searchSpaceSize, (float) this.searchSpaceSize, (float) this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) -this.searchSpaceSize, (float) this.searchSpaceSize, (float) this.searchSpaceSize),
			new Vector3f((float) this.searchSpaceSize, (float) this.searchSpaceSize, (float) this.searchSpaceSize)
		));
				
		//sides
		gameObjs.add(new Line(
			new Vector3f((float) this.searchSpaceSize, (float) this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) this.searchSpaceSize, (float) this.searchSpaceSize, (float) this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) -this.searchSpaceSize, (float) this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) -this.searchSpaceSize, (float) this.searchSpaceSize, (float) this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) this.searchSpaceSize, (float) -this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) this.searchSpaceSize, (float) -this.searchSpaceSize, (float) this.searchSpaceSize)
		));
		gameObjs.add(new Line(
			new Vector3f((float) -this.searchSpaceSize, (float) -this.searchSpaceSize, (float) -this.searchSpaceSize),
			new Vector3f((float) -this.searchSpaceSize, (float) -this.searchSpaceSize, (float) this.searchSpaceSize)
		));
	}

}
