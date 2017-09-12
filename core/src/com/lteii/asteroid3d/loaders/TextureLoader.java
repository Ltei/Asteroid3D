package com.lteii.asteroid3d.loaders;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import static com.lteii.asteroid3d.utils.Math.abs;
import static com.lteii.asteroid3d.utils.Math.max;
import static com.lteii.asteroid3d.utils.Math.min;
import static com.lteii.asteroid3d.utils.Math.mixColors;

public class TextureLoader extends Loader<TextureLoader.TextureID, Texture> {

    public static class TextureID extends Loader.Key {
        private final boolean isFilePath;
        private final String id;
        private TextureID(boolean isFilePath, String id) {
            this.isFilePath = isFilePath;
            this.id = id;
        }
    }


    public static final TextureID TID_WHITE_SQUARE = new TextureID(false, "whiteSquare");
    public static final TextureID TID_FLOOR = new TextureID(false, "floor");
    public static final TextureID TID_BUTTON_RECT = new TextureID(false, "buttonRect");
    public static final TextureID TID_SKY = new TextureID(true, "texture/sky.jpg");
    public static final TextureID TID_ICON_RELOAD = new TextureID(true, "texture/iconReload.png");
    public static final TextureID TID_ICON_NEXT = new TextureID(true, "texture/iconNext.png");
    public static final TextureID TID_ICON_MENU = new TextureID(true, "texture/iconMenu.png");



    public Texture get(TextureID id) {
        return loaded.get(id);
    }


    @Override
    protected void load(TextureID id) {
        if (isLoaded(id)) throw new IllegalStateException();
        if (id.isFilePath) {
            loaded.put(id, new Texture(Gdx.files.internal(id.id)));
        } else {
            loadPixmapTexture(id);
        }
    }
    @Override
    protected void unload(TextureID id) {
        if (!isLoaded(id)) throw new IllegalStateException();
        loaded.remove(id).dispose();
    }

    private void loadPixmapTexture(TextureID id) {
        if (id == TID_WHITE_SQUARE) {
            loadWhiteSquare(id);
        } else if (id == TID_FLOOR) {
            loadFloor(id);
        } else if (id == TID_BUTTON_RECT) {
            loadButtonRect(id);
        } else {
            throw new IllegalStateException();
        }
    }
    private void loadPixmapTexture(TextureID id, Pixmap pixmap) {
        loaded.put(id, new Texture(pixmap));
    }
    private void loadWhiteSquare(TextureID id) {
        final Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                pixmap.setColor(1, 1, 1, 1);
                pixmap.drawPixel(i, j);
            }
        }
        loadPixmapTexture(id, pixmap);
        pixmap.dispose();
    }
    private void loadFloor(TextureID id) {
        final Color colorLazer = new Color(1f, 1f, 1f, .75f);
        final Color colorBackground = new Color(.8f, .8f, .8f, .25f);
        final int size = 512;
        final float halfSize = size/2f;
        final Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                float radius = max(abs(i-halfSize), abs(j-halfSize));
                float x = radius / halfSize;
                x = x*x;
                pixmap.setColor(mixColors(colorLazer, colorBackground, x));
                pixmap.drawPixel(i, j);
            }
        }
        loadPixmapTexture(id, pixmap);
        pixmap.dispose();


        get(id).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        get(id).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    private void loadButtonRect(TextureID id) {
        final int width = 512, height = width/2;
        final int halfWidth = width/2, halfHeight = height/2;
        final int border = 25;
        final Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        for (int i=0; i<width; i++) {
            final float xDistance = abs(i-halfWidth);
            for (int j=0; j<height; j++) {
                final float yDistance = abs(j-halfHeight);

                if (xDistance < halfWidth-border && yDistance < halfHeight-border) {
                    pixmap.setColor(1,1,1,1);
                    pixmap.drawPixel(i, j);
                } else {
                    float x = min(halfWidth-xDistance, halfHeight-yDistance)/border;
                    x = 1-x;
                    x = x*x*x*x*x*x*x*x*x*x;
                    x = 1-x;
                    pixmap.setColor(1,1,1,x);
                    pixmap.drawPixel(i, j);
                }
            }
        }
        loadPixmapTexture(id, pixmap);
        pixmap.dispose();


        get(id).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        get(id).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }


    @Override
    public void dispose() {
        for (Texture texture : loaded.values()) {
            texture.dispose();
        }
    }


}