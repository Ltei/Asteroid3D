package com.lteii.asteroid3d.loaders;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.utils.Array;

import static com.lteii.asteroid3d.Globals.WORLD_CAM;

public class ParticleLoader extends Loader<ParticleLoader.ParticleID, ParticleLoader.ParticleInfo> {


    public static class ParticleID extends Loader.Key {
        private final String id;
        private ParticleID(String id) {
            this.id = id;
        }
    }
    public static class ParticleInfo {
        public final ParticleEffect effect;
        public final ParticleBatch<?> batch;
        private ParticleInfo(ParticleEffect effect, ParticleBatch<?> batch) {
            this.effect = effect;
            this.batch = batch;
        }
        private ParticleInfo copy() {
            return new ParticleInfo(effect.copy(), batch);
        }
    }


    public static final ParticleID PID_CAR_TRAIL = new ParticleID("particle/carTrail.pfx");
    public static final ParticleID PID_EXPLOSION = new ParticleID("particle/explosion.pfx");
    public static final ParticleID PID_SPEED_BONUS = new ParticleID("particle/speedBonus.pfx");


    private final AssetManager manager;

    public ParticleLoader() {
        final FileHandleResolver resolver = new InternalFileHandleResolver();
        this.manager = new AssetManager(resolver, false);
        this.manager.setLoader(Texture.class, new com.badlogic.gdx.assets.loaders.TextureLoader(resolver));
        this.manager.setLoader(ParticleEffect.class, new ParticleEffectLoader(resolver));
    }


    /**
     * Return a copy of the corresponding ParticleInfo
     * /!\ The returned effect is a copy of the original, you  have to call it's dispose() youself!
     * /!\ You must set the returned effect's batches yourself
     * @param key The ParticleInfo's key
     * @return A copy of the corresponding ParticleEffect
     */
    public ParticleInfo get(ParticleID key) {
        if (!isLoaded(key)) throw new IllegalStateException();
        return loaded.get(key).copy();
    }

    @Override
    protected void load(ParticleID key) {
        final BillboardParticleBatch batch = new BillboardParticleBatch();
        batch.setCamera(WORLD_CAM);
        final Array<ParticleBatch<?>> batches = new Array<ParticleBatch<?>>();
        batches.add(batch);
        final ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(batches);
        this.manager.load(key.id, ParticleEffect.class, loadParam);
        this.manager.finishLoadingAsset(key.id);
        this.loaded.put(key, new ParticleInfo((ParticleEffect)this.manager.get(key.id), batch));
    }

    @Override
    protected void unload(ParticleID key) {
        this.loaded.remove(key);
        this.manager.unload(key.id);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }


}
