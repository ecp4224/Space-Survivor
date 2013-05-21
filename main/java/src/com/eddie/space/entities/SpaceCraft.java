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
import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.CollisionMover;
import com.eddie.rpeg.engine.entity.mover.model.SimpleCollisionMover;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.entity.types.Smart;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;

public abstract class SpaceCraft extends Entity implements Killable, Damager, Smart {
	private static final long serialVersionUID = 5982525011317556405L;
	protected int rot;
	private AffineTransformOp op;
	protected Class<? extends Bullet> bullet_type;
	
	public SpaceCraft(String name, RPEG core, Level level) {
		super(name, core, level);
		setRotation(0);
	}
	
	public void setBulletType(Class<? extends Bullet> class_) {
		this.bullet_type = class_;
	}
	
	public void fire() {
		try {
			Constructor<? extends Bullet> construct = bullet_type.getConstructor(Level.class, RPEG.class);
			Bullet b = construct.newInstance(getLevel(), system);
			CollisionMover cm = new SimpleCollisionMover(b, system);
			cm.ignoreEntity(this);
			b.setX(getX() + getBulletXOffset());
			b.setY(getY() + getBulletYOffset());
			b.setVisable(true);
			getDrawerParent().addObject(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public abstract int getBulletXOffset();
	
	public abstract int getBulletYOffset();
}
