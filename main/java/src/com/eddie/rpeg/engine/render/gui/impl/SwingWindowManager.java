/**
 * Programmer: Eddie Penta
 * Date: Jun 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SwingWindowManager.java
 */
package com.eddie.rpeg.engine.render.gui.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import com.eddie.rpeg.engine.render.gui.GameWindow;
import com.eddie.rpeg.engine.render.gui.SwingWindow;
import com.eddie.rpeg.engine.render.gui.WindowManager;

public class SwingWindowManager implements WindowManager {
	
	private JFrame frame = new JFrame("Loading...");
	private SwingWindow currentlyselected;
	private int MAX_SCREEN_X;
	private int MAX_SCREEN_Y;
	private final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	public SwingWindowManager(int maxx, int maxy) {
		MAX_SCREEN_X = maxx;
		MAX_SCREEN_Y = maxy;
		frame.setMinimumSize(new Dimension(maxx, maxy));
		frame.setLocation((gd.getDisplayMode().getWidth() - frame.getWidth()) / 2, (gd.getDisplayMode().getHeight() - frame.getHeight()) / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.WindowManager#setWindow(com.eddie.rpeg.engine.render.gui.GameWindow)
	 */
	@Override
	public void setWindow(GameWindow game) {
		if (!(game instanceof SwingWindow))
			return;
		SwingWindow w = (SwingWindow)game;
		if (currentlyselected != null) {
    	    currentlyselected.onUnload();
    		currentlyselected = w;
    		frame.setVisible(false);
    		frame.dispose();
    		frame = null;
    		
    		frame = new JFrame("Loading..");
    		frame.setMinimumSize(new Dimension(MAX_SCREEN_X, MAX_SCREEN_Y));
            frame.setLocation((gd.getDisplayMode().getWidth() - frame.getWidth()) / 2, (gd.getDisplayMode().getHeight() - frame.getHeight()) / 2);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	}
		currentlyselected = w;
		currentlyselected.init();
    	frame.setLayout(new BorderLayout());
		frame.add(currentlyselected, "Center");
		frame.setTitle(w.getName());
		currentlyselected.setFocusable(true);
		frame.pack();
		frame.setVisible(true);
    	currentlyselected.onLoad();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.WindowManager#getActiveWindow()
	 */
	@Override
	public GameWindow getActiveWindow() {
		return currentlyselected;
	}
	
}
