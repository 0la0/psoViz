package driver3d;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import pso.Controls;
import pso.Options;

public class Swarm3dStart {
	
	private long lastFrame;
	private int fps;
	private long lastFPS;
	private boolean fpsIsOn = true;
	//private static SwarmWorld3D world;
	private static DoubleSwarmWorld3D world;

	public Swarm3dStart () {
		init3dDisplay();
		getDelta();
		lastFPS = getTime();
		startLoop();	
	}
	
	private void init3dDisplay () {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(90, 16 / 9, 1, 1700);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
	}
	
	private void startLoop(){
		while (!Display.isCloseRequested()) {
			if (fpsIsOn){
				updateFPS();
			}
			world.update(getDelta());
			world.render();
			
			Display.update();
			Display.sync(60); 
		}
		Display.destroy();
	}
	
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public static void main (String[] args) {
		Options options = new Options();
		//options.c1 = 0.1f;
		//options.c2 = 0.0001f;
		//options.speedLimit = 10.0f;
		
		Controls c = new Controls(options);
		//world = new SwarmWorld3D(options);
		world = new DoubleSwarmWorld3D(options);
		new Swarm3dStart();
	}

}
