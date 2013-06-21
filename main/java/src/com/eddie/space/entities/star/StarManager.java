package com.eddie.space.entities.star;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.gui.SwingWindow;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.events.OnBeat;
import com.eddie.space.game.Game;
import com.eddie.space.windows.GameWindow;


public class StarManager implements Listener {
    private int avg;
    private int oldavg;
    private int wait;
    private List<Star> stars = new ArrayList<Star>();
    private RPEG system;
    private Level world;
    private GameWindow window;
    private static final Random random = new Random();
    private int num;
    private float num2;
    private long lastMove;
    
    public StarManager(RPEG system, Level world, GameWindow window) {
        this.system = system;
        this.world = world;
        system.getEventSystem().registerEvents(this);
    }
    
    @EventHandler
    public void onBeat(OnBeat event) {
        calculateAVG(event.getSpeed());
        moveStars();
        num = (int) event.getBeat();
        num2 = event.getMel();
    }
    
    public double getBeat() {
        return num;
    }
    
    public float getMel() {
        return num2;
    }
    
    public long getLastMove() {
        return lastMove;
    }
    
    private void moveStars() {
        double plus = (avg * 2) / Game.DIFFICULTY;
        plus *= 2;
        if (Game.DEBUG)
            System.out.println("SHIP SPEED " + plus);
        if (plus == 0)
            plus = 2 * 5;
        Star[] stars = this.stars.toArray(new Star[this.stars.size()]);
        for (Star s : stars) {
            s.move((int)plus);
            if (s.getY() - 10 >= system.getMaxScreenY())
                removeStar(s);
        }
        lastMove = System.currentTimeMillis();
    }
    
    public void createStars() {
    	int count = random.nextInt((avg == 0 ? 1 : avg) * 4) + (int)((60 / Game.DIFFICULTY));
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
