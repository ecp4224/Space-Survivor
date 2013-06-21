/**
 * Programmer: Eddie Penta
 * Date: Jun 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: BaseWindow.java
 */
package com.eddie.rpeg.engine.render.gui;

import java.awt.event.KeyListener;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.ObjectDrawer;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.rpeg.engine.system.Tick;

public interface GameWindow extends Tick, Listener, KeyListener {
	public void onLoad();
	
	public void onUnload();
	
	public String getName();
	
	public Level getLevel();
	
	public void addEntity(Entity e);
	
	public RPEG getSystem();
	
	public ObjectDrawer getObjectDrawer();
	
	public void init();
}
