/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SpaceCraft.java
 */
package com.eddie.space.entities.ships;

import java.lang.reflect.Constructor;
import com.eddie.rpeg.engine.entity.mover.CollisionMover;
import com.eddie.rpeg.engine.entity.mover.model.SimpleCollisionMover;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.entity.types.Smart;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.Explode;
import com.eddie.space.entities.RotatableEntity;
import com.eddie.space.entities.bullets.Bullet;
import com.eddie.space.entities.bullets.BulletManager;

public abstract class SpaceCraft extends RotatableEntity implements Killable, Damager, Smart {
	private static final long serialVersionUID = 5982525011317556405L;
	protected Class<? extends Bullet> bullet_type;

	public SpaceCraft(String name, RPEG core, Level level) {
		super(name, core, level);
		setRotation(0);
	}
	
	public void explode() {
		Explode e = new Explode(getLevel(), system);
		e.setX(getX());
		e.setY(getY());
		e.setVisible(true);
		e.setAnimationSpeed(50);
		getDrawerParent().addObject(e);
		
		
		setVisible(false);
		dispose();
	}
	
	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Killable#kill()
	 */
	@Override
	public void kill() {
		explode();
	}

	public void setBulletType(Class<? extends Bullet> class_) {
		this.bullet_type = class_;
	}
	
	public void fire(int keyPressed) {
		fire(keyPressed, true);
	}

	public void fire(int keyPressed, boolean goingup) {
		if (!BulletManager.initRan())
			BulletManager.init(system);
		try {
			for (Gun g : getGuns()) {
				if (g.getBindKey() == keyPressed)
					fireFromGun(g, goingup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fireFromGun(Gun g, boolean goingup) {
		if (getDrawerParent() == null)
			return;
		try {
			Constructor<? extends Bullet> construct = bullet_type.getConstructor(Level.class, RPEG.class);
			Bullet b = construct.newInstance(getLevel(), system);
			double bx = getX() + (getImage().getWidth() / 2);
			double by = getY() + (getImage().getHeight() / 2);
			b.setX(bx + g.getXOffset());
			b.setY(by + g.getYOffset());
			b.setRotation(getRotation());
			b.goingUp(((getRotation() < 90 || getRotation() > 270) && goingup) || ((getRotation() > 90 && getRotation() < 270 && !goingup)));
			double xx = getImage().getHeight() / 2 * Math.tan(Math.toRadians(getRotation()));
			if (getRotation() > 90 && getRotation() < 270)
				xx *= -1;
			b.setXAdd(xx);
			b.setVisible(true);
			CollisionMover cm = new SimpleCollisionMover(b, system);
			cm.ignoreEntity(this);
			if (getMover("CollisionMover") != null) {
				CollisionMover move = (CollisionMover) getMover("CollisionMover");
				move.ignoreEntity(b);
			}
			b.addMover(cm);
			if (getDrawerParent() == null)
				return;
			getDrawerParent().addObject(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all the guns this spacecraft can use to fire bullets.
	 * @return
	 */
	public abstract Gun[] getGuns();
}
