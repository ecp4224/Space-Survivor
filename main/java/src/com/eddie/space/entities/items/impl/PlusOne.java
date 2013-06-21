/**
 * Programmer: Eddie Penta
 * Date: Jun 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: PlusOne.java
 */
package com.eddie.space.entities.items.impl;

import java.awt.Color;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.items.Item;
import com.eddie.space.entities.ships.impl.PlayerShip;

public class PlusOne extends Item {
	private static final long serialVersionUID = -3117044060852455203L;

	/**
	 * @param core
	 * @param level
	 */
	public PlusOne(RPEG core, Level level) {
		super(core, level);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#getColor()
	 */
	@Override
	public Color getColor() {
		return Color.white;
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#getChar()
	 */
	@Override
	public String getChar() {
		return "+1";
	}

	/* (non-Javadoc)
	 * @see com.eddie.space.entities.items.Item#onObtain(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void onObtain(Entity e) {
		if (e instanceof PlayerShip) {
			PlayerShip p = (PlayerShip)e;
			p.firerate++;
		}
	}

}
