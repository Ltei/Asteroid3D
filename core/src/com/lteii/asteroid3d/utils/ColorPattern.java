package com.lteii.asteroid3d.utils;


import com.badlogic.gdx.graphics.Color;

import static com.lteii.asteroid3d.utils.Math.mixColors;
import static com.lteii.asteroid3d.utils.Math.rescale;

public class ColorPattern {

    public static class Item {

        private final Color color;
        private final float duration;
        private final Computation xComputation;

        public Item(Color color, float duration, Computation xComputation) {
            this.color = color;
            this.duration = duration;
            this.xComputation = xComputation;
            if (xComputation.compute(0) != 0 || xComputation.compute(1) != 1) throw new IllegalArgumentException();
        }

    }


    private final Item[] items;
    private final LoopValue loopTime;

    public ColorPattern(Item... items) {
        this.items = items;

        float totalDuration = 0;
        for (Item item : items) totalDuration += item.duration;
        this.loopTime = new LoopValue(totalDuration);
    }


    public void update(float deltaS) {
        loopTime.update(deltaS);
    }


    public Color computeColor() {
        final int nbColors = items.length;
        final float time = loopTime.time;

        float addedDuration = 0;
        for (int i=1; i<=nbColors; i++) {    // i E [1, nbColors]
            addedDuration += items[i-1].duration;
            if (time <= addedDuration) {
                final int i0 = i-1;
                final int i1 = (i == nbColors) ? 0 : i;
                final float localX = rescale(time, addedDuration-items[i-1].duration, addedDuration, 0, 1);
                final Color output = mixColors(items[i1].color, items[i0].color, localX);
                return output;
            }
        }

        throw new IllegalStateException(String.valueOf(time));
    }

}
