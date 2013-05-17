package com.eddie.space.music.impl;

import static jouvieje.bass.Bass.BASS_ChannelPlay;
import static jouvieje.bass.Bass.BASS_Init;
import static jouvieje.bass.Bass.BASS_StreamCreateFile;
import static jouvieje.bass.defines.BASS_SAMPLE.BASS_SAMPLE_FLOAT;
import static jouvieje.bass.defines.BASS_STREAM.BASS_STREAM_PRESCAN;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import jouvieje.bass.Bass;
import jouvieje.bass.BassInit;
import jouvieje.bass.exceptions.BassException;
import jouvieje.bass.structures.HSTREAM;
import jouvieje.bass.utils.BufferUtils;

import com.eddie.rpeg.engine.render.ObjectDrawer;
import com.eddie.rpeg.engine.system.Core;
import com.eddie.rpeg.engine.system.Tick;
import com.eddie.space.events.OnBeat;
import com.eddie.space.music.MediaPlayer;


public class BASS_Player implements MediaPlayer, Tick {
    private int chan;
    private static final int SIZE = 1024 * 4;
    private double speed;
    
    private float oldnum = -1;
    private double highest;
    private double lowest;
    private int all;
    private double count;
    private double avg;
    private Core system;
    private ObjectDrawer draw;
    public BASS_Player(Core system, ObjectDrawer drawer) { 
        try {
            BassInit.loadLibraries();
        } catch(BassException e) {
            System.out.println("NativeBass error! \n"+ e.getMessage());
            return;
        }
        
        if(BassInit.NATIVEBASS_LIBRARY_VERSION() != BassInit.NATIVEBASS_JAR_VERSION()) {
            System.out.println("Error!  NativeBass library version (" +  BassInit.NATIVEBASS_LIBRARY_VERSION() + ") is different to jar version (" +BassInit.NATIVEBASS_JAR_VERSION() +  ")");
            return;
        }
        
        BASS_Init(-1, 44100, 0, null, null);
        this.system = system;
    }
    @Override
    public int getSpeed() {
        return (int)avg;
    }

    @Override
    public void play(String file) throws IOException {
        HSTREAM stream = null;
        if((stream = BASS_StreamCreateFile(false, file, 0, 0, BASS_SAMPLE_FLOAT | BASS_STREAM_PRESCAN )) == null) {
            throw new IOException("The file failed to load!");
        }

        chan = stream.asInt();
        BASS_ChannelPlay(chan, false);
        
        system.getTicker().addTick(this);
    }

    @Override
    public void close() {
        Bass.BASS_ChannelStop(chan);
        system.getTicker().removeTick(this);
    }

    @Override
    public void tick() {
        calculateSpeed();
    }
    
    private void calculateSpeed() {
        ByteBuffer array = BufferUtils.newByteBuffer(SIZE);
        Bass.BASS_ChannelGetData(chan, array, -2147483645); //TODO Try using -2147483644
        FloatBuffer floats = array.asFloatBuffer();
        
        //GETTING SONG SPEED
        float num = 0f;
        //float[] speeds = doSomething(floats);
        for (int i = 0; i <= 15; i++)
            num += floats.get(i);
        num *= (13f/15f);
        //num /= 2000f;
        System.out.println("NUM " + num);
        double dis = (double)Math.abs(((double)oldnum - (double)num));
        dis *= 10000;
        speed = (dis / getTimeout());
        //speed /= 3.0;
        if (highest == 0 || speed > highest) {
            highest = speed;
            all = 0;
            count = 0;
        }
        if (lowest == 0 || speed < lowest) {
            lowest = speed;
            all = 0;
            count = 0;
        }
        all += Math.round(speed);
        count++;
        avg = all / count;
        if (count >= 1000) {
            count = 0;
            all = 0;
            highest = 0;
            lowest = 0;
        }
        oldnum = num;
        System.out.println("SPEED " + avg);
        OnBeat event = new OnBeat(avg, num, draw);
        system.getEventSystem().callEvent(event);
    }

    @Override
    public boolean inSeperateThread() {
        return true;
    }

    @Override
    public int getTimeout() {
        return 30;
    }
    
}
