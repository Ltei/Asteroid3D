package com.lteii.asteroid3d.gameBase.game3D;


public interface CollisionListener {
    void onCollision(EntityCollidable object1, EntityCollidable object2);
}
