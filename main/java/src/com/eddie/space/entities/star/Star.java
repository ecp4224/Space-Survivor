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

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Core;

public class Star extends Entity {
    private static final long serialVersionUID = -7584479734207900386L;
    private static double speed = 4;
    private final int TOLERENCE = 10;
    private static BufferedImage star;
    private static StarManager manager;
    private static Window window;

    public Star(Core system, Level level, Window w) {
        super("Star", system, level, false);
        if (window == null)
        	Star.window = w;
        validateStar();
        system.getEventSystem().registerEvents(this);
        system.getTicker().removeTick(this); //We dont need the tick
        manager.addStar(this);
    }

    private void validateStar() {
        if (manager == null)
            manager = new StarManager(system, getLevel(), window);
        if (star != null)
            return;
        star = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        Graphics g = star.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 4, 4);
        g.dispose();
    }

    /* (non-Javadoc)
     * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
     */
    @Override
    public void draw(Graphics g, BufferedImage screen) {
        if (isVisable() && getImage() != null) {
            g.drawImage(star, (int)getDrawX(), (int)getDrawY(), 4, 4, null);
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
        setY(getY() + plus);
        if (getY() - TOLERENCE >= system.getMaxScreenY())
            dispose();
    }

    @Override
    public void setImage(BufferedImage image) { } //Do nothing

    @Override
    public BufferedImage getImage() {
        if (star == null)
            validateStar();
        return star;
    }

    @Override
    public boolean inSeperateThread() {
        return false;
    }

}
