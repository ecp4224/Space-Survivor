/**
 * Programmer: Eddie Penta
 * Date: May 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Player_Level1.java
 */
package com.eddie.space.entities.bullets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.animation.AnimationStyle;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.Bullet;

public class Player_Level1 extends Bullet {
	
	private static final int SPEED = 20;

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
	
	@Override
	public void tick() {
		super.tick();
		if (isVisable() && getAnimation().getStyle() != AnimationStyle.DEATH) {
			setY(getY() - SPEED);
			if (getY() <= 0)
				dispose();
		}
	}

}
