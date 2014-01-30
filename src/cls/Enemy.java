package cls;

import cls.Mission.Difficulty;

public class Enemy extends Battler {
	
	final public String name = "\"Do-gooder\"";

	public Enemy(int strength, int defence, int speed, int maxHealth) {
		super(strength, defence, speed, maxHealth);
	}

	public static Enemy generateRandom(Difficulty difficulty) {
		int minStrength = 0, 
			maxStrength = 0, 
			minDefence = 0, 
			maxDefence = 0,
			minSpeed = 0, 
			maxSpeed = 0, 
			minHealth = 0, 
			maxHealth = 0;
		switch (difficulty) {
			case EASY:
				minStrength = 2;
				maxStrength = 7; 
				minDefence = 2;
				maxDefence = 7;
				minSpeed = 1;
				maxSpeed = 3; 
				minHealth = 5; 
				maxHealth = 10;
				break;
			case MEDIUM:
				minStrength = 4;
				maxStrength = 14; 
				minDefence = 4;
				maxDefence = 14;
				minSpeed = 3;
				maxSpeed = 8; 
				minHealth = 10; 
				maxHealth = 20;
				break;
			case HARD:
				minStrength = 8;
				maxStrength = 20; 
				minDefence = 8;
				maxDefence = 20;
				minSpeed = 5;
				maxSpeed = 10; 
				minHealth = 15; 
				maxHealth = 40;
				break;
		}
		int str = minStrength + (int)(Math.random() * (maxStrength - minStrength));
		int def = minDefence + (int)(Math.random() * (maxDefence - minDefence));
		int spd = minSpeed + (int)(Math.random() * (maxSpeed - minSpeed));
		int mhp = minHealth + (int)(Math.random() * (maxHealth - minHealth));
		return new Enemy(str, def, spd, mhp);
	}
	
}
