package com.lteii.asteroid3d.game.entities;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.lteii.asteroid3d.gameBase.collision3d.Box3D;
import com.lteii.asteroid3d.gameBase.game3D.InstanceShapeEntity;

import static com.lteii.asteroid3d.Globals.MODEL_L;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_HORIZONTAL_BAR;
import static com.lteii.asteroid3d.utils.Math.color255;

public class EndLine extends InstanceShapeEntity {


    public EndLine() {
        super(MODEL_L.get(MID_ROAD_HORIZONTAL_BAR), new Box3D(10f, .5f, .5f));
        instance.materials.get(0).set(ColorAttribute.createDiffuse(color255(0,200,75)), ColorAttribute.createSpecular(Color.BLUE));
    }


}
