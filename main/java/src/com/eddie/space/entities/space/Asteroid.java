/**
 * Programmer: Eddie Penta
 * Date: May 29, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Asteroid.java
 */
package com.eddie.space.entities.space;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.bullets.Bullet;
import com.eddie.space.entities.items.Item;
import com.eddie.space.entities.ships.impl.PlayerShip;

public class Asteroid extends Entity implements Killable, Damager {
	private static final long serialVersionUID = 190032552849804267L;
	private static final Random RANDOM = new Random();
	private static final int COUNT = 1;
	private int size_multiplier = 1;
	private int health;
	
	public Asteroid(Level level, RPEG core) {
		super("asteroid" + RANDOM.nextInt(COUNT), core, level);
		size_multiplier = RANDOM.nextInt(2) + 1;
		health = 40 / size_multiplier;
	}
	
	@Override
	public void onLoad() {
		super.tick(); //Load up the image
		system.getTicker().removeTick(this); //Stop ticking
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AsteroidSpawner.remove(this);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		if (isVisible() && getImage() != null)
			g.drawImage(getImage(), (int)(getDrawX()), (int)(getDrawY()), getWidth(), getHeight(), null);
	}
	
	@Override
	public int getWidth() {
		if (getImage() == null) {
			dispose();
			return 1;
		}
		return getImage().getWidth() / size_multiplier;
	}
	
	@Override
	public int getHeight() {
		if (getImage() == null) {
			dispose();
			return 1;
		}
		return getImage().getHeight() / size_multiplier;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#getDamage()
	 */
	@Override
	public int getDamage() {
		//TODO Faster moving means more damage
		return 5 / size_multiplier;
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	public void move() {
		if (isVisible()) {
			setY((getY() + ((AsteroidSpawner.getSpeed() / 1.5) * size_multiplier)));
			if (getY() >= system.getMaxScreenY()) {
				dispose();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#onHit(com.eddie.rpeg.engine.entity.Entity, double, double)
	 */
	@Override
	public void onHit(Entity hit, double cx, double cy) {
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
	public void hit(int damage, Entity e) {
		if (e instanceof Asteroid)
			return;
		health -= damage;
		if (health <= 0) {
			kill();
			if (e instanceof Bullet) {
				PlayerShip.score += (Math.abs(PlayerShip.instance.getY() - getY()) / 2) * size_multiplier;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#canKill()
	 */
	@Override
	public boolean canKill() {
		return health > 0;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#kill()
	 */
	@Override
	public void kill() {
		if (RANDOM.nextDouble() < .8)
			Item.createRandomItem(system, getLevel(), getDrawerParent(), getX(), getY());
		dispose();
	}
	
	@Override
	public boolean inSeperateThread() {
		return false;
	}

}
