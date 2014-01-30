package cls;

import scn.Village;

public class Mission {

	public enum Result {
		SUCCESS, DEFEAT
	}
	
	public enum Difficulty {
		EASY, MEDIUM, HARD 
	}
	
	public double timer;
	public double timerReturn;
	public double waitPre = 2;
	public double waitPost = 2;
	public double[] characterTimers;
	public boolean battleFinished;
	public boolean missionFinished;
	public Minion[] heroes;
	public Enemy[] villains;
	public Difficulty difficulty;
	public Village returnScene;
	public Result result;

	public Mission(Village returnScene, Minion[] heroes, Difficulty difficulty) {
		battleFinished = false;
		missionFinished = false;
		this.heroes = heroes;
		this.difficulty = difficulty;
		this.returnScene = returnScene;
		villains = generateEnemies();
		characterTimers = new double[heroes.length + villains.length];
		for (int i = 0; i < characterTimers.length; i ++) {
			characterTimers[i] = 0;
		}
		timer = 0;
		timerReturn = 0;
		
		System.out.println("New Adventure");
		System.out.println("-------------");
		for (int i = 0; i < Math.max(heroes.length, villains.length); i ++) {
			String h = (i < heroes.length) ? heroes[i].name : "     ";
			String v = (i < villains.length) ? villains[i].name : "";
			String vs = (i+1 == Math.max(heroes.length, villains.length) / 2) ? "vs" : "  ";
			System.out.println(h + "\t" + vs + "\t" + v);
		}
		System.out.println("-------------");
	}
	
	private Enemy[] generateEnemies() {
		int amount = heroes.length;
		amount += (int)(Math.random() * 4) - 2;
		amount = Math.max(1, amount);
		Enemy[] enemies = new Enemy[amount];
		for (int i = 0; i < amount; i ++) {
			enemies[i] = Enemy.generateRandom(difficulty);
		}
		return enemies;
	}
	
	public boolean isFinished() {
		return missionFinished;
	}
	
	public void update(double dt) {
		if (missionFinished) return;
		timer += dt;
		if (timer < waitPre) {
			return;
		}
		if (!battleFinished) {
			updateBattles(dt);
			return;
		} else if (timerReturn < waitPost) {
			timerReturn += dt;
			return;
		}
		end();
	}
	
	private void updateBattles(double dt) {
		for (int i = 0; i < characterTimers.length; i ++) {
			characterTimers[i] += dt;
			Battler b;
			if (i < heroes.length) {
				b = heroes[i];
			} else {
				b = villains[i - heroes.length];
			}
			if (characterTimers[i] >= 1.0 / ((double)b.speed / 4)) {
				if (i < heroes.length) {
					attack(heroes[i]);
				} else {
					attack(villains[i - heroes.length]);
				}
				characterTimers[i] -= 1.0 / ((double)b.speed / 4);
			}
		}
		if (allHeroesDead()) endBattle(Result.DEFEAT);
		if (allEnemiesDead()) endBattle(Result.SUCCESS);
	}
	
	private void attack(Minion hero) {
		if (hero.health <= 0) return;
		// Choose a random target
		int n = (int)(Math.random() * villains.length);
		Enemy target = villains[n];
		while (target.health <= 0) {
			n = (int)(Math.random() * villains.length);
			target = villains[n];
		}
		// Calculate damage
		int damage = Math.max(1, hero.strength - target.defence);
		// Deal damage
		target.takeDamage(damage);
		System.out.println(hero.name + " attacks " + target.name + " for " + damage + " damage! Health now " + target.health);
		// Gain exp
		if (target.health <= 0) {
			hero.gainExp(target.maxHealth);
			hero.gainGold(target.maxHealth * 3);
			System.out.println(hero.name + " gains " + target.maxHealth + " experience.");
		}
	}
	
	private void attack(Enemy enemy) {
		if (enemy.health <= 0) return;
		// Choose a random target
		int n = (int)(Math.random() * heroes.length);
		Minion target = heroes[n];
		while (target.health <= 0 && n < 5) {
			n = (int)(Math.random() * heroes.length);
			target = heroes[n];
		}
		// Calculate damage
		int damage = Math.max(1, enemy.strength - target.defence);
		// Deal damage
		target.takeDamage(damage);
		System.out.println(enemy.name + " attacks " + target.name + " for " + damage + " damage! Health now " + target.health);
	}
	
	private boolean allHeroesDead() {
		for (Minion hero : heroes) {
			if (hero.health > 0) return false;
		}
		return true;
	}
	
	private boolean allEnemiesDead() {
		for (Enemy enemy : villains) {
			if (enemy.health > 0) return false;
		}
		return true;
	}
	
	private void endBattle(Result result) {
		battleFinished = true;
		this.result = result;
	}
	
	private void end() {
		missionFinished = true;
		// Gain experience and gold
		int experienceGained = 0;
		int goldGained = 0;
		switch (difficulty) {
			case EASY:
				experienceGained = 4;
				goldGained = 10;
				break;
			case MEDIUM:
				experienceGained = 8;
				goldGained = 20;
				break;
			case HARD:
				experienceGained = 16;
				goldGained = 40;
				break;
		}
		for (Minion hero : heroes) {
			hero.gainExp(experienceGained);
			hero.gainGold(goldGained);
		}
		System.out.println("Mission finished.");
		returnScene.missionReturn(this, result);
	}

}
