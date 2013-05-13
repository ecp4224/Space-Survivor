/**
 * Programmer: Eddie Penta
 * Date: Mar 21, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Pickupable.java
 */
package com.eddie.rpeg.engine.entity.types;

import com.eddie.rpeg.engine.entity.Entity;

/**
 * This object can be picked <b>AND</b> thrown.
 */
public interface Pickupable extends Throwable {
	
	public void attach(Entity e);
	
	public void deattach();
}
