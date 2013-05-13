/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Player.java
 */
package com.eddie.space.entities.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.space.entities.SpaceCraft;

public class Player extends SpaceCraft {
	private static final long serialVersionUID = -3696970516358028607L;

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#getHealth()
	 */
	@Override
	public double getHealth() {
		return 100;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#hit(int)
	 */
	@Override
	public void hit(int damage) {
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#canKill()
	 */
	@Override
	public boolean canKill() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#kill()
	 */
	@Override
	public void kill() {
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#getDamage()
	 */
	@Override
	public int getDamage() {
		return 50;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#hit(com.eddie.rpeg.engine.entity.Entity, double, double)
	 */
	@Override
	public void onHit(Entity hit, double cx, double cy) {
		if (hit instanceof Killable) {
			((Killable)hit)
		}
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Smart#alert(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void alert(Entity cause) {
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
	}

}
