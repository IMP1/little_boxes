package scn;

import jog.graphics;
import jog.input;
import jog.window;
import cls.Box;
import cls.Button;
import cls.Map;
import cls.Player;
import run.Main;

public class LevelPreview extends Game {

	private Map map;
	private boolean winState;
	private Player player;
	
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
	public void mousePressed(int key, int x, int y) {}

	@Override
	public void mouseReleased(int key, int x, int y) {}

	@Override
	public void keyPressed(int key) {
		if (key == input.KEY_ESCAPE) {
			main.closeScene();
		}
		if (key == input.KEY_SPACE && winState) {
			main.closeScene();
		}
		if (key == input.KEY_R) {
			start();
		}
		
		if (winState) return;
		
		int dx = 0, dy = 0;
		if (key == input.KEY_UP && playerCanMove(0, -1)) dy --;
		if (key == input.KEY_DOWN && playerCanMove(0, 1)) dy ++;
		if (key == input.KEY_LEFT && playerCanMove(-1, 0)) dx --;
		if (key == input.KEY_RIGHT && playerCanMove(1, 0)) dx ++;
		
		int smallerThan = -1;
		for (Box box : map.boxes) {
			if (box.x() == player.x() && box.y() == player.y() && box.solidFrom(dx, dy)) {
				smallerThan = Math.max(smallerThan, box.size);
			}
		}
		for (Box box : map.boxes) {
			if (box.x() == player.x() && box.y() == player.y() && box.size <= smallerThan) {
				box.moveBy(dx, dy);
			}
		}
		
		player.moveBy(dx, dy);

		for (Button button : map.buttons) {
			if (button.x == player.x() && button.y == player.y()) {
				button.act(map);
			}
		}
		
		checkWinState();
	}

	private boolean playerCanMove(int dx, int dy) {
		int newX = player.x() + dx;
		int newY = player.y() + dy;
		// Not in map
		if (!map.passable(newX, newY)) {
			return false;
		}
		// Find smallest box we're in
		Box boxIn = null;
		for (Box box : map.boxes) {
			if (box.x() == player.x() && box.y() == player.y()) {
				if (boxIn == null || box.size < boxIn.size ) {
					boxIn = box;
				}
			}
		}
		for (Box box : map.boxes) {
			// Box in the way
			if (box.x() == newX && box.y() == newY && box.solidFrom(-dx, -dy)) {
				return false;
			}
			// Smaller box in the way
			if (boxIn != null && box.x() == newX && box.y() == newY && boxIn.size > box.size && boxIn.solidFrom(dx, dy)) {
				return false;
			}
		}
		if (map.tileAt(newX, newY) == 2) {
			// Will we be in a box
			boolean boxWith = false;
			for (Box box : map.boxes) {
				if (box.x() == player.x() && box.y() == player.y() && box.solidFrom(dx, dy)) {
					boxWith = true;
				}
			}
			if (!boxWith) return false;
		}
		return true;
	}
	
	private void checkWinState() {
		Box inner = null, outer = null;
		for (Box box : map.boxes) {
			if (box.isGoalInner) inner = box;
			if (box.isGoalOuter) outer = box;
		}
		if (inner != null && outer != null) {
			winState = (inner.x() == outer.x() && inner.y() == outer.y());
		}
	}

	@Override
	public void keyReleased(int key) {}

	@Override
	public void update(double dt) {}

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
