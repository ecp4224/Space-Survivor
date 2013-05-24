/**
 * Programmer: Eddie Penta
 * Date: May 24, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Enemy1.java
 */
package com.eddie.space.entities.ships.impl.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.ships.Gun;
import com.eddie.space.entities.ships.SpaceCraft;

public class Enemy1 extends SpaceCraft {
	private int health = 20;

	/**
	 * @param name
	 * @param core
	 * @param level
	 */
	public Enemy1(RPEG core, Level level) {
		super("E1", core, level);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#getHealth()
	 */
	@Override
	public double getHealth() {
		return health;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#hit(int)
	 */
	@Override
	public void hit(int damage) {
		health -= damage;
		if (health <= 0)
			kill();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#canKill()
	 */
	@Override
	public boolean canKill() {
		return health > 0;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#getDamage()
	 */
	@Override
	public int getDamage() {
		return 50;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#onHit(com.eddie.rpeg.engine.entity.Entity, double, double)
	 */
	@Override
	public void onHit(Entity hit, double cx, double cy) {
		if (hit instanceof Killable)
			((Killable)hit).hit(getDamage());
		kill();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Smart#alert(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void alert(Entity cause) {
		//TODO AI stuff
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.ships.SpaceCraft#getGuns()
	 */
	@Override
	public Gun[] getGuns() {
		return new Gun[] {
				new Gun() {

					@Override
					public int getXOffset() {
						return 0;
					}

					@Override
					public int getYOffset() {
						return 10;
					}

					@Override
					public int getBindKey() {
						return 0;
					}
					
				}
		};
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
