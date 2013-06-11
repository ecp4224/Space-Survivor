/**
 * Programmer: Eddie Penta
 * Date: Jun 7, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Shooter.java
 */
package com.eddie.space.game.mover.aimover;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.Mover;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.ships.impl.PlayerShip;
import com.eddie.space.game.Game;

public class Shooter extends Mover {

	/**
	 * @param parent
	 * @param core
	 * @param name
	 */
	public Shooter(Entity parent, RPEG core) {
		super(parent, core, "Shooter");
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.mover.Mover#moveTick()
	 */
	@Override
	public void moveTick() {
		switch ((int)Game.DIFFICULTY) {
		case 3:
			return;
		case 2:
			if (getParent().getY() > PlayerShip.instance.getY() && getParent().getY() < PlayerShip.instance.getY() + PlayerShip.instance.getWidth()) {
				
			}
		}
	}

}
