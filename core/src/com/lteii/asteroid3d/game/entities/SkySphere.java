package com.lteii.asteroid3d.game.entities;


import com.badlogic.gdx.math.Vector3;
import com.lteii.asteroid3d.gameBase.game3D.InstanceEntity;
import com.lteii.asteroid3d.loaders.ModelLoader;

import static com.lteii.asteroid3d.Globals.MODEL_L;

public class SkySphere extends InstanceEntity {

    public SkySphere() {
        super(MODEL_L.get(ModelLoader.MID_SKY_SPHERE));
    }

    public void update(Vector3 playerPosition) {
        instance.transform.idt().translate(playerPosition);
    }

}
