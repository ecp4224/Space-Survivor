package com.eddie.rpeg.engine.render.animation;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.eddie.rpeg.engine.entity.Entity;

public class Animation {
	protected BufferedImage[] walking;
	protected BufferedImage[] jumping;
	protected BufferedImage[] shooting;
	protected BufferedImage[] death;
	protected BufferedImage[] victory;
	protected BufferedImage[] melee;
	protected BufferedImage[] idle;
	protected BufferedImage[] trip;
	protected BufferedImage[] temp;
	protected BufferedImage[] falling;
	protected Hashtable<BufferedImage, BufferedImage> cache = new Hashtable<BufferedImage, BufferedImage>();
	protected int current;
	protected boolean reset;
	protected boolean isStopped;
	protected boolean loaded = false;
	protected AnimationStyle style;
	public Entity character;
	public Animation(Entity Character) {
		this.character = Character;
	}
	/**
	 * 
	 */
	public Animation() {
	}
	public AnimationStyle getStyle() {
		return style;
	}
	public void setImage(int frame, BufferedImage image) {
		if (temp == null || current >= temp.length)
			return;
		temp[frame] = image;
	}
	public void setCurrentImage(BufferedImage image) {
		if (temp == null || current >= temp.length)
			return;
		temp[current] = image;
	}
	public BufferedImage getFliped() {
	    try {
	        if (!cache.containsKey(getCurrentImage())) {
	            BufferedImage img = getCurrentImage();
	            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	            tx.translate(-img.getWidth(null), 0);
	            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	            img = op.filter(img, null);
	            cache.put(getCurrentImage(), img);
	            return img;
	        }
	        else
	            return cache.get(getCurrentImage());
	    } catch (Exception e) {
	        BufferedImage img = getCurrentImage();
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-img.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
            cache.put(getCurrentImage(), img);
            return img;
	    }
	}
	public BufferedImage[] getAnimation() {
		return temp;
	}
	public BufferedImage getCurrentImage() {
		if (current >= temp.length)
			current = temp.length - 1;
		return temp[current];
	}
	public boolean hasCurrent() {
		try {
			return temp[current] != null;
		}
		catch (Exception e) {
			return false;
		}
	}
	public boolean hasNext() {
		if (!loaded)
			return false;
		return current < temp.length;
	}
	public boolean isStopped() {
		return isStopped;
	}
	public synchronized void reset() {
		current = 0;
		reset = true;
		super.notify();
	}
	public BufferedImage next() {
		BufferedImage img = getCurrentImage();
		current++;
		return img;
	}
	public BufferedImage nextFlipped() {
		BufferedImage img = getFliped();
		current++;
		return img;
	}
	public synchronized void waitForEnd() throws InterruptedException {
		reset = false;
		while (true) {
			if (reset)
				break;
			super.wait(0L);
		}
	}
	public void load() throws IOException {
		ArrayList<String> file = new ArrayList<String>(); 
		if (!new File("entities/" + character.getName() + "/config.config").exists())
			return;
		FileInputStream fstream = new FileInputStream("entities/" + character.getName() + "/config.config");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			if (strLine.split("\\:")[1].equals("walking"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("jumping"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("shooting"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("death"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("victory"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("melee"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("idle"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("trip"))
				file.add(strLine);
			else if (strLine.split("\\:")[1].equals("falling"))
				file.add(strLine);
		}
		// Directory path here
		ArrayList<BufferedImage> bf = new ArrayList<BufferedImage>();
		for (int ii = 0; ii < file.size(); ii++) {
			String f = file.get(ii);
			String path = "entities/" + character.getName() + "/"; 

			String files;
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles(); 

			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
				{
					files = listOfFiles[i].getName();
					if ((files.endsWith(".png") || files.endsWith(".PNG")) && files.startsWith(f.split("\\:")[0]))
						bf.add(ImageIO.read(new File("entities/" + character.getName() + "/" + files)));
				}
			}
			if (f.split("\\:")[1].equals("walking")) {
				walking = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					walking[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("jumping")) {
				jumping = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					jumping[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("shooting")) {
				shooting = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					shooting[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("death")) {
				death = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					death[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("victory")) {
				victory = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					victory[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("melee")) {
				melee = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					melee[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("idle")) {
				idle = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					idle[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("trip")) {
				trip = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					trip[i] = bf.get(i);
				}
			}
			else if (f.split("\\:")[1].equals("falling")) {
				falling = new BufferedImage[bf.size()];
				for (int i = 0; i < bf.size(); i++) {
					falling[i] = bf.get(i);
				}
			}
			bf.clear();
		}
		setAnimation(AnimationStyle.IDLE);
		loaded = true;
	}

	public void setAnimation(AnimationStyle style) {
		try {
			this.style = style;
			if (style == AnimationStyle.WALKING) {
				temp = new BufferedImage[walking.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = walking[i];
			}
			if (style == AnimationStyle.IDLE) {
				temp = new BufferedImage[idle.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = idle[i];
			}
			if (style == AnimationStyle.DEATH) {
				temp = new BufferedImage[death.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = death[i];
			}
			if (style == AnimationStyle.JUMPING) {
				temp = new BufferedImage[jumping.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = jumping[i];
			}
			if (style == AnimationStyle.MELEE) {
				temp = new BufferedImage[melee.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = melee[i];
			}
			if (style == AnimationStyle.SHOOTING) {
				temp = new BufferedImage[shooting.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = shooting[i];
			}
			if (style == AnimationStyle.VICTORY) {
				temp = new BufferedImage[victory.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = victory[i];
			}
			if (style == AnimationStyle.TRIP) {
				temp = new BufferedImage[trip.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = trip[i];
			}
			if (style == AnimationStyle.FALLING) {
				temp = new BufferedImage[falling.length];
				for (int i = 0; i < temp.length; i++)
					temp[i] = falling[i];
			}

		}
		catch (Exception e) { }

	}
	
	/**
	 * 
	 */
	public void stop() {
		isStopped = true;
	}

}
