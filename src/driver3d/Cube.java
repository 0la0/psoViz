package driver3d;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class Cube implements RenderObject{

	private Vector3f position = new Vector3f();
	private Vector3f direction = new Vector3f();
	private float angle = 0;
	private double halfLength;
	private float angleMult;
	private float directionMult;
	
	private double r = 0.75;
	private double g = 0;
	private double b = 0.5;
	private int alpha = 1;
	public boolean isDead = false;
	private int ttl; //time to live
	private int totTimeElapsed = 0;
	
	public Cube (Vector3f position, int width){
		this.position = position;
		this.halfLength = width / 2;

		direction.x = (float) Math.random();
		direction.y = (float) Math.random();
		direction.z = (float) Math.random();
		angle = (float) Math.random();
		angleMult = (float) (0.05 * Math.random());
		directionMult = (float) (0.03 * Math.random());
		ttl = (int) (100 * Math.random());
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
		ttl = (int) (500 * Math.random());
	}
	
	public void setPosition (float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	
	@Override
	public void update(int elapsedTime) {
		totTimeElapsed += elapsedTime;
		angle += (float)elapsedTime * angleMult;
		position.x += direction.x * elapsedTime * directionMult;
		position.y += direction.y * elapsedTime * directionMult;
		position.z += direction.z * elapsedTime * directionMult;
		if (totTimeElapsed >= ttl){
			isDead = true;
		}
	}

	@Override
	public void render() {
		
		GL11.glPushMatrix();
        GL11.glRotatef(angle, position.x, position.y, position.z);

        GL11.glColor4d(r, g, b, alpha);
        
        GL11.glBegin(GL11.GL_QUADS);
        {
            //Front
            //GL11.glColor4d(_colors[0], _colors[1], _colors[2], alpha);
            //GL11.glNormal3f(0.0f, 0.0f, 1.0f * _invNorm);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z + halfLength);

            //Back
            //GL11.glColor4d(_colors[3], _colors[4], _colors[5], alpha);
            //GL11.glNormal3f(0.0f, 0.0f, -1.0f * _invNorm);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z - halfLength);

            //LEFT
            //GL11.glColor4d(_colors[6], _colors[7], _colors[8], alpha);
            //GL11.glNormal3f(-1.0f * _invNorm, 0.0f, 0.0f);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z + halfLength);

            //Right
            //.glColor4d(_colors[9], _colors[10], _colors[11], alpha);
            //GL11.glNormal3f(1.0f * _invNorm, 0.0f, 0.0f);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z + halfLength);

            //BOTTOM
            //GL11.glColor4d(_colors[12], _colors[13], _colors[14], alpha);
            //GL11.glNormal3f(0.0f, -1.0f * _invNorm, 0.0f);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y - halfLength, position.z - halfLength);

            //TOP
            //GL11.glColor4d(_colors[15], _colors[16], _colors[17], alpha);
            //GL11.glNormal3f(0.0f, 1.0f * _invNorm, 0.0f);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z - halfLength);
            GL11.glVertex3d(position.x - halfLength, position.y + halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z + halfLength);
            GL11.glVertex3d(position.x + halfLength, position.y + halfLength, position.z - halfLength);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
		
	}

	@Override
	public boolean getIsDead() {
		return isDead;
	}

}
