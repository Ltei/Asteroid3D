package com.lteii.asteroid3d.game.patterns;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lteii.asteroid3d.WorldRenderer;
import com.lteii.asteroid3d.game.entities.PlayerCar;
import com.lteii.asteroid3d.gameBase.collision3d.Box3D;
import com.lteii.asteroid3d.gameBase.collision3d.Shape3D;
import com.lteii.asteroid3d.gameBase.game3D.EntityCollidable;
import com.lteii.asteroid3d.gameBase.game3D.EntityRenderable;
import com.lteii.asteroid3d.loaders.ModelLoader;
import com.lteii.asteroid3d.utils.Direction;
import com.lteii.asteroid3d.utils.annotations.Nullable;

import static com.lteii.asteroid3d.Globals.MODEL_L;
import static com.lteii.asteroid3d.Globals.WORLD_CAM;

public class Road implements EntityRenderable, EntityCollidable, Disposable {

    public static final float FLOOR_SIZE = 10;
    private static Box3D createBarrierShape() {
        return new Box3D(10, .5f, .5f);
    }

    public static class FloorConstructor {
        private final float x, z;
        public FloorConstructor(float x, float z) {
            this.x = x;
            this.z = z;
        }
    }
    public static class BarrierConstructor {
        private final Direction direction;
        private final float preRotX, preRotZ;
        private final float postRotX, postRotZ;
        public BarrierConstructor(Direction direction, float preRotX, float preRotZ, float postRotX, float postRotZ) {
            this.direction = direction;
            this.preRotX = preRotX;
            this.preRotZ = preRotZ;
            this.postRotX = postRotX;
            this.postRotZ = postRotZ;
        }
    }
    private static void addFloor(Array<Matrix4> floorTransforms, FloorConstructor constructor) {
        floorTransforms.add(new Matrix4().translate(constructor.x, -2, constructor.z));
    }
    private static void addBarrier(Array<Matrix4> barrierTransforms, Array<Shape3D> barrierShapes, BarrierConstructor constructor) {
        final Box3D shape = createBarrierShape();
        final Matrix4 transform = new Matrix4()
                .translate(constructor.preRotX, 0, constructor.preRotZ)
                .rotate(Vector3.Y, getBarrierYAngle(constructor.direction))
                .translate(constructor.postRotX, 0, constructor.postRotZ);
        shape.set(transform);
        barrierTransforms.add(transform);
        barrierShapes.add(shape);
    }
    private static float getBarrierYAngle(Direction direction) {
        if (direction.isUp()) return 0;
        if (direction.isDown()) return 180;
        if (direction.isLeft()) return 90;
        if (direction.isRight()) return -90;
        throw new IllegalStateException();
    }


    private final ModelCache instances;
    private final Array<Shape3D> barrierShapes = new Array<Shape3D>();

    public Road(Iterable<FloorConstructor> floorConstructors, Iterable<BarrierConstructor> barrierConstructors, @Nullable Iterable<RenderableProvider> additionals) {
        final Array<Matrix4> floorTransforms = new Array<Matrix4>();
        final Array<Matrix4> barrierTransforms = new Array<Matrix4>();
        for (FloorConstructor constructor : floorConstructors)
            addFloor(floorTransforms, constructor);
        for (BarrierConstructor constructor : barrierConstructors)
            addBarrier(barrierTransforms, barrierShapes, constructor);

        final Model floorModel = MODEL_L.get(ModelLoader.MID_ROAD_FLOOR);
        final Model barrierHModel = MODEL_L.get(ModelLoader.MID_ROAD_HORIZONTAL_BAR);
        final Model barrierVModel = MODEL_L.get(ModelLoader.MID_ROAD_VERTICAL_BAR);
        final Model barrierBallModel = MODEL_L.get(ModelLoader.MID_ROAD_BAR_BALL);
        instances = new ModelCache();
        instances.begin(WORLD_CAM);
        for (Matrix4 transform : floorTransforms)
            instances.add(new ModelInstance(floorModel, transform));
        for (Matrix4 transform : barrierTransforms) {
            instances.add(new ModelInstance(barrierHModel, transform));
            instances.add(new ModelInstance(barrierVModel, new Matrix4().mul(transform).translate(-5,-1,0)));
            instances.add(new ModelInstance(barrierBallModel, new Matrix4().mul(transform).translate(-5,0,0)));
        }
        for (RenderableProvider additional : additionals) {
            instances.add(additional);
        }
        instances.end();
    }



    @Override
    public void render(WorldRenderer renderer) {
        renderer.render(instances);
    }


    @Override
    public boolean canCollideWith(EntityCollidable entity) {
        return entity instanceof PlayerCar;
    }
    @Override
    public boolean intersects(Shape3D shape) {
        for (Shape3D barrierShape : barrierShapes)
            if (barrierShape.collideWith(shape)) return true;
        return false;
    }
    @Override
    public boolean intersects(EntityCollidable entity) {
        for (Shape3D barrierShape : barrierShapes)
            if (entity.intersects(barrierShape)) return true;
        return false;
    }


    @Override
    public void dispose() {
        instances.dispose();
    }
}
