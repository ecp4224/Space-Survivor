/**
 * Programmer: Eddie Penta
 * Date: Jan 24, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: FrameMover.java
 */
package com.eddie.rpeg.engine.entity.mover.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.model.StaticEntity;
import com.eddie.rpeg.engine.entity.mover.KeyMover;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Core;

public class FrameMover extends StaticEntity {
	private static final long serialVersionUID = -982486138992646201L;
	private transient KeyMover move;
	
	public FrameMover(Core system, Window w, Level l) {
		super("FrameMover", system, l);
		move = new SimpleKeyMover(this, system);
		addMover(move);
		move.attachMover(w);
		setX(0);
		setY(0);
	}
	
	public FrameMover() {
		
	}
	
	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.Entity#dispose()
	 */
	@Override
	public void dispose() {
		move.detachMover();
		super.move.remove(move);
	}
	
	@Override
	public void setX(double x) {
		if (x < 0)
			Core.CENTER_X = 0;
		else
			Core.CENTER_X = x;
	}
	
	@Override
	public void setY(double y) {
		if (y < 0)
			Core.CENTER_Y = 0;
		else
			Core.CENTER_X = y;
	}
	
	@Override
	public double getX() {
		return Core.CENTER_X;
	}
	
	@Override
	public double getY() {
		return Core.CENTER_Y;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) { }

}
