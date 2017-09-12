package com.lteii.asteroid3d.gameBase.ui.button;


import com.lteii.asteroid3d.UIRenderer;

public interface Button {


    void render(UIRenderer renderer);

    boolean touchP(float uiX, float uiY);
    boolean touchR(float uiX, float uiY);

}
