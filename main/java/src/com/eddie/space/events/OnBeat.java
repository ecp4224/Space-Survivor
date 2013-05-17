package com.eddie.space.events;

import com.eddie.rpeg.engine.events.Event;
import com.eddie.rpeg.engine.events.EventList;
import com.eddie.rpeg.engine.render.ObjectDrawer;


public class OnBeat extends Event {
    private static final EventList events = new EventList();
    private double speed;
    private float beat;
    private ObjectDrawer drawer;
    
    public OnBeat(double avg, float beat, ObjectDrawer drawer) {
        this.speed = avg;
        this.beat = beat;
    }
    
    public double getSpeed() {
        return speed;
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
