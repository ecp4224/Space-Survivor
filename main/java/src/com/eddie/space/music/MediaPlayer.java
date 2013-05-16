/**
 * Programmer: Eddie Penta
 * Date: May 16, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: MediaPlayer.java
 */
package com.eddie.space.music;

public interface MediaPlayer {
	public int getSpeed();
	public void play(String filepath);
	public void close();
}
