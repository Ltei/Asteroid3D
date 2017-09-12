/*package com.lteii.asteroid3d.gameBase.game3D;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.utils.Disposable;
import com.lteii.asteroid3d.WorldRenderer;

import static com.lteii.asteroid3d.Globals.WORLD_CAM;


public class CacheEntity implements EntityRenderable, Disposable {



    private final ModelCache cache;

    public CacheEntity(Iterable<RenderableProvider> renderableProviders) {
        cache = new ModelCache();
        cache.begin(WORLD_CAM);
        cache.add(renderableProviders);
        cache.end();
    }



    @Override
    public void render(WorldRenderer renderer) {
        renderer.render(cache);
    }


    @Override
    public void dispose() {
        cache.dispose();
    }
}
*/