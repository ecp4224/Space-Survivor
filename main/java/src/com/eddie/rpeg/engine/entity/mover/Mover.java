/**
 * Programmer: Eddie Penta
 * Date: Oct 23, 2012
 * Purpose: <INSERT PURPOSE>
 * File name: Mover.java
 */
package com.eddie.rpeg.engine.entity.mover;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.rpeg.engine.system.Tick;

public abstract class Mover implements Tick {
	
	private Entity parent;
	
	private RPEG core;
	
	protected String name;
	
	protected boolean suspend;
	
	public Mover(Entity parent, RPEG core, String name) {
		this.parent = parent;
		this.core = core;
		this.name = name;
		core.getTicker().addTick(this);
	}
	
	public Mover(RPEG core) {
		this(null, core, "Mover");
	}
	
	public Entity getParent() {
		return parent;
	}
	
	public void setParent(Entity c) {
		this.parent = c;
	}
	
	public String getName() {
		return (name == null ? getClass().getSimpleName() : name);
	}
	
	public RPEG getCore() {
		return core;
	}
	
	protected void dispose() {
		core.getTicker().removeTick(this);
	}
	
	public abstract void moveTick();
	
	@Override
	public void tick() {
		if (suspend)
			return;
		moveTick();
	}
	
	
	public boolean isSuspended() {
		return suspend;
	}
	
	public void suspend(boolean suspend) {
		this.suspend = suspend;
	}
	
	/* (non-Javadoc)
	 * @see com.eddie.rpeg.system.Tick#inSeperateThread()
	 */
	@Override
	public boolean inSeperateThread() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.system.Tick#getTimeout()
	 */
	@Override
	public int getTimeout() {
		return 5;
	}
}
