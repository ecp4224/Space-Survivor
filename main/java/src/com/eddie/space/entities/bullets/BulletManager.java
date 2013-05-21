/**
 * Programmer: Eddie Penta
 * Date: May 21, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: BulletManager.java
 */
package com.eddie.space.entities.bullets;

import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.events.OnBeat;

public class BulletManager implements Listener {
	private static double bspeed = 10;
	private static boolean init;
	
	public static void init(RPEG engine) {
		engine.getEventSystem().registerEvents(new BulletManager());
		init = true;
	}
	
	@EventHandler
	public void onBeat(OnBeat event) {
		bspeed = event.getSpeed() / 2;
	}
	
	public static double getSpeed() {
		return bspeed;
	}
	
	public static boolean initRan() {
		return init;
	}
}
