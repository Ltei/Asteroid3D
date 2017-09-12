package com.lteii.asteroid3d.game.entities;


import com.lteii.asteroid3d.gameBase.collision3d.Box3D;
import com.lteii.asteroid3d.gameBase.game3D.InstanceShapeEntity;

import static com.lteii.asteroid3d.Globals.MODEL_L;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_HORIZONTAL_BAR;

public class BeginLine extends InstanceShapeEntity {


    public BeginLine() {
        super(MODEL_L.get(MID_ROAD_HORIZONTAL_BAR), new Box3D(10f, .5f, .5f));
    }

}
