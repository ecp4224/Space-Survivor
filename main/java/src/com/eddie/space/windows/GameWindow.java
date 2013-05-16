/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: GameWindow.java
 */
package com.eddie.space.windows;

import java.awt.event.KeyEvent;
import java.util.Random;

import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.space.entities.Star;
import com.eddie.space.entities.player.Player;
import com.eddie.space.game.mover.SpeedKeyMover;
import com.eddie.space.game.world.SpaceWorld;

public class GameWindow extends Window {
	private static final long serialVersionUID = 2704165502672086720L;
	private SpaceWorld w;

	/**
	 * @param system
	 */
	public GameWindow(Core system) {
		super(system);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#tick()
	 */
	@Override
	public void tick() {
		createStar();
		createStar();
		createStar();
	}
	
	private void createStar() {
		Star s = new Star(getSystem(), w, null);
		s.setX(new Random().nextInt(getSystem().getMaxScreenX()));
		s.setY(0);
		Star.setSpeed(20.0);
		s.setVisable(true);
		
		getObjectDrawer().addObject(s);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#inSeperateThread()
	 */
	@Override
	public boolean inSeperateThread() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#getTimeout()
	 */
	@Override
	public int getTimeout() {
		return 50;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.Window#init()
	 */
	@Override
	public void init() {
		getObjectDrawer().register(getSystem());
		getObjectDrawer().layerEntities(false);
		w = new SpaceWorld(this);
		
		addPlayer();
		
	}
	
	private void addPlayer() {
		Player p = new Player(getSystem(), w);
		p.setX(50);
		p.setY(50);
		p.setVisable(true);
		SpeedKeyMover move = new SpeedKeyMover(p, super.getSystem());
		p.addMover(move);
		getObjectDrawer().addObject(p);
		move.attachMover(this);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.Window#getName()
	 */
	@Override
	public String getName() {
		return "Space!";
	}
}
