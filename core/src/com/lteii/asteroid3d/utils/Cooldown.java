package com.lteii.asteroid3d.utils;


public class Cooldown {


    public float duration, time;
    public boolean isOnCD;

    public Cooldown(float duration) {
        this.duration = duration;
        this.time = 0;
        this.isOnCD = false;
    }

    public void startCD() {
        time = 0;
        isOnCD = true;
    }

    public void update(float deltaS) {
        if (isOnCD) {
            time += deltaS;
            if (time > duration) {
                isOnCD = false;
            }
        }
    }


    public float getTimeRatio() { return time/duration; }



}
