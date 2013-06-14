/**
 * Programmer: Eddie Penta
 * Date: Feb 6, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Damageable.java
 */
package com.eddie.rpeg.engine.entity.types;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.InvalidCollisionException;

/**
 * Any entity implementing this class shows that they can cause
 * damage to other entities.
 */
public interface Damager {
	
	/**
	 * How much damage this damager does
	 */
	public int getDamage();
	
	/**
	 * What happens when this damager hits an Entity. (Ex: When a bullet hits a Entity)
	 * @param hit
	 * @param cx
	 * @param cy
	 */
	public void onHit(Entity hit, double cx, double cy) throws InvalidCollisionException;

}
