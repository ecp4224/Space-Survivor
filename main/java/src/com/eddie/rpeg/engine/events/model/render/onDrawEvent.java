package com.eddie.rpeg.engine.events.model.render;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.eddie.rpeg.engine.events.EventList;
import com.eddie.rpeg.engine.render.Render;

public class onDrawEvent extends RenderEvent {

	private Graphics g;
	
	private BufferedImage screen;
	
	private static EventList events = new EventList();
	
	public onDrawEvent(Graphics g, Render render, BufferedImage screen) {
		super(render);
		this.g = g;
		this.screen = screen;
		
	}

	@Override
	public EventList getEvents() {
		return events;
	}
	
	public BufferedImage getScreenImage() {
	    return screen;
	}
	
	public Graphics getGraphics() {
		return g;
	}
	
	public static EventList getEventList() {
		return events;
	}

}
