package com.lteii.asteroid3d.loaders;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.badlogic.gdx.graphics.g3d.particles.influencers.Influencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.SpawnInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardRenderer;
import com.badlogic.gdx.graphics.g3d.particles.values.GradientColorValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.RangedNumericValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;

public class ParticleLoaderUtils {

    static void unactivateValue(ParticleValue...values) {
        for (ParticleValue value : values) {
            value.setActive(false);
            if (value instanceof ScaledNumericValue) {
                final ScaledNumericValue v = (ScaledNumericValue)value;
                v.setLow(0);
                v.setHigh(0);
                v.setRelative(false);
                v.setScaling(new float[] {1});
                v.setTimeline(new float[] {0});
            } else if (value instanceof RangedNumericValue) {
                final RangedNumericValue v = (RangedNumericValue)value;
                v.setLow(0);
            } else if (value instanceof GradientColorValue) {
                final GradientColorValue v = (GradientColorValue)value;
                v.setColors(new float[] {1,1,1});
                v.setTimeline(new float[] {0});
            } else {
                throw new IllegalStateException();
            }
        }
    }

    static void setScaledNumericValue(ScaledNumericValue value, int low, int high, boolean relative, float[] scaling, float[] timeline) {
        value.setActive(true);
        value.setLow(low);
        value.setHigh(high);
        value.setRelative(relative);
        value.setScaling(scaling);
        value.setTimeline(timeline);
    }
    static void setRangedNumericValue(RangedNumericValue value, int low) {
        if (value instanceof ScaledNumericValue) throw new IllegalArgumentException();
        value.setActive(true);
        value.setLow(low);
    }
    static void setGradientColorValue(GradientColorValue value, float[] colors, float[] timeline) {
        value.setActive(true);
        value.setColors(colors);
        value.setTimeline(timeline);
    }

    static ParticleEffect test(BillboardParticleBatch batch) {
        final Texture texture = new Texture(Gdx.files.internal("particle/carTrailTexture.png"));

        final RegularEmitter emitter = new RegularEmitter();
        emitter.setMinParticleCount(0);
        emitter.setMaxParticleCount(200);
        emitter.setContinuous(true);
        setScaledNumericValue(emitter.emissionValue, 0, 250, false, new float[] {1}, new float[] {0});
        setRangedNumericValue(emitter.durationValue, 3000);
        setScaledNumericValue(emitter.lifeValue, 0, 500, false, new float[] {1,1,1}, new float[] {0,0.51369864f,0.9931507f});
        unactivateValue(emitter.delayValue, emitter.lifeOffsetValue);

        final RegionInfluencer regionInfluencer = new RegionInfluencer.Single();
        regionInfluencer.regions.add(new RegionInfluencer.AspectTextureRegion(new TextureRegion(texture)));

        final PointSpawnShapeValue spawnShapeValue = new PointSpawnShapeValue();
        spawnShapeValue.setActive(false);
        unactivateValue(spawnShapeValue.xOffsetValue, spawnShapeValue.yOffsetValue, spawnShapeValue.zOffsetValue,
                spawnShapeValue.spawnWidthValue, spawnShapeValue.spawnHeightValue, spawnShapeValue.spawnDepthValue);
        final SpawnInfluencer spawnInfluencer = new SpawnInfluencer(spawnShapeValue);

        final ColorInfluencer.Single colorInfluencer = new ColorInfluencer.Single();
        unactivateValue(colorInfluencer.alphaValue, colorInfluencer.colorValue);
        //setScaledNumericValue(colorInfluencer.alphaValue, false, 0, 1, false, new float[] {0,0.2631579f,1,0}, new float[] {0,0.6849315f,0.7808219f,1});
        //setGradientColorValue(colorInfluencer.colorValue, new float[] {0.38431373f,1,0.9529412f, 0.38431373f,1,0.9529412f, 0,0,0}, new float[] {0,0.9143686f,1});

        final DynamicsModifier.BrownianAcceleration acceleration = new DynamicsModifier.BrownianAcceleration();
        acceleration.isGlobal = false;
        setScaledNumericValue(acceleration.strengthValue, 0, 100, false, new float[] {1,1,1}, new float[] {0, 0.38356164f, 0.6766467f});

        return loadParticleEffect(batch, emitter, regionInfluencer, spawnInfluencer, colorInfluencer, acceleration);
    }
    static ParticleEffect loadParticleEffect(BillboardParticleBatch batch, Emitter emitter, Influencer...influencers) {
        //influencers:[{RegionInfluencer$Single,regions:[{u2:1,v2:1,halfInvAspectRatio:0.5}]},
        return new ParticleEffect(new ParticleController("", emitter, new BillboardRenderer(batch), influencers));
    }


}
