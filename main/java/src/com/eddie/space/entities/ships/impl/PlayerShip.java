/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Player.java
 */
package com.eddie.space.entities.ships.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.ships.Gun;
import com.eddie.space.entities.ships.SpaceCraft;
import com.eddie.space.entities.space.Asteroid;
import com.eddie.space.events.OnBeat;
import com.eddie.space.game.Game;

import java.awt.event.KeyEvent;

public class PlayerShip extends SpaceCraft implements Listener {
	private Entity lasthit;
	private boolean inv;
	private final Gun[] guns = new Gun[] {
			new Gun() {

				@Override
				public int getXOffset() {
					return -8;
				}

				@Override
				public int getYOffset() {
					return 4;
				}

				@Override
				public int getBindKey() {
					return KeyEvent.VK_SPACE;
				}

			},
			new Gun() {

				@Override
				public int getXOffset() {
					return 8;
				}

				@Override
				public int getYOffset() {
					return 4;
				}

				@Override
				public int getBindKey() {
					return KeyEvent.VK_SPACE;
				}

			}
	};
	private double yadd;
	public static double score;
	private boolean bounce;
	/**
	 * @param name
	 * @param core
	 * @param level
	 */
	public PlayerShip(String name, RPEG core, Level level) {
		super(name, core, level);
		core.getEventSystem().registerEvents(this);
	}

	public PlayerShip(RPEG core, Level level) {
		this("player", core, level);
	}

	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}

	public boolean isBouncing() {
		return bounce;
	}
	
	@Override
	public void onLoad() {
		instance = this;
	}

	private static final long serialVersionUID = -3696970516358028607L;
	private double health = 100;
	public static PlayerShip instance;

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
	public void hit(int damage, Entity e) {
	    if (inv)
	        return;
		if (lasthit == e)
			return;
		lasthit = e;
		health -= damage;
		if (health <= 0)
			kill();
		inv = true;
	}

	@Override
	public double getDrawY() {
		return super.getDrawY() + yadd;
	}
	
	int wait;
	@Override
	public void tick() {
		super.tick();
		lasthit = null;
		if (inv) {
		    int max = (int)(70 * Game.DIFFICULTY);
		    if (wait >= max) {
		        wait = 0;
		        inv = false;
		        setVisible(true);
		        return;
		    }
		    wait++;
		    if (wait % (wait > (max - (max / 3)) ? 2 : 5) == 0)
		        setVisible(!isVisible());
		}
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#canKill()
	 */
	@Override
	public boolean canKill() {
		return getHealth() > 0;
	}

	public void drawHealth(Graphics g) {
		g.setColor(Color.GREEN);
		double width = (getHealth() / 100.0);
		width *= 200;
		g.fillRect(0, 10, (int)(width), 20);
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
		if (hit instanceof SpaceCraft)
			kill();
		if (hit instanceof Asteroid) {
			Asteroid a = (Asteroid)hit;
			hit(a.getDamage(), a);
		}
	}
	
	@Override
	public void kill() {
		Game.GAME.onDeath((int)score);
		super.kill();
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
		if (isVisible() && getImage() != null)
			g.drawImage(getImage(), (int)(getDrawX()), (int)(getDrawY()), getImage().getWidth(), getImage().getHeight(), null);
		drawHealth(g);
		drawScore(g);
	}
	
	public void drawScore(Graphics g) {
	    g.setFont(g.getFont().deriveFont(25f));
		g.drawString("Score: " + (int)(score), system.getMaxScreenX() - (g.getFontMetrics().stringWidth("Score: " + (int)(score)) + 20), 20);
	}

	@EventHandler
	public void onBeat(OnBeat beat) {
		if (!bounce)
			return;
		yadd = beat.getBeat() * 10;
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.ships.SpaceCraft#getGuns()
	 */
	@Override
	public Gun[] getGuns() {
		return guns;
	}
	
	@Override
	public void dispose() {
		instance = null;
		super.dispose();
	}



}
