/**
 * Programmer: Eddie Penta
 * Date: May 16, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Star.java
 */
package com.eddie.space.entities.star;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.types.Backdrop;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.gui.SwingWindow;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.game.Game;
import com.eddie.space.windows.GameWindow;

public class Star extends Entity implements Backdrop {
	private static final long serialVersionUID = -7584479734207900386L;
	private static double speed = 4;
	private final int TOLERENCE = 10;
	private static BufferedImage star;
	private static StarManager manager;
	private static GameWindow window;
	private byte z;
	private float hue;
	private long lastMove;
	private static final Random RANDOM = new Random();
    public Star(RPEG system, Level level, GameWindow w) {
        super("Star", system, level, false);
        if (window == null)
            Star.window = w;
        system.getTicker().removeTick(this); //We dont need the tick
        if (manager == null)
        	manager = new StarManager(system, level, w);
        manager.addStar(this);
        lastMove = manager.getLastMove();
        z = (byte)(RANDOM.nextInt(50) + 10);           
        hue = RANDOM.nextFloat();

    }
    
    /* (non-Javadoc)
     * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
     */
    @Override
    public void draw(Graphics g, BufferedImage screen) {
        if (isVisible()) {
            float s = (float)(Game.m.getBeat() * 4.0);
            Color c = Color.getHSBColor(hue, s, 1.0f);
            g.setColor(c);
            g.fillOval((int)getDrawX(), (int)getDrawY(), (int)(100 / z), (int)(100 / z));
        }
        if (Math.abs(lastMove - manager.getLastMove()) > 1000) {
            System.out.println("Dead star detected (ID " + getID() + ")");
            dispose();
        }
    }

	@Override
	public void tick() { }

	public static double getSpeed() {
		return speed;
	}

	public static void setSpeed(double speed) {
		Star.speed = speed;
	}

	public void move(int plus) {
		setY(getY() + (plus / (z == 0 ? 1 : z)));
		if (getY() - TOLERENCE >= system.getMaxScreenY())
			dispose();
		lastMove = System.currentTimeMillis();
	}

	@Override
	public void setImage(BufferedImage image) { } //Do nothing

	@Override
	public BufferedImage getImage() {
		return star;
	}

	@Override
	public boolean inSeperateThread() {
		return false;
	}

}
