package cls;

import jog.graphics;

public class Player {
	
	public final int width;
	public final int height;
	
	private int x;
	private int y;

	public Player() {
		width = (int)(Map.TILE_SIZE * 0.4);
		height = (int)(Map.TILE_SIZE * 0.4);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int x() { return x; }
	public int y() { return y; }

	public void moveBy(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	public void draw() {
		double centreX = (x + 0.5) * Map.TILE_SIZE;
		double centreY = (y + 0.5) * Map.TILE_SIZE;
		graphics.setColour(32, 32, 32);
		graphics.rectangle(true, centreX - width/2, centreY - height/2, width, height);
	}

}
