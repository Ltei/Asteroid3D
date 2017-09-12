package com.lteii.asteroid3d.utils;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;

public class ApplicationAdapter implements ApplicationListener, InputProcessor {

    // ApplicationListener
    @Override public void create() {

    }
    @Override public void resize(int width, int height) {

    }
    @Override public void render() {

    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void dispose() {

    }

    // InputProcessor
    @Override public boolean keyDown(int keycode) {
        return false;
    }
    @Override public boolean keyUp(int keycode) {
        return false;
    }
    @Override public boolean keyTyped(char character) {
        return false;
    }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override public boolean scrolled(int amount) {
        return false;
    }

}
