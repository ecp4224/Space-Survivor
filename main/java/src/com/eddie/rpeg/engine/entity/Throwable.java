/**
 * Programmer: Eddie Penta
 * Date: Jan 9, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Throwable.java
 */
package com.eddie.rpeg.engine.entity;

import com.eddie.rpeg.engine.entity.Entity;


public interface Throwable {
	
	public void throwObject(Entity parent);
	
	public void throwObject(Entity parent, double targetx, double targety);

}
