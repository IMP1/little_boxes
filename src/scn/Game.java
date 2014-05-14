package scn;

import java.util.ArrayList;

import jog.*;
import lvl.LevelLoader;
import cls.*;
import run.Main;

public class Game extends Scene {
	
	private final static jog.audio.Source SUCCESS_SOUND = jog.audio.newSource("success.ogg");
	
	protected Map map;
	protected boolean winState;
	protected Player player;
	protected int currentLevel;	
//	private audio.Music music;
	protected ArrayList<LevelSolution.Action> actions;

	public Game(Main main) {
		super(main);
	}

	@Override
	public void start() {
//		music = audio.newMusic("Wallpaper.mp3");
//		music.play();
		player = new Player();
		loadMap(main.score.levelUpTo());
	}
	
	private void loadMap(int id) {
		currentLevel = id;
		map = LevelLoader.loadLevel(id);
		player.setPosition(map.playerStartX, map.playerStartY);
		winState = false;
		actions = new ArrayList<LevelSolution.Action>();
	}

	@Override
	public void mousePressed(int key, int x, int y) {}

	@Override
	public void mouseReleased(int key, int x, int y) {}

	@Override
	public void keyPressed(int key) {
		// Back to Title
		if (key == input.KEY_ESCAPE) {
			main.setScene(new Title(main));
		}
		// Next level
		if (key == input.KEY_SPACE && winState) {
			if (currentLevel < LevelLoader.LEVEL_COUNT) {
				LevelSolution solution = new LevelSolution(actions.toArray(new LevelSolution.Action[1]));
				main.score.completeLevel(currentLevel, solution);
				loadMap(currentLevel + 1);
			} else {
				main.setScene(new Title(main));
			}
		}
		// Reset
		if (key == input.KEY_R) {
			loadMap(currentLevel);
		}
		// Previous Level
		if (key == input.KEY_MINUS && currentLevel > 1) {
			loadMap(currentLevel - 1);
		}
		// Next level
		if (key == input.KEY_EQUALS && currentLevel < main.score.levelUpTo()) {
			loadMap(currentLevel + 1);
		}
		// If we've won, wait for one of the above inputs
		if (winState) return;
		
		int dx = 0, dy = 0;
		if (key == input.KEY_UP && playerCanMove(0, -1)) dy --;
		if (key == input.KEY_DOWN && playerCanMove(0, 1)) dy ++;
		if (key == input.KEY_LEFT && playerCanMove(-1, 0)) dx --;
		if (key == input.KEY_RIGHT && playerCanMove(1, 0)) dx ++;
		
		// Find biggest box we're pushing
		int smallerThan = -1;
		for (Box box : map.boxes) {
			if (box.x() == player.x() && box.y() == player.y() && box.solidFrom(dx, dy)) {
				smallerThan = Math.max(smallerThan, box.size);
			}
		}
		// Push all boxes smaller than (and including) that box
		for (Box box : map.boxes) {
			if (box.x() == player.x() && box.y() == player.y() && box.size <= smallerThan) {
				box.moveBy(dx, dy);
			}
		}
		
		// Move
		player.moveBy(dx, dy);
		
		// If we've not moved, don't do buttons, or log the action
		if (dx == 0 && dy == 0) return;
		
		// Log action
		if (actions != null) {
			if (dy < 0) actions.add(LevelSolution.Action.UP);
			if (dy > 0) actions.add(LevelSolution.Action.DOWN);
			if (dx < 0) actions.add(LevelSolution.Action.LEFT);
			if (dx > 0) actions.add(LevelSolution.Action.RIGHT);
		}

		// Press buttons we've moved onto
		for (Button button : map.buttons) {
			if (button.x == player.x() && button.y == player.y()) {
				button.act(map);
			}
		}
		
		checkWinState();
		// Play the sound
		if (winState) {
			SUCCESS_SOUND.play();
		}
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
			if (boxIn != null && box.x() == newX && box.y() == newY && box.size <= boxIn.size && boxIn.solidFrom(dx, dy)) {
				return false;
			}
		}
		if (map.tileAt(newX, newY) == Map.Tile.WATER) {
			// Will we be in a box
			boolean boxWith = false;
			boolean boxThere = false;
			for (Box box : map.boxes) {
				if (box.x() == player.x() && box.y() == player.y() && box.solidFrom(dx, dy)) {
					boxWith = true;
				}
				if (box.x() == newX && box.y() == newY && !box.solidFrom(-dx, -dy)) {
					boxThere = true;
				}
			}
			// Not bringing a box, and no box already there
			if (!boxWith && !boxThere) return false;
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
	public void update(double dt) {
		map.update(dt, player);
	}

	@Override
	public void draw() {
		map.draw(player);
		if (winState && currentLevel < LevelLoader.LEVEL_COUNT) {
			graphics.printCentred("Level " + currentLevel + " cleared!", 0, 32, window.width());
			graphics.printCentred("Press [SPACE] to advance.", 0, 48, window.width());
		} else if (winState) {
			graphics.printCentred("Level " + currentLevel + " cleared!", 0, 32, window.width());
			graphics.printCentred("Congratulations! You've reached the end of Little Boxes!", 0, 48, window.width());
		} else {
			graphics.printCentred("Level " + currentLevel + ".", 0, 32, window.width());
		}
		graphics.print("Moves: " + actions.size(), 0, 0);
	}

	@Override
	public void close() {
//		music.stop();
	}

}
