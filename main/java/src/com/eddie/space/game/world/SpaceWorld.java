/**
 * Programmer: Eddie Penta
 * Date: May 15, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SpaceWorld.java
 */
package com.eddie.space.game.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.events.Priority;
import com.eddie.rpeg.engine.events.model.render.onDrawEvent;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.gui.Window;

public class SpaceWorld implements Level, Listener {
	private Window parent;
	private BufferedImage background;
	private int max_x;
	private int max_y;
	public SpaceWorld(Window parent) {
		this.parent = parent;
		max_x = parent.getSystem().getMaxScreenX();
		max_y = parent.getSystem().getMaxScreenY();
		background = new BufferedImage(max_x, max_y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = background.createGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, background.getWidth(), background.getHeight());
		g.dispose();
		parent.getSystem().getEventSystem().registerEvents(this);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.level.Level#getParentWindow()
	 */
	@Override
	public Window getParentWindow() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.level.Level#getBackground()
	 */
	@Override
	public BufferedImage getBackground() {
		return background;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.level.Level#getName()
	 */
	@Override
	public String getName() {
		return "Space";
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.level.Level#save()
	 */
	@Override
	public void save() throws IOException { }

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.level.Level#load(java.lang.String)
	 */
	@Override
	public void load(String name) throws IOException { }

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.level.Level#isInBounds(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public boolean isInBounds(Entity e) {
		return e.getX() < 0 || e.getX() > max_x || e.getY() < 0 || e.getY() > max_y;
	}
	
	@EventHandler(priority = Priority.Low)
	public void onDraw(onDrawEvent event) {
		event.getGraphics().drawImage(getBackground(), 0, 0, getBackground().getWidth(), getBackground().getHeight(), null);
	}

}
