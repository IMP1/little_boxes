package scn;

public abstract class Scene implements jog.input.EventHandler {

	protected run.Main main;
	
	protected Scene(run.Main main) {
		this.main = main;
	}
	
	abstract public void start();
	
	abstract public void update(double dt);
	
	abstract public void draw();
	
	abstract public void close();

}
