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
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.space.entities.SpaceCraft;

public class Player extends SpaceCraft {
	/**
	 * @param name
	 * @param core
	 * @param level
	 */
	public Player(String name, Core core, Level level) {
		super(name, core, level);
	}

	public Player(Core core, Level level) {
		this("player", core, level);
	}

	private static final long serialVersionUID = -3696970516358028607L;
	private double health = 100;

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#getHealth()
	 */
	@Override
	public double getHealth() {
		return health;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#hit(int)
	 */
	@Override
	public void hit(int damage) {
		health -= damage;
		if (health <= 0)
			kill();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#canKill()
	 */
	@Override
	public boolean canKill() {
		return getHealth() > 0;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#kill()
	 */
	@Override
	public void kill() {
		//TODO Blow up
		dispose();
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
		if (hit instanceof Killable)
			((Killable)hit).hit(getDamage());
		kill();
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
		if (isVisable() && getImage() != null)
			g.drawImage(getImage(), (int)(getDrawX()), (int)(getDrawY()), getImage().getWidth(), getImage().getHeight(), null);
	}

}
