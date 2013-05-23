/**
 * Programmer: Eddie Penta
 * Date: May 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Player_Level1.java
 */
package com.eddie.space.entities.bullets.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.bullets.Bullet;

public class Player_Level1 extends Bullet {
	private static final long serialVersionUID = 5278199183053091282L;

	/**
	 * @param name
	 * @param l
	 * @param core
	 */
	public Player_Level1(Level l, RPEG core) {
		super("p1_bullet", l, core);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#getDamage()
	 */
	@Override
	public int getDamage() {
		return 15;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		if (isVisable() && getImage() != null)
			g.drawImage(getImage(), (int)(getDrawX()), (int)(getDrawY()), getImage().getWidth(), getImage().getHeight(), null);
	}

}
