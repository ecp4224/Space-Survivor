/**
 * Programmer: Eddie Penta
 * Date: May 16, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SpeedKeyMover.java
 */
package com.eddie.space.game.mover;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.model.SimpleKeyMover;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.RotatableEntity;
import com.eddie.space.entities.ships.Gun;
import com.eddie.space.entities.ships.SpaceCraft;

public class ShipKeyMover extends SimpleKeyMover {
	protected double speed = 1.5;
	protected Map<Integer, Boolean> bind_keys = new HashMap<Integer, Boolean>();
	
	/**
	 * @param parent
	 * @param core
	 */
	public ShipKeyMover(Entity parent, RPEG core) {
		super(parent, core);
		if (parent instanceof SpaceCraft) {
			SpaceCraft sc = (SpaceCraft)parent;
			for (Gun g : sc.getGuns()) {
				if (!bind_keys.containsKey(g.getBindKey()))
					bind_keys.put(g.getBindKey(), false);
			}
		}
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
		if (isKeyPressed(KeyEvent.VK_Q) && getParent() instanceof RotatableEntity) {
			RotatableEntity e = (RotatableEntity)getParent();
			int set = (int)(e.getRotation() - 5);
			if (set < 0)
				set = (int) (360 - speed);
			e.setRotation(set);
		}
		if (isKeyPressed(KeyEvent.VK_E) && getParent() instanceof RotatableEntity) {
			RotatableEntity e = (RotatableEntity)getParent();
			int set = (int)(e.getRotation() + 5);
			if (set > 360)
				set = (int) (speed);
			e.setRotation(set);
		}
		if (isKeyPressed(KeyEvent.VK_W) && getParent() instanceof RotatableEntity) {
			RotatableEntity e = (RotatableEntity)getParent();
			e.setRotation(0);
		}
		for (int i : bind_keys.keySet()) {
			if (isKeyPressed(i) && !bind_keys.get(i)) {
				SpaceCraft sc = (SpaceCraft)getParent();
				sc.fire(i);
				bind_keys.put(i, true);
			}
		}
	}
	
	@Override
	public void keyReleased(int key) {
		for (int i : bind_keys.keySet()) {
			if (key == i)
				bind_keys.put(key, false);
		}
	}

}
