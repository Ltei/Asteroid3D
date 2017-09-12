package com.lteii.asteroid3d.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.lteii.asteroid3d.WorldRenderer;
import com.lteii.asteroid3d.game.entities.EndLine;
import com.lteii.asteroid3d.game.entities.PlayerCar;
import com.lteii.asteroid3d.game.entities.SkySphere;
import com.lteii.asteroid3d.game.patterns.Road;
import com.lteii.asteroid3d.gameBase.game3D.CollisionListener;
import com.lteii.asteroid3d.gameBase.game3D.Entity;
import com.lteii.asteroid3d.gameBase.game3D.EntityCollidable;
import com.lteii.asteroid3d.gameBase.game3D.GameCameraManager;
import com.lteii.asteroid3d.gameBase.game3D.World;
import com.lteii.asteroid3d.loaders.ParticleLoader;
import com.lteii.asteroid3d.loaders.SoundLoader;
import com.lteii.asteroid3d.utils.ColorPattern;
import com.lteii.asteroid3d.utils.Computation;

import static com.lteii.asteroid3d.Globals.PARTICLE_L;
import static com.lteii.asteroid3d.Globals.SOUND_L;
import static com.lteii.asteroid3d.Globals.WORLD_CAM;
import static com.lteii.asteroid3d.Globals.WORLD_LIGHT;
import static com.lteii.asteroid3d.loaders.ParticleLoader.PID_EXPLOSION;

public class GameWorld extends World {

    public interface OnLoseListener {
        void onLevelEnd(boolean win);
    }

    private final Vector3 tmpPosition = new Vector3();


    private final GameCameraManager cameraManager;

    private LevelConstructor levelConstructor;

    private final SkySphere skySphere;
    private final PlayerCar playerCar;
    private Road road;

    private final ParticleEffect explosionEffect;
    private final ParticleBatch<?> explosionEffectBatch;

    private final ColorPattern lightColor;

    private int level;
    public int getLevel() { return level; }

    public GameWorld(int level, final OnLoseListener onLoseListener) {
        super();

        this.level = level;

        final ParticleLoader.ParticleInfo particleInfo = PARTICLE_L.get(PID_EXPLOSION);
        explosionEffect = particleInfo.effect;
        explosionEffectBatch = particleInfo.batch;

        collisionListener = new CollisionListener() {
            @Override
            public void onCollision(EntityCollidable object1, EntityCollidable object2) {
                if ((object1 instanceof PlayerCar) || (object2 instanceof PlayerCar)) {
                    if ((object1 instanceof EndLine) || (object2 instanceof EndLine)) {
                        playing = false;
                        onLoseListener.onLevelEnd(true);
                        explosionEffect.init();
                        explosionEffect.end();
                        SOUND_L.play(SoundLoader.SID_LEVEL_WIN, 2);
                    } else {
                        playing = false;
                        onLoseListener.onLevelEnd(false);
                        playerCar.getPosition(tmpPosition);
                        explosionEffect.setTransform(new Matrix4().translate(tmpPosition));
                        explosionEffect.init();
                        explosionEffect.start();
                        SOUND_L.play(SoundLoader.SID_EXPLOSION, 2);
                    }
                }
            }
        };

        skySphere = new SkySphere();
        playerCar = PlayerCar.create();
        this.add(skySphere);
        this.add(playerCar);

        init(LevelConstructor.create(level));

        lightColor = new ColorPattern(
                new ColorPattern.Item(Color.WHITE, 3, Computation.IDENTITY),
                new ColorPattern.Item(Color.BLUE,  3, Computation.IDENTITY));

        cameraManager = new GameCameraManager(WORLD_CAM, playerCar);
    }

    private void init(LevelConstructor levelConstructor) {
        this.levelConstructor = levelConstructor;
        for (Entity entity : levelConstructor.entities)
            this.add(entity);
        road = new Road(levelConstructor.floorConstructors, levelConstructor.barrierConstructors, levelConstructor.additionals);
        this.add(road);
    }
    public void resetLevel() {
        playerCar.reset();
        cameraManager.touchR();
        playing = true;
    }
    public void startNextLevel() {
        this.remove(road);
        road.dispose();
        for (Entity entity : levelConstructor.entities)
            this.remove(entity);
        level++;
        init(LevelConstructor.create(level));
        resetLevel();
    }



    @Override
    public void render(WorldRenderer renderer) {
        super.render(renderer);

        if (!playing) {
            explosionEffectBatch.begin();
            explosionEffect.draw();
            explosionEffectBatch.end();
            renderer.render(explosionEffectBatch);
        }
    }

    public void update(float deltaS) {
        if (playing) {
            super.update(deltaS);
        } else {
            explosionEffect.update();
        }

        playerCar.getPosition(tmpPosition);
        skySphere.update(tmpPosition);

        lightColor.update(deltaS);
        WORLD_LIGHT.setColor(lightColor.computeColor());

        cameraManager.update(deltaS);
    }


    public void touchP(float uiX, float uiY) {
        if (playing) {
            playerCar.touchP(uiX);
        } else {
            cameraManager.touchP();
        }
    }
    public void touchD(float lastUiX, float lastUiY, float newUiX, float newUiY) {
        if (playing) {
            playerCar.touchD(newUiX);
        } else {
            cameraManager.touchD(lastUiX, newUiX);
        }
    }
    public void touchR(float uiX, float uiY) {
        if (playing) {
            playerCar.touchR();
        } else {
            cameraManager.touchP();
        }
    }



    @Override
    public void dispose() {
        playerCar.dispose();
        road.dispose();
        explosionEffect.dispose();
    }

}
