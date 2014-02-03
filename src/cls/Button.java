package cls;

import jog.graphics;

public abstract class Button {
	
	public final static int WIDTH = Map.TILE_SIZE / 3;
	public final static int HEIGHT = Map.TILE_SIZE / 4;
	
	public final int x;
	public final int y;
	public final int targetX;
	public final int targetY;

	public Button(int x, int y, int targetX, int targetY, int[] tiles) {
		this.x = x;
		this.y = y;
		this.targetX = targetX;
		this.targetY = targetY;
		findShortestPath();
	}
	
	public void findShortestPath() {
		
	}
	
	public abstract void act(Map map);
	
	public void draw() {
		graphics.setColour(64, 64, 64);
		double centreX = (x + 0.5) * Map.TILE_SIZE;
		double centreY = (y + 0.5) * Map.TILE_SIZE;
		graphics.rectangle(true, centreX - WIDTH/2, centreY - HEIGHT/2, WIDTH, HEIGHT);
		graphics.setColour(255, 255, 224);
		double tx = (targetX + 0.5) * Map.TILE_SIZE;
		double ty = (targetY + 0.5) * Map.TILE_SIZE;
		graphics.line(centreX, centreY, tx, centreY);
		graphics.line(tx, centreY, tx, ty);
	}

}
