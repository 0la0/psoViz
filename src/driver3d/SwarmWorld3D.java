package driver3d;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import pso.FitnessDistance;
import pso.IFitness;
import pso.Options;
import pso.Particle;
import pso.Population;
import pso.Position;


public class SwarmWorld3D {
	
	private ArrayList<RenderObject> gameObjs = new ArrayList<RenderObject>();
	private Vector3f cameraPosition = new Vector3f(-500, 0, -500);
	private double totElapsedTime = 0;
	//private ArrayList<Line> lines = new ArrayList<Line>();
	private Cube goalCube = new Cube(new Vector3f (0f, 0f, 0f), 50, 200, 0, 40);
	private ArrayList<Cube> cubes = new ArrayList<Cube>();
	
	private Population p;
	private IFitness fitnessFunction;
	private Options options = new Options();
	
	public SwarmWorld3D () {
		//swarm parameters
		this.options.c1 = 0.006f;
		this.options.c2 = 0.001f;
		this.options.speedLimit = 30.0f;
		
		Position size = new Position(new int[]{500, 500, 500, 500, 500, 500});
		this.fitnessFunction = new FitnessDistance(new int[]{0, 0, 0, 0, 0, 0});
		//Position size = new Position(new int[]{500, 500, 500});
		int populationSize = 300;
		this.p = new Population(size, populationSize, fitnessFunction, options);
		
		this.createBoundingBox ();
		
		//initialize cubes
		for (Particle particle : p.getParticles()) {
			/*
			cubes.add(new Cube(
					new Vector3f(
						(float) particle.getPosition().get()[0],
						(float) particle.getPosition().get()[1],
						(float) particle.getPosition().get()[2]),
						10.0, 0, 0, 1
				));
			*/
			cubes.add(new Cube(
				new Vector3f(
					(float) particle.getPosition().get()[0],
					(float) particle.getPosition().get()[1],
					(float) particle.getPosition().get()[2]),
					10.0,
					particle.getPosition().get()[3],
					particle.getPosition().get()[4],
					particle.getPosition().get()[5]
			));
		}

	}
	
	private void createBoundingBox () {
		//bottom
		gameObjs.add(new Line(
			new Vector3f(500f, 500f, -500f),
			new Vector3f(500f, -500f, -500f)
		));
		gameObjs.add(new Line(
			new Vector3f(500f, -500f, -500f),
			new Vector3f(-500f, -500f, -500f)
		));
		gameObjs.add(new Line(
			new Vector3f(-500f, -500f, -500f),
			new Vector3f(-500f, 500f, -500f)
		));
		gameObjs.add(new Line(
			new Vector3f(-500f, 500f, -500f),
			new Vector3f(500f, 500f, -500f)
		));
		
		//top
		gameObjs.add(new Line(
			new Vector3f(500f, 500f, 500f),
			new Vector3f(500f, -500f, 500f)
		));
		gameObjs.add(new Line(
			new Vector3f(500f, -500f, 500f),
			new Vector3f(-500f, -500f, 500f)
		));
		gameObjs.add(new Line(
			new Vector3f(-500f, -500f, 500f),
			new Vector3f(-500f, 500f, 500f)
		));
		gameObjs.add(new Line(
			new Vector3f(-500f, 500f, 500f),
			new Vector3f(500f, 500f, 500f)
		));
				
		//sides
		gameObjs.add(new Line(
			new Vector3f(500f, 500f, -500f),
			new Vector3f(500f, 500f, 500f)
		));
		gameObjs.add(new Line(
			new Vector3f(-500f, 500f, -500f),
			new Vector3f(-500f, 500f, 500f)
		));
		gameObjs.add(new Line(
			new Vector3f(500f, -500f, -500f),
			new Vector3f(500f, -500f, 500f)
		));
		gameObjs.add(new Line(
			new Vector3f(-500f, -500f, -500f),
			new Vector3f(-500f, -500f, 500f)
		));
	}
	
	private int getPosNeg () {
		if (Math.random() < 0.5)
			return -1;
		else 
			return 1;
	}
	
	public void update (int elapsedTime) {
		totElapsedTime += elapsedTime / 600.0;
		
		if (Math.random() < 0.005) {
			this.p.resetGoal( new int[]{
				(int) (500 * this.getPosNeg() * Math.random()),
				(int) (500 * this.getPosNeg() * Math.random()),
				(int) (500 * this.getPosNeg() * Math.random()),
				//---test---
				
				(int) (500 * this.getPosNeg() * Math.random()),
				(int) (500 * this.getPosNeg() * Math.random()),
				(int) (500 * this.getPosNeg() * Math.random())
				
			});
			this.goalCube.setPosition( 
				(float) this.fitnessFunction.getGoal()[0],
				(float) this.fitnessFunction.getGoal()[1],
				(float) this.fitnessFunction.getGoal()[2]
			);
			
			double goalR = (this.fitnessFunction.getGoal()[3] + 500) / 1000.0;
			double goalG = (this.fitnessFunction.getGoal()[4] + 500) / 1000.0;
			double goalB = (this.fitnessFunction.getGoal()[5] + 500) / 1000.0;
			this.goalCube.setColor(goalR, goalG, goalB);
			
		}
		/*
		for (RenderObject go : gameObjs){
			go.update(elapsedTime);
		}
		*/
		
		
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
			int[] position = particle.getPosition().get();
			//---SET POSITION---//
			cube.setPosition(
				(float) position[0], 
				(float) position[1], 
				(float) position[2]
			);
			//---SET COLOR---//
			
			double cubeR = (position[3] + 500) / 1000.0 + 0.0;
			double cubeG = (position[4] + 500) / 1000.0 + 0.0;
			double cubeB = (position[5] + 500) / 1000.0 + 0.0;
			cube.setColor(cubeR, cubeG, cubeB);
			
			//if (Math.random() < 0.001) {
			//	System.out.printf("\ncube color %3.4f, %3.4f, %3.4f", cubeR, cubeG, cubeB);
			//}
		}
	}

	public void render () {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0.75f, 0.8f, 0.9f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        
        updateCameraPos();
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
		//cameraPosition.y = (float) (500 * Math.cos(totElapsedTime));
		cameraPosition.z = (float) (900 * Math.cos(totElapsedTime / 4));
        GLU.gluLookAt(
        		cameraPosition.x, cameraPosition.y, cameraPosition.z,
        		0, 0, 0,
                0, 1, 0
        );
	}

}
