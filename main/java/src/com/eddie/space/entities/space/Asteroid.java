/**
 * Programmer: Eddie Penta
 * Date: May 29, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: Asteroid.java
 */
package com.eddie.space.entities.space;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.level.Level;
import com.eddie.rpeg.engine.system.RPEG;

public class Asteroid extends Entity {
	private static final long serialVersionUID = 190032552849804267L;
	private static final ArrayList<BufferedImage> generations = new ArrayList<BufferedImage>();
	private static final Random RANDOM = new Random();
	
	public Asteroid(Level level, RPEG core) {
		super("Asteroid", core, level, false);
		createImage();
	}
	
	private void createImage() {
		if (RANDOM.nextDouble() < .3 || generations.size() == 0) {
			double dens = RANDOM.nextDouble();
			int xsize = RANDOM.nextInt(64) + 64;
			int ysize = RANDOM.nextInt(64) + 64;
			
		} else {
			setImage(generations.get(RANDOM.nextInt(generations.size())));
		}
	}
	
	private BufferedImage generateFromCenter(double dens, int xsize, int ysize) {
		BufferedImage generate = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = generate.createGraphics();
		int xcenter = xsize / 2;
		int ycenter = ysize / 2;
		
		
		
		return generate;
	}
	
	private void addPixel(Graphics g, int x, int y, double dens, int cx, int cy) {
		//g.fillRect(x, y, 1, 1);
		//int dis = Math.sqrt(Math.pow((x - cx), 2) + Math.pow(y - cy, 2));
	}

	/* (non-Javadoc)
	 * @see com.eddie.rpeg.engine.entity.Entity#draw(java.awt.Graphics, java.awt.image.BufferedImage)
	 */
	@Override
	public void draw(Graphics g, BufferedImage screen) {
		// TODO Auto-generated method stub
		
	}

}
