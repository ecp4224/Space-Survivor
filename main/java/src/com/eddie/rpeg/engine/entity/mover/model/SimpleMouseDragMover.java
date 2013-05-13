/**
 * Programmer: Eddie Penta
 * Date: Jan 15, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: MouseDragMover.java
 */
package com.eddie.rpeg.engine.entity.mover.model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.mover.Mover;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Core;

public class SimpleMouseDragMover extends Mover implements MouseListener, MouseMotionListener {

	Window window;
	private boolean isdragging;
	private double _dragFromX = 0;
	private double _dragFromY = 0;
	private boolean dispose;
	private static boolean entitymoving = false;
	/**
	 * @param core
	 */
	public SimpleMouseDragMover(Entity parent, Core core, Window window) {
		super(parent, core, "MouseDragMover");
		this.window = window;
	}
	
	public void attachMover() {
		if (dispose)
			return;
		window.addMouseListener(this);
		window.addMouseMotionListener(this);
	}
	
	public void detachMover() {
		this.window.removeMouseListener(this);
		this.window = null;
		super.dispose();
		dispose = true;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (isdragging) {
			getParent().setX(arg0.getX() - _dragFromX);
			getParent().setY(arg0.getY() - _dragFromY);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		isdragging = false;
		entitymoving = false;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (isdragging)
			isdragging = false;
		int x = arg0.getX();
		int y = arg0.getY();
		System.out.println(x + "<=" + (getParent().getDrawX() + getParent().getWidth()) + " " + x + ">=" + getParent().getDrawX());
		if (x <= getParent().getDrawX() + getParent().getWidth() && x >= getParent().getDrawX() && y <= getParent().getDrawY() + getParent().getHeight() && y >= getParent().getDrawY() && !entitymoving) {
			entitymoving = true;
			isdragging = true;
			_dragFromX = x - getParent().getX();
			_dragFromY = y - getParent().getY();
		}
		else {
			entitymoving = false;
			isdragging = false;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) { }
	
	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.character.mover.Mover#moveTick()
	 */
	@Override
	public void moveTick() { }
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) { }
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) { }
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) { }
}
