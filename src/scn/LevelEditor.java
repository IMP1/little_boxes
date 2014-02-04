package scn;

import java.util.ArrayList;

import cls.Box;
import cls.Button;
import cls.Map;
import cls.Player;
import cls.RotateButton;
import jog.graphics;
import jog.input;
import run.Main;

public class LevelEditor extends Scene {
	
	public final static int MAX_ROWS = 9;
	public final static int MAX_COLUMNS = 13;
	
	private int mapWidth;
	private int mapHeight;
	private int[] tiles;
	private ArrayList<Box> boxes;
	private ArrayList<Button> buttons;
	
	private Map map;
	private Player player;
	
	private int dragX = -1;
	private int dragY = -1;
	private Class<? extends Button> dragButtonType = null;

	public LevelEditor(Main main) {
		super(main);
	}

	@Override
	public void start() {
		mapWidth = 5;
		mapHeight = 5;
		boxes = new ArrayList<Box>();
		buttons = new ArrayList<Button>();
		tiles = new int[mapWidth * mapHeight];
		for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;
		player = new Player();
		player.setPosition(0, 0);
		refreshMap();
	}
	
	private void refreshMap() {
		Box[] bxs = new Box[boxes.size()];
		Button[] btns = new Button[buttons.size()];
		map = new Map(mapWidth, mapHeight, player.x(), player.y(), tiles, boxes.toArray(bxs), buttons.toArray(btns), "");
	}

	@Override
	public void mousePressed(int key, int mx, int my) {
		if (key == input.MOUSE_LEFT) {
			int i = map.mouseToTileX(mx);
			int j = map.mouseToTileY(my);
			// Place solid tiles
			if (input.isKeyDown(input.KEY_1)) {
				tiles[j * mapWidth + i] = 1;
			}
			// Place non-solid tiles
			else if (input.isKeyDown(input.KEY_BACKTICK)) {
				tiles[j * mapWidth + i] = 0;
			} 
			// Place starting position
			else if (input.isKeyDown(input.KEY_H)) {
				player.setPosition(i, j);
			} 
			// Delete things
			else if (input.isKeyDown(input.KEY_DELETE)) {
				// buttons
				if (buttonAt(i, j) != null) {
					buttons.remove(buttonAt(i, j));
				}
				// boxes
				if (boxAt(i, j) != null) {
					boxes.remove(boxAt(i, j));
				}
				refreshMap();
			}
			// Make rotate button
			else if (input.isKeyDown(input.KEY_R)) {
				dragButtonType = RotateButton.class;
				dragX = i;
				dragY = j;
			}
		}
	}

	@Override
	public void mouseReleased(int key, int mx, int my) {
		if (key == input.MOUSE_LEFT && dragX > -1 && dragY > -1) {
			int i = map.mouseToTileX(mx);
			int j = map.mouseToTileY(my);
			if (dragButtonType == RotateButton.class) {
				Button btn = new RotateButton(dragX, dragY, i, j, 0, tiles, 1);
				buttons.add(btn);
				dragButtonType = null;
				dragX = -1;
				dragY = -1;
				refreshMap();
			}
		}
	}

	@Override
	public void keyPressed(int key) {
		if (key == input.KEY_P && input.isKeyDown(input.KEY_LCRTL)) {
			printMapText();
		}
		if (key == input.KEY_PAGEUP) {
			if (input.isKeyDown(input.KEY_LSHIFT)) {
				removeRows();
			} else {
				addRows();
			}
		}
		if (key == input.KEY_PAGEDOWN) {
			if (input.isKeyDown(input.KEY_LSHIFT)) {
				removeColumns();
			} else {
				addColumns();
			}
		}
	}
	
	private void addColumns() {
		if (mapWidth + 2 > MAX_COLUMNS) return;
		int oldWidth = mapWidth;
		mapWidth += 2;
		int[] newTiles = new int[mapWidth * mapHeight];
		// Pad 0s
		for (int i = 0; i < newTiles.length; i ++) newTiles[i] = 0;
		// Copy old tiles
		for (int i = 0; i < tiles.length; i ++) {
			int id = i + 1 + 2 * (i / oldWidth);
			newTiles[id] = tiles[i];
		}
		tiles = newTiles;
		refreshMap();
		player.moveBy(1, 0);
	}
	
	private void addRows() {
		if (mapHeight + 2 > MAX_ROWS) return;
		mapHeight += 2;
		int[] newTiles = new int[mapWidth * mapHeight];
		// Pad 0s
		for (int i = 0; i < newTiles.length; i ++) newTiles[i] = 0;
		// Copy old tiles
		for (int i = 0; i < tiles.length; i ++) {
			int id = mapWidth + i;
			newTiles[id] = tiles[i];
		}
		tiles = newTiles;
		refreshMap();
		player.moveBy(0, 1);
	}
	
	private void removeColumns() {
		if (mapWidth < 2) return;
		mapWidth -= 2;
		int[] newTiles = new int[mapWidth * mapHeight];
		// Copy old tiles
		for (int i = 0; i < mapWidth * mapHeight; i ++) {
			int id = i + 1 + 2 * (i / mapWidth);
			newTiles[i] = tiles[id];
		}
		tiles = newTiles;
		refreshMap();
		if (player.x() > 0) player.moveBy(-1, 0);
	}
	
	private void removeRows() {
		if (mapHeight < 2) return;
		mapHeight -= 2;
		int[] newTiles = new int[mapWidth * mapHeight];
		// Copy old tiles
		for (int i = 0; i < mapWidth * mapHeight; i ++) {
			int id = mapWidth + i;
			newTiles[i] = tiles[id];
		}
		tiles = newTiles;
		refreshMap();
		if (player.y() > 0) player.moveBy(0, -1);
	}

	private void printMapText() {
		System.out.println("----------------");
		System.out.println("Map Textual Representation");
		System.out.println("(Copy and paste this into lvl.Levels)");
		System.out.println("----------------");
		
		System.out.println("width = " + mapWidth + ";");
		System.out.println("height = " + mapHeight + ";");
		System.out.println("startX = " + player.x() + ";");
		System.out.println("startY = " + player.y()+ ";");
		System.out.println("tiles = new int[width * height];");
		System.out.println("for (int i = 0; i < tiles.length; i ++) tiles[i] = 0;");
		System.out.println("boxes = new Box[" + boxes.size() + "];");
		for (Box box : boxes) {
			if (box.isGoalInner) {
				String line = "boxes[0] = new Box(Box.Type.GOAL_INNER, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.size + ");";
				System.out.println(line);
			}
		}
		for (Box box : boxes) {
			if (box.isGoalOuter) {
				String line = "boxes[1] = new Box(Box.Type.GOAL_OUTER, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.size + ");";
				System.out.println(line);
			}
		}
		int n = 2;
		for (Box box : boxes) {
			if (!box.isGoalInner && !box.isGoalOuter) {
				String line = "boxes[" + n + "] = new Box(Box.Type.DEFAULT, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.size + ");";
				System.out.println(line);
				n ++;
			}
		}
		System.out.println("buttons = new Buttons[" + buttons.size() + "];");
		for (int i = 0; i < buttons.size(); i ++) {
			if (buttons.get(i).getClass() == RotateButton.class) {
				RotateButton btn = (RotateButton)buttons.get(i);
				String line = "buttons[" + i + "] = new RotateButton(";
				line += btn.x + ", ";
				line += btn.y + ", ";
				line += btn.targetX + ", ";
				line += btn.targetY + ", Map.CIRCLE_RADIUS * 2, tiles, ";
				line += btn.direction + ");";
				System.out.println(line);
			}
		}
		System.out.println("message = \"\";");
		System.out.println("return new Map(width, height, startX, startY, tiles, boxes, buttons, message);");
		System.out.println("----------------");
	}

	@Override
	public void keyReleased(int key) {}

	@Override
	public void update(double dt) {}

	@Override
	public void draw() {
		map.draw(player);
		int mx = map.mouseToTileX(input.mouseX());
		int my = map.mouseToTileY(input.mouseY());
		graphics.print("" + mx + ", " + my, 0, 0);
		
	}
	
	private Button buttonAt(int x, int y) {
		for (Button btn : buttons) {
			if (btn.x == x && btn.y == y) return btn;
		}
		return null;
	}
	
	private Box boxAt(int x, int y) {
		for (Box box : boxes) {
			if (box.x() == x && box.y() == y) return box;
		}
		return null;
	}


	@Override
	public void close() {}
	
}
