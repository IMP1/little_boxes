package lvl;

import cls.*;
import cls.Map.Tile;

public class LevelLoader {
	
	public static Map loadLevel(int id) {
		int width, height, startX, startY;
		Map.Tile[] tiles;
		Box[] boxes;
		Button[] buttons;
		String message;
		
		switch(id) {
		
		case 1:
			width = 3;
			height = 5;
			startX = 1;
			startY = 4;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			boxes = new Box[2];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 1, 3, true, false, true, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 1, 0, true, false, true, true, Box.MAX_SIZE);
			message = "Move into the red box, and then move it into the blue box.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 2:
			width = 5;
			height = 3;
			startX = 1;
			startY = 1;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			boxes = new Box[2];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 2, 2, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 4, 0, true, false, true, true, Box.MAX_SIZE);
			message = "When in a box, you can push it in any direction it has a solid border on.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 3:
			width = 3;
			height = 5;
			startX = 0;
			startY = 3;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			boxes = new Box[2];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 1, 3, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 0, true, true, false, true, Box.MAX_SIZE);
			message = "If you're stuck, pressing [R] will restart the current level.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 4:
			width = 5;
			height = 3;
			startX = 0;
			startY = 0;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			boxes = new Box[3];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 4, 0, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 2, true, true, true, false, Box.MAX_SIZE);
			boxes[2] = new Box(Box.Type.DEFAULT, 0, 1, false, true, true, true, 2 );
			message = "Other boxes can end up in the blue box along with the red one.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
			
		case 5:
			width = 5;
			height = 3;
			startX = 0;
			startY = 0;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			boxes = new Box[3];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 0, 2, false, true, true, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 1, false, true, true, true, Box.MAX_SIZE);
			boxes[2] = new Box(Box.Type.DEFAULT, 4, 0, true, true, false, true, 2 );
			message = "But only the red is necessary.";
			return new Map(width, height, startX, startY, tiles, boxes, message);
		
		case 6:
			width = 3;
			height = 3;
			startX = 1;
			startY = 0;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			boxes = new Box[2];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 0, 1, true, false, true, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 2, true, true, false, true, 4);
			buttons = new Button[0];
			message = "The blue box can be moved like all the others.";
			return new Map(width, height, startX, startY, tiles, boxes, buttons, message);
			
		case 7:
			width = 5;
			height = 3;
			startX = 0;
			startY = 0;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			boxes = new Box[3];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 4, 0, true, true, false, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 2, 2, true, true, true, false, Box.MAX_SIZE);
			boxes[2] = new Box(Box.Type.DEFAULT, 0, 1, false, false, true, true, 2 );
			message = "Some boxes have fewer than 3 edges.";
			return new Map(width, height, startX, startY, tiles, boxes, message);

		case 8:
			width = 5;
			height = 3;
			startX = 0;
			startY = 0;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			tiles[0 * width + 2] = Tile.WATER;
			tiles[1 * width + 2] = Tile.WATER;
			tiles[2 * width + 2] = Tile.WATER;
			boxes = new Box[3];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 3, 1, true, true, true, false, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 0, 2, true, true, true, false, 4);
			boxes[2] = new Box(Box.Type.DEFAULT, 1, 0, false, false, false, true, 2);
			buttons = new Button[0];
			message = "Unfortunately you can't float. But boxes can.";
			return new Map(width, height, startX, startY, tiles, boxes, buttons, message);
			
		case 9:
			width = 5;
			height = 5;
			startX = 0;
			startY = 4;
			tiles = new Map.Tile[width * height];
			for (int i = 0; i < tiles.length; i ++) tiles[i] = Tile.EMPTY;
			tiles[0 * width + 4] = Tile.SOLID;
			tiles[1 * width + 1] = Tile.SOLID;
			tiles[2 * width + 3] = Tile.SOLID;
			tiles[3 * width + 2] = Tile.SOLID;
			tiles[4 * width + 1] = Tile.SOLID;
			boxes = new Box[4];
			boxes[0] = new Box(Box.Type.GOAL_INNER, 0, 0, true, false, true, true, 1);
			boxes[1] = new Box(Box.Type.GOAL_OUTER, 3, 3, true, true, true, false, Box.MAX_SIZE);
			boxes[2] = new Box(Box.Type.DEFAULT, 2, 0, true, true, true, false, 3);
			boxes[3] = new Box(Box.Type.DEFAULT, 4, 4, true, true, false, true, 3);
			message = "Some levels are harder than others.";
			return new Map(width, height, startX, startY, tiles, boxes, message);

		default:
			throw new IndexOutOfBoundsException("There is no map with the id " + id + ".");
				
		}
	}
	
	public final static int LEVEL_COUNT = 9;
	
}
