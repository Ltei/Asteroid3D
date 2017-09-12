package com.lteii.asteroid3d.state;


import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.WorldRenderer;

abstract class State {


    void update(float deltaS) {}

    void renderWorld(WorldRenderer renderer) {}
    void renderUI(UIRenderer renderer) {}

    void touchP(float uiX, float uiY) {}
    void touchD(float lastUiX, float lastUiY, float newUiX, float newUiY) {}
    void touchR(float uiX, float uiY) {}

    void dispose() {}

}
