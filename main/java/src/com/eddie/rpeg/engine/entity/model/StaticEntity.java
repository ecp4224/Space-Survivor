/**
 * Programmer: Eddie Penta
 * Date: Jan 16, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: StaticEntity.java
 */
package com.eddie.rpeg.engine.entity.model;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.rpeg.engine.level.Level;

public abstract class StaticEntity extends Entity {
	private static final long serialVersionUID = 1291841221466455343L;

	/**
	 * @param name
	 * @param system
	 * @param level
	 * @param load
	 */
	public StaticEntity(String name, Core system, Level level) {
		super(name, system, level, false);
	}

	/**
	 * 
	 */
	public StaticEntity() { }

	@Override
	public void tick() {
		if (super.animation != null)
			super.tick(); //Only tick if animation is loaded
	}

}
