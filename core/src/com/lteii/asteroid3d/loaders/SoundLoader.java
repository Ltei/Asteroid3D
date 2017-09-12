package com.lteii.asteroid3d.loaders;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundLoader extends Loader<SoundLoader.SoundID, Sound> {

    public static class SoundID extends Loader.Key {
        private final String id;
        private SoundID(String id) {
            this.id = id;
        }
    }


    public static final SoundID SID_EXPLOSION = new SoundID("sound/explosion.wav");
    public static final SoundID SID_LEVEL_WIN = new SoundID("sound/levelWin.wav");



    public void play(SoundID id, float volume) {
        loaded.get(id).play(volume);
    }

    @Override
    protected void load(SoundID key) {
        if (isLoaded(key)) throw new IllegalStateException();
        loaded.put(key, Gdx.audio.newSound(Gdx.files.internal(key.id)));
    }
    @Override
    protected void unload(SoundID key) {
        if (!isLoaded(key)) throw new IllegalStateException();
        loaded.remove(key).dispose();
    }


    @Override
    public void dispose() {
        for (Sound sound : loaded.values())
            sound.dispose();
        loaded.clear();
    }
}
