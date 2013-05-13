package com.eddie.rpeg.engine.system;

public interface Tick {
	
	/**
	 * This is called whenever the system ticks
	 */
	public void tick();
	
	/**
	 * Weather or not to run this tick object
	 * in a separate thread
	 * @return
	 *        returns true if it will run in a separate thread,
	 *        otherwise it will return false.
	 */
	public boolean inSeperateThread();
	
	
	public int getTimeout();
}
