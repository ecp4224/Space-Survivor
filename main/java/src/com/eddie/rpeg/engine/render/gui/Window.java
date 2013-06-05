package com.eddie.rpeg.engine.render.gui;

import java.awt.Canvas;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.ObjectDrawer;
import com.eddie.rpeg.engine.render.Render;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.rpeg.engine.system.Tick;

public abstract class Window extends Canvas implements Tick, Listener, KeyListener {
	private static final long serialVersionUID = -8891174773885679995L;

	private RPEG system;
	
	protected Level level;
	
	private ObjectDrawer objdraw = new ObjectDrawer();
	
	private Render render;
	
	public Window(RPEG system) {
		this.system = system;
		this.system.getEventSystem().registerEvents(this);
		render = new Render(this);
		system.getTicker().addTick(this);
		super.addKeyListener(this);
	}
	
	public BufferedImage getBackgroundImage() {
		return level.getBackground();
	}
	
	public abstract void init();
	
	public void addEntity(Entity e) {
		getObjectDrawer().addObject(e);
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void onLoad() {
		render.start();
	}
	
	public void onUnload() {
		render.finalize();
		objdraw.unregister();
	}
	
	public abstract String getName();
	
	public RPEG getSystem() {
		return system;
	}

	/**
	 * @return the objdraw
	 */
	public ObjectDrawer getObjectDrawer() {
		return objdraw;
	}
}
