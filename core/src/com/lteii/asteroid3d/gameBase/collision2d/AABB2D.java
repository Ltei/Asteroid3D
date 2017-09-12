package com.lteii.asteroid3d.gameBase.collision2d;


import com.badlogic.gdx.math.Vector2;
import com.lteii.asteroid3d.utils.annotations.Outptr;

public class AABB2D implements Shape2D {


    public float minX, minY, maxX, maxY;

    public AABB2D(float centerX, float centerY, float width, float height) {
        minX = centerX-width/2;
        minY = centerY-height/2;
        maxX = centerX+width/2;
        maxY = centerY+height/2;
    }


    @Override
    public boolean contains(float x, float y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    @Override
    public void getCenter(@Outptr Vector2 output) {
        output.set((minX+maxX)/2, (minY+maxY)/2);
    }

    @Override
    public void getSize(@Outptr Vector2 output) {
        output.set(maxX-minX, maxY-minY);
    }


}
