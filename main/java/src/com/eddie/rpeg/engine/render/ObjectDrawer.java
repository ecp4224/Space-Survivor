/**
 * Programmer: Eddie Penta
 * Date: Nov 15, 2012
 * Purpose: <INSERT PURPOSE>
 * File name: EntityDrawer.java
 */
package com.eddie.rpeg.engine.render;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.model.design.LightSource;
import com.eddie.rpeg.engine.entity.types.Backdrop;
import com.eddie.rpeg.engine.entity.types.Foreground;
import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.events.model.render.onDrawEvent;
import com.eddie.rpeg.engine.system.RPEG;

public class ObjectDrawer extends ArrayList<Entity> implements Listener {

    private static final long serialVersionUID = 5093029560102217004L;
    private boolean run;
    private Thread test;
    private int fps;
    private int zc;
    private boolean breako;
    private boolean layer = true;
    boolean lightstarted = false;
    BufferedImage light = null;

    public void register(RPEG system) {
        system.getEventSystem().registerEvents(this);
        test = new FPS();
        run = true;
        test.start();
    }

    public void unregister() {
        onDrawEvent.getEventList().unregister(this);
        run = false;
        test.interrupt();
        try {
            test.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clear();
    }

    /**
     * Whether the object drawer should draw entities so Entities with a lower Y value
     * are drawn first and entities with a higher y value are drawn last to give a 3d like effect.
     * @param value
     */
    public void layerEntities(boolean value) {
        this.layer = value;
    }

    public boolean isLayeringEntities() {
        return layer;
    }

    public int getPriority(Entity object) {
        return indexOf(object);
    }

    public void addObject(Entity object) {
        if (object.getID() == -1) {
            long ID = size();
            for (int i = 0; i < size(); i++) {
                if (get(i) == null)
                    continue;
                if (get(i).getID() == ID) {
                    ID++;
                    i = 0;
                }
            }
            object.setID(ID);
        }
        if (!contains(object)) {
            //System.out.println("Adding " + object.getName() + " @ X:" + object.getX() + " Y:" + object.getY() + " ID: " + object.getID());
            object.setDrawerParent(this);
            add(object);
        }
    }

    public void removeObject(Entity object) {
        if (contains(object)) {
            //System.out.println("Removing " + object.getName() + " @ X:" + object.getX() + " Y:" + object.getY() + " ID: " + object.getID());
            object.setDrawerParent(null);
            object.dispose();
            remove(object);
        }
    }

    public void organize() {
        if (!layer)
            return;
        while (!isOrganized() && !breako) {
            for (int i = 0; i < size(); i++) {
                if (i + 1 >= size())
                    break;
                if (get(i) == null || get(i + 1) == null)
                    continue;
                if (get(i) instanceof Foreground && !(get(i + 1) instanceof Foreground)) {
                    Collections.swap(this, i, i + 1);
                    continue;
                }
                if (get(i) instanceof Backdrop && i != 0 && !(get(i - 1) instanceof Backdrop)) {
                    Collections.swap(this, i, i - 1);
                    continue;
                }
                if (get(i) instanceof Backdrop)
                    continue;
                if (get(i).getDrawY() + get(i).getHeight() > get(i + 1).getDrawY() + get(i + 1).getHeight() && !(get(i + 1) instanceof Foreground))
                    Collections.swap(this, i, i + 1);
            }
        }
    }

    private boolean isOrganized() {
        Entity current;
        for (int i = 0; i < size(); i++) {
            if (i + 1 >= size())
                break;
            current = get(i);
            if (current instanceof Foreground && !(get(i + 1) instanceof Foreground))
                return false;
            if (current instanceof Backdrop && i != 0 && !(get(i - 1) instanceof Backdrop))
                return false;
            if (current instanceof Backdrop)
                continue;
            if (current.getDrawY() + (current.getHeight()) > get(i + 1).getDrawY() + get(i + 1).getHeight() && !(get(i + 1) instanceof Foreground))
                return false;
        }
        return true;
    }

    @EventHandler
    public void onDraw(final onDrawEvent event) {
        if (!run)
            return;
        if (layer && !isOrganized())
            organize();
        ArrayList<Entity> toremove = new ArrayList<Entity>();
        for (int i = 0; i < size(); i++) {
            if (get(i) == null) {
                toremove.add(get(i));
                continue;
            }
            if (!get(i).willVisibleNextDraw())
            	continue;
            if (get(i) instanceof LightSource && !lightstarted) {
                lightstarted = true;
                ColorModel cm = event.getScreenImage().getColorModel();
                boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
                WritableRaster raster = event.getScreenImage().copyData(null);
                light = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
            }
            if (get(i) instanceof LightSource)
                get(i).draw(event.getGraphics(), light);
            else
                get(i).draw(event.getGraphics(), event.getScreenImage());
        }
        for (Entity e : toremove){
            remove(e); 
        }
        toremove.clear();
        fps++;
    }

    private class FPS extends Thread {

        @Override
        public void run() {
            Thread.currentThread().setName("FPS-Checker");
            while (run) {
                System.out.println("FPS: " + fps);
                if (fps == 0) {
                    zc++;
                    if (zc == 3) {
                        System.out.println("Frame Lock Detected.");
                        System.out.println("Attempting to break Frame Lock.");
                        breako = true;
                    }
                }
                if (fps != 0 && zc >= 3) {
                    breako = false;
                    zc = 0;
                    System.out.println("Frame Lock broken.");
                }
                fps = 0;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) { }
            }
        }
    }

}
