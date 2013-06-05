package com.eddie.space.events;

import com.eddie.rpeg.engine.events.Event;
import com.eddie.rpeg.engine.events.EventList;
import com.eddie.space.music.impl.BASS_Player;


public class OnBeat extends Event {
    private static final EventList events = new EventList();
    private double speed;
    private float beat;
    private float num2;
    private BASS_Player parent;
    
    public OnBeat(double avg, float beat, float num2, BASS_Player parent) {
        this.speed = avg;
        this.beat = beat;
        this.num2 = num2;
        this.parent = parent;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public BASS_Player getParent() {
    	return parent;
    }
    
    public float getMel() {
        return num2;
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
