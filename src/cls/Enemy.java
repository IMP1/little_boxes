package cls;

public class Enemy extends Battler {
	
	final public String name = "\"Do-gooder\"";

	public Enemy(int strength, int defence, int speed, int maxHealth) {
		super(strength, defence, speed, maxHealth);
	}

	public static Enemy generateRandom() {
		int str = 2 + (int)(Math.random() * 5);
		int def = 2 + (int)(Math.random() * 5);
		int spd = 1 + (int)(Math.random() * 1);
		int mhp = 5 + (int)(Math.random() * 5);
		return new Enemy(str, def, spd, mhp);
	}
	
}
