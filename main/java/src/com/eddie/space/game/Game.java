/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Game.java
 */
package com.eddie.space.game;

import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.space.windows.GameWindow;

public class Game {
	
	private static final Game GAME = new Game();
	private final Core RPEG = new Core();
	private void startGame() {
		Core.center_player = false;
		RPEG.init(800, 600);
		Window w = new GameWindow(RPEG);
		RPEG.setWindow(w);
	}
	
	
	
	
	public static void main(String[] args) {
		GAME.startGame();
	}
}
