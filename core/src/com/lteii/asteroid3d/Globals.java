package com.lteii.asteroid3d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.lteii.asteroid3d.loaders.FontLoader;
import com.lteii.asteroid3d.loaders.ModelLoader;
import com.lteii.asteroid3d.loaders.ParticleLoader;
import com.lteii.asteroid3d.loaders.SoundLoader;
import com.lteii.asteroid3d.loaders.TextureLoader;
import com.lteii.asteroid3d.state.StatesManager;
import com.lteii.asteroid3d.utils.annotations.Nullable;

public class Globals {

    public static final float UI_WIDTH = 800;
    public static final float UI_HEIGHT = 480;

    public static Main MAIN;

    public static final TextureLoader TEXTURE_L = new TextureLoader();
    public static final FontLoader FONT_L = new FontLoader();
    public static final SoundLoader SOUND_L = new SoundLoader();
    public static final ModelLoader MODEL_L = new ModelLoader();
    public static final ParticleLoader PARTICLE_L = new ParticleLoader();

    public static final StatesManager STATES_MANAGER = new StatesManager();

    public static final PerspectiveCamera WORLD_CAM;
    public static final DirectionalLight WORLD_LIGHT;

    static {
        WORLD_CAM = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        WORLD_CAM.far = 300;
        WORLD_CAM.update();
        WORLD_LIGHT = new DirectionalLight().set(.75f,.75f,.75f, 0,-1,0);
    }

    public static void setLoaded(@Nullable TextureLoader.TextureID[] textureIDs,
                                 @Nullable FontLoader.FontID[] fontIDs,
                                 @Nullable SoundLoader.SoundID[] soundIDs,
                                 @Nullable ModelLoader.ModelID[] modelIDs,
                                 @Nullable ParticleLoader.ParticleID[] particleIDs) {
        if (textureIDs == null) TEXTURE_L.setLoaded();
        else TEXTURE_L.setLoaded(textureIDs);
        if (fontIDs == null) FONT_L.setLoaded();
        else FONT_L.setLoaded(fontIDs);
        if (soundIDs == null) SOUND_L.setLoaded();
        else SOUND_L.setLoaded(soundIDs);
        if (modelIDs == null) MODEL_L.setLoaded();
        else MODEL_L.setLoaded(modelIDs);
        if (particleIDs == null) PARTICLE_L.setLoaded();
        else PARTICLE_L.setLoaded(particleIDs);
    }


}
