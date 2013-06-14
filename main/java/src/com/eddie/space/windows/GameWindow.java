/**
 * Programmer: Eddie Penta
 * Date: May 13, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: GameWindow.java
 */
package com.eddie.space.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.eddie.rpeg.engine.entity.mover.model.SimpleCollisionMover;
import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.Listener;
import com.eddie.rpeg.engine.events.Priority;
import com.eddie.rpeg.engine.events.model.render.onDrawEvent;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.InvaderSpawner;
import com.eddie.space.entities.bullets.impl.Player_Level1;
import com.eddie.space.entities.ships.impl.PlayerShip;
import com.eddie.space.entities.ships.impl.enemy.Enemy1;
import com.eddie.space.entities.ships.impl.enemy.Invader;
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
	public static boolean finish;
	private boolean fade;
	public static InvaderSpawner spawn;
	private BufferedImage gameover;

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
		finish = false;
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
		
		spawn = new InvaderSpawner(getSystem(), w, getObjectDrawer());
		new AsteroidSpawner(getSystem(), w, getObjectDrawer());
		
	}
	
	public void playerDied(int finalscore) {
		gameover = new BufferedImage(getSystem().getMaxScreenX(), getSystem().getMaxScreenY(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = gameover.createGraphics();
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("libs/exoblackitalic.ttf")).deriveFont(48f).deriveFont(Font.PLAIN);
			g.setFont(font);
			g.setFont(g.getFont().deriveFont(108f));
			g.setColor(Color.white);
			g.drawString("Game Over", 108, (getSystem().getMaxScreenY() / 2) - 72);
			ImageIO.write(gameover, "PNG", new File("wat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		g.dispose();
		if (Game.m.getSpeed() < 50)
			fade = true;
		else
			fade = false;
		finish = true;
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
	
	private void addRandomEnemy() {
		Invader p = new Invader(getSystem(), w);
		p.setY(100);
		p.setVisible(true);
		getObjectDrawer().addObject(p);
		p.setX(getSystem().getMaxScreenX() / 2 - 32);
	}
	
	@EventHandler
	public void onBeat(OnBeat beat) {
		if (finish)
			return;
	    if (player_mover == null)
	        return;
		player_mover.setSpeed(beat.getSpeed() / (Game.DIFFICULTY * 10));
	}
	
	@EventHandler (priority = Priority.High)
	public void onDraw(onDrawEvent event) {
		if (finish) {
			event.getGraphics().drawImage(gameover, 0, 0, getSystem().getMaxScreenX(), getSystem().getMaxScreenY(), null);
		}
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.render.gui.Window#getName()
	 */
	@Override
	public String getName() {
		return "Space!";
	}
}
