package com.lteii.asteroid3d.gameBase.game3D;


import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.lteii.asteroid3d.utils.Math;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static com.lteii.asteroid3d.utils.Math.abs;

public class GameCameraManager {

    private static final Vector3 tmpVector3 = new Vector3();


    private final PerspectiveCamera camera;
    private final CameraTarget target;

    private boolean grabbed = false;
    private float angleY = 90;

    public GameCameraManager(PerspectiveCamera camera, CameraTarget target) {
        this.camera = camera;
        this.target = target;
    }


    public void update(float deltaS) {
        // Update angle from target
        if (!grabbed) {
            final float targetAngleY = target.getAngleY();
            final float distance = Math.angleDistance(angleY, targetAngleY);
            if (abs(distance) > 1) angleY = (angleY - 10*deltaS*distance)%360;
        }

        // Update camera
        target.getPosition(tmpVector3);
        camera.position.set(tmpVector3).add(-10*sinDeg(angleY), 5, -10*cosDeg(angleY));
        camera.lookAt(tmpVector3);
        camera.up.set(Vector3.Y);
        camera.update();
    }

    public boolean touchP() {
        grabbed = true;
        return true;
    }

    public boolean touchD(float lastUiX, float newUiX) {
        if (grabbed) {
            angleY += lastUiX-newUiX;
            return true;
        }
        return false;
    }

    public boolean touchR() {
        if (grabbed) {
            grabbed = false;
            return true;
        }
        return false;
    }

}
