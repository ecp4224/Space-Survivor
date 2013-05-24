/**
 * Programmer: Eddie Penta
 * Date: May 24, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Explode.java
 */
package com.eddie.space.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.render.animation.AnimationCallback.OnAnimationCompleted;
import com.eddie.rpeg.engine.render.animation.AnimationStyle;
import com.eddie.rpeg.engine.system.RPEG;

public class Explode extends Entity {
	private static final long serialVersionUID = -5934259906249118613L;

	public Explode(Level level, RPEG core) {
		super("explode", core, level, false);
		
	}
	
	@Override
	public void onLoad() {
		setHasAnimation(true);
		loadAnimation();
		getAnimation().setOnAnimationCompletedCallback(new OnAnimationCompleted() {

			@Override
			public void onAnimationCompleted(AnimationStyle style) {
				setVisible(false);
				dispose();
			}
			
		});
	}
	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		if (isVisible() && getImage() != null)
			g.drawImage(getImage(), (int)(getDrawX()), (int)(getDrawY()), getImage().getWidth(), getImage().getHeight(), null);
	}

}
