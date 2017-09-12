package com.lteii.asteroid3d.loaders;


import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public abstract class Loader<K extends Loader.Key, V> implements Disposable {

    public static abstract class Key {
    }


    protected final HashMap<K,V> loaded = new HashMap<K, V>();


    public final void setLoaded(K...keys) {
        final Object[] loadedKeys = loaded.keySet().toArray();
        for (Object loadedKey : loadedKeys) {
            boolean unload = true;
            for (K key : keys) {
                if (loadedKey.equals(key)) {
                    unload = false;
                    break;
                }
            }
            if (unload) unload((K)loadedKey);
        }

        addLoaded(keys);
    }
    public final void addLoaded(K...keys) {
        for (K key : keys)
            if (!isLoaded(key))
                load(key);
    }

    protected final boolean isLoaded(K key) {
        return loaded.containsKey(key);
    }

    protected abstract void load(K key);
    protected abstract void unload(K key);

}
