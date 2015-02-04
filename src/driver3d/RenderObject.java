package driver3d;


public interface RenderObject{
	public boolean getIsDead();
	public void update (int elapsedTime);
	public void render();
}
