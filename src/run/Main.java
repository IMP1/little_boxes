package run;

import org.lwjgl.Sys;

import cls.Score;
import jog.*;

public class Main implements jog.input.EventHandler {
	
	public static void main(String[] args) {
		new Main();
	}
	
	final private String TITLE = "Little Boxes";
	final private int WIDTH = 960;
	final private int HEIGHT = 640;
	final private String[] ICONS = {
		"icon16.png", // 16
		"icon32.png", // 32
		"icon64.png", // 64
	};
	
	private double lastFrameTime;
	private double dt;
	private java.util.Stack<scn.Scene> sceneStack;
	private scn.Scene currentScene;
	
	public Score score;
	
	public Main() {
		lastFrameTime = (double)(Sys.getTime()) / Sys.getTimerResolution();
		window.initialise(TITLE, WIDTH, HEIGHT);
		window.setIcon(ICONS);
		graphics.initialise();
		sceneStack = new java.util.Stack<scn.Scene>();
		start();
		while(!window.isClosed()) {
			dt = getDeltaTime();
			update(dt);
			draw();
		}
		quit();
	}
	
	private void start() {
		score = new Score();
		graphics.Font font = graphics.newBitmapFont("font.png", "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz1234567890.,_-!?()[]><#~:;/\\^'\"{}£$+=@@@@@@@");
		graphics.setFont(font);
		setScene(new scn.Title(this));
	}
	
	public void setScene(scn.Scene newScene) {
		if (currentScene != null) currentScene.close();
		while (!sceneStack.empty()) {
			sceneStack.pop();
		}
		addScene(newScene);
	}
	
	public void addScene(scn.Scene newScene) {
		sceneStack.push(newScene);
		currentScene = sceneStack.peek();
		currentScene.start();
	}
	
	public void closeScene() {
		currentScene.close();
		sceneStack.pop();
		currentScene = sceneStack.peek();
	}
	
	private void update(double dt) {
		audio.update();
		input.update(this);
		window.update();
//		currentScene.update(dt);
		for (scn.Scene scene : sceneStack) {
			scene.update(dt);
		}
	}
	
	private void draw() {
		graphics.clear();
		graphics.setColour(255, 255, 255);
		for (scn.Scene scene : sceneStack) {
			scene.draw();
		}
	}
	
	private void quit() {
		window.dispose();
		audio.dispose();
	}

	@Override
	public void mousePressed(int key, int x, int y) {
		currentScene.mousePressed(key, x, y);
	}

	@Override
	public void mouseReleased(int key, int x, int y) {
		currentScene.mouseReleased(key, x, y);
	}

	@Override
	public void keyPressed(int key) {
		if (key == input.KEY_R && input.isKeyDown(input.KEY_LCRTL)) { currentScene.start(); }
		currentScene.keyPressed(key);
	}

	@Override
	public void keyReleased(int key) {
		currentScene.keyReleased(key);
	}

	/**
	 * Calculates the time taken since the last tick in seconds as a double-precision floating point n)umber.
	 * @return the time in seconds since the last frame.
	 */
	private double getDeltaTime() {
		double time = (double)(Sys.getTime()) / Sys.getTimerResolution();
	    double delta = (time - lastFrameTime);
	    lastFrameTime = time;
	    return delta;
	}
	
}
