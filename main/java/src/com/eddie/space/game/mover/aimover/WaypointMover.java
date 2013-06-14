/**
 * Programmer: Eddie Penta
 * Date: Jun 4, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: WaypointMover.java
 */
package com.eddie.space.game.mover.aimover;

import java.util.ArrayList;
import java.util.List;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.Mover;
import com.eddie.rpeg.engine.system.RPEG;

public class WaypointMover extends Mover {
	private List<Waypoint> waypoints = new ArrayList<Waypoint>();
	private Waypoint current_target;
	private boolean moving;
	private double directionx;
	private double directiony;
	private boolean xreached;
	private boolean yreached;
	private double speed = 1.5;
	private boolean active = false;

	/**
	 * @param parent
	 * @param core
	 * @param name
	 */
	public WaypointMover(Entity parent, RPEG core) {
		super(parent, core, "WaypointMover");
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.mover.Mover#moveTick()
	 */
	@Override
	public void moveTick() {
		if (!active)
			return;
		if (moving) {
			if (current_target != null && !hasReachedTarget()) {
				moveTowards(current_target, speed);
			} else {
				moving = false;
				waypoints.remove(current_target);
				current_target = null;
				directionx = 0;
				directiony = 0;
				xreached = false;
				yreached = false;
				return;
			}
		}
		else {
			if (waypoints.size() > 0) {
				current_target = waypoints.get(0);
				moving = true;
				moveTowards(current_target, speed);
				return;
			}
		}
	}

	private void moveTowards(Waypoint waypoint, double speed) {
		
		if (directionx == 0)
			directionx = (waypoint.getX() - getParent().getX() < -1 ? -speed : speed);
		if (directiony == 0)
			directiony = (waypoint.getY() - getParent().getY() < -1 ? -speed : speed);

		if ((directionx < 0 && waypoint.getX() - getParent().getX() < -1) || (directionx > 0 && waypoint.getX() - getParent().getX() >= -1)) {
			getParent().setX(getParent().getX() + directionx);
		} else if ((directionx < 0 && waypoint.getX() - getParent().getX() >= -1) || (directionx > 0 && waypoint.getX() - getParent().getX() <= 1)) {
			xreached = true;
		}

		if ((directiony < 0 && waypoint.getY() - getParent().getY() < -1) || (directiony > 0 && waypoint.getY() - getParent().getY() >= -1)) {
			getParent().setY(getParent().getY() + directiony);
		} else if ((directiony < 0 && waypoint.getY() - getParent().getY() >= -1) || (directiony > 0 && waypoint.getY() - getParent().getY() <= 1)) {
			yreached = true;
		}
	}

	private boolean hasReachedTarget() {
		return xreached && yreached;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public void clearMover() {
		current_target = null;
		waypoints.clear();
	}

	public void addWaypoint(Waypoint toadd) {
		waypoints.add(toadd);
	}

	public void addWaypoint(double targetx, double targety) {
		addWaypoint(new Waypoint(targetx, targety));
	}
	
	public void setActive(boolean value) {
		this.active = value;
	}
	
	public boolean isActive() {
		return active;
	}
}
