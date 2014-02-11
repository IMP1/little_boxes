package scn;

import jog.*;
import cls.Map;
import cls.Player;
import run.Main;

public class LevelPreview extends Game {

	private boolean winState;
	
	public LevelPreview(Main main, Map map) {
		super(main);
		this.map = map;
	}

	@Override
	public void start() {
		player = new Player();
		player.setPosition(map.playerStartX, map.playerStartY);
		winState = false;
	}
	
	@Override
	public void keyPressed(int key) {
		if (key == input.KEY_ESCAPE) {
			main.closeScene();
			return;
		}
		if (key == input.KEY_SPACE && winState) {
			main.closeScene();
			return;
		}
		if (key == input.KEY_R) {
			start();
			return;
		}
		super.keyPressed(key);
	}
	
	@Override
	public void draw() {
		graphics.setColour(graphics.getBackgroundColour());
		graphics.rectangle(true, 0, 0, window.width(), window.height());
		graphics.setColour(255, 255, 255);
		map.draw(player);
		if (winState) {
			graphics.printCentred("Press [SPACE] to return to level editor.", 0, 32, 1, window.width());
		}
		graphics.printCentred("Level Testing", 0, 8, 1, window.width());
	}

	@Override
	public void close() {}

}
