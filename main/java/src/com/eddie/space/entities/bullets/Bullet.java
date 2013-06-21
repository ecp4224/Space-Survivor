/**
 * Programmer: Eddie Penta
 * Date: May 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Bullet.java
 */
package com.eddie.space.entities.bullets;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.InvalidCollisionException;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.animation.AnimationCallback.OnAnimationCompleted;
import com.eddie.rpeg.engine.render.animation.AnimationStyle;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.RotatableEntity;
import com.eddie.space.windows.GameWindow;

public abstract class Bullet extends RotatableEntity implements Damager {
	private static final long serialVersionUID = -3436725886675567328L;
	protected int yadd;
	protected double xadd;
	
	public Bullet(String name, Level l, RPEG core) {
		super(name, core, l);
		if (getAnimation() != null)
			getAnimation().setOnAnimationCompletedCallback(ANIFINISH);
	}
	
	@Override
	public void onLoad() {
		if (getAnimation() != null)
			getAnimation().setOnAnimationCompletedCallback(ANIFINISH);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Damager#onHit(com.eddie.rpeg.engine.entity.Entity, double, double)
	 */
	@Override
	public void onHit(Entity hit, double cx, double cy) throws InvalidCollisionException {
		if (getAnimation() == null) {
			dispose();
			return;
		}
		if (getAnimation().getStyle() == AnimationStyle.DEATH)
			throw new InvalidCollisionException();
		super.clearMoverList();
		setY(getY() + (hit.getImage().getHeight() / 2));
		super.getAnimation().setAnimation(AnimationStyle.DEATH);
		super.tick();
	}
	
	
	@Override
	public void tick() {
		if (GameWindow.finish) {
			dispose();
			return;
		}
		super.tick();
		if (getAnimation() == null) {
			dispose();
		}
		else if (isVisible() && getAnimation().getStyle() != AnimationStyle.DEATH) {
			setX(getX() + xadd);
			double y = getY() - (BulletManager.getSpeed() * yadd);
			setY(y);
			if (getY() <= 0 || getY() > system.getMaxScreenY())
				dispose();
		}
	}
	
	public void setXAdd(double d) {
		this.xadd = d;
	}
	
	public void goingUp(boolean up) {
		if (up)
			yadd = 1;
		else
			yadd = -1;
	}
	
	private final OnAnimationCompleted ANIFINISH = new OnAnimationCompleted() {

		@Override
		public void onAnimationCompleted(AnimationStyle style) {
			if (style == AnimationStyle.DEATH)
				dispose();
		}
		
	};

}
