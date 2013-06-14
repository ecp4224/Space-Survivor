/**
 * Programmer: Eddie Penta
 * Date: Jun 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Invader.java
 */
package com.eddie.space.entities.ships.impl.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.space.entities.space.Asteroid;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.bullets.Bullet;
import com.eddie.space.entities.bullets.impl.Invader_Bullet;
import com.eddie.space.entities.ships.Gun;
import com.eddie.space.entities.ships.SpaceCraft;
import com.eddie.space.entities.ships.impl.PlayerShip;
import com.eddie.space.game.Game;
import com.eddie.space.game.mover.aimover.Waypoint;
import com.eddie.space.game.mover.aimover.WaypointMover;
import com.eddie.space.windows.GameWindow;

public class Invader extends SpaceCraft {
	private static final long serialVersionUID = 2688367797495846007L;
	private int level = 0;
	private int health;
	private Entity lastdamage;
	public WaypointMover mover;
	private boolean loaded;
	public static final int MAX_LEVEL = 1;
	private static final Random RANDOM = new Random();
	public static int instancecount = 0;

	/**
	 * @param name
	 * @param core
	 * @param level
	 */
	public Invader(RPEG core, Level level, int difficulty) {
		super("invader" + difficulty, core, level);
		health = 35 * (this.level + 1);

		mover = new WaypointMover(this, core);
		mover.setActive(true);
		addMover(mover);
		setBulletType(Invader_Bullet.class);
	}
	
	public Invader(RPEG core, Level level) {
		this(core, level, RANDOM.nextInt(MAX_LEVEL));
	}
	
	@Override
	public void onLoad() {
		loaded = true;
		instancecount++;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#getHealth()
	 */
	@Override
	public double getHealth() {
		return health;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#hit(int, com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void hit(int damage, Entity by) {
		if (by instanceof Asteroid)
			return;
		if (lastdamage != null && lastdamage.getID() == by.getID())
			return;
		for (int i = 0; i < getDrawerParent().size(); i++) {
			Entity e = getDrawerParent().get(i);
			if (e instanceof Invader) {
				Invader in = (Invader)e;
				if (in.level == level)
					in.alert(by);
			}
		}
		lastdamage = by;
		health -= damage;
		if (health <= 0) {
			if (by instanceof Bullet) {
				PlayerShip.score += (Math.abs(PlayerShip.instance.getY() - getY()) / 2) * (level + 1);
			}
			kill();
		}
	}
	
	@Override
	public void kill() {
		super.kill();
		instancecount--;
		System.out.println(instancecount);
		if (instancecount <= 0) {
			instancecount = 0;
			GameWindow.spawn.spawnInvaders(RANDOM.nextInt(MAX_LEVEL));
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
	 * @see com.eddie.rpeg.engine.entity.types.Damager#getDamage()
	 */
	@Override
	public int getDamage() {
		return 3 * (level + 1);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#onHit(com.eddie.rpeg.engine.entity.Entity, double, double)
	 */
	@Override
	public void onHit(Entity hit, double cx, double cy) {
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Smart#alert(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void alert(Entity cause) {
		if (cause instanceof Bullet) {
			Waypoint w = new Waypoint(getY(), cause.getX() + (-25 + (int)(Math.random() * ((25 - -25) + 1))));
			mover.addWaypoint(w);
		} else
			fire(0, false);
	}

	int wait = 0;
	@Override
	public void tick() {
		if (!loaded)
			return;
		if (Game.m.getBeat() > .5 || getImage() == null)
			super.tick();
		if (wait <= 0) {
			fire(0, false);
			wait = 500 - level;
		} else
			wait--;

		mover.setSpeed(Game.m.getSpeed() / 20);

		if (!mover.isMoving() && RANDOM.nextDouble() > 0.7) {
			Waypoint w = new Waypoint(RANDOM.nextInt(system.getMaxScreenX()), getY());
			mover.addWaypoint(w);
		}

		lastdamage = null;
	}

	@Override
	public int getTimeout() {
		return (100 - Game.m.getSpeed() > 0 ? 50 - Game.m.getSpeed() : 1);
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
						return 7;
					}

					@Override
					public int getYOffset() {
						return 7;
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
