package com.lteii.asteroid3d.utils;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Math {

    public static final float COS45, SIN45;
    static {
        COS45 = SIN45 = 1/sqrt(2);
    }



    public static Color color255(float lum) {
        return color255(lum, lum, lum);
    }
    public static Color color255(float r, float g, float b) {
        return new Color(r/255f, g/255f, b/255f, 1);
    }
    public static Color color255(float r, float g, float b, float a) {
        return new Color(r/255f, g/255f, b/255f, a/255f);
    }
    /** Return color0 * color0Ratio + color1 * (1-color0Ratio) */
    public static Color mixColors(Color color0, Color color1, float color0Ratio) {
        final float color1Ratio = 1-color0Ratio;
        return new Color(color0.r*color0Ratio + color1.r*color1Ratio,
                color0.g*color0Ratio + color1.g*color1Ratio,
                color0.b*color0Ratio + color1.b*color1Ratio,
                color0.a*color0Ratio + color1.a*color1Ratio);
    }
    public static Color mixColorLoop(Array<Color> colors, float x) {
        final int nbColors = colors.size;
        for (int i=1; i<=nbColors; i++) {    // i E [1, nbColors]
            if (x <= i/(float)nbColors) {
                final int i0 = i-1;
                final int i1 = (i == nbColors) ? 0 : i;
                final float localX = rescale(x, (i-1f)/nbColors, i/(float)nbColors, 0, 1);
                return mixColors(colors.get(i1), colors.get(i0), localX);
            }
        }
        throw new IllegalStateException(""+x);
    }



    public static float max(float a, float b) { return a > b ? a : b; }
    public static float min(float a, float b) { return a < b ? a : b; }
    public static float abs(float x) {
        return x<0 ? -x : x;
    }
    public static float square(float x) { return x*x; }
    public static float sqrt(float x) { return (float) java.lang.Math.sqrt(x); }
    /** rescale x from [lastMin, lastMax] to [newMin, newMax] */
    public static float rescale(float x, float lastMin, float lastMax, float newMin, float newMax) {
        if (x < lastMin || x > lastMax || lastMin >= lastMax || newMin >= newMax) throw new IllegalArgumentException(x+", "+lastMin+", "+lastMax+", "+newMin+", "+newMax);
                                       // x E [lastMin, lastMax]
        x -= lastMin;                  // x E [0, (lastMax-lastMin)]
        x /= (lastMax-lastMin);        // x E [0, 1]
        x *= (newMax-newMin);          // x E [0, (newMax-newMin)]
        x += newMin;                   // x E [newMin, newMax]
        return x;
    }
    /** Is between inclusive */
    public static boolean isBetweenInc(float value, float min, float max) {
        if (max < min) {
            final float tmp = min;
            min = max;
            max = tmp;
        }
        return value >= min && value <= max;
    }
    /** Circular distance */
    public static float angleDistance(float angle0, float angle1) {
        final float delta = angle0-angle1;
        final float  distance = Math.abs(delta) % 360;
        final float minDistance = (distance > 180) ? 360 - distance : distance;
        final int sign = isBetweenInc(delta, 0, 180) || isBetweenInc(delta, -180, -360) ? 1 : -1;
        return minDistance*sign;
    }
    public static int mod(int x, int mod) {
        while (x < 0) x += mod;
        while (x >= mod) x -= mod;
        return x;
    }

}
