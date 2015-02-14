package driver3d;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Cube implements RenderObject{

	private Vector3f position = new Vector3f();
	private Vector3f direction = new Vector3f();
	private float angle = 0;
	private double halfLength;
	private float angleMult;
	private float directionMult;
	
	private double r = 32.0;
	private double g = 0;
	private double b = 0.5;
	private int alpha = 1;
	public boolean isDead = false;
	
	public Cube (Vector3f position, int width){
		this.position = position;
		this.halfLength = width / 2;

		direction.x = (float) Math.random();
		direction.y = (float) Math.random();
		direction.z = (float) Math.random();
		angle = (float) Math.random();
		angleMult = (float) (0.05 * Math.random());
		directionMult = (float) (0.03 * Math.random());
	}
	
	public Cube (Vector3f position, double width, double r, double g, double b){
		this.position =  position;
		this.halfLength = width / 2;
		this.r = r;
		this.g = g;
		this.b = b;
		
		direction.x = (float) Math.random();
		direction.y = (float) Math.random();
		direction.z = (float) Math.random();
		angle = (float) Math.random();
		angleMult = (float) (0.05 * Math.random());
		directionMult = (float) (0.03 * Math.random());
	}
	
	public void setPosition (float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	
	@Override
	public void update(int elapsedTime) {
		angle += (float)elapsedTime * angleMult;
		position.x += direction.x * elapsedTime * directionMult;
		position.y += direction.y * elapsedTime * directionMult;
		position.z += direction.z * elapsedTime * directionMult;
	}
	
	public void setColor (double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
        GL11.glRotatef(angle, position.x, position.y, position.z);
        GL11.glColor4d(this.r, this.g, this.b, alpha);
        GL11.glBegin(GL11.GL_QUADS);
        {
            //Front
            //GL11.glNormal3f(0.0f, 0.0f, 1.0f * _invNorm);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z + halfLength);
            //Back
            //GL11.glNormal3f(0.0f, 0.0f, -1.0f * _invNorm);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z - halfLength);
            //LEFT
            //GL11.glNormal3f(-1.0f * _invNorm, 0.0f, 0.0f);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z + halfLength);
            //Right
            //GL11.glNormal3f(1.0f * _invNorm, 0.0f, 0.0f);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z + halfLength);
            //BOTTOM
            //GL11.glNormal3f(0.0f, -1.0f * _invNorm, 0.0f);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z - halfLength);
            //TOP
            //GL11.glNormal3f(0.0f, 1.0f * _invNorm, 0.0f);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z - halfLength);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
	}

}
