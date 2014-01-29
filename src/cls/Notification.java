package cls;

import jog.graphics;
import jog.window;

public class Notification {
	
	public double duration;
	public double timer;
	public String[] lines;
	public boolean finished;
	public int height;

	public Notification(String[] lines, double duration) {
		timer = 0;
		this.lines = lines;
		this.duration = duration;
		height = lines.length * 16;
	}
	
	public void update(double dt) {
		if (finished) return;
		timer += dt;
		if (timer >= duration) {
			finished = true;
		}
	}
	
	public void draw() {
		if (finished) return;
		int y = window.height() - height;
		graphics.setColour(0, 0, 0, 128);
		graphics.rectangle(true, 0, y - 16, window.width(), height + 16);
		graphics.setColour(255, 255, 255);
		for (int i = 0; i < lines.length; i ++) {
			graphics.printCentred(lines[i], 0, y - 8 + i * 16, 1, window.width());
		}
	}

}
