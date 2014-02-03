package lvl;

import cls.Map;
import cls.Box;

public class Levels {

	public static Map loadLevel(int id) {
		int width, height, startX, startY;
		int[] tiles;
		Box[] boxes;
		String message;
		
		switch(id) {
		
		case 1:
			width = 3;
			height = 5;
			startX = 1;
			startY = 4;
			tiles = new int[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
			boxes = new Box[2];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 1, 3, true, false, true, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 1, 0, true, false, true, true, 4);
			message = "Move into the red box, and then move it into the blue box.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 2:
			width = 5;
			height = 3;
			startX = 1;
			startY = 1;
			tiles = new int[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
			boxes = new Box[2];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 2, 2, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 4, 0, true, false, true, true, 4);
			message = "When in a box, you can push it in any direction it has a solid border on.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 3:
			width = 3;
			height = 5;
			startX = 0;
			startY = 3;
			tiles = new int[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
			boxes = new Box[2];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 1, 3, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 0, true, true, false, true, 4);
			message = "If you're stuck, pressing [R] will restart the current level.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 4:
			width = 5;
			height = 3;
			startX = 0;
			startY = 0;
			tiles = new int[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
			boxes = new Box[3];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 0, 2, false, true, true, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 1, false, true, true, true, 4);
			boxes[2] = new Box(Box.Type.DEFAULT, 4, 0, true, true, false, true, 2 );
			message = "You can move boxes in other boxes.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 5:
			width = 5;
			height = 3;
			startX = 0;
			startY = 0;
			tiles = new int[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
			boxes = new Box[3];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 4, 0, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 2, true, true, true, false, 4);
			boxes[2] = new Box(Box.Type.DEFAULT, 0, 1, false, true, true, true, 2 );
			message = "Other boxes can end up in the blue box along with the red one.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 6:
			width = 5;
			height = 3;
			startX = 0;
			startY = 0;
			tiles = new int[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
			boxes = new Box[3];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 4, 0, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 2, true, true, true, false, 4);
			boxes[2] = new Box(Box.Type.DEFAULT, 0, 1, false, false, true, true, 2 );
			message = "Some boxes have fewer than 3 edges.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
		
		case 7:
			width = 5;
			height = 5;
			startX = 0;
			startY = 4;
			tiles = new int[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
			tiles[0 * width + 4] = 1;
			tiles[1 * width + 1] = 1;
			tiles[2 * width + 3] = 1;
			tiles[3 * width + 2] = 1;
			tiles[4 * width + 1] = 1;
			boxes = new Box[4];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 0, 0, true, false, true, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 3, 3, true, true, true, false, 4);
			boxes[2] = new Box(Box.Type.DEFAULT, 2, 0, true, true, true, false, 3);
			boxes[3] = new Box(Box.Type.DEFAULT, 4, 4, true, true, false, true, 3);
			message = "Some levels are harder than others.";
			return new Map(width, height, startX, startY, tiles, boxes, message);

		default:
			throw new IndexOutOfBoundsException("There is no map with the id " + id + ".");
				
		}
	}
	
	public final static int LEVEL_COUNT = 7;
	
}
