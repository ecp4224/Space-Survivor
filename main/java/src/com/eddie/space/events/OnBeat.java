package com.eddie.space.events;

import com.eddie.rpeg.engine.events.Event;
import com.eddie.rpeg.engine.events.EventList;


public class OnBeat extends Event {
    private static final EventList events = new EventList();
    private double speed;
    private float beat;
    
    public OnBeat(double avg, float beat) {
        this.speed = avg;
        this.beat = beat;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public float getBeat() {
        return beat;
    }
    
    @Override
    public EventList getEvents() {
        return events;
    }
    
    public static EventList getEventList() {
        return events;
    }
    
}
