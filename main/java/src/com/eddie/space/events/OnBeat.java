package com.eddie.space.events;

import com.eddie.rpeg.engine.events.Event;
import com.eddie.rpeg.engine.events.EventList;
import com.eddie.rpeg.engine.render.ObjectDrawer;


public class OnBeat extends Event {
    private static final EventList events = new EventList();
    private double speed;
    private float beat;
    private ObjectDrawer drawer;
    private float num2;
    
    public OnBeat(double avg, float beat, ObjectDrawer drawer, float num2) {
        this.speed = avg;
        this.beat = beat;
        this.num2 = num2;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public float getMel() {
        return num2;
    }
    
    public float getBeat() {
        return beat;
    }
    
    public ObjectDrawer getObjectDrawer() {
    	return drawer;
    }
    
    @Override
    public EventList getEvents() {
        return events;
    }
    
    public static EventList getEventList() {
        return events;
    }
    
}
