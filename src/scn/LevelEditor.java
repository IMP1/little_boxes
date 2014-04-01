package scn;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import cls.Box;
import cls.Box.Type;
import cls.Button;
import cls.Map;
import cls.Player;
import cls.RotateButton;
import jog.graphics;
import jog.input;
import jog.window;
import run.Main;

public class LevelEditor extends Scene {
	
	public final static int MAX_ROWS = 9;
	public final static int MAX_COLUMNS = 13;
	
	private int mapWidth;
	private int mapHeight;
	private Map.Tile[] tiles;
	private ArrayList<Box> boxes;
	private ArrayList<Button> buttons;
	
	private Map map;
	private Player player;
	
	private int dragX = -1;
	private int dragY = -1;
	private Class<? extends Button> dragButtonType = null;
	
	private boolean showKey = false;

	public LevelEditor(Main main) {
		super(main);
	}

	@Override
	public void start() {
		mapWidth = 5;
		mapHeight = 5;
		boxes = new ArrayList<Box>();
		buttons = new ArrayList<Button>();
		tiles = new Map.Tile[mapWidth * mapHeight];
		for (int i = 0; i < tiles.length; i ++) tiles[i] = Map.Tile.EMPTY;
		player = new Player();
		player.setPosition(0, 0);
		refreshMap();
	}
	
	private void refreshMap() {
		Box[] bxs = new Box[boxes.size()];
		Button[] btns = new Button[buttons.size()];
		map = new Map(mapWidth, mapHeight, player.x(), player.y(), tiles, boxes.toArray(bxs), buttons.toArray(btns), "Level under construction.");
	}
	
	private Map testMap() {
		Box[] boxes = new Box[this.boxes.size()];
		for (int i = 0; i < boxes.length; i ++) {
			Box b = this.boxes.get(i);
			if (b.isGoalInner) {
				boxes[i] = new Box(Box.Type.GOAL_INNER, b.x(), b.y(), b.solidFrom(0, -1), b.solidFrom(0, 1), b.solidFrom(-1, 0), b.solidFrom(1, 0), b.size);
			} else if (b.isGoalOuter) {
				boxes[i] = new Box(Box.Type.GOAL_OUTER, b.x(), b.y(), b.solidFrom(0, -1), b.solidFrom(0, 1), b.solidFrom(-1, 0), b.solidFrom(1, 0), b.size);
			} else {
				boxes[i] = new Box(Box.Type.DEFAULT, b.x(), b.y(), b.solidFrom(0, -1), b.solidFrom(0, 1), b.solidFrom(-1, 0), b.solidFrom(1, 0), b.size);
			}
		}
		Button[] btns = new Button[buttons.size()];
		return new Map(mapWidth, mapHeight, player.x(), player.y(), tiles, boxes, buttons.toArray(btns), "Testing Level.");
	}

	@Override
	public void mousePressed(int key, int mx, int my) {
		if (key == input.MOUSE_LEFT) {
			int i = map.mouseToTileX(mx);
			int j = map.mouseToTileY(my);
			// Place empty tiles
			if (input.isKeyDown(input.KEY_BACKTICK) || input.isKeyDown(input.KEY_0)) {
				tiles[j * mapWidth + i] = Map.Tile.EMPTY;
			} 
			// Place solid tiles
			else if (input.isKeyDown(input.KEY_1)) {
				tiles[j * mapWidth + i] = Map.Tile.SOLID;
			}
			// Place water tiles
			else if (input.isKeyDown(input.KEY_2)) {
				tiles[j * mapWidth + i] = Map.Tile.WATER;
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
			}
			// Create 3 sided inner goal box
			else if (input.isKeyDown(input.KEY_RIGHT_BRACKET) && input.isKeyDown(input.KEY_RCRTL)) {
				boxes.add(new Box(Box.Type.GOAL_INNER, i, j, true, true, false, true, 1));
			}
			// Create 2 sided inner goal box
			else if (input.isKeyDown(input.KEY_EQUALS) && input.isKeyDown(input.KEY_RCRTL)) {
				boxes.add(new Box(Box.Type.GOAL_INNER, i, j, true, true, false, false, 1));
			}
			// Create corner inner goal box
			else if (input.isKeyDown(input.KEY_LEFT_BRACKET) && input.isKeyDown(input.KEY_RCRTL)) {
				boxes.add(new Box(Box.Type.GOAL_INNER, i, j, true, false, false, true, 1));
			}
			// Create 1 sided inner goal box
			else if (input.isKeyDown(input.KEY_MINUS) && input.isKeyDown(input.KEY_RCRTL)) {
				boxes.add(new Box(Box.Type.GOAL_INNER, i, j, true, false, false, false, 1));
			} 
			// Create 3 sided outer goal box
			else if (input.isKeyDown(input.KEY_RIGHT_BRACKET) && input.isKeyDown(input.KEY_RSHIFT)) {
				boxes.add(new Box(Box.Type.GOAL_OUTER, i, j, true, true, false, true, Box.MAX_SIZE));
			}
			// Create 2 sided outer goal box
			else if (input.isKeyDown(input.KEY_EQUALS) && input.isKeyDown(input.KEY_RSHIFT)) {
				boxes.add(new Box(Box.Type.GOAL_OUTER, i, j, true, true, false, false, Box.MAX_SIZE));
			}
			// Create corner outer goal box
			else if (input.isKeyDown(input.KEY_LEFT_BRACKET) && input.isKeyDown(input.KEY_RSHIFT)) {
				boxes.add(new Box(Box.Type.GOAL_OUTER, i, j, true, false, false, true, Box.MAX_SIZE));
			}
			// Create 1 sided outer goal box
			else if (input.isKeyDown(input.KEY_MINUS) && input.isKeyDown(input.KEY_RSHIFT)) {
				boxes.add(new Box(Box.Type.GOAL_OUTER, i, j, true, false, false, false, Box.MAX_SIZE));
			} 
			// Create 3 sided box
			else if (input.isKeyDown(input.KEY_RIGHT_BRACKET)) {
				boxes.add(new Box(Box.Type.DEFAULT, i, j, true, true, false, true, 2));
			}
			// Create 2 sided box
			else if (input.isKeyDown(input.KEY_EQUALS)) {
				boxes.add(new Box(Box.Type.DEFAULT, i, j, true, true, false, false, 2));
			}
			// Create corner box
			else if (input.isKeyDown(input.KEY_LEFT_BRACKET)) {
				boxes.add(new Box(Box.Type.DEFAULT, i, j, true, false, false, true, 2));
			}
			// Create 1 sided box
			else if (input.isKeyDown(input.KEY_MINUS)) {
				boxes.add(new Box(Box.Type.DEFAULT, i, j, true, false, false, false, 2));
			} 
			// Create rotate button
			else if (input.isKeyDown(input.KEY_R)) {
				dragButtonType = RotateButton.class;
				dragX = i;
				dragY = j;
			}
		}
		if (key == input.MOUSE_MIDDLE) {
			int i = map.mouseToTileX(mx);
			int j = map.mouseToTileY(my);
			if (boxAt(i, j) != null) {
				boxAt(i, j).turnRight();
				refreshMap();
			}
		}
		if (key == input.MOUSE_RIGHT) {
			int i = map.mouseToTileX(mx);
			int j = map.mouseToTileY(my);
			if (boxAt(i, j) != null) {
				Box oldBox = boxAt(i, j);
				Box.Type type;
				if (oldBox.isGoalInner) {
					type = Type.GOAL_INNER;
				} else if (oldBox.isGoalOuter) { 
					type = Type.GOAL_OUTER;
				} else {
					type = Type.DEFAULT;
				}
				Box newBox = new Box(type, i, j, oldBox.solidFrom(0, -1), oldBox.solidFrom(0, 1), oldBox.solidFrom(-1, 0), oldBox.solidFrom(1, 0), (oldBox.size % Box.MAX_SIZE) + 1);
				boxes.remove(oldBox);
				boxes.add(newBox);
				refreshMap();
			}
		}
		refreshMap();
	}

	@Override
	public void mouseReleased(int key, int mx, int my) {}

	@Override
	public void keyPressed(int key) {
		if (key == input.KEY_ESCAPE) {
			main.setScene(new Title(main));
		}
		else if (key == input.KEY_TAB) {
			showKey = !showKey;
		}
		else if (key == input.KEY_R && input.isKeyDown(input.KEY_LCRTL)) {
			start();
		}
		else if (key == input.KEY_P && input.isKeyDown(input.KEY_LCRTL)) {
			printMapText();
		}
		else if (key == input.KEY_C && input.isKeyDown(input.KEY_LCRTL)) {
			copyMapTextToClipboard();
		}
		else if (key == input.KEY_SPACE && input.isKeyDown(input.KEY_LCRTL)) {
			main.addScene(new LevelPreview(main, testMap()));
		}
		else if (key == input.KEY_PAGEUP) {
			if (input.isKeyDown(input.KEY_LSHIFT)) {
				removeRows();
			} else {
				addRows();
			}
		}
		else if (key == input.KEY_PAGEDOWN) {
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
		Map.Tile[] newTiles = new Map.Tile[mapWidth * mapHeight];
		// Pad 0s
		for (int i = 0; i < newTiles.length; i ++) newTiles[i] = Map.Tile.EMPTY;
		// Copy old tiles
		for (int i = 0; i < tiles.length; i ++) {
			int id = i + 1 + 2 * (i / oldWidth);
			newTiles[id] = tiles[i];
		}
		tiles = newTiles;
		refreshMap();
		player.moveBy(1, 0);
		for (Box box : boxes) box.moveBy(1, 0);
	}
	
	private void addRows() {
		if (mapHeight + 2 > MAX_ROWS) return;
		mapHeight += 2;
		Map.Tile[] newTiles = new Map.Tile[mapWidth * mapHeight];
		// Pad 0s
		for (int i = 0; i < newTiles.length; i ++) newTiles[i] = Map.Tile.EMPTY;
		// Copy old tiles
		for (int i = 0; i < tiles.length; i ++) {
			int id = mapWidth + i;
			newTiles[id] = tiles[i];
		}
		tiles = newTiles;
		refreshMap();
		player.moveBy(0, 1);
		for (Box box : boxes) box.moveBy(0, 1);
	}
	
	private void removeColumns() {
		if (mapWidth < 2) return;
		mapWidth -= 2;
		Map.Tile[] newTiles = new Map.Tile[mapWidth * mapHeight];
		// Copy old tiles
		for (int i = 0; i < mapWidth * mapHeight; i ++) {
			int id = i + 1 + 2 * (i / mapWidth);
			newTiles[i] = tiles[id];
		}
		tiles = newTiles;
		refreshMap();
		if (player.x() > 0) player.moveBy(-1, 0);
		for (Box box : boxes) if (box.x() > 0) box.moveBy(-1, 0);
	}
	
	private void removeRows() {
		if (mapHeight < 2) return;
		mapHeight -= 2;
		Map.Tile[] newTiles = new Map.Tile[mapWidth * mapHeight];
		// Copy old tiles
		for (int i = 0; i < mapWidth * mapHeight; i ++) {
			int id = mapWidth + i;
			newTiles[i] = tiles[id];
		}
		tiles = newTiles;
		refreshMap();
		if (player.y() > 0) player.moveBy(0, -1);
		for (Box box : boxes) if (box.y() > 0) box.moveBy(0, -1);
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
		System.out.println("tiles = new Map.Tile[width * height];");
		System.out.println("for (int i = 0; i < tiles.length; i ++) tiles[i] = Map.Tile.EMPTY;");
		for (int i = 0; i < tiles.length; i ++) {
			if (tiles[i] != Map.Tile.EMPTY) {
				int x = i % mapWidth;
				int y = i / mapHeight;
				System.out.println("tiles[" + y + " * width + " + x + "] = Map.Tile." + tiles[i] + ";");
			}
		}
		System.out.println("boxes = new Box[" + boxes.size() + "];");
		for (Box box : boxes) {
			if (box.isGoalInner) {
				String line = "boxes[0] = new Box(Box.Type.GOAL_INNER, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.size + ");";
				System.out.println(line);
			}
		}
		for (Box box : boxes) {
			if (box.isGoalOuter) {
				String line = "boxes[1] = new Box(Box.Type.GOAL_OUTER, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.solidFrom(1, 0) + ", ";
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
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.size + ");";
				System.out.println(line);
				n ++;
			}
		}
		System.out.println("buttons = new Button[" + buttons.size() + "];");
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
	
	private void copyMapTextToClipboard() {
		String text = "";
		text += "width = " + mapWidth + ";\n";
		text += "height = " + mapHeight + ";\n";
		text += "startX = " + player.x() + ";\n";
		text += "startY = " + player.y()+ ";\n";
		text += "tiles = new Map.Tile[width * height];\n";
		text += "for (int i = 0; i < tiles.length; i ++) tiles[i] = Map.Tile.EMPTY;\n";
		for (int i = 0; i < tiles.length; i ++) {
			if (tiles[i] != Map.Tile.EMPTY) {
				int x = i % mapWidth;
				int y = i / mapWidth;
				text += ("tiles[" + y + " * width + " + x + "] = Map.Tile." + tiles[i] + ";\n");
			}
		}
		text += "boxes = new Box[" + boxes.size() + "];\n";
		for (Box box : boxes) {
			if (box.isGoalInner) {
				String line = "boxes[0] = new Box(Box.Type.GOAL_INNER, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.size + ");";
				text += line + "\n";
			}
		}
		for (Box box : boxes) {
			if (box.isGoalOuter) {
				String line = "boxes[1] = new Box(Box.Type.GOAL_OUTER, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.size + ");";
				text += line + "\n";
			}
		}
		int n = 2;
		for (Box box : boxes) {
			if (!box.isGoalInner && !box.isGoalOuter) {
				String line = "boxes[" + n + "] = new Box(Box.Type.DEFAULT, ";
				line += box.x() + ", ";
				line += box.y() + ", ";
				line += box.solidFrom(0, -1) + ", ";
				line += box.solidFrom(0, 1) + ", ";
				line += box.solidFrom(-1, 0) + ", ";
				line += box.solidFrom(1, 0) + ", ";
				line += box.size + ");";
				text += line + "\n";
				n ++;
			}
		}
		text += "buttons = new Button[" + buttons.size() + "];\n";
		for (int i = 0; i < buttons.size(); i ++) {
			if (buttons.get(i).getClass() == RotateButton.class) {
				RotateButton btn = (RotateButton)buttons.get(i);
				String line = "buttons[" + i + "] = new RotateButton(";
				line += btn.x + ", ";
				line += btn.y + ", ";
				line += btn.targetX + ", ";
				line += btn.targetY + ", Map.CIRCLE_RADIUS * 2, tiles, ";
				line += btn.direction + ");";
				text += line + "\n";
			}
		}
		text += "message = \"\";\n";
		text += "return new Map(width, height, startX, startY, tiles, boxes, buttons, message);\n";
		StringSelection selection = new StringSelection(text);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
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
		Map.Tile t;
		try {
			t = map.tileAt(mx, my);
			graphics.print("(" + mx + ", " + my + ") = " + t, 0, 0);
		} catch (IndexOutOfBoundsException e) {}
		graphics.printCentred("Level Editor", 0, 8, 1, window.width());
		if (showKey) {
			drawKey();
		} else {
			graphics.print("Press [TAB] to show key", 0, 32);
		}
		graphics.print("   Press [CTRL] + [C] to copy", window.width() - (8 * 29), 32);
		graphics.print("the map text to the clipboard", window.width() - (8 * 29), 48);
		graphics.printCentred("Press [CTRL] + [SPACE] to test the level", 0, 32, 1, window.width());
	}
	
	private void drawKey() {
		graphics.print("Press [TAB] to hide key", 0, 32);
		int x = 16;
		int y = 64;
		int w = 300;
		int h = 560;
		graphics.setColour(255, 255, 255, 192);
		graphics.rectangle(true, x - 4, y - 4, w, h);
		graphics.rectangle(true, window.width() - 256 - 4, window.height() - 144 - 4, 256 - 8, 144 - 8);
		graphics.setColour(0, 0, 0);
		graphics.rectangle(false, x - 4, y - 4, w, h);
		graphics.rectangle(false, window.width() - 256 - 4, window.height() - 144 - 4, 256 - 8, 144 - 8);
		// Tiles
		graphics.print("Left Mouse Click  + ", x, y);
		graphics.print("    [0] : Remove tile", x, y + 16 * 1);
		graphics.print("    [1] : Place solid tile", x, y + 16 * 2);
		graphics.print("    [2] : Place rotate tile", x, y + 16 * 3);
		graphics.print("                  (anti-clockwise)", x, y + 16 * 4);
		graphics.print("    [3] : Place rotate tile ", x, y + 16 * 5);
		graphics.print("                  (clockwise)", x, y + 16 * 6);
		// Buttons
		graphics.print("    [DELETE] : Delete object", x, y + 16 * 9);
		graphics.print("    [H]      : Place start position", x, y + 16 * 10);
		// Blue boxes
		graphics.print("    [RIGHT SHIFT] + ", x, y + 16 * 13);
		graphics.print("        [-] : Single-sided blue box ", x, y + 16 * 14);
		graphics.print("        [=] : Double-sided blue box ", x, y + 16 * 15);
		graphics.print("        [[] : Corner blue box ", x, y + 16 * 16);
		graphics.print("        []] : Treble-sided blue box ", x, y + 16 * 17);
		// Red boxes
		graphics.print("    [RIGHT CTRL] + ", x, y + 16 * 20);
		graphics.print("        [-] : Single-sided red box ", x, y + 16 * 21);
		graphics.print("        [=] : Double-sided red box ", x, y + 16 * 22);
		graphics.print("        [[] : Corner red box ", x, y + 16 * 23);
		graphics.print("        []] : Treble-sided red box ", x, y + 16 * 24);
		// Grey boxes
		graphics.print("    [-] : Single-sided grey box ", x, y + 16 * 27);
		graphics.print("    [=] : Double-sided grey box ", x, y + 16 * 28);
		graphics.print("    [[] : Corner grey box ", x, y + 16 * 29);
		graphics.print("    []] : Treble-sided grey box ", x, y + 16 * 30);
		// All boxes
		graphics.print("Middle Mouse Click : Turn box", x, y + 16 * 33);
		graphics.print("Right Mouse Click : Resize box", x, y + 16 * 34);
		// Map size
		x = window.width() - 256;
		y = window.height() - 144;
		graphics.print("[PAGEUP]   :", x, y + 16 * 0);
		graphics.print("        Increase map height", x, y + 16 * 1);
		graphics.print("[PAGEDOWN] :", x, y + 16 * 2);
		graphics.print("        Increase map width", x, y + 16 * 3);
		graphics.print("[LEFT SHIFT] + [PAGEUP]   :", x, y + 16 * 4);
		graphics.print("        Decrease map height", x, y + 16 * 5);
		graphics.print("[LEFT SHIFT] + [PAGEDOWN] :", x, y + 16 * 6);
		graphics.print("        Decrease map width", x, y + 16 * 7);
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
