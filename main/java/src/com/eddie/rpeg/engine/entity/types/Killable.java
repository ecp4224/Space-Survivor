/**
 * Programmer: Eddie Penta
 * Date: Jan 9, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Killable.java
 */
package com.eddie.rpeg.engine.entity.types;

/**
 * This object can die
 */
public interface Killable {
	
	/**
	 * Get the current health of this entity
	 * @return
	 */
	public double getHealth();
	
	/**
	 * Hit this entity and dealing the damage set in the parameter
	 * @param damage
	 */
	public void hit(int damage);
	
	/**
	 * Whether this object can die, or "be killed"
	 * @return
	 */
	public boolean canKill();
	
	/**
	 * This object should die.
	 */
	public void kill();

}
