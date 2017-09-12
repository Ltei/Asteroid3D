package com.lteii.asteroid3d.gameBase.collision2d;


import com.badlogic.gdx.math.Vector2;
import com.lteii.asteroid3d.utils.annotations.Outptr;

public class Circle2D implements Shape2D {


    public float centerX, centerY, radius, radius2;

    public Circle2D(float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.radius2 = radius*radius;
    }


    @Override
    public boolean contains(float x, float y) {
        final float dx = centerX-x;
        final float dy = centerY-y;
        return dx*dx + dy*dy <= radius2;
    }

    @Override
    public void getCenter(@Outptr Vector2 output) {
        output.set(centerX, centerY);
    }

    @Override
    public void getSize(@Outptr Vector2 output) {
        output.set(2*radius, 2*radius);
    }


}
