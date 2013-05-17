/**
 * Programmer: Eddie Penta
 * Date: May 16, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: MediaPlayer.java
 */
package com.eddie.space.music;

import java.io.IOException;

public interface MediaPlayer {
	public int getSpeed();
	public void play(String filepath) throws IOException;
	public void close();
}
