package com.eddie.rpeg.engine.entity.mover;

import java.util.ArrayList;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.rpeg.engine.system.UtilMath;

public abstract class CollisionMover extends Mover {
	public ArrayList<Entity> ignore = new ArrayList<Entity>();
	public CollisionMover(Entity parent, RPEG core) {
		super(parent, core, "CollisionMover");
	}

	public void ignoreEntity(Entity e) {
		if (!ignore.contains(e))
			ignore.add(e);
	}

	@Override
	public void moveTick() {
		try {
			double[] a = UtilMath.collision(getParent().getX(), getParent().getY(), getParent().getWidth(), getParent().getHeight(), getParent(), this);
			if (a[0] != 0 && a[1] != 0) {
				getParent().setX(getParent().getX() + a[0]);
				getParent().setY(getParent().getY() + a[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract boolean doesColide(Entity e);
	
}
