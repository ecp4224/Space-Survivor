/**
 * Programmer: Eddie Penta
 * Date: Oct 18, 2012
 * Purpose: <INSERT PURPOSE>
 * File name: Mover.java
 */
package com.eddie.rpeg.engine.entity.mover;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.render.gui.SwingWindow;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.windows.GameWindow;

public abstract class KeyMover extends Mover implements KeyListener {
	private GameWindow window;
	protected boolean dispose = false;
	protected final boolean[] keys = new boolean[256];
	
	/**
	 * @param parent
	 */
	public KeyMover(Entity parent, RPEG core) {
		super(parent, core, "KeyMover");
	}
	
	public void attachMover(GameWindow w) {
		if (dispose)
			return;
		this.window = w;
		w.addKeyListener(this);
	}
	
	public void detachMover() {
		this.window.removeKeyListener(this);
		this.window = null;
		super.dispose();
		dispose = true;
	}
	
	/* (non-Javadoc)
	 * @see com.eddie.rpeg.system.Tick#moveTick()
	 */
	@Override
	public void moveTick() {
		if (getParent() == null)
			return;
		checkKeys();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		keyReleased(e.getKeyCode());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) { }
	
	public abstract void checkKeys();
	
	public abstract void keyPressed(int key);
	
	public abstract void keyReleased(int key);
	
	public boolean isKeyPressed(int key) {
		return keys[key];
	}
	
	
}
