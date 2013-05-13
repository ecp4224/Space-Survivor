/**
 * Programmer: Eddie Penta
 * Date: Apr 12, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SimpleCollisionMover.java
 */
package com.eddie.rpeg.engine.entity.mover.model;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.CollisionMover;
import com.eddie.rpeg.engine.system.Core;

public class SimpleCollisionMover extends CollisionMover {

	/**
	 * @param parent
	 * @param core
	 */
	public SimpleCollisionMover(Entity parent, Core core) {
		super(parent, core);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.mover.CollisionMover#doesColide(com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public boolean doesColide(Entity e) { //No special effect..
		return false;
	}

}
