package com.eddie.rpeg.engine.events.model.render;

import com.eddie.rpeg.engine.events.Event;
import com.eddie.rpeg.engine.render.Render;

public abstract class RenderEvent extends Event {
	private Render render;
	
	public RenderEvent(Render render) {
		this.render = render;
	}
	
	public Render getRender() {
		return render;
	}
}
