/**
 * Programmer: Eddie Penta
 * Date: Nov 29, 2012
 * Purpose: <INSERT PURPOSE>
 * File name: LightSource.java
 */
package com.eddie.rpeg.engine.entity.model.design;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.types.Foreground;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.Core;

public class LightSource extends Entity implements Foreground {
	private static final long serialVersionUID = 1L;
    public static boolean darken;
    private BufferedImage light_cache;
    private BufferedImage screen_cache;

	public LightSource(Core system, Level level) {
        super("light", system, level);
    }

    /* (non-Javadoc)
     * @see com.eddie.rpeg.game.Entity#dispose()
     */
    @Override
    public void dispose() {

    }

    @Override
    public int getHeight() {
        return 1;
    }

    public void renderLight(Graphics2D g, BufferedImage screen) {
        makeLevelDark(g, screen);
        this.screen_cache = screen;
        if (light_cache == null)
            return;
        g.drawImage(light_cache, (int)getDrawX(), (int)getDrawY(), null);
    }
    
    public void makeLevelDark(Graphics2D g, BufferedImage screen) {
        if (!darken) {
            Color c = new Color(0,0,0,0.5f);
            final Color oldc = g.getColor();
            g.setPaint(c);
            g.fillRect(0, 0, system.getMaxScreenX(), system.getMaxScreenY());
            g.setPaint(oldc);
            darken = true;
        }
    }

    private void lightUp(double x, double y, int radius, Graphics2D g, BufferedImage image) {
        for (int xx = (int)(x - radius); xx <= x + radius; xx += 2) {
            for (int yy = (int)(y - radius); yy <= y + radius; yy += 2) {
                if (xx <= 0 || xx >= system.getMaxScreenX())
                    continue;
                if (yy <= 0 || yy >= system.getMaxScreenY())
                    continue;
                float alpha = (float)((Math.abs((x + radius) - x) + Math.abs((y + radius) - y)) / (Math.abs(xx - x) + Math.abs(yy - y)));
                alpha /= 2;
                alpha = alpha - 1;
                if (alpha > 1)
                    alpha = 1;
                if (alpha < 0)
                    alpha = 0;
                int[] values = intToRGB(image.getRGB(xx, yy));
                float r = values[0] / 255f;
                float green = values[1] / 255f;
                float b = values[2] / 255f;
                Color c = new Color(r, green, b, alpha);
                g.setPaint(c);
                g.fillRect(xx, yy, 2, 2);
            }
        }
    }

    private int[] intToRGB(int argb) {
        Color c = new Color(argb);
        return new int[] { c.getRed(), c.getGreen(), c.getBlue() };
    }

    public static Color getColorAt(int x, int y) throws AWTException{
        return new Robot().getPixelColor(x, y);
    }

    @Override
    public void tick() {
        if (screen_cache == null)
            return;
        light_cache = new BufferedImage((int)(200), (int)(200), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) light_cache.getGraphics();
        lightUp(getDrawX(), getDrawY(), 100, g, screen_cache);
        //lightUp(getX(), getX(), getY(), 100, g, screen_cache);
        g.dispose();
        screen_cache = null;
    }
    
    @Override
    public int getTimeout() {
        return 10;
    }

    /* (non-Javadoc)
     * @see com.eddie.rpeg.game.Entity#draw(java.awt.Graphics)
     */
    @Override
    public void draw(Graphics g, BufferedImage screen) {
        renderLight((Graphics2D)g, screen);
    }

}
