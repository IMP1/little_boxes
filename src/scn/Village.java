package scn;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import jog.graphics;

import cls.Mission;
import cls.Minion;
import cls.Notification;

import run.Main;

public class Village extends Scene {
	
	final static public int MAX_HEROES = 8;
	private ArrayList<Minion> heroes;
	private ArrayList<Minion> heroesOnAdventures;
	private ArrayList<Mission> missions;
	private ArrayList<Notification> messages;

	public Village(Main main) {
		super(main);
	}

	@Override
	public void start() {
		missions = new ArrayList<Mission>();
		heroes = new ArrayList<Minion>();
		heroesOnAdventures = new ArrayList<Minion>();
		for (int i = 0; i < 4; i ++) {
			String name = "Fish " + (i+1);
			heroes.add(Minion.generateRandom(name));
		}
		graphics.setBackgroundColour(8, 0, 32);
		messages = new ArrayList<Notification>();
	}

	@Override
	public void mousePressed(int key, int x, int y) {

	}

	@Override
	public void mouseReleased(int key, int x, int y) {

	}

	@Override
	public void keyPressed(int key) {
		if (key == Input.KEY_SPACE) {
			testMission();
		}
	}

	@Override
	public void keyReleased(int key) {

	}

	@Override
	public void update(double dt) {
		if (messages.size() > 0) {
			messages.get(0).update(dt);
			if (messages.get(0).finished) messages.remove(0); 
			return;
		}
		ArrayList<Mission> finishedMissions = new ArrayList<Mission>();
		for (Mission a : missions) {
			a.update(dt);
		}
		for (Mission a : missions) {
			if (a.isFinished()) {
				System.out.println("Mission confirmed finished & set to be removed.");
				finishedMissions.add(a);
			}
		}
		for (Mission a : finishedMissions) {
			missions.remove(a);
			System.out.println("Mission removed");
		}
	}

	@Override
	public void draw() {
		graphics.setColour(255, 255, 255);
		graphics.print("Heroes:", 32, 32);
		graphics.print("_______________", 30, 40);
		graphics.print("______________", 30 + 4, 40);
		for (int i = 0; i < heroes.size(); i ++) {
			int height = 80;
			Minion hero = heroes.get(i);
			if (heroesOnAdventures.contains(hero)) {
				graphics.setColour(128, 128, 128);
			} else {
				graphics.setColour(255, 255, 255);
			}
			graphics.print(">   " + hero.name, 40, 64 + i * height);
			if (heroesOnAdventures.contains(hero)) {
				graphics.print("<Away>", 96, 96 + i * height);
			} else {
				graphics.print(hero.stats(), 80, 80 + i * height);
				int expWidth = 64;
				int exp = expWidth * hero.expirience / hero.experienceToNextLevel();
				graphics.rectangle(false, 88, 100 + i * height, expWidth, 3);
				graphics.rectangle(true, 88, 100 + i * height, exp, 3);
				graphics.print("" + hero.level, 78, 97 + i * height);
				graphics.print("" + (hero.level+1), 78 + 12 + expWidth, 97 + i * height);
				int healthHeight = 48;
				int health = healthHeight * hero.health / hero.maxHealth;
				graphics.rectangle(false, 58, 60 + i * height, 4, healthHeight);
				graphics.rectangle(true, 58, 60 + i * height + healthHeight - health, 4, health);
				graphics.print("£" + hero.gold, 168, 97 + i * height);
			}
		}
		
		graphics.print("Current Adventures:", 576, 32);
		graphics.print("_________________________", 574, 40);
		graphics.print("________________________", 574 + 4, 40);
		for (int i = 0; i < missions.size(); i ++) {
			Mission m = missions.get(i);
			graphics.print("Difficulty: " + m.difficulty, 600, 64 + i * 64);
			for (int j = 0; j < m.heroes.length; j ++) {
				graphics.print(m.heroes[j].name, 600 + j * 96, 96 + i * 64);
			}
			double r = (m.timer * Math.PI) % (4 * Math.PI);
			double angle = Math.PI / 2;
			if (r > (2 * Math.PI)) {
				r = (r % (2 * Math.PI)) - (2 * Math.PI);
			}
			graphics.arc(true, 580, 80 + i * 64, 6, angle, -r);
		}
		if (messages.size() > 0) {
			messages.get(0).draw();
		}
	}

	@Override
	public void close() {

	}
	
	public void missionReturn(Mission mission, Mission.Result result) {
		String[] msg = new String[4]; 
		msg[0] = "The following heroes have returned from a mission:";
		msg[1] = ""; msg[2] = "";
		msg[3] = "";
		for (Minion hero : mission.heroes) {
			if (hero.health > 0) msg[3] += hero.name + ", ";
		}
		messages.add(new Notification(msg, 2));
		for (Minion hero : mission.heroes) {
			heroesOnAdventures.remove(hero);
		}
//		System.out.println("Mission returned.");
	}
	
	private void testMission() {
		Minion[] people = new Minion[2];
		people[0] = heroes.get(0);
		people[1] = heroes.get(2);
		missions.add(new Mission(this, people, Mission.Difficulty.EASY));
		heroesOnAdventures.add(people[0]);
		heroesOnAdventures.add(people[1]);
	}

}
