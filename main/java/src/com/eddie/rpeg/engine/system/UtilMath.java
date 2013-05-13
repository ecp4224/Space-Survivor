package com.eddie.rpeg.engine.system;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.CollisionMover;
import com.eddie.rpeg.engine.entity.types.Damager;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.entity.types.Throwable;

public class UtilMath {
	public final static Random rand = new Random();
	public static double[] collision(double x, double y, int width, int height, BufferedImage map) {
		return collision(x, y, width, height, null, null);
	}
	public static double[] collision(double x, double y, int width, int height, Entity owner, CollisionMover parent){
		double[] score=new double[2];
		for(int i=0;i<18;i++){
			if (owner != null && owner.getDrawerParent() != null) {
				Entity[] entities = owner.getDrawerParent().toArray(new Entity[owner.getDrawerParent().size()]);
				for (Entity e : entities) {
					if (e == owner)
						continue;
					if (e == null)
						continue;
					if (ignoreEntity(parent, e))
						continue;
					if (parent.doesColide(e)) {
						score[0]=score[0]+Math.cos(Math.toRadians(i*20));
						score[1]=score[1]+Math.sin(Math.toRadians(i*20));
						continue;
					}
					if (Math.abs(e.getY() - y) < 10 || e instanceof Throwable) {
						if (Math.abs(e.getX() - x+width*Math.cos(Math.toRadians(i*20))) < e.getWidth() && Math.abs(e.getY() - y+height*Math.sin(Math.toRadians(i*20))) < e.getHeight()) {
							if (owner instanceof Damager) {
								Damager d = (Damager)owner;
								if (e instanceof Killable) {
									Killable k = (Killable)e;
									if (k.canKill()) {
										d.onHit(e, e.getX(), e.getY());
										k.hit(d.getDamage());
										System.out.println(k.getHealth());
									}
								}
							}
							score[0]=score[0]+Math.cos(Math.toRadians(i*20));
							score[1]=score[1]+Math.sin(Math.toRadians(i*20));
						}
					}
				}
			}
		}
		return score;
	}
	
	private static boolean ignoreEntity(CollisionMover mover, Entity e) {
		for (Entity ee : mover.ignore) {
			if (ee == null)
				continue;
			if (ee.getName().equals(e.getName()) && e.getID() == ee.getID())
				return true;
		}
		return false;
	}
}
