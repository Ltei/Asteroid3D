package com.lteii.asteroid3d.state;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.lteii.asteroid3d.Globals;
import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.game.LevelConstructor;
import com.lteii.asteroid3d.gameBase.collision2d.AABB2D;
import com.lteii.asteroid3d.gameBase.ui.UIText;
import com.lteii.asteroid3d.gameBase.ui.button.BasicButton;
import com.lteii.asteroid3d.gameBase.ui.button.Button;
import com.lteii.asteroid3d.loaders.FontLoader;
import com.lteii.asteroid3d.loaders.TextureLoader;

import static com.lteii.asteroid3d.Globals.FONT_L;
import static com.lteii.asteroid3d.Globals.MAIN;
import static com.lteii.asteroid3d.Globals.MODEL_L;
import static com.lteii.asteroid3d.Globals.SOUND_L;
import static com.lteii.asteroid3d.Globals.STATES_MANAGER;
import static com.lteii.asteroid3d.Globals.TEXTURE_L;
import static com.lteii.asteroid3d.Globals.UI_HEIGHT;
import static com.lteii.asteroid3d.Globals.UI_WIDTH;
import static com.lteii.asteroid3d.loaders.FontLoader.FID_TITLE;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_BUTTON_RECT;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_SKY;

public class StateLevels extends State {

    private static final TextureLoader.TextureID[] textureIDs = new TextureLoader.TextureID[] {TID_SKY, TID_BUTTON_RECT};
    private static final FontLoader.FontID[] fontIDs = new FontLoader.FontID[] {FID_TITLE};


    private final Sprite background;
    private final UIText title;

    private Array<Button> levelButtons = new Array<Button>();

    public StateLevels() {
        Globals.setLoaded(textureIDs, fontIDs, null, null, null);

        background = new Sprite(TEXTURE_L.get(TID_SKY));
        background.setSize(UI_WIDTH, UI_HEIGHT);
        background.setCenter(0,0);
        title = new UIText(0, .3f*UI_HEIGHT, FONT_L.get(FID_TITLE), "All levels");

        final float levelButtonSize = UI_HEIGHT/10;
        final int nbLevelButtonsX = 10;
        final float levelButtonX0 = -UI_WIDTH/4, levelButtonY0 = .05f*UI_HEIGHT;
        final float levelButtonDX = UI_WIDTH/10, levelButtonDY = -UI_HEIGHT/10;
        for (int i=0; i<=LevelConstructor.LEVEL_MAX; i++) {
            final int level = i;
            final int x = i % nbLevelButtonsX;
            final int y = i / nbLevelButtonsX;
            levelButtons.add(new BasicButton(new AABB2D(levelButtonX0+x*levelButtonDX, levelButtonY0+y*levelButtonDY, levelButtonSize, levelButtonSize),
                    TEXTURE_L.get(TID_BUTTON_RECT), Color.WHITE) {
                @Override
                protected void action() {
                    STATES_MANAGER.setState(new StatePlay(level));
                }
            });
        }

        MAIN.setDraws(true, false);
        MAIN.updateLastTimeMillis();
    }


    @Override
    void renderUI(UIRenderer renderer) {
        renderer.render(background);
        title.render(renderer);
        for (Button button : levelButtons) {
            button.render(renderer);
        }
    }

    @Override
    void touchP(float uiX, float uiY) {
        for (Button button : levelButtons) {
            button.touchP(uiX, uiY);
        }
    }

    @Override
    void touchR(float uiX, float uiY) {
        for (Button button : levelButtons) {
            button.touchR(uiX, uiY);
        }
    }


}
