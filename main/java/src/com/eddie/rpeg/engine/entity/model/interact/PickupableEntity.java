/**
 * Programmer: Eddie Penta
 * Date: Mar 21, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: PickupableEntity.java
 */
package com.eddie.rpeg.engine.entity.model.interact;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.Mover;
import com.eddie.rpeg.engine.entity.types.Pickupable;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;

public abstract class PickupableEntity extends ThrowableEntity implements Pickupable {
	private static final long serialVersionUID = 212391759553154172L;
	Entity e;
	boolean attached;
	/**
	 * @param name
	 * @param system
	 * @param level
	 */
	public PickupableEntity(String name, RPEG system, Level level) {
		super(name, system, level);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.character.interfaces.Pickupable#attach(com.eddie.rpeg.game.Entity)
	 */
	@Override
	public void attach(Entity e) {
		this.e = e;
		this.attached = true;
		for (Mover m : getMoverList()) {
			system.getTicker().removeTick(m);
		}
		clearMoverList();
	}

	@Override
	public void deattach() {
		this.e = null;
		this.attached = false;
		attachAllMovers();
	}
	
	@Override
	public void tick() {
		super.tick();
		if (attached) {
			if (e == null) {
				attached = false;
				return;
			}
			setX(e.getX());
			setY(e.getY());
		}
	}
	
	public abstract void attachAllMovers();

}
