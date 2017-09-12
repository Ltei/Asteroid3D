package com.lteii.asteroid3d.gameBase.game3D;


import com.lteii.asteroid3d.gameBase.collision3d.Shape3D;

public interface EntityCollidable {
    boolean canCollideWith(EntityCollidable entity);
    boolean intersects(Shape3D shape);
    boolean intersects(EntityCollidable entity);
}
