package com.eddie.space.entities.star;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.events.OnBeat;
import com.eddie.space.game.Game;


public class StarManager implements Listener {
    private int avg;
    private int oldavg;
    private int wait;
    private List<Star> stars = new ArrayList<Star>();
    private RPEG system;
    private Level world;
    private Window window;
    private static final Random random = new Random();
    
    public StarManager(RPEG system, Level world, Window window) {
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
        double plus = avg / Game.DIFFICULTY;
        if (Game.DEBUG)
            System.out.println("SHIP SPEED " + plus);
        if (plus == 0)
            plus = 2;
        Star[] stars = this.stars.toArray(new Star[this.stars.size()]);
        for (Star s : stars) {
            s.move((int)plus);
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
		s.setVisible(true);
		
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
