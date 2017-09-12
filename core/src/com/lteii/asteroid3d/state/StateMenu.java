package com.lteii.asteroid3d.state;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.lteii.asteroid3d.Globals;
import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.gameBase.collision2d.AABB2D;
import com.lteii.asteroid3d.gameBase.ui.UIText;
import com.lteii.asteroid3d.gameBase.ui.button.BasicButtonText;
import com.lteii.asteroid3d.gameBase.ui.button.Button;
import com.lteii.asteroid3d.loaders.FontLoader;
import com.lteii.asteroid3d.loaders.TextureLoader;

import static com.lteii.asteroid3d.Globals.FONT_L;
import static com.lteii.asteroid3d.Globals.MAIN;
import static com.lteii.asteroid3d.Globals.STATES_MANAGER;
import static com.lteii.asteroid3d.Globals.TEXTURE_L;
import static com.lteii.asteroid3d.Globals.UI_HEIGHT;
import static com.lteii.asteroid3d.Globals.UI_WIDTH;
import static com.lteii.asteroid3d.loaders.FontLoader.FID_BUTTON_TEXT_0;
import static com.lteii.asteroid3d.loaders.FontLoader.FID_BUTTON_TEXT_1;
import static com.lteii.asteroid3d.loaders.FontLoader.FID_TITLE;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_BUTTON_RECT;
import static com.lteii.asteroid3d.loaders.TextureLoader.TID_SKY;

public class StateMenu extends State {

    private static final TextureLoader.TextureID[] textureIDs = new TextureLoader.TextureID[] {TID_BUTTON_RECT, TID_SKY};
    private static final FontLoader.FontID[] fontIDs = new FontLoader.FontID[] {FID_TITLE, FID_BUTTON_TEXT_0, FID_BUTTON_TEXT_1};



    private final Sprite background;
    private final UIText title;

    private final Array<Button> buttons = new Array<Button>();

    public StateMenu() {
        Globals.setLoaded(textureIDs, fontIDs, null, null, null);

        background = new Sprite(TEXTURE_L.get(TID_SKY));
        background.setSize(UI_WIDTH, UI_HEIGHT);
        background.setCenter(0,0);
        title = new UIText(0, .3f*UI_HEIGHT, FONT_L.get(FID_TITLE), "That car");

        buttons.add(new BasicButtonText(new AABB2D(-UI_WIDTH/5, -UI_HEIGHT/20, UI_WIDTH/3, UI_WIDTH/8),
                TEXTURE_L.get(TID_BUTTON_RECT), Color.WHITE,
                "Continue", FONT_L.get(FID_BUTTON_TEXT_0), FONT_L.get(FontLoader.FID_BUTTON_TEXT_1)) {
            @Override
            protected void action() {
                STATES_MANAGER.setState(new StatePlay(0));
            }
        });
        buttons.add(new BasicButtonText(new AABB2D(UI_WIDTH/5, -UI_HEIGHT/20, UI_WIDTH/3, UI_WIDTH/8),
                TEXTURE_L.get(TID_BUTTON_RECT), Color.WHITE,
                "Levels", FONT_L.get(FID_BUTTON_TEXT_0), FONT_L.get(FontLoader.FID_BUTTON_TEXT_1)) {
            @Override
            protected void action() {
                STATES_MANAGER.setState(new StateLevels());
            }
        });

        MAIN.setDraws(true, false);
        MAIN.updateLastTimeMillis();
    }


    @Override
    void update(float deltaS) {

    }

    @Override
    void renderUI(UIRenderer renderer) {
        renderer.render(background);
        title.render(renderer);
        for (Button button : buttons) {
            button.render(renderer);
        }
    }

    @Override
    void touchP(float uiX, float uiY) {
        for (Button button : buttons) {
            button.touchP(uiX, uiY);
        }
    }


    @Override
    void touchR(float uiX, float uiY) {
        for (Button button : buttons) {
            button.touchR(uiX, uiY);
        }
    }

    @Override
    void dispose() {

    }
}
