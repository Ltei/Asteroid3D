package com.lteii.asteroid3d.gameBase.ui;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.lteii.asteroid3d.UIRenderer;

public class UIText {

    private static class Line {
        private float x0, y0;
        private String string;
        private Line(float x0, float y0, String string) {
            this.x0 = x0;
            this.y0 = y0;
            this.string = string;
        }
    }


    public float x, y, padX, padY;
    public BitmapFont font;
    public Line[] lines;

    public UIText(float x, float y, BitmapFont font, String string) {
        this(x, y, .5f, .5f, font, string);
    }
    public UIText(float x, float y, float padX, float padY, BitmapFont font, String string) {
        this.x = x;
        this.y = y;
        this.padX = padX;
        this.padY = padY;
        this.font = font;
        setString(string);
    }



    public void setString(String string) {
        if (string == null) {
            lines = new Line[0];
            return;
        }
        final String[] strs = string.split("\n");
        lines = new Line[strs.length];
        for (int i=0; i<strs.length; i++) {
            final String str = strs[i];
            final GlyphLayout layout = new GlyphLayout();
            layout.setText(font, str);
            final float lw = layout.width;
            final float lh = layout.height;
            lines[i] = new Line(x - (1-padX)*lw, y + (1-padY)*lh - 1.55f*i*lh, str);
        }
    }


    public void render(UIRenderer renderer) {
        for (Line line : lines) {
            renderer.render(font, line.string, line.x0, line.y0);
        }
    }

}
