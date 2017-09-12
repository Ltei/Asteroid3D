package com.lteii.asteroid3d.gameBase.ui.animation;

import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.gameBase.ui.UIText;
import com.lteii.asteroid3d.utils.Computation;
import com.lteii.asteroid3d.utils.Math;


public class ToastAnimation extends TextAnimation {


    public float duration;
    private float time;

    public ToastAnimation(UIText text, float duration) {
        super(text);
        this.duration = duration;
    }


    @Override
    public void start() {
        super.start();
        time = 0;
    }

    @Override
    public void render(UIRenderer renderer) {
        if (playing) {
            if (time < 0.25f) {
                final float x = Math.rescale(time, 0f, .25f, 0, 1);
                text.font.setColor(1,1,1, x*x);
            } else {
                final float x = Math.rescale(time, .25f, duration, 0, 1);
                text.font.setColor(1,1,1, 1-x*x*x);
            }
            text.render(renderer);
        }
    }

    @Override
    public void update(float deltaS) {
        time += deltaS;
        if (time > duration) stop();
    }
}
