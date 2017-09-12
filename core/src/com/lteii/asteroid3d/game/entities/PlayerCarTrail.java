package com.lteii.asteroid3d.game.entities;


import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.lteii.asteroid3d.WorldRenderer;
import com.lteii.asteroid3d.loaders.ParticleLoader;

import static com.lteii.asteroid3d.Globals.PARTICLE_L;

public class PlayerCarTrail implements Disposable {


    private final ParticleEffect effect;
    private final ParticleBatch<?> batch;
    private final float zOffset;

    public PlayerCarTrail(float modelSizeZ) {
        final ParticleLoader.ParticleInfo particleInfo = PARTICLE_L.get(ParticleLoader.PID_CAR_TRAIL);
        effect = particleInfo.effect;
        effect.init();
        effect.start();
        batch = particleInfo.batch;
        zOffset = -.5f*modelSizeZ;
    }


    public void render(WorldRenderer renderer) {
        batch.begin();
        effect.draw();
        batch.end();
        renderer.render(batch);
    }

    public void update(Vector3 playerPosition, float playerAngleY) {
        effect.setTransform(new Matrix4().translate(playerPosition).rotate(Vector3.Y, playerAngleY).translate(0,0,zOffset));
        effect.update();
    }

    @Override
    public void dispose() {
        effect.dispose();
    }
}
