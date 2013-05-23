/**
 * Programmer: Eddie Penta
 * Date: May 21, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: RotatableEntity.java
 */
package com.eddie.space.entities;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;

public abstract class RotatableEntity extends Entity {
	private static final long serialVersionUID = 3747773792496905457L;
	protected int rot;
	private AffineTransformOp op;
	
	public RotatableEntity(String name, RPEG core, Level level) {
		super(name, core, level);
	}
	
	public int getRotation() {
		return rot;
	}
	
	public void setRotation(int rotation) {
		if (getImage() == null)
			return;
		if (rotation < 0 || rotation > 360)
			throw new InvalidParameterException("The rotation must be between 0 and 360!");
		this.rot = rotation;
		double rotationRequired = Math.toRadians(rot);
		double w = super.getImage().getWidth() / 2;
		if (w <= 0)
			w = super.getImage().getWidth() * 2;
		double h = super.getImage().getHeight() / 2;
		if (h <= 0)
			h = super.getImage().getHeight() * 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, w, h);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	}
	
	@Override
	public BufferedImage getImage() {
		if (op != null)
			return op.filter(super.getImage(), null);
		return super.getImage();
	}

}
