package com.lteii.asteroid3d.state;


import com.lteii.asteroid3d.UIRenderer;
import com.lteii.asteroid3d.WorldRenderer;

public class StatesManager {


    private State state = null;

    public StatesManager() {}


    public void setState(State state) {
        if (this.state != null) this.state.dispose();
        this.state = state;
    }


    public void update(float deltaS) {
        if (state != null) state.update(deltaS);
    }

    public void renderWorld(WorldRenderer renderer) {if (state != null) state.renderWorld(renderer);}
    public void renderUI(UIRenderer renderer) { if (state != null) state.renderUI(renderer); }

    public void touchP(float uiX, float uiY) { if (state != null) state.touchP(uiX, uiY); }
    public void touchD(float lastUiX, float lastUiY, float newUiX, float newUiY) {
        if (state != null) state.touchD(lastUiX, lastUiY, newUiX, newUiY);
    }
    public void touchR(float uiX, float uiY) { if (state != null) state.touchR(uiX, uiY); }

    public void dispose() {
        if (state != null) state.dispose();
    }

}
