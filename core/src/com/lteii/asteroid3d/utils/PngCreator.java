package com.lteii.asteroid3d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.math.Vector2;


public class PngCreator {

    public static void createPng(String name) {
        final int size = 512, halfSize = size/2;
        final Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                final float radius = new Vector2(i-halfSize,j-halfSize).len();
                if (radius < halfSize) {
                    float x = radius/halfSize;
                    x = x*x*x*x*x;
                    pixmap.setColor(1, 1, 1, x);
                    pixmap.drawPixel(i, j);
                }
            }
        }
        PixmapIO.writePNG(Gdx.files.external(name+".png"), pixmap);
        pixmap.dispose();
    }


}
