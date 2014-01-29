package cls;

public class Battler {
	
	final public String name = "";
	
	public int strength;
	public int defence;
	public int speed;
	public int maxHealth;
	public int health;

	public Battler(int strength, int defence, int speed, int maxHealth) {
		this.strength = strength;
		this.defence = defence;
		this.speed = speed;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	public void takeDamage(int damage) {
		health = Math.max(0, health - damage);
	}

}
