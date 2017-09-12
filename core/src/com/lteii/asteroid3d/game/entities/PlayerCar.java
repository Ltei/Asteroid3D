package com.lteii.asteroid3d.game.entities;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;
import com.lteii.asteroid3d.WorldRenderer;
import com.lteii.asteroid3d.gameBase.collision3d.Box3D;
import com.lteii.asteroid3d.gameBase.collision3d.CompoundShape3D;
import com.lteii.asteroid3d.gameBase.game3D.CameraTarget;
import com.lteii.asteroid3d.gameBase.game3D.EntityUpdatable;
import com.lteii.asteroid3d.gameBase.game3D.InstanceShapeEntity;
import com.lteii.asteroid3d.loaders.ModelLoader;
import com.lteii.asteroid3d.utils.annotations.Outptr;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;
import static com.lteii.asteroid3d.Globals.MODEL_L;

public class PlayerCar extends InstanceShapeEntity implements CameraTarget, EntityUpdatable, Disposable {


    private final float speed = 20;
    private final float angleYSpeed = 180;
    private final float angleZMoveSpeed = 110;
    private final float angleZMoveSpeedWOpposite = 2*angleZMoveSpeed;
    private final float angleZResetSpeed = 2*angleZMoveSpeed;
    private final float maxAngleZ = 60;

    private int turning = 0;
    private final Vector3 position = new Vector3();
    private float angleY = 90;
    private float angleZ = 0;

    private final PlayerCarTrail trail;

    private final CompoundShape3D.TestModel boxTestModel;

    private PlayerCar(Model model, Vector3 modelSize) {
        super(model, new CompoundShape3D(new Box3D(.5f*modelSize.x, .5f*modelSize.y, modelSize.z),
                new Box3D(.8f*modelSize.x, .5f*modelSize.y, .8f*modelSize.z)));
        reset();

        // Add effect
        trail = new PlayerCarTrail(modelSize.z);

        // Test
        ((CompoundShape3D)shape).shapes.first().transform.translate(0,-.2f,0);
        ((CompoundShape3D)shape).shapes.get(1).transform.translate(0,-.2f,0);
        boxTestModel = ((CompoundShape3D)shape).createTestModel();
        boxTestModel.instance.transform.set(instance.transform).mul(shape.transform);
    }
    public static PlayerCar create() {
        final Model model = MODEL_L.get(ModelLoader.MID_PLAYER_CAR);
        final BoundingBox box = model.calculateBoundingBox(new BoundingBox());
        final Vector3 boxSize = box.getDimensions(new Vector3());
        return new PlayerCar(model, boxSize);
    }

    public void reset() {
        turning = 0;
        position.set(0,0,0);
        angleY = 90;
        angleZ = 0;
    }

    public void teleportForward(float distance) {
        position.add(new Vector3(sinDeg(angleY), 0, cosDeg(angleY)).setLength2(distance*distance));
    }


    @Override
    public void render(WorldRenderer renderer) {
        super.render(renderer);
        trail.render(renderer);
        renderer.render(boxTestModel);
    }

    @Override
    public void update(float deltaS) {
        // Update angleZ
        if (turning != 0) {
            if (turning * angleZ > 0) { // AngleZ is opposite to turning
                angleZ = clamp(angleZ - turning*deltaS* angleZMoveSpeedWOpposite, -maxAngleZ, maxAngleZ);
            } else {
                angleZ = clamp(angleZ - turning*deltaS* angleZMoveSpeed, -maxAngleZ, maxAngleZ);
            }
        } else if (angleZ != 0) {
            if (angleZ < 0) {
                angleZ += angleZResetSpeed*deltaS;
                if (angleZ > 0) angleZ = 0;
            } else { // angleZ > 0
                angleZ -= angleZResetSpeed*deltaS;
                if (angleZ < 0) angleZ = 0;
            }
        }

        // Update angleY based on angleZ
        if (angleZ != 0) {
            angleY = (angleY - angleZ*deltaS*angleYSpeed/maxAngleZ) % 360;
        }

        // Update position
        position.add(deltaS*speed*sinDeg(angleY), 0, deltaS*speed*cosDeg(angleY));

        // Update instance body and particles
        instance.transform.idt().translate(position).rotate(Vector3.Y, angleY).rotate(Vector3.Z, angleZ);
        updateBodyFromInstance();
        trail.update(position, angleY);

        boxTestModel.instance.transform.set(instance.transform).mul(shape.transform);
    }


    public boolean touchP(float uiX) {
        turning = (uiX > 0) ? -1 : 1;
        return true;
    }

    public boolean touchD(float newUiX) {
        turning = (newUiX > 0) ? -1 : 1;
        return true;
    }

    public boolean touchR() {
        turning = 0;
        return true;
    }


    // Camera Target
    @Override
    public void getPosition(@Outptr Vector3 output) {
        output.set(position);
    }
    @Override
    public float getAngleY() {
        return angleY;
    }

    @Override
    public void dispose() {
        trail.dispose();
        boxTestModel.dispose();
    }
}