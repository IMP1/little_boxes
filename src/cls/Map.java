package cls;

import jog.graphics;
import jog.window;

public class Map {
	
	public enum Tile {
		EMPTY,
		SOLID,
		WATER,
	}
	
	public final static int TILE_SIZE = 64;
	public final static int CIRCLE_RADIUS = TILE_SIZE / 4;

	private final static int BOB_HEIGHT = 4;
	private final static int BOB_SPEED = 2;

	/* Tiles
		0 = empty
		1 = solid
		2 = water
		
	*/

	public final int width;
	public final int height;
	public final int playerStartX;
	public final int playerStartY;
	public final Tile[] tiles;
	public final Box[] boxes;
	public final Button[] buttons;
	public final String message;
	
	private double bobTimer;
	
	public Map(int width, int height, int startX, int startY, Tile[] tiles, Box[] boxes, Button[] buttons, String message) {
		this.width = width;
		this.height = height;
		this.tiles = tiles;
		this.boxes = boxes;
		this.playerStartX = startX;
		this.playerStartY = startY;
		this.message = message;
		this.buttons = buttons;
	}
	
	public Map(int width, int height, int startX, int startY, Tile[] tiles, Box[] boxes, String message) {
		this(width, height, startX, startY, tiles, boxes, new Button[0], message);
	}
	
	public boolean passable(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return false;
		return tiles[y * width + x] != Tile.SOLID;
	}
	
	public Tile tileAt(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) throw new IndexOutOfBoundsException("Invalid co-ordinates: " + x + ", " + y + ".");
		return tiles[y * width + x];
	}
	
	public int mouseToTileX(int mx) { 
		int centreX = (window.width() - (width * TILE_SIZE)) / 2;
		return (mx - centreX) / TILE_SIZE;
	}
	
	public int mouseToTileY(int my) { 
		int centreY = (window.height() - (height * TILE_SIZE)) / 2;
		return (my - centreY) / TILE_SIZE;
	}
	
	public void update(double dt, Player player) {
		bobTimer += dt * BOB_SPEED;
	}

	public void draw(Player player) {
		int centreX = (window.width() - (width * TILE_SIZE)) / 2;
		int centreY = (window.height() - (height * TILE_SIZE)) / 2;
		graphics.push();
		graphics.translate(centreX, centreY);
		for (int i = 0; i < tiles.length; i ++) {
			int x = i % width;
			int y = i / width;
			drawTile(tiles[i], x * TILE_SIZE, y * TILE_SIZE);
		}
		for (Button button : buttons) button.draw();
		double bob = BOB_HEIGHT * Math.sin(bobTimer);
		for (Box box : boxes) {
			if (tileAt(box.x(), box.y()) == Tile.WATER) {
				box.draw(0, bob);
			} else {
				box.draw();
			}
		}
		if (tileAt(player.x(), player.y()) == Tile.WATER) {
			graphics.push();
			graphics.translate(0, bob);
		}
		player.draw();
		if (tileAt(player.x(), player.y()) == Tile.WATER) {
			graphics.pop();
		}
		graphics.pop();
		graphics.printCentred(message, 0, window.height() - 64, 1, window.width());
	}
	
	private void drawTile(Tile tile, int x, int y) {
		if (tile == Tile.EMPTY) graphics.setColour(128, 192, 192);
		if (tile == Tile.SOLID) graphics.setColour(64, 64, 64);
		if (tile == Tile.WATER) graphics.setColour(128, 192, 256);
		graphics.rectangle(true, x, y, TILE_SIZE, TILE_SIZE);
		graphics.setColour(64, 64, 64);
		graphics.rectangle(false, x, y, TILE_SIZE, TILE_SIZE);
	}

}
