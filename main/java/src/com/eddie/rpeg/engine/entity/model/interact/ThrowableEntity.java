/**
 * Programmer: Eddie Penta
 * Date: Jan 9, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Rock.java
 */
package com.eddie.rpeg.engine.entity.model.interact;

import com.eddie.rpeg.engine.entity.mover.Mover;
import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.CollisionMover;
import com.eddie.rpeg.engine.entity.mover.model.ProjectileMover;
import com.eddie.rpeg.engine.entity.mover.model.SimpleCollisionMover;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Smart;
import com.eddie.rpeg.engine.entity.types.Throwable;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;

public abstract class ThrowableEntity extends Entity implements Throwable, Damager {
	private static final long serialVersionUID = 1L;
	private boolean disposed;
	private boolean ehit;
	private boolean attached;
	private Entity parent;

	/**
	 * @param name
	 * @param system
	 */
	public ThrowableEntity(String name, RPEG system, Level level) {
		super(name, system, level);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.Entity#dispose()
	 */
	@Override
	public void dispose() {
		if (disposed)
			return;
		disposed = true;
		system.getTicker().removeTick(this);
		Mover[] m = move.toArray(new Mover[move.size()]);
		for (Mover mo : m) {
			move.remove(mo);
			system.getTicker().removeTick(mo);
		}
		parent = null;
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.character.Throwable#throwObject()
	 */
	@Override
	public void throwObject(Entity parent) {
		
	}
	
	public Entity getParent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.character.Throwable#throwObject(com.eddie.rpeg.game.Entity, int, int)
	 */
	@Override
	public void throwObject(Entity parent, double targetx, double targety) {
		this.parent = parent;
		attachMovers();
		for (Mover m : move) {
			if (m instanceof ProjectileMover)
				((ProjectileMover)m).startThrow(targetx, targety, parent);
		}
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.model.interact.PickupableEntity#attachAllMovers()
	 */
	public void attachMovers() {
		if (attached)
			return;
		attached = true;
		CollisionMover m = new SimpleCollisionMover(this, system);
		m.ignoreEntity(parent);
		addMover(m);
		ProjectileMover mm = new ProjectileMover(this, system);
		addMover(mm);
	}
	
	@Override
	public void clearMoverList() {
		super.clearMoverList();
		attached = false;
	}
	

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.character.Damageable#hit(com.eddie.rpeg.game.Entity, double, double)
	 */
	@Override
	public void onHit(Entity hit, double cx, double cy) {
		if (ehit || hit == parent)
			return;
		System.out.println("Hit " + hit.getName());
		if (hit instanceof Smart) {
			Smart c = (Smart)hit;
			c.alert(parent);
		}
		ehit = true;
		setVisable(false);
		dispose();
	}

}
