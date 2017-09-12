package com.lteii.asteroid3d.gameBase.ui.animation;


import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.gameBase.ui.UIText;

public abstract class TextAnimation extends Animation {

    public final UIText text;

    public TextAnimation(UIText text) {
        this.text = text;
    }

    @Override
    public void render(UIRenderer renderer) {
        if (playing) text.render(renderer);
    }
}
