package com.lteii.asteroid3d.gameBase.ui.button;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.gameBase.collision2d.Shape2D;
import com.lteii.asteroid3d.gameBase.ui.UIText;

public abstract class BasicButtonText extends BasicButton {


    private final UIText text0, text1;

    public BasicButtonText(Shape2D shape, Texture texture0, Texture texture1, Color color0, Color color1, String text, BitmapFont font0, BitmapFont font1) {
        super(shape, texture0, texture1, color0, color1);
        this.text0 = new UIText(center.x, center.y, font0, text);
        this.text1 = new UIText(center.x, center.y, font1, text);
    }
    public BasicButtonText(Shape2D shape, Texture texture, Color color0, Color color1, String text, BitmapFont font0, BitmapFont font1) {
        super(shape, texture, color0, color1);
        this.text0 = new UIText(center.x, center.y, font0, text);
        this.text1 = new UIText(center.x, center.y, font1, text);
    }
    public BasicButtonText(Shape2D shape, Texture texture, Color color, String text, BitmapFont font0, BitmapFont font1) {
        super(shape, texture, color);
        this.text0 = new UIText(center.x, center.y, font0, text);
        this.text1 = new UIText(center.x, center.y, font1, text);
    }

    @Override
    public void render(UIRenderer renderer) {
        if (visible) {
            if (isPressed) {
                sprite1.setColor(color1);
                sprite1.setSize(1.1f*size.x, 1.1f*size.y);
                sprite1.setCenter(center.x, center.y);
                renderer.render(sprite1);
                text1.render(renderer);
            } else {
                sprite0.setColor(color0);
                sprite0.setSize(size.x, size.y);
                sprite0.setCenter(center.x, center.y);
                renderer.render(sprite0);
                text0.render(renderer);
            }
        }
    }

}
