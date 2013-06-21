/**
 * Programmer: Eddie Penta
 * Date: Jun 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Point.java
 */
package com.eddie.space.entities.items.impl;

import java.awt.Color;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.items.Item;
import com.eddie.space.entities.ships.impl.PlayerShip;

public class Point extends Item {
	private static final long serialVersionUID = -5382909512064804762L;

	/**
	 * @param core
	 * @param level
	 */
	public Point(RPEG core, Level level) {
		super(core, level);
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#getColor()
	 */
	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#getChar()
	 */
	@Override
	public String getChar() {
		return "P";
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#onObtain(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void onObtain(Entity e) {
		PlayerShip.score += Health.RAND.nextInt(500) + 100;
	}

}
