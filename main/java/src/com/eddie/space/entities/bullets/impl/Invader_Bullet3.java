/**
 * Programmer: Eddie Penta
 * Date: Jun 14, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Invader.java
 */
package com.eddie.space.entities.bullets.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.bullets.Bullet;

public class Invader_Bullet3 extends Bullet  {
	private static final long serialVersionUID = -5590959963570988601L;

	/**
	 * @param name
	 * @param l
	 * @param core
	 */
	public Invader_Bullet3(Level l, RPEG core) {
		super("ib_1", l, core);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#getDamage()
	 */
	@Override
	public int getDamage() {
		return 20;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		if (isVisible() && getImage() != null)
			g.drawImage(getImage(), (int)(getDrawX()), (int)(getDrawY()), getImage().getWidth(), getImage().getHeight(), null);
	}

}
