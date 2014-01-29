package cls;

public class Minion extends Battler {

	final public String name;
	public int expirience;
	public int level;
	public int gold;
	
	public Minion(String name, int strength, int defence, int speed, int maxHealth) {
		super(strength, defence, speed, maxHealth);
		this.name = name;
		expirience = 0;
		level = 1;
		gold = 0;
	}
	
	public static Minion generateRandom(String name) {
		int str = 4 + (int)(Math.random() * 6);
		int def = 4 + (int)(Math.random() * 6);
		int spd = 1 + (int)(Math.random() * 3);
		int mhp = 4 + (int)(Math.random() * 6);
		return new Minion(name, str, def, spd, mhp);
	}
	
	public void gainExp(int exp) {
		expirience += exp;
		while (expirience >= experienceToNextLevel()) {
			expirience -= experienceToNextLevel();
			levelUp();
		}
	}
	
	public void gainGold(int gold) {
		this.gold += gold;
	}
	
	public int experienceToNextLevel() {
		return level * 20;
	}
	
	public void levelUp() {
		level ++;
	}

	public String stats() {
		return "" + health + "/" + maxHealth + " " + strength + " " + defence + " " + speed;
	}

}
