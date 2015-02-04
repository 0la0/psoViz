package driver3d;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Line implements RenderObject{

	private Vector3f startPos;
	private Vector3f endPos;
	
	public Line () {
		this.startPos = new Vector3f(0.0f, 0.0f, 0.0f);
		this.endPos = new Vector3f(500f, 500f, 500f);
	}
	
	public Line (Vector3f startPos, Vector3f endPos) {
		this.startPos = startPos;
		this.endPos = endPos;
	}
	
	public void setStart (Vector3f startPos) {
		this.startPos = startPos;
	}
	
	public void setEnd (Vector3f endPos) {
		this.endPos = endPos;
	}
	
	@Override
	public void update(int elapsedTime) {
		
	}

	@Override
	public void render() {
		GL11.glLineWidth(3f); 
		GL11.glColor3f(0.0f, 100.0f, 0.0f);
		GL11.glBegin(GL11.GL_LINES);
		{
			GL11.glVertex3f(startPos.x, startPos.y, startPos.z);
			GL11.glVertex3f(endPos.x, endPos.y, endPos.z);
		}
		GL11.glEnd();
	}

	@Override
	public boolean getIsDead() {
		// TODO Auto-generated method stub
		return false;
	}

}
