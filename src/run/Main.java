package run;

import cls.Score;
import jog.*;
import jog.input.InputEventHandler;

public class Main implements InputEventHandler {
	
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
	
	private double dt;
	private java.util.Stack<scn.Scene> sceneStack;
	private scn.Scene currentScene;
	
	public Score score;
	
	public Main() {
		window.initialise(TITLE, WIDTH, HEIGHT, 60);
		filesystem.addLocation("src");
		window.setIcon(ICONS);
		graphics.initialise();
		sceneStack = new java.util.Stack<scn.Scene>();
		start();
		while(!window.isClosed()) {
			dt = window.getDeltaTime();
			update(dt);
			draw();
		}
		quit();
	}
	
	private void start() {
		score = new Score();
		font.Font f = font.newBitmapFont("font.png", "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz1234567890.,_-!?()[]><#~:;/\\^'\"{}£$+=@@@@@@@");
		graphics.setFont(f);
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
	
}
