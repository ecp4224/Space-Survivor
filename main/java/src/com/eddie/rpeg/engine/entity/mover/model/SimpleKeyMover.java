/**
 * Programmer: Eddie Penta
 * Date: Apr 12, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SimpleKeyMover.java
 */
package com.eddie.rpeg.engine.entity.mover.model;

import java.awt.event.KeyEvent;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.KeyMover;
import com.eddie.rpeg.engine.entity.types.Pickupable;
import com.eddie.rpeg.engine.render.animation.AnimationStyle;
import com.eddie.rpeg.engine.system.Core;

public class SimpleKeyMover extends KeyMover {

	/**
	 * @param parent
	 * @param core
	 */
	public SimpleKeyMover(Entity parent, Core core) {
		super(parent, core);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.mover.KeyMover#checkKeys()
	 */
	@Override
	public void checkKeys() {
		if (isKeyPressed(KeyEvent.VK_A))
			getParent().setX(getParent().getX() - 1.5);
		if (isKeyPressed(KeyEvent.VK_D))
			getParent().setX(getParent().getX() + 1.5);
		if (isKeyPressed(KeyEvent.VK_S))
			getParent().setY(getParent().getY() + 1.5);
		if (isKeyPressed(KeyEvent.VK_W))
			getParent().setY(getParent().getY() - 1.5);
		if (getParent().getAnimation() != null) {
			if ((isKeyPressed(KeyEvent.VK_W) || isKeyPressed(KeyEvent.VK_A) || isKeyPressed(KeyEvent.VK_S) || isKeyPressed(KeyEvent.VK_D)))
				getParent().getAnimation().setAnimation(AnimationStyle.WALKING);
			else if (getParent().getAnimation().getStyle() == AnimationStyle.WALKING)
				getParent().getAnimation().setAnimation(AnimationStyle.IDLE);
		}
		if (isKeyPressed(KeyEvent.VK_D))
			getParent().flipped = true;
		else
			getParent().flipped = false;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.mover.KeyMover#keyPressed(int)
	 */
	@Override
	public void keyPressed(int key) { }

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.mover.KeyMover#keyReleased(int)
	 */
	@Override
	public void keyReleased(int key) {
		if (key == KeyEvent.VK_SPACE) {
			for (int i = 0; i < getParent().getDrawerParent().size(); i++) {
				final Entity ee = getParent().getDrawerParent().get(i);
				if (!(ee instanceof Pickupable))
					continue;
				System.out.println("[ATTEMPT] Picking up " + ee.getName() + "(" + ee.getX() + ", " + ee.getY() + "), (" + ee.getDrawX() + ", " + ee.getDrawY() + ")");
				Pickupable p = (Pickupable)ee;
				if (Math.abs(ee.getX() - getParent().getX()) < getParent().getWidth() + 15 && Math.abs(ee.getY() - getParent().getY()) < getParent().getHeight() + 15) {
					getParent().pickUp(p);
					System.out.println("[DONE] Picking up " + ee.getName());
				}
			}
		}
	}

}
