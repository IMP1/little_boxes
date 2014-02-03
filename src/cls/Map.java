package cls;

import jog.graphics;
import jog.window;

public class Map {
	
	public final static int TILE_SIZE = 64;

	public final int width;
	public final int height;
	public final int playerStartX;
	public final int playerStartY;
	public final int[] tiles;
	public final Box[] boxes;
	public final String message;
	
	public Map(int width, int height, int startX, int startY, int[] tiles, Box[] boxes, String message) {
		this.width = width;
		this.height = height;
		this.tiles = tiles;
		this.boxes = boxes;
		this.playerStartX = startX;
		this.playerStartY = startY;
		this.message = message;
	}
	
	public boolean passable(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return false;
		return tiles[y * width + x] != 1;
	}

	public void draw(Player player) {
		int centreX = (window.width() - (width * TILE_SIZE)) / 2;
		int centreY = (window.height() - (height * TILE_SIZE)) / 2;
		graphics.push();
		graphics.translate(centreX, centreY);
		for (int i = 0; i < tiles.length; i ++) {
			int x = i % width;
			int y = i / width;
			if (tiles[i] == 0) graphics.setColour(128, 192, 192);
			if (tiles[i] == 1) graphics.setColour(64, 64, 64);
			graphics.rectangle(true, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			graphics.setColour(64, 64, 64);
			graphics.rectangle(false, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		}
		for (Box box : boxes) box.draw();
		player.draw();
		graphics.pop();
		graphics.printCentred(message, 0, window.height() - 64, 1, window.width());
	}

}
