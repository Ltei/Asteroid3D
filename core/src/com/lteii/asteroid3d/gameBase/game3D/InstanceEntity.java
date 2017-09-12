package com.lteii.asteroid3d.gameBase.game3D;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.lteii.asteroid3d.WorldRenderer;

public class InstanceEntity implements EntityRenderable {


    public final ModelInstance instance;

    public InstanceEntity(Model model) {
        this.instance = new ModelInstance(model);
    }


    @Override
    public void render(WorldRenderer renderer) {
        renderer.render(instance);
    }
}
