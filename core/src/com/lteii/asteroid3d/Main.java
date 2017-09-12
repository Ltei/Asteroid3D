package com.lteii.asteroid3d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.lteii.asteroid3d.state.StateMenu;
import com.lteii.asteroid3d.utils.ApplicationAdapter;

import static com.lteii.asteroid3d.Globals.FONT_L;
import static com.lteii.asteroid3d.Globals.MODEL_L;
import static com.lteii.asteroid3d.Globals.PARTICLE_L;
import static com.lteii.asteroid3d.Globals.SOUND_L;
import static com.lteii.asteroid3d.Globals.STATES_MANAGER;
import static com.lteii.asteroid3d.Globals.TEXTURE_L;
import static com.lteii.asteroid3d.Globals.WORLD_CAM;
import static com.lteii.asteroid3d.Globals.WORLD_LIGHT;

public class Main extends ApplicationAdapter {

    private static final Vector3 tmpVector3 = new Vector3();


    public boolean drawUI = true;
    public boolean drawWorld = true;
    public void setDraws(boolean drawUI, boolean drawWorld) {
        this.drawUI = drawUI;
        this.drawWorld = drawWorld;
    }

    private ModelBatch worldBatch;
    private WorldRenderer worldRenderer;

    private SpriteBatch uiBatch;
    private OrthographicCamera uiCamera;
    private UIRenderer uiRenderer;

    @Override
    public void create () {
        Gdx.input.setInputProcessor(this);

        Globals.MAIN = this;

        // Setup world
        final Environment worldEnvironment = new Environment();
        worldEnvironment.set(new ColorAttribute(ColorAttribute.AmbientLight, .75f, .75f, .75f, 1f));
        worldEnvironment.add(WORLD_LIGHT);
        worldBatch = new ModelBatch();
        worldRenderer = new WorldRenderer() {
            @Override
            public void render(RenderableProvider provider) {
                worldBatch.render(provider, worldEnvironment);
            }
        };

        // Setup ui
        uiBatch = new SpriteBatch();
        uiCamera = new OrthographicCamera(Globals.UI_WIDTH, Globals.UI_HEIGHT);
        uiRenderer = new UIRenderer() {
            @Override
            public void render(Sprite sprite) {
                sprite.draw(uiBatch);
            }
            @Override
            public void render(BitmapFont font, String str, float x0, float y0) {
                font.draw(uiBatch, str, x0, y0);
            }
        };

        // Launch
        STATES_MANAGER.setState(new StateMenu());
    }


    private long lastTimeMillis = -1L;
    public void updateLastTimeMillis() {
        lastTimeMillis = System.currentTimeMillis();
    }

    private int frameCount = 0;
    private long lastFrameResetMillis = 0L;

    @Override
    public void render () {
        if (lastTimeMillis == -1L) {
            lastTimeMillis = System.currentTimeMillis();
        } else {
            final long currentTimeMillis = System.currentTimeMillis();
            final float deltaS = (currentTimeMillis-lastTimeMillis)/1000f;
            lastTimeMillis = currentTimeMillis;
            STATES_MANAGER.update(deltaS);

            frameCount++;
            if (currentTimeMillis - lastFrameResetMillis > 1000f) {
                System.out.println("FPS : "+frameCount);
                frameCount = 0;
                lastFrameResetMillis = currentTimeMillis;
            }
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0,.15f,.3f,1);



        if (drawWorld) {
            worldBatch.begin(WORLD_CAM);
            STATES_MANAGER.renderWorld(worldRenderer);
            worldBatch.end();
        }

        if (drawUI) {
            uiBatch.begin();
            uiBatch.setProjectionMatrix(uiCamera.combined);
            STATES_MANAGER.renderUI(uiRenderer);
            uiBatch.end();
        }

    }


    private Vector3 touchPosition = new Vector3();

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPosition.set(screenX, screenY, 0);
        uiCamera.unproject(touchPosition);
        STATES_MANAGER.touchP(touchPosition.x, touchPosition.y);
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        tmpVector3.set(screenX, screenY, 0);
        uiCamera.unproject(tmpVector3);
        STATES_MANAGER.touchD(touchPosition.x, touchPosition.y, tmpVector3.x, tmpVector3.y);
        touchPosition.set(tmpVector3);
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchPosition.set(screenX, screenY, 0);
        uiCamera.unproject(touchPosition);
        STATES_MANAGER.touchR(touchPosition.x, touchPosition.y);
        return false;
    }


    @Override
    public void resume() {
        updateLastTimeMillis();
    }

    @Override
    public void dispose () {
        worldBatch.dispose();
        uiBatch.dispose();
        STATES_MANAGER.dispose();
        TEXTURE_L.dispose();
        FONT_L.dispose();
        SOUND_L.dispose();
        MODEL_L.dispose();
        PARTICLE_L.dispose();
    }

}