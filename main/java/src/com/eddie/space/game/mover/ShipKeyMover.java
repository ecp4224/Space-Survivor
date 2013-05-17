/**
 * Programmer: Eddie Penta
 * Date: May 16, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SpeedKeyMover.java
 */
package com.eddie.space.game.mover;

import java.awt.event.KeyEvent;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.model.SimpleKeyMover;
import com.eddie.rpeg.engine.system.Core;

public class ShipKeyMover extends SimpleKeyMover {
	protected double speed = 1.5;
	
	/**
	 * @param parent
	 * @param core
	 */
	public ShipKeyMover(Entity parent, Core core) {
		super(parent, core);
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	@Override
	public void checkKeys() {
		if (isKeyPressed(KeyEvent.VK_A))
			getParent().setX(getParent().getX() - speed);
		if (isKeyPressed(KeyEvent.VK_D))
			getParent().setX(getParent().getX() + speed);
	}

}
