package com.lteii.asteroid3d.loaders;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.lteii.asteroid3d.Globals.UI_HEIGHT;
import static com.lteii.asteroid3d.Globals.UI_WIDTH;
import static com.lteii.asteroid3d.utils.Math.color255;

public class FontLoader extends Loader<FontLoader.FontID, BitmapFont> {

    public static class FontID extends Loader.Key {
        private FontID() {
        }
    }


    public static final FontID FID_TITLE = new FontID();
    public static final FontID FID_BUTTON_TEXT_0 = new FontID();
    public static final FontID FID_BUTTON_TEXT_1 = new FontID();
    public static final FontID FID_GAME_TOAST = new FontID();


    private final FreeTypeFontGenerator FTFG = new FreeTypeFontGenerator(Gdx.files.internal("font/blow.ttf"));



    public BitmapFont get(FontID id) {
        return loaded.get(id);
    }

    @Override
    protected void load(FontID key) {
        if (key == FID_TITLE) {
            loadFont(FID_TITLE, FTFG, getFparam(.14f*UI_WIDTH, color255(25), 3, color255(225)));
        } else if (key == FID_BUTTON_TEXT_0) {
            loadFont(FID_BUTTON_TEXT_0, FTFG, getFparam(.07f*UI_WIDTH, color255(25)));
        } else if (key == FID_BUTTON_TEXT_1) {
            loadFont(FID_BUTTON_TEXT_1, FTFG, getFparam(1.1f*.07f*UI_WIDTH, color255(25)));
        } else if (key == FID_GAME_TOAST) {
            loadFont(FID_GAME_TOAST, FTFG, getFparam(.08f*UI_HEIGHT, color255(200,200,50), 2, color255(150,125,25)));
        }  else {
            throw  new IllegalStateException();
        }
    }

    @Override
    protected void unload(FontID key) {
        loaded.remove(key).dispose();
    }


    private void loadFont(FontID id, BitmapFont font) {
        loaded.put(id, font);
    }
    private void loadFont(FontID id, FreeTypeFontGenerator ftfg, FreeTypeFontGenerator.FreeTypeFontParameter fontparam) {
        loadFont(id, ftfg.generateFont(fontparam));
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter getFparam(float size, Color color) {
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = MathUtils.round(size);
        param.color = color;
        return param;
    }
    private FreeTypeFontGenerator.FreeTypeFontParameter getFparam(float size, Color color, float borderwidth, Color bordercolor) {
        FreeTypeFontGenerator.FreeTypeFontParameter param = getFparam(size, color);
        param.borderWidth = borderwidth;
        param.borderColor = bordercolor;
        return param;
    }
    private FreeTypeFontGenerator.FreeTypeFontParameter getFparam(float size, Color color, float borderwidth, Color bordercolor, Vector2 shadowoffset, Color shadowcolor) {
        FreeTypeFontGenerator.FreeTypeFontParameter param = getFparam(size, color, borderwidth, bordercolor);
        param.shadowOffsetX = MathUtils.round(shadowoffset.x);
        param.shadowOffsetY = MathUtils.round(shadowoffset.y);
        param.shadowColor = shadowcolor;
        return param;
    }


    @Override
    public void dispose() {
        for (BitmapFont font : loaded.values())
            font.dispose();
    }



}
