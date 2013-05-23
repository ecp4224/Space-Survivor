/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SpaceCraft.java
 */
package com.eddie.space.entities;

import java.lang.reflect.Constructor;
import com.eddie.rpeg.engine.entity.mover.CollisionMover;
import com.eddie.rpeg.engine.entity.mover.model.SimpleCollisionMover;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.entity.types.Smart;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.bullets.BulletManager;

public abstract class SpaceCraft extends RotatableEntity implements Killable, Damager, Smart {
	private static final long serialVersionUID = 5982525011317556405L;
	protected Class<? extends Bullet> bullet_type;
	
	public SpaceCraft(String name, RPEG core, Level level) {
		super(name, core, level);
		setRotation(0);
	}
	
	public void setBulletType(Class<? extends Bullet> class_) {
		this.bullet_type = class_;
	}
	
	public void fire() {
		if (!BulletManager.initRan())
			BulletManager.init(system);
		if (getRotation() + 10 >= 360)
			setRotation(0);
		else
			setRotation(getRotation() + 10);
		try {
			Constructor<? extends Bullet> construct = bullet_type.getConstructor(Level.class, RPEG.class);
			Bullet b = construct.newInstance(getLevel(), system);
			CollisionMover cm = new SimpleCollisionMover(b, system);
			cm.ignoreEntity(this);
			double bx = getX() + (getImage().getWidth() / 2);
			double by = getY() + (getImage().getHeight() / 2);
			b.setX(bx + getBulletXOffset());
			b.setY(by + getBulletYOffset());
			b.setRotation(getRotation());
			b.goingUp(getRotation() < 90 || getRotation() > 270);
			double xx;
			if (getRotation() < 90 || getRotation() > 270) {
				xx = getImage().getHeight() / 2 * Math.tan(Math.toRadians(getRotation()));
			} else {
				xx = getImage().getHeight() / 2 * Math.tan(Math.toRadians(getRotation()));
				xx *= -1;
			}
			b.setXAdd(xx);
			b.setVisable(true);
			getDrawerParent().addObject(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract int getBulletXOffset(); 
	
	public abstract int getBulletYOffset();
}
