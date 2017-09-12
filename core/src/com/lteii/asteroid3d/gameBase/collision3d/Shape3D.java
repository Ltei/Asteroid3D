package com.lteii.asteroid3d.gameBase.collision3d;


import com.badlogic.gdx.math.Matrix4;

public abstract class Shape3D {

    final BoundingBox3D boundingBox = new BoundingBox3D();
    public final Matrix4 transform = new Matrix4();


    public abstract boolean collideWith(Shape3D shape);
    public abstract void set(Matrix4 transform);

}
