/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Game.java
 */
package com.eddie.space.game;

import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.music.MediaPlayer;
import com.eddie.space.windows.GameWindow;
import com.eddie.space.windows.MenuWindow;

public class Game {
	
	public static final Game GAME = new Game();
	private final RPEG engine = new RPEG();
    public static MediaPlayer m;
	public static final boolean DEBUG = false;
	/**
	 * The difficulty of the game
	 * .5 = HARD
	 * 1 = NORMAL
	 * 2 = EASY
	 * 3 = TRAINING
	 */
	public static final double DIFFICULTY = .5;
	private void startGame() {
		RPEG.center_player = false;
		engine.init(800, 600);
		Window w = new MenuWindow(engine);
		engine.setWindow(w);
	}
	
	public void begin(String song) {
	    Window w = new GameWindow(engine, song);
        engine.setWindow(w);
	}
	
	
	
	
	public static void main(String[] args) {
		GAME.startGame();
	}
}
