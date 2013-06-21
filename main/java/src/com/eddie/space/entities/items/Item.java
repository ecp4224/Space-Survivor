/**
 * Programmer: Eddie Penta
 * Date: Jun 19, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Item.java
 */
package com.eddie.space.entities.items;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.entity.types.Killable;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.ObjectDrawer;
import com.eddie.rpeg.engine.system.RPEG;
import com.eddie.space.entities.ships.impl.PlayerShip;
import com.eddie.space.game.Game;

public abstract class Item extends Entity implements Killable {
	private static final long serialVersionUID = -548948407111311669L;
	private static Font font;
	private boolean killed;
	private static final String[] ITEMS = {
		"Health",
		"Health",
		"Health",
		"Point",
		"Point",
		"Point",
		"Point",
		"Health",
		"PlusOne",
		"Health",
		"Point",
		"Point",
		"Point",
		"Health",
		"Health",
		"Point",
		"Point",
		"Point",
		"Point",
		"Health",
	};

	public Item(RPEG core, Level level) {
		super("item", core, level);
		if (font == null) {
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("libs/exoblackitalic.ttf")).deriveFont(16f).deriveFont(Font.PLAIN);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		if (isVisible() && getImage() != null) {
			g.drawImage(getImage(), (int)(getDrawX()), (int)(getDrawY()), getImage().getWidth(), getImage().getHeight(), null);
			g.setFont(font);
			g.setColor(getColor());
			g.drawString("" + getChar(), (int)(getDrawX() + 8), (int)(getDrawY() + 24));
		}
	}

	public abstract Color getColor();

	public abstract String getChar();

	public abstract void onObtain(Entity e);

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#getHealth()
	 */
	@Override
	public double getHealth() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#hit(int, com.eddie.rpeg.engine.entity.Entity)
	 */
	@Override
	public void hit(int damage, Entity by) {
		if (!(by instanceof PlayerShip))
			return;
		onObtain(by);
		kill();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#canKill()
	 */
	@Override
	public boolean canKill() {
		return !killed;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.types.Killable#kill()
	 */
	@Override
	public void kill() {
		if (killed)
			return;
		dispose();
		killed = true;
	}
	
	@Override
	public void tick() {
		super.tick();
		setY(getY() + (Game.m.getSpeed() / 12));
		if (getY() > system.getMaxScreenY())
			kill();
	}
	
	public static void createItem(String item, RPEG core, Level level, ObjectDrawer draw, double x, double y) {
		try {
			@SuppressWarnings("unchecked")
			Constructor<? extends Item> conz_ = (Constructor<? extends Item>) Class.forName("com.eddie.space.entities.items.impl." + item).getConstructor(RPEG.class, Level.class);
			Item i = conz_.newInstance(core, level);
			i.setX(x);
			i.setY(y);
			i.setVisible(true);
			draw.addObject(i);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void createRandomItem(RPEG core, Level level, ObjectDrawer draw, double x, double y) {
		createItem(ITEMS[new Random().nextInt(ITEMS.length)], core, level, draw, x, y);
	}

}
