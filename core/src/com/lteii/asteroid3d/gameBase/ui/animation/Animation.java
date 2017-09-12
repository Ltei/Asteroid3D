package com.lteii.asteroid3d.gameBase.ui.animation;


import com.lteii.asteroid3d.UIRenderer;

public abstract class Animation {


    public boolean playing = false;


    public void start() {
        playing = true;
    }
    public void stop() {
        playing = false;
    }
    public abstract void update(float deltaS);
    public abstract void render(UIRenderer renderer);

}
