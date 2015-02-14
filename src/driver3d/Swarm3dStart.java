package driver3d;


import java.nio.FloatBuffer;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Swarm3dStart {
	
	private long lastFrame;
	private int fps;
	private long lastFPS;
	private boolean fpsIsOn = true;
	private SwarmWorld3D world = new SwarmWorld3D();
	
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
		//GLU.gluPerspective(fovy, aspect, zNear, zFar);
		GLU.gluPerspective(90, 16 / 9, 1, 1700);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		//GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void startLoop(){
		while (!Display.isCloseRequested()) {
			//int delta = getDelta();
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
		new Swarm3dStart();
	}

}
