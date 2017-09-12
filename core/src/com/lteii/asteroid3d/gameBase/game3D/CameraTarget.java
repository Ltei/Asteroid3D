package com.lteii.asteroid3d.gameBase.game3D;


import com.badlogic.gdx.math.Vector3;
import com.lteii.asteroid3d.utils.annotations.Outptr;

public interface CameraTarget {
    void getPosition(@Outptr Vector3 output);
    float getAngleY();
}
