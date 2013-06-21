package com.eddie.rpeg.engine.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.eddie.rpeg.engine.entity.Entity;
import com.eddie.rpeg.engine.render.gui.SwingWindow;
import com.eddie.space.windows.GameWindow;

public interface Level {
    
    public GameWindow getParentWindow();
    
    public BufferedImage getBackground();
    
    public String getName();
    
    public void save() throws IOException;
    
    public void load(String name) throws IOException;
    
    public boolean isInBounds(Entity e);
}
