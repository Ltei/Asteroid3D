package com.lteii.asteroid3d.state;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.lteii.asteroid3d.Globals;
import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.WorldRenderer;
import com.lteii.asteroid3d.game.GameWorld;
import com.lteii.asteroid3d.game.LevelConstructor;
import com.lteii.asteroid3d.gameBase.collision2d.AABB2D;
import com.lteii.asteroid3d.gameBase.collision2d.Circle2D;
import com.lteii.asteroid3d.gameBase.ui.UIText;
import com.lteii.asteroid3d.gameBase.ui.animation.ToastAnimation;
import com.lteii.asteroid3d.gameBase.ui.button.BasicButton;
import com.lteii.asteroid3d.gameBase.ui.button.BasicButtonText;
import com.lteii.asteroid3d.gameBase.ui.button.Button;
import com.lteii.asteroid3d.loaders.FontLoader;
import com.lteii.asteroid3d.loaders.ModelLoader;
import com.lteii.asteroid3d.loaders.ParticleLoader;
import com.lteii.asteroid3d.loaders.SoundLoader;
import com.lteii.asteroid3d.loaders.TextureLoader;

import static com.lteii.asteroid3d.Globals.FONT_L;
import static com.lteii.asteroid3d.Globals.MAIN;
import static com.lteii.asteroid3d.Globals.STATES_MANAGER;
import static com.lteii.asteroid3d.Globals.TEXTURE_L;
import static com.lteii.asteroid3d.Globals.UI_HEIGHT;
import static com.lteii.asteroid3d.Globals.UI_WIDTH;
import static com.lteii.asteroid3d.loaders.FontLoader.FID_BUTTON_TEXT_0;
import static com.lteii.asteroid3d.loaders.FontLoader.FID_BUTTON_TEXT_1;
import static com.lteii.asteroid3d.loaders.FontLoader.FID_GAME_TOAST;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ASTEROID;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_PLAYER_CAR;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_BAR_BALL;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_FLOOR;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_HORIZONTAL_BAR;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_VERTICAL_BAR;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_SKY_SPHERE;
import static com.lteii.asteroid3d.loaders.ParticleLoader.PID_CAR_TRAIL;
import static com.lteii.asteroid3d.loaders.ParticleLoader.PID_EXPLOSION;
import static com.lteii.asteroid3d.loaders.SoundLoader.SID_EXPLOSION;
import static com.lteii.asteroid3d.loaders.SoundLoader.SID_LEVEL_WIN;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_BUTTON_RECT;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_FLOOR;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_ICON_MENU;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_ICON_NEXT;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_ICON_RELOAD;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_SKY;
import static com.lteii.asteroid3d.utils.Math.color255;

public class StatePlay extends State {

    private static final TextureLoader.TextureID[] textureIDs = new TextureLoader.TextureID[] {
            TID_ICON_RELOAD, TID_ICON_NEXT, TID_ICON_MENU, TID_BUTTON_RECT, TID_SKY, TID_FLOOR};
    private static final FontLoader.FontID[] fontIDs = new FontLoader.FontID[] {
            FID_BUTTON_TEXT_0, FID_BUTTON_TEXT_1, FID_GAME_TOAST};
    private static final SoundLoader.SoundID[] soundIDs = new SoundLoader.SoundID[] {
            SID_EXPLOSION, SID_LEVEL_WIN};
    private static final ModelLoader.ModelID[] modelIDs = new ModelLoader.ModelID[] {
            MID_PLAYER_CAR, MID_SKY_SPHERE, MID_ROAD_FLOOR, MID_ROAD_HORIZONTAL_BAR, MID_ROAD_VERTICAL_BAR, MID_ROAD_BAR_BALL, MID_ASTEROID, MID_ROAD_HORIZONTAL_BAR};
    private static final ParticleLoader.ParticleID[] particleIDs = new ParticleLoader.ParticleID[] {
            PID_CAR_TRAIL, PID_EXPLOSION};


    private final GameWorld world;

    private final Array<Button> buttons = new Array<Button>();
    private final BasicButton nextLevelButton;
    private final BasicButton tryAgainButton;

    private final ToastAnimation toast;

    public StatePlay(int level) {
        Globals.setLoaded(textureIDs, fontIDs, soundIDs, modelIDs, particleIDs);

        world = new GameWorld(level,
                new GameWorld.OnLoseListener() {
            @Override
            public void onLevelEnd(boolean win) {
                if (win && world.getLevel() < LevelConstructor.LEVEL_MAX) {
                    nextLevelButton.setVisible(true);
                } else if (!win) {
                    tryAgainButton.setVisible(true);
                }
                toast(LevelConstructor.getEndMessage(world.getLevel(), win), 4);
            }
        });

        buttons.add(new BasicButton(new AABB2D(-.45f*UI_WIDTH, -.4f*UI_HEIGHT, UI_HEIGHT/20, UI_HEIGHT/20),
                TEXTURE_L.get(TID_ICON_MENU), color255(255)) {
            @Override
            protected void action() {
                STATES_MANAGER.setState(new StateMenu());
            }
        });
        buttons.add(new BasicButton(new Circle2D(-.4f*UI_WIDTH, -.4f*UI_HEIGHT, UI_HEIGHT/40),
                TEXTURE_L.get(TID_ICON_RELOAD), color255(255)) {
            @Override protected void action() {
                world.resetLevel();
                startPlaying();
            }
        });

        nextLevelButton = new BasicButtonText(new AABB2D(0, -.2f*UI_HEIGHT, .4f*UI_WIDTH, UI_WIDTH/8),
                TEXTURE_L.get(TID_BUTTON_RECT), Color.WHITE,
                "Next level", FONT_L.get(FID_BUTTON_TEXT_0), FONT_L.get(FID_BUTTON_TEXT_1)) {
            @Override
            protected void action() {
                world.startNextLevel();
                startPlaying();
            }
        };
        buttons.add(nextLevelButton);

        tryAgainButton = new BasicButtonText(new AABB2D(0, -.2f*UI_HEIGHT, .4f*UI_WIDTH, UI_WIDTH/8),
                TEXTURE_L.get(TID_BUTTON_RECT), Color.WHITE,
                "Try again", FONT_L.get(FID_BUTTON_TEXT_0), FONT_L.get(FID_BUTTON_TEXT_1)) {
            @Override
            protected void action() {
                world.resetLevel();
                startPlaying();
            }
        };
        buttons.add(tryAgainButton);

        toast = new ToastAnimation(new UIText(0, .4f*UI_HEIGHT, FONT_L.get(FID_GAME_TOAST), LevelConstructor.getBeginMessage(level)), 2);

        startPlaying();
        MAIN.setDraws(true, true);
    }


    private void startPlaying() {
        nextLevelButton.setVisible(false);
        tryAgainButton.setVisible(false);
        final String message = LevelConstructor.getBeginMessage(world.getLevel());
        if (message != null) {
            toast(message, 2);
        }
        MAIN.updateLastTimeMillis();
    }

    private void toast(String message, float length) {
        toast.text.setString(message);
        toast.duration = length;
        toast.start();
    }


    @Override
    void update(float deltaS) {
        world.update(deltaS);
        toast.update(deltaS);
    }

    @Override
    void renderWorld(WorldRenderer renderer) {
        world.render(renderer);
    }

    @Override
    void renderUI(UIRenderer renderer) {
        for (Button button : buttons)
            button.render(renderer);
        toast.render(renderer);
    }

    @Override
    void touchP(float uiX, float uiY) {
        if (MAIN.drawUI)
            for (Button button : buttons)
                if (button.touchP(uiX, uiY))
                    return;
        world.touchP(uiX, uiY);
    }

    @Override
    void touchD(float lastUiX, float lastUiY, float newUiX, float newUiY) {
        world.touchD(lastUiX, lastUiY, newUiX, newUiY);
    }

    @Override
    void touchR(float uiX, float uiY) {
        if (MAIN.drawUI)
            for (Button button : buttons)
                if (button.touchR(uiX, uiY))
                    return;
        world.touchR(uiX, uiY);
    }

    @Override
    void dispose() {
        world.dispose();
    }

}