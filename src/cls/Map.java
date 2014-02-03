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
	public final Button[] buttons;
	public final String message;
	
	public Map(int width, int height, int startX, int startY, int[] tiles, Box[] boxes, Button[] buttons, String message) {
		this.width = width;
		this.height = height;
		this.tiles = tiles;
		this.boxes = boxes;
		this.playerStartX = startX;
		this.playerStartY = startY;
		this.message = message;
		this.buttons = buttons;
	}
	
	public Map(int width, int height, int startX, int startY, int[] tiles, Box[] boxes, String message) {
		this.width = width;
		this.height = height;
		this.tiles = tiles;
		this.boxes = boxes;
		this.playerStartX = startX;
		this.playerStartY = startY;
		this.message = message;
		this.buttons = new Button[0];
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
			drawTile(tiles[i], x * TILE_SIZE, y * TILE_SIZE);
		}
		for (Box box : boxes) box.draw();
		for (Button button : buttons) button.draw();
		player.draw();
		graphics.pop();
		graphics.printCentred(message, 0, window.height() - 64, 1, window.width());
	}
	
	private void drawTile(int tile, int x, int y) {
		if (tile == 0 || tile == 2 || tile == 3) graphics.setColour(128, 192, 192);
		if (tile == 1) graphics.setColour(64, 64, 64);
		graphics.rectangle(true, x, y, TILE_SIZE, TILE_SIZE);
		graphics.setColour(64, 64, 64);
		graphics.rectangle(false, x, y, TILE_SIZE, TILE_SIZE);
		if (tile == 2 || tile == 3) {
			int circleX = x + TILE_SIZE/2;
			int circleY = y + TILE_SIZE/2;
			int circleRadius = TILE_SIZE / 4;
			int arrowRadius = circleRadius * 2 / 3;
			graphics.setColour(64, 64, 64, 192);
			graphics.circle(true, circleX, circleY, circleRadius);
			double arcLength =  Math.PI * 3 / 2;
			graphics.setColour(255, 255, 255);
			graphics.arc(false, circleX, circleY, arrowRadius, Math.PI/2, (tile == 2) ? arcLength : -arcLength );
			int tx = x + TILE_SIZE/2 + ( (tile == 2) ? arrowRadius : -arrowRadius );
			int ty = y + TILE_SIZE/2;
			graphics.triangle(false, tx-4, ty, tx, ty-6, tx+4, ty);
		}
		
	}

}
