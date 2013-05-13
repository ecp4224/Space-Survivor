/**
 * Programmer: Eddie Penta
 * Date: Jan 17, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: SceneryEntity.java
 */
package com.eddie.rpeg.engine.entity.model.design;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.eddie.rpeg.engine.entity.model.StaticEntity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.animation.Animation;
import com.eddie.rpeg.engine.render.animation.AnimationStyle;
import com.eddie.rpeg.engine.system.Core;

public class SceneryEntity extends StaticEntity {
	private static final long serialVersionUID = 1L;
	
	private transient boolean init;

	/**
	 * @param name
	 * @param system
	 * @param level
	 */
	public SceneryEntity(String name, Core system, Level level) {
		super(name, system, level);
		try {
			setImage(ImageIO.read(new File("entities/" + name + "/image.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		initAnimation();
	}
	
	public SceneryEntity() {
		super();
		init = false;
	}
	
	public void initAnimation() {
		if (init)
			return;
		if (new File("entities/" + name + "/config.config").exists()) {
			animation = new Animation(this);
			try {
				animation.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			animation.setAnimation(AnimationStyle.IDLE);
		}
		init = true;
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.Entity#dispose()
	 */
	@Override
	public void dispose() {
		
	}
	
	
	@Override
	public int getWidth() {
		return getImage().getWidth();
	}
	
	@Override
	public int getHeight() {
		return getImage().getHeight();
	}
	
	@Override
	public BufferedImage getImage() {
		if (image == null) {
			try {
				setImage(ImageIO.read(new File("entities/" + name + "/image.png")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getImage();
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.game.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		if (!init)
			initAnimation();
		if (!system.getTicker().contains(this))
			system.getTicker().addTick(this);
		g.drawImage(getImage(), (int)getDrawX(), (int)getDrawY(), getImage().getWidth(), getImage().getHeight(), null);
	}

}
