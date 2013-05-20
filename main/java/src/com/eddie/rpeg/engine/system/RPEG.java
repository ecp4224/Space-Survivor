package com.eddie.rpeg.engine.system;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import com.eddie.rpeg.engine.events.EventSystem;
import com.eddie.rpeg.engine.render.gui.Window;

public class RPEG {
    /**
     * Determines whether or not RPEG should draw entities relative to
     * {@link RPEG#CENTER_X} and {@link RPEG#CENTER_Y}.
     */
    public static boolean center_player = true;
    
    private int MAX_SCREEN_X;
    
    private int MAX_SCREEN_Y;
    
    public static Font DEFAULT_FONT;
    
    public static String DEFAULT_FONT_NAME = "8BIT WONDER";
    
    public double CENTER_X;
    
    public double CENTER_Y;
    
    private Ticker tick;

    private EventSystem es;
    
    private JFrame frame = new JFrame("Loading...");
    
    private Window currentlyselected;
    
    /**
     * Initialize the engine and set the max screen width and height.
     * @param maxx
     *            The max screen width to set
     * @param maxy
     *            The max screen height to set
     */
    public void init(int maxx, int maxy) {
    	this.MAX_SCREEN_X = maxx;
    	this.MAX_SCREEN_Y = maxy;
    	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame.setMinimumSize(new Dimension(MAX_SCREEN_X, MAX_SCREEN_Y));
		frame.setLocation((gd.getDisplayMode().getWidth() - frame.getWidth()) / 2, (gd.getDisplayMode().getHeight() - frame.getHeight()) / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public int getMaxScreenX() {
    	return MAX_SCREEN_X;
    }
    
    public int getMaxScreenY() {
    	return MAX_SCREEN_Y;
    }
    
    public void setMaxScreenX(int maxx) {
    	this.MAX_SCREEN_X = maxx;
    	frame.setMinimumSize(new Dimension(MAX_SCREEN_X, MAX_SCREEN_Y));
    }
    
    public void setMaxScreenY(int maxy) {
    	this.MAX_SCREEN_Y = maxy;
    	frame.setMinimumSize(new Dimension(MAX_SCREEN_X, MAX_SCREEN_Y));
    }
    
    /**
     * Change the currently active window.
     * @param w
     *         The window to change to.
     */
    public void setWindow(Window w) {
    	if (currentlyselected != null) {
    		currentlyselected = w;
    		frame.setVisible(false);
    		frame.dispose();
    		frame = null;
    		
    		frame = new JFrame("Loading..");
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

    public RPEG() {
        tick = new Ticker();
        es = new EventSystem(this);
        tick.startTick();
    }
    /**
     * Log something to the console
     * @param line
     *            The message to log
     */
    public void log(String line) {
    	System.out.println("[RPEG ENGINE] " + line);
    }

    /**
     * Get the ticker object
     * @return
     *        The {@link Ticker} object
     */
    public final Ticker getTicker() {
        return tick;
    }

    public final EventSystem getEventSystem() {
        return es;
    }

    public double getCenterY() {
        if (!center_player)
            return 0;
        double smallx =  (MAX_SCREEN_Y / 2) - CENTER_Y;
        if (smallx > MAX_SCREEN_Y)
            smallx = MAX_SCREEN_Y;
        return smallx;
    }

    public double getCenterX() {
        if (!center_player)
            return 0;
        double smallx =  (MAX_SCREEN_X / 2) - CENTER_X;
        if (smallx > MAX_SCREEN_X)
            smallx = MAX_SCREEN_X;
        return smallx;
    }

}
