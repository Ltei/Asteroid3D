package com.lteii.asteroid3d.gameBase.ui.button;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.gameBase.collision2d.Shape2D;

public abstract class BasicButton implements Button {


    protected boolean visible = true;
    protected boolean isPressed = false;
    public void setVisible(boolean visible) {
        if (this.visible == true && visible == false) isPressed = false;
        this.visible = visible;
    }
    public void unpress() {
        isPressed = false;
    }

    // Body
    private final Shape2D shape;

    // Instance
    protected final Vector2 center, size;
    protected final Color color0, color1;
    protected final Sprite sprite0, sprite1;


    public BasicButton(Shape2D shape, Texture texture0, Texture texture1, Color color0, Color color1) {
        this.shape = shape;

        this.center = new Vector2();
        shape.getCenter(this.center);
        this.size = new Vector2();
        shape.getSize(this.size);

        this.color0 = color0;
        this.color1 = color1;

        this.sprite0 = new Sprite(texture0);
        this.sprite1 = new Sprite(texture1);
    }
    public BasicButton(Shape2D shape, Texture texture, Color color0, Color color1) {
        this(shape, texture, texture, color0, color1);
    }
    public BasicButton(Shape2D shape, Texture texture, Color color) {
        this(shape, texture, texture, color, color);
    }


    @Override
    public void render(UIRenderer renderer) {
        if (visible) {
            if (isPressed) {
                sprite1.setColor(color1);
                sprite1.setSize(1.1f*size.x, 1.1f*size.y);
                sprite1.setCenter(center.x, center.y);
                renderer.render(sprite1);
            } else {
                sprite0.setColor(color0);
                sprite0.setSize(size.x, size.y);
                sprite0.setCenter(center.x, center.y);
                renderer.render(sprite0);
            }
        }
    }

    @Override
    public boolean touchP(float uiX, float uiY) {
        if (visible) {
            if (shape.contains(uiX, uiY)) {
                isPressed = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchR(float uiX, float uiY) {
        if (visible) {
            if (isPressed) {
                isPressed = false;
                if (shape.contains(uiX, uiY)) {
                    action();
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract void action();

}
