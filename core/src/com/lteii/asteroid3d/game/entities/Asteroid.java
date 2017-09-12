package com.lteii.asteroid3d.game.entities;


import com.lteii.asteroid3d.gameBase.collision3d.Box3D;
import com.lteii.asteroid3d.gameBase.game3D.InstanceShapeEntity;
import com.lteii.asteroid3d.loaders.ModelLoader;

import static com.lteii.asteroid3d.Globals.MODEL_L;

public class Asteroid extends InstanceShapeEntity {

    public Asteroid() {
        super(MODEL_L.get(ModelLoader.MID_ASTEROID), new Box3D(1,1,1));
    }

}
