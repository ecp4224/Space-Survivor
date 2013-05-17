package com.eddie.space.entities.star;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.space.events.OnBeat;


public class StarManager implements Listener {
    private int avg;
    private int oldavg;
    private int wait;
    private List<Star> stars = new ArrayList<Star>();
    private Core system;
    private Level world;
    private Window window;
    private static final Random random = new Random();
    
    public StarManager(Core system, Level world, Window window) {
        this.system = system;
        this.world = world;
        system.getEventSystem().registerEvents(this);
    }
    
    @EventHandler
    public void onBeat(OnBeat event) {
        calculateAVG(event.getSpeed());
        moveStars();
    }
    
    private void moveStars() {
        System.out.println("AVG " + avg);
        int plus = (int) avg;
        if (plus == 0)
            plus = 2;
        Star[] stars = this.stars.toArray(new Star[this.stars.size()]);
        for (Star s : stars) {
            s.move(plus);
            if (s.getY() - 10 >= system.getMaxScreenY())
                removeStar(s);
        }
    }
    
    public void createStars() {
    	if (stars.size() > avg * 4)
    		return;
    	int count = random.nextInt(avg) + 1;
    	for (int i = 0; i < count; i++) {
    		createStar();
    	}
    }
    
    private void createStar() {
    	if (window == null)
    		return;
		Star s = new Star(system, world, window);
		s.setX(new Random().nextInt(system.getMaxScreenX()));
		s.setY(0);
		Star.setSpeed(20.0);
		s.setVisable(true);
		
		window.addEntity(s);
		addStar(s);
	}
    
    public void addStar(Star s) {
        stars.add(s);
    }
    
    public void removeStar(Star s) {
        stars.remove(s);
        createStars();
    }
    
    private void calculateAVG(double d) {
        oldavg = avg;
        avg = (int)d;
        if (avg == 0 && oldavg > 1 && wait > 10) {
            avg = oldavg - 1;
            wait = 0;
        } else if (avg == 0 && oldavg > 1 && wait <= 10)
            wait++;
        else if (avg == 0 && oldavg <= 1)
            avg = oldavg;
    }
}
