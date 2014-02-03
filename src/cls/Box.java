package cls;

import jog.graphics;

public class Box {
	
	public final static int SIZE_MULTIPLIER = 6;
	
	public enum Type {
		DEFAULT, GOAL_INNER, GOAL_OUTER,
	}
	
	public final boolean isGoalOuter;
	public final boolean isGoalInner;
	public final int size;
	public final int colourRed;
	public final int colourGreen;
	public final int colourBlue;
	
	private boolean[] solid;
	private int x;
	private int y;
	

	public Box(Type boxType, int x, int y, boolean solidTop, boolean solidBottom, boolean solidLeft, boolean solidRight, int size) {
		isGoalInner = (boxType == Type.GOAL_INNER);
		isGoalOuter = (boxType == Type.GOAL_OUTER);
		if (isGoalInner) {
			colourRed = 192;
			colourGreen = 64;
			colourBlue = 64;
		} else if (isGoalOuter) {
			colourRed = 64;
			colourGreen = 64;
			colourBlue = 192;
		} else {
			colourRed = 64;
			colourGreen = 64;
			colourBlue = 64;
		}
		this.x = x;
		this.y = y;
		solid = new boolean[4]; 
		solid[0] = solidRight;
		solid[1] = solidBottom;
		solid[2] = solidLeft;
		solid[3] = solidTop;
		this.size = size;
		
	}
	
	public int x() { return x; }
	public int y() { return y; }
	
	public boolean solidFrom(int dx, int dy) {
		if (dx == -1) return solid[2];
		if (dx == 1) return solid[0];
		if (dy == -1) return solid[3];
		if (dy == 1) return solid[1];
		return true;
	}
	
	private void turnBy(int dr) {
		boolean[] newSolid = new boolean[4];
		for (int i = 0; i < 4; i ++) {
			int newID = (i - dr) % 4;
			while (newID < 0) newID += solid.length;
			newSolid[i] = solid[newID];
		}
		solid = newSolid;
	}
	
	public void moveBy(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	public void turnRight() {
		turnBy(1);
	}
	
	public void turnLeft() {
		turnBy(-1);
	}
	
	public void draw() {
		double centreX = (x + 0.5) * Map.TILE_SIZE;
		double centreY = (y + 0.5) * Map.TILE_SIZE;
		double w = (Map.TILE_SIZE * 0.4) + (size * SIZE_MULTIPLIER);
		double h = (Map.TILE_SIZE * 0.4) + (size * SIZE_MULTIPLIER);
		graphics.setColour(colourRed, colourGreen, colourBlue, 128);
		graphics.rectangle(true, centreX - w/2, centreY - h/2, w, h);
		graphics.setColour(colourRed, colourGreen, colourBlue);
		drawLines(centreX, centreY, w, h, 0);
		drawLines(centreX, centreY, w, h, 1);
	}
	
	private void drawLines(double centreX, double centreY, double width, double height, int distance) {
		double w = width + (distance * 2);
		double h = height + (distance * 2);
		if (solid[0]) graphics.line(centreX + w/2, centreY - h/2, centreX + w/2, centreY + h/2);
		if (solid[1]) graphics.line(centreX - w/2, centreY + h/2, centreX + w/2, centreY + h/2);
		if (solid[2]) graphics.line(centreX - w/2, centreY - h/2, centreX - w/2, centreY + h/2);
		if (solid[3]) graphics.line(centreX - w/2, centreY - h/2, centreX + w/2, centreY - h/2);
	}

}
