/**
 * Programmer: Eddie Penta
 * Date: May 16, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Star.java
 */
package com.eddie.space.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.space.music.MediaPlayer;

public class Star extends Entity {
	private static final long serialVersionUID = -7584479734207900386L;
	private static double speed = 4;
	private final int TOLERENCE = 10;
	private static BufferedImage star;
	
	public Star(Core system, Level level, MediaPlayer media) {
		super("Star", system, level, false);
		validateStar();
	}
	
	private static void validateStar() {
		if (star != null)
			return;
		star = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
		Graphics g = star.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 4, 4);
		g.dispose();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		if (isVisable() && getImage() != null) {
			g.drawImage(star, (int)getDrawX(), (int)getDrawY(), 4, 4, null);
		}
	}
	
	@Override
	public void tick() {
		super.tick();
		setY(getY() + speed);
		if (getY() - TOLERENCE >= system.getMaxScreenY())
			dispose();
	}
	
	public static double getSpeed() {
		return speed;
	}
	
	public static void setSpeed(double speed) {
		Star.speed = speed;
	}
	
	@Override
	public void setImage(BufferedImage image) { } //Do nothing
	
	@Override
	public BufferedImage getImage() {
		if (star == null)
			validateStar();
		return star;
	}

}
