package com.lteii.asteroid3d.gameBase.collision2d;


import com.badlogic.gdx.math.Vector2;
import com.lteii.asteroid3d.utils.annotations.Outptr;

public interface Shape2D {
    boolean contains(float x, float y);
    void getCenter(@Outptr Vector2 output);
    void getSize(@Outptr Vector2 output);
}
