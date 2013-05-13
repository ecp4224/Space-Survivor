/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SpaceCraft.java
 */
package com.eddie.space.entities;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.entity.types.Smart;

public abstract class SpaceCraft extends Entity implements Killable, Damager, Smart {
	private static final long serialVersionUID = 5982525011317556405L;

}
