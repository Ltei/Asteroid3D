package com.lteii.asteroid3d.gameBase.game3D;


import com.lteii.asteroid3d.WorldRenderer;

public interface EntityRenderable extends Entity {
    void render(WorldRenderer renderer);
}
