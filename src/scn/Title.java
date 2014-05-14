package scn;

import jog.*;

import run.Main;

public class Title extends Scene {
	
	public Title(Main main) {
		super(main);
	}

	@Override
	public void start() {
		graphics.setBackgroundColour(128, 192, 255);
	}

	@Override
	public void mousePressed(int key, int mx, int my) {

	}

	@Override
	public void mouseReleased(int key, int mx, int my) {

	}

	@Override
	public void keyPressed(int key) {
		if (key == input.KEY_SPACE) {
			main.setScene(new Game(main));
		} else if (key == input.KEY_BACKSLASH) {
			main.setScene(new LevelEditor(main));
		}
	}

	@Override
	public void keyReleased(int key) {

	}

	@Override
	public void update(double dt) {
		
	}

	@Override
	public void draw() {
		graphics.setColour(0, 0, 0);
		graphics.printCentred("Press [SPACE] to begin.", 0, 256, window.width());
	}

	@Override
	public void close() {

	}

}
