/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: GameWindow.java
 */
package com.eddie.space.windows;

import java.awt.event.KeyEvent;

import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Core;

public class GameWindow extends Window {
	private static final long serialVersionUID = 2704165502672086720L;

	/**
	 * @param system
	 */
	public GameWindow(Core system) {
		super(system);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#tick()
	 */
	@Override
	public void tick() {
		
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
		return 0;
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
		
		
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.Window#getName()
	 */
	@Override
	public String getName() {
		return "Space!";
	}
}
