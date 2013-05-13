package com.eddie.rpeg.engine.render;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.entity.model.design.LightSource;
import com.eddie.rpeg.engine.events.model.render.onDrawEvent;
import com.eddie.rpeg.engine.render.gui.Window;
import com.eddie.rpeg.engine.system.Tick;

public class Render implements Tick {
	
	private Window currentWindow;
	private BufferedImage temp;
	private BufferStrategy bf;
	
	public Render(Window window) {
		this.currentWindow = window;
	}
	
	public void start() {
		currentWindow.createBufferStrategy(2);
		bf = currentWindow.getBufferStrategy();
		currentWindow.getSystem().getTicker().addTick(this);
	}
	
	public void finalize() {
		currentWindow.getSystem().getTicker().removeTick(this);
		bf = null;
	}
	
	public void draw() {
		Graphics g = bf.getDrawGraphics();
		temp = new BufferedImage(currentWindow.getSystem().getMaxScreenX(), currentWindow.getSystem().getMaxScreenY(), BufferedImage.TYPE_INT_ARGB);
		Graphics tempg = temp.getGraphics();
		try {
			onDrawEvent event = new onDrawEvent(tempg, this, temp);
			currentWindow.getSystem().getEventSystem().callEvent(event);
			g.drawImage(temp, 0, 0, null);
		} catch (Exception e) { }
		finally {
			g.dispose();
			tempg.dispose();
			temp = null;
		}
		bf.show();
		Toolkit.getDefaultToolkit().sync();
		g = null;
		tempg = null;
		LightSource.darken = false;
	}

	@Override
	public void tick() {
		draw();
	}

	@Override
	public boolean inSeperateThread() {
		return true;
	}

	@Override
	public int getTimeout() {
		return 50 / 3;
	}
}
