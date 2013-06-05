/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: GameWindow.java
 */
package com.eddie.space.windows;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import com.eddie.rpeg.engine.entity.mover.model.SimpleCollisionMover;
import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.bullets.impl.Player_Level1;
import com.eddie.space.entities.ships.impl.PlayerShip;
import com.eddie.space.entities.ships.impl.enemy.Enemy1;
import com.eddie.space.entities.space.AsteroidSpawner;
import com.eddie.space.entities.star.Star;
import com.eddie.space.events.OnBeat;
import com.eddie.space.game.Game;
import com.eddie.space.game.mover.ShipKeyMover;
import com.eddie.space.game.mover.aimover.WaypointMover;
import com.eddie.space.game.world.SpaceWorld;

public class GameWindow extends Window implements Listener {
	
	private static final long serialVersionUID = 2704165502672086720L;
	private SpaceWorld w;
	private ShipKeyMover player_mover;
	private String song;

	/**
	 * @param system
	 */
	public GameWindow(RPEG system, String song) {
		super(system);
		this.song = song;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#tick()
	 */
	@Override
	public void tick() {
		createStar();
		createStar();
		createStar();
	}
	
	private void createStar() {
	    if (Game.m == null)
	        return;
		Star s = new Star(getSystem(), w, this);
		s.setX(new Random().nextInt(getSystem().getMaxScreenX()));
		s.setY(0);
		Star.setSpeed(20.0);
		s.setVisible(true);
		
		getObjectDrawer().addObject(s);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#inSeperateThread()
	 */
	@Override
	public boolean inSeperateThread() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#getTimeout()
	 */
	@Override
	public int getTimeout() {
		return 50;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.Window#init()
	 */
	@Override
	public void init() {
		getObjectDrawer().register(getSystem());
		getObjectDrawer().layerEntities(false);
		getSystem().getEventSystem().registerEvents(this);
		w = new SpaceWorld(this);
		
		addPlayer();
		try {
		    Game.m.play(song);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		//addRandomEnemy();
		new AsteroidSpawner(getSystem(), w, getObjectDrawer());
		
	}
	
	private void addPlayer() {
		PlayerShip p = new PlayerShip(getSystem(), w);
		p.setY(getSystem().getMaxScreenY() - 100);
		p.setVisible(true);
		player_mover = new ShipKeyMover(p, super.getSystem());
		SimpleCollisionMover c_m = new SimpleCollisionMover(p, super.getSystem());
		p.addMover(player_mover);
		p.addMover(c_m);
		getObjectDrawer().addObject(p);
		player_mover.attachMover(this);
		p.setX(getSystem().getMaxScreenX() / 2 - 32);
		p.setBulletType(Player_Level1.class);
		p.setBounce(true);
	}
	
	@SuppressWarnings("unused")
	private void addRandomEnemy() {
		Enemy1 p = new Enemy1(getSystem(), w);
		p.setY(100);
		p.setVisible(true);
		getObjectDrawer().addObject(p);
		p.setX(getSystem().getMaxScreenX() / 2 - 32);
		p.setBulletType(Player_Level1.class);
		WaypointMover test_move = new WaypointMover(p, getSystem());
		Random r = new Random();
		test_move.addWaypoint(r.nextInt(getSystem().getMaxScreenX()), r.nextInt(getSystem().getMaxScreenY()));
		p.addMover(test_move);
	}
	
	@EventHandler
	public void onBeat(OnBeat beat) {
	    if (player_mover == null)
	        return;
		player_mover.setSpeed(beat.getSpeed() / (Game.DIFFICULTY * 10));
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.Window#getName()
	 */
	@Override
	public String getName() {
		return "Space!";
	}
}
