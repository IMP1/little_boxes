package run;

import java.io.File;

import org.lwjgl.Sys;

import jog.*;

public class Main implements jog.input.EventHandler {
	
	public static void main(String[] args) {
		new Main();
	}
	
	final private String TITLE = "Heroes";
	final private int WIDTH = 960;
	final private int HEIGHT = 640;
	
	private double lastFrameTime;
	private double dt;
	private scn.Scene currentScene;
	
	public Main() {
		start();
		while(!window.isClosed()) {
			dt = getDeltaTime();
			update(dt);
			draw();
		}
		quit();
	}
	
	private void start() {
		lastFrameTime = (double)(Sys.getTime()) / Sys.getTimerResolution();
		window.initialise(TITLE, WIDTH, HEIGHT);
		graphics.initialise();
		graphics.Font font = graphics.newBitmapFont("font.png", "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz1234567890.,_-!?()[]><#~:;/\\^'\"{}£$@@@@@@@@");
		graphics.setFont(font);
		currentScene = new scn.Village(this);
		currentScene.start();
	}
	
	private void update(double dt) {
		audio.update();
		input.update(this);
		window.update();
		currentScene.update(dt);
	}
	
	private void draw() {
		graphics.clear();
		graphics.setColour(255, 255, 255);
		currentScene.draw();
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
	 * Calculates the time taken since the last tick in seconds as a double-precision floating point number.
	 * @return the time in seconds since the last frame.
	 */
	private double getDeltaTime() {
		double time = (double)(Sys.getTime()) / Sys.getTimerResolution();
	    double delta = (time - lastFrameTime);
	    lastFrameTime = time;
	    return delta;
	}
	
}
