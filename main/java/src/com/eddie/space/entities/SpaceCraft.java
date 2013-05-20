/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SpaceCraft.java
 */
package com.eddie.space.entities;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.entity.types.Smart;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;

public abstract class SpaceCraft extends Entity implements Killable, Damager, Smart {
	private static final long serialVersionUID = 5982525011317556405L;
	protected int rot;
	private AffineTransformOp op;
	
	public SpaceCraft(String name, RPEG core, Level level) {
		super(name, core, level);
		setRotation(0);
	}
	
	public void fire() {
		
	}
	
	public int getRotation() {
		return rot;
	}
	
	public void setRotation(int rotation) {
		if (rotation < 0 || rotation > 360)
			throw new InvalidParameterException("The rotation must be between 0 and 360!");
		this.rot = rotation;
		double rotationRequired = Math.toRadians(rot);
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, getX(), getY());
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	}
	
	@Override
	public BufferedImage getImage() {
		return op.filter(super.getImage(), null);
	}
}
