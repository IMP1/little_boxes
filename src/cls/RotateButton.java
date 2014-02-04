package cls;

public class RotateButton extends Button {
	
	public final int direction;
	
	public RotateButton(int x, int y, int targetX, int targetY, int targetHeight, int[] tiles, int direction) {
		super(x, y, targetX, targetY, targetHeight, tiles);
		this.direction = direction;
	}

	@Override
	public void act(Map map) {
		for (Box box : map.boxes) {
			if (box.x() == targetX && box.y() == targetY) {
				if (direction == 1) {
					box.turnLeft();
				} else if (direction == 2) {
					box.turnRight();
				}
			}
		}
	}

}
