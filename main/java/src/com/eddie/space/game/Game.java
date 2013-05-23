/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Game.java
 */
package com.eddie.space.game;

import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.windows.GameWindow;

public class Game {
	
	private static final Game GAME = new Game();
	private final RPEG engine = new RPEG();
	public static final boolean DEBUG = false;
	/**
	 * The difficulty of the game
	 * .5 = HARD
	 * 1 = NORMAL
	 * 2 = EASY
	 * 3 = TRAINING
	 */
	public static final double DIFFICULTY = 1;
	private void startGame() {
		RPEG.center_player = false;
		engine.init(800, 600);
		Window w = new GameWindow(engine);
		engine.setWindow(w);
	}
	
	
	
	
	public static void main(String[] args) {
		GAME.startGame();
	}
}
