/**
 * Programmer: Eddie Penta
 * Date: Jun 4, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Waypoint.java
 */
package com.eddie.space.game.mover.aimover;

public class Waypoint {
	private double x;
	private double y;
	public Waypoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getDistance(double x2, double y2) {
		return Math.sqrt((Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)));
	}
	
	public double getDistance(Waypoint to) {
		return getDistance(to.getX(), to.getY());
	}
}
