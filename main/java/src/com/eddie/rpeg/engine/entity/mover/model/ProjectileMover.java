/**
 * Programmer: Eddie Penta
 * Date: Jan 14, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: ProjectileMover.java
 */
package com.eddie.rpeg.engine.entity.mover.model;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.model.interact.ThrowableEntity;
import com.eddie.rpeg.engine.entity.mover.CollisionMover;
import com.eddie.rpeg.engine.entity.mover.Mover;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.rpeg.engine.system.UtilMath;

public class ProjectileMover extends Mover {
	final double gravity = 1;
	final double vi = 60;

	double xvel, yvel;

	double time;
	double startx;
	double starty;
	double tx;
	double ty;
	int dcount;
	double wait;
	boolean move;
	boolean subtract;
	boolean towardstarget;
	boolean neg;

	/**
	 * @param parent
	 * @param core
	 * @param name
	 */
	public ProjectileMover(Entity parent, Core core) {
		super(parent, core, "ProjectileMover");
	}

	@Override
	public int getTimeout() {
		return 20;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.character.mover.Mover#moveTick()
	 */
	@Override
	public void moveTick() {
		if (move) {
			try {
				if (getParent() == null) {
					move = false;
					return;
				}
				if (!towardstarget) {
					if (subtract) {
						getParent().setX(getParent().getX() - 1.5);
						getParent().setY(-(2 * Math.cos(0.6 * (Math.abs(startx - getParent().getX())) + 2)));
					}
					else {
						getParent().setX(getParent().getX() + 1.5);
						getParent().setY(-(2 * Math.cos(0.6 * (Math.abs(startx - getParent().getX())) + 2)));
					}
				}
				else {
					int toadd = 0;
					yvel += gravity;
					if (getParent().getMover("CollisionMover") != null) {
						double[] a = UtilMath.collision(getParent().getX() + xvel, getParent().getY() + yvel, getParent().getWidth(), getParent().getHeight(), getParent(), (CollisionMover)getParent().getMover("CollisionMover"));
						toadd += a[0];
						yvel += a[1];
					}
					getParent().setX(getParent().getX() + xvel + toadd);
					if (getParent() instanceof ThrowableEntity) {
						ThrowableEntity te = (ThrowableEntity)getParent();
						if (getParent().getY() > te.getParent().getY() + (te.getParent().getHeight() * 2)) {
							xvel -= ((neg && xvel < 0) ? -.5 : (!neg && xvel > 0) ? .5 : 0);
							if ((neg && xvel > 0) || (!neg && xvel < 0))
								xvel = 0;
							getParent().setAnimationSpeed(getParent().getAnimationSpeed() * 2);
							if ((neg && xvel >= 0) || (!neg && xvel <= 0)) {
								dcount++;
								if (dcount < 25)
									return;
								move = false;
								getParent().clearMoverList();
								return;
							}
						}
					}
					getParent().setY(getParent().getY() + yvel);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void startThrow(double targetx, double targety, Entity parent) {
		towardstarget = true;
		tx = targetx;
		ty = targety;
		subtract = !parent.flipped;
		move = true;
		startx = getParent().getX();
		starty = getParent().getY();
		double distance = Math.sqrt((tx - startx)*(tx - startx) + (ty - starty)*(ty - starty));
		double power = 20;
		xvel = ((tx-startx)/distance)*power;
		yvel = ((ty-starty)/distance)*power;
		getParent().setVisable(true);
		time = 1;
		dcount = 0;
		if (xvel < 0)
			neg = true;
		else
			neg = false;
	}

}
