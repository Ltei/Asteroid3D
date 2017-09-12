package com.lteii.asteroid3d;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface UIRenderer {
    void render(Sprite sprite);
    void render(BitmapFont font, String str, float x0, float y0);
}
