/**
 * Programmer: Eddie Penta
 * Date: Jun 14, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: InvaderSpawner.java
 */
package com.eddie.space.entities;

import java.util.Random;

import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.ObjectDrawer;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.ships.impl.enemy.Invader;
import com.eddie.space.events.OnBeat;
import com.eddie.space.game.Game;
import com.eddie.space.game.mover.aimover.Waypoint;

public class InvaderSpawner implements Listener {
	
	private RPEG core;
	private Level level;
	private ObjectDrawer drawer;
	private static final Random RANDOM = new Random();
	public InvaderSpawner(RPEG system, Level w, ObjectDrawer drawer) {
		system.getEventSystem().registerEvents(this);
		this.core = system;
		this.level = w;
		this.drawer = drawer;
	}
	
	@EventHandler
	public void onBeat(OnBeat event) {
		if (event.getMel() > 1) {
			//TODO Spawn some more while some are on the field
		}
		else if (event.getBeat() > .5 && Invader.instancecount <= 0) {
			Invader.instancecount = 0;
			spawnInvaders(RANDOM.nextInt(Invader.MAX_LEVEL));
		}
	}
	
	
	public void spawnInvaders(int dif) {
		int count = RANDOM.nextInt(Game.m.getSpeed() / 4);
		
		for (int i = 0; i < count; i++) {
			Invader invader = new Invader(core, level, dif);
			invader.setX((dif % 2 == 0 ? 0 - (32 * i) : core.getMaxScreenX() + (32 * i)));
			invader.setY(10);
			
			int y = 10;
			for (int z = 0; z < (dif + 1) * 2; z++) {
				if ((z % 2 != 0 && dif % 2 == 0) || (z % 2 == 0 && dif % 2 != 0)) {
					Waypoint w = new Waypoint(core.getMaxScreenX() - (32 * i), y);
					invader.mover.addWaypoint(w);
				} else if ((z % 2 == 0 && dif % 2 != 0) || (z % 2 != 0 && dif % 2 == 0)) {
					Waypoint w = new Waypoint(0 + (32 * i), y);
					invader.mover.addWaypoint(w);
				}
				
				y += 50;
			}
			
			invader.setVisible(true);
			drawer.addObject(invader);
		}
	}
}
