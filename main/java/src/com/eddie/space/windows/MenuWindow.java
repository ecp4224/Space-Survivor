package com.eddie.space.windows;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import com.eddie.rpeg.engine.events.EventHandler;
import com.eddie.rpeg.engine.events.model.render.onDrawEvent;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.bullets.impl.Player_Level1;
import com.eddie.space.entities.ships.impl.PlayerShip;
import com.eddie.space.entities.star.Star;
import com.eddie.space.game.Game;
import com.eddie.space.game.world.SpaceWorld;
import com.eddie.space.music.impl.BASS_Player;


public class MenuWindow extends Window {
	private static final long serialVersionUID = 1920644121510306595L;
	private boolean space;
	private int MENU_ITEM = 1;
	private BufferedImage backdrop;
	private SpaceWorld w;
	private String topass;
	PlayerShip p;
	int step;
	int wait;
	boolean ii;
	public MenuWindow(RPEG system) {
		super(system);
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.system.Tick#tick()
	 */
	@Override
	public void tick() {
		createStar();
		createStar();
		createStar();

		//ROTATE SHIP, ROTATE!
		if (p != null) {
			if (step == 0) {
				p.setRotation(p.getRotation() + 5);
				if (p.getRotation() > 45) {
					step = 1;
				}
			} else if (step == 1) {
				if (p.getRotation() - 3 > 0)
					p.setRotation(p.getRotation() - 5);
				else {
					p.setRotation(360 - 3);
					ii = true;
				}
				if (p.getRotation() < 315 && ii) {
					step = 2;
					ii = false;
				}
			} else if (step >= 2) {
				step++;
				if (step == 60) {
					p.setRotation(0);
					step = 61;
				}
			} else if (step == 61) {
				wait++;
				if (wait >= 40) {
					wait = 0;
					step = 0;
				}
			}
		}
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

	@Override
	public void keyTyped(KeyEvent paramKeyEvent) {
	}

	@Override
	public void keyPressed(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getKeyCode() == KeyEvent.VK_SPACE && !space) {
			space = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent paramKeyEvent) {
		if (paramKeyEvent.getKeyCode() == KeyEvent.VK_SPACE && space) {
			MENU_ITEM++;
			if (MENU_ITEM > 3) {
				Game.GAME.begin(topass);
				return;
			}
			loadNewBackdrop();
		}
	}

	@Override
	public void init() {
		Game.m = new BASS_Player(getSystem());

		File songs = new File("songs/");
		if (!songs.exists()) {
			songs.mkdir();
		} else {
			final Random rand = new Random();
			File[] song_list = songs.listFiles();
			if (song_list.length != 0) {
				while (true) {
					File f = song_list[rand.nextInt(song_list.length)];
					if (f.isFile() && f.getName().endsWith(".mp3")) {
						try {
							Game.m.play(f.getAbsolutePath());
						}
						catch (IOException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
		getSystem().getEventSystem().registerEvents(this);
		try {
			backdrop = ImageIO.read(new File("entities/menu/start.png"));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		Thread c = new Continue();
		c.start();

	}

	private void loadNewBackdrop() {
		try {
			backdrop = ImageIO.read(new File("entities/menu/" + MENU_ITEM + ".png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		if (MENU_ITEM == 3) {
			addPlayer();
		}
	}

	private void addPlayer() {
		p = new PlayerShip(getSystem(), w);
		p.setY(200);
		p.setVisible(true);
		getObjectDrawer().addObject(p);
		p.setX(350);
		p.setBulletType(Player_Level1.class);
	}

	@Override
	public String getName() {
		return "Menu";
	}

	@EventHandler
	public void draw(onDrawEvent event) {
		event.getGraphics().drawImage(backdrop, 0, 0, getWidth(), getHeight(), null);
	}

	@Override
	public void onUnload() {
		Game.m.close();
		onDrawEvent.getEventList().unregister(this);
		super.onUnload();
	}

	private class Continue extends Thread {

		@Override
		public void run() {
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("songs/"));
			int result = fc.showOpenDialog(MenuWindow.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				topass = fc.getSelectedFile().getAbsolutePath();
			} else {
				return;
			}

			getObjectDrawer().register(getSystem());
			getObjectDrawer().layerEntities(false);
			w = new SpaceWorld(MenuWindow.this);
			loadNewBackdrop();
		}
	}
}
