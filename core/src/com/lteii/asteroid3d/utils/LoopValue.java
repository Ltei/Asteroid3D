package com.lteii.asteroid3d.utils;


public class LoopValue {


    public float time, duration;

    public LoopValue(float duration) {
        this.duration = duration;
        this.time = 0;
    }

    public void update(float deltaS) {
        time += deltaS;
        if (time > duration) time = 0;
    }


    public float getTimeRatio() { return time/duration; }

}
