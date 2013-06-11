/**
 * Programmer: Eddie Penta
 * Date: May 30, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: AsteroidSpawner.java
 */
package com.eddie.space.entities.space;

import java.util.ArrayList;
import java.util.Random;

import com.eddie.rpeg.engine.entity.mover.model.SimpleCollisionMover;
import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.ObjectDrawer;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.events.OnBeat;
import com.eddie.space.windows.GameWindow;

public class AsteroidSpawner implements Listener {
	public static double move_speed;
	private static RPEG core;
	private static Level world;
	private static ObjectDrawer drawer;
	private static boolean init;
	private static int avg;
	private static ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private static final Random RAND = new Random();
	
	public AsteroidSpawner(RPEG core, Level level, ObjectDrawer drawer) {
		AsteroidSpawner.core = core;
		world = level;
		AsteroidSpawner.drawer = drawer;
		core.getEventSystem().registerEvents(this);
		init = true;
	}
	
	public static boolean initRan() {
		return init;
	}
	
	@EventHandler
	public void beat(OnBeat event) {
		if (GameWindow.finish) {
			OnBeat.getEventList().unregister(this);
			for (Asteroid a : asteroids) {
				a.dispose();
				asteroids.clear();
				return;
			}
		}
		avg = (int)(event.getSpeed());
		move_speed = (avg / 3.0) / 1.5;
		if (asteroids.size() == 0)
			spawnAll();
		moveThem();
	}
	
	public static double getSpeed() {
		return move_speed / 3;
	}
	
	public static void add(Asteroid a) {
		asteroids.add(a);
	}
	
	public static void remove(Asteroid a) {
		asteroids.remove(a);
		spawnAll();
	}
	
	private static void moveThem() {
		Asteroid[] temp = asteroids.toArray(new Asteroid[asteroids.size()]);
		for (Asteroid p : temp) {
			p.move();
		}
	}
	
	private static void spawn() {
		Asteroid p = new Asteroid(world, core);
		SimpleCollisionMover c_m = new SimpleCollisionMover(p, core);
		p.addMover(c_m);
		p.setY(0 - RAND.nextInt(1000));
		p.setX(RAND.nextInt(core.getMaxScreenX()));
		p.setVisible(true);
		drawer.addObject(p);
		add(p);
	}
	
	private static synchronized void spawnAll() {
		if (asteroids.size() > 3)
			return;
		int count = RAND.nextInt(3) + 1;
    	for (int i = 0; i < count; i++) {
    		spawn();
    	}
	}
}
