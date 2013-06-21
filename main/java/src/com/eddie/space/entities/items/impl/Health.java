/**
 * Programmer: Eddie Penta
 * Date: Jun 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Health.java
 */
package com.eddie.space.entities.items.impl;

import java.awt.Color;
import java.util.Random;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.items.Item;
import com.eddie.space.entities.ships.impl.PlayerShip;

public class Health extends Item {
	private static final long serialVersionUID = 4873945118128431306L;
	public static final Random RAND = new Random();
	/**
	 * @param core
	 * @param level
	 */
	public Health(RPEG core, Level level) {
		super(core, level);
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#getColor()
	 */
	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#getChar()
	 */
	@Override
	public String getChar() {
		return "H";
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#onObtain(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void onObtain(Entity e) {
		if (e instanceof PlayerShip) {
			PlayerShip p = (PlayerShip)e;
			p.health += RAND.nextInt(20) + 10;
		}
	}

}
