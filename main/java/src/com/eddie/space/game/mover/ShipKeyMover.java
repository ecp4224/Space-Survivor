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
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.SpaceCraft;

public class ShipKeyMover extends SimpleKeyMover {
	protected double speed = 1.5;
	protected boolean fired = false;
	
	/**
	 * @param parent
	 * @param core
	 */
	public ShipKeyMover(Entity parent, RPEG core) {
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
		if (isKeyPressed(KeyEvent.VK_SPACE) && !fired) {
			if (getParent() instanceof SpaceCraft) {
				SpaceCraft sc = (SpaceCraft)getParent();
				sc.fire();
				fired = true;
			}
		}
	}
	
	@Override
	public void keyReleased(int key) {
		if (key == KeyEvent.VK_SPACE)
			fired = false;
	}

}
