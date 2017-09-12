package com.lteii.asteroid3d.game;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.lteii.asteroid3d.game.entities.Asteroid;
import com.lteii.asteroid3d.game.entities.BeginLine;
import com.lteii.asteroid3d.game.entities.EndLine;
import com.lteii.asteroid3d.game.patterns.Road;
import com.lteii.asteroid3d.gameBase.game3D.Entity;
import com.lteii.asteroid3d.utils.Direction;
import com.lteii.asteroid3d.utils.annotations.Nullable;

import static com.lteii.asteroid3d.Globals.MODEL_L;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_BAR_BALL;
import static com.lteii.asteroid3d.loaders.ModelLoader.MID_ROAD_VERTICAL_BAR;

public class LevelConstructor {

    public static final int LEVEL_MAX = 4;

    static LevelConstructor create(int level) {
        if (level > LEVEL_MAX || level < 0) throw new IllegalArgumentException();
        switch (level) {
            case 0: return create0();
            case 1: return create1();
            case 2: return create2();
            case 3: return create3();
            case 4: return create4();
        }
        throw new IllegalStateException();
    }
    public static @Nullable String getBeginMessage(int level) {
        if (level > LEVEL_MAX || level < 0) throw new IllegalArgumentException();
        switch (level) {
            case 0:
                return "Hello";
            case 1:
                return "You can\npress the screen to turn";
        }
        return null;
    }
    public static @Nullable String getEndMessage(int level, boolean win) {
        if (level > LEVEL_MAX || level < 0) throw new IllegalArgumentException();
        if (win) {
            switch (level) {
                case 0: return "WOAW YOU ARE AWESOME";
                case 1: return "Impressive!\nAre you Chuck Norris?\nIs Chuck Norris playing my game?";
            }
        } else {
            switch (level) {
                case 0: return "WOAW YOU SUCK SO HARD";
                case 1: return "That's embarrassing but...\nAre you a monkey?";
            }
        }
        return null;
    }
    private static LevelConstructor create0() {
        final RoadBuilder roadBuilder = new RoadBuilder(new RoadBuilder.Move(Direction.newUp(), 5));

        final BeginLine beginLine = new BeginLine();
        beginLine.instance.transform.idt().translate(-5,0,0).rotate(Vector3.Y, 90);
        beginLine.updateBodyFromInstance();
        final EndLine endLine = new EndLine();
        endLine.instance.transform.idt().translate(45,0,0).rotate(Vector3.Y, 90);
        endLine.updateBodyFromInstance();
        final Array<Entity> entities = new Array<Entity>();
        entities.add(beginLine);
        entities.add(endLine);

        final Array<RenderableProvider> additionals = new Array<RenderableProvider>();
        final Model hBarModel = MODEL_L.get(MID_ROAD_VERTICAL_BAR);
        final Model barBallModel = MODEL_L.get(MID_ROAD_BAR_BALL);
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,-5)));
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,+5)));
        additionals.add(new ModelInstance(barBallModel, new Matrix4().translate(45,0,-5)));
        additionals.add(new ModelInstance(barBallModel, new Matrix4().translate(45,0,+5)));

        return new LevelConstructor(roadBuilder.floorConstructors, roadBuilder.barrierConstructors, additionals, entities);
    }
    private static LevelConstructor create1() {
        final RoadBuilder roadBuilder = new RoadBuilder(
                new RoadBuilder.Move(Direction.newUp(), 5),
                new RoadBuilder.Move(Direction.newLeft(), 5)
        );

        final BeginLine beginLine = new BeginLine();
        beginLine.instance.transform.idt().translate(-5,0,0).rotate(Vector3.Y, 90);
        beginLine.updateBodyFromInstance();
        final EndLine endLine = new EndLine();
        endLine.instance.transform.idt().translate(50,0,-45);
        endLine.updateBodyFromInstance();
        final Array<Entity> entities = new Array<Entity>();
        entities.add(beginLine);
        entities.add(endLine);

        final Array<RenderableProvider> additionals = new Array<RenderableProvider>();
        final Model hBarModel = MODEL_L.get(MID_ROAD_VERTICAL_BAR);
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,-5)));
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,+5)));

        return new LevelConstructor(roadBuilder.floorConstructors, roadBuilder.barrierConstructors, additionals, entities);
    }
    private static LevelConstructor create2() {
        final RoadBuilder roadBuilder = new RoadBuilder(
                new RoadBuilder.Move(Direction.newUp(), 5),
                new RoadBuilder.Move(Direction.newRight(), 1),
                new RoadBuilder.Move(Direction.newDown(), 6)
        );

        final BeginLine beginLine = new BeginLine();
        beginLine.instance.transform.idt().translate(-5,0,0).rotate(Vector3.Y, 90);
        beginLine.updateBodyFromInstance();
        final EndLine endLine = new EndLine();
        endLine.instance.transform.idt().translate(-5,0,10).rotate(Vector3.Y, 90);
        endLine.updateBodyFromInstance();
        final Array<Entity> entities = new Array<Entity>();
        entities.add(beginLine);
        entities.add(endLine);

        final Array<RenderableProvider> additionals = new Array<RenderableProvider>();
        final Model hBarModel = MODEL_L.get(MID_ROAD_VERTICAL_BAR);
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,-5)));
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,+5)));

        return new LevelConstructor(roadBuilder.floorConstructors, roadBuilder.barrierConstructors, additionals, entities);
    }
    private static LevelConstructor create3() {
        final RoadBuilder roadBuilder = new RoadBuilder(
                new RoadBuilder.Move(Direction.newUp(), 5),
                new RoadBuilder.Move(Direction.newLeft(), 1),
                new RoadBuilder.Move(Direction.newUp(), 1),
                new RoadBuilder.Move(Direction.newRight(), 5)
        );

        final BeginLine beginLine = new BeginLine();
        beginLine.instance.transform.idt().translate(-5,0,0).rotate(Vector3.Y, 90);
        beginLine.updateBodyFromInstance();
        final EndLine endLine = new EndLine();
        endLine.instance.transform.idt().translate(60,0,35);
        endLine.updateBodyFromInstance();
        final Array<Entity> entities = new Array<Entity>();
        entities.add(beginLine);
        entities.add(endLine);

        final Array<RenderableProvider> additionals = new Array<RenderableProvider>();
        final Model hBarModel = MODEL_L.get(MID_ROAD_VERTICAL_BAR);
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,-5)));
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,+5)));

        return new LevelConstructor(roadBuilder.floorConstructors, roadBuilder.barrierConstructors, additionals, entities);
    }
    private static LevelConstructor create4() {
        final RoadBuilder roadBuilder = new RoadBuilder(
                new RoadBuilder.Move(Direction.newUp(), 4),
                new RoadBuilder.Move(Direction.newLeft(), 1),
                new RoadBuilder.Move(Direction.newUp(), 1),
                new RoadBuilder.Move(Direction.newLeft(), 3),
                new RoadBuilder.Move(Direction.newDown(), 3),
                new RoadBuilder.Move(Direction.newLeft(), 2),
                new RoadBuilder.Move(Direction.newUp(), 2),
                new RoadBuilder.Move(Direction.newLeft(), 1),
                new RoadBuilder.Move(Direction.newUp(), 4),
                new RoadBuilder.Move(Direction.newRight(), 2),
                new RoadBuilder.Move(Direction.newDown(), 1),
                new RoadBuilder.Move(Direction.newRight(), 3),
                new RoadBuilder.Move(Direction.newUp(), 2),
                new RoadBuilder.Move(Direction.newRight(), 7),
                new RoadBuilder.Move(Direction.newDown(), 2),
                new RoadBuilder.Move(Direction.newLeft(), 2),
                new RoadBuilder.Move(Direction.newDown(), 3),
                new RoadBuilder.Move(Direction.newRight(), 2),
                new RoadBuilder.Move(Direction.newDown(), 2),
                new RoadBuilder.Move(Direction.newLeft(), 3),
                new RoadBuilder.Move(Direction.newDown(), 4),
                new RoadBuilder.Move(Direction.newLeft(), 2),
                new RoadBuilder.Move(Direction.newUp(), 1)
        );

        final BeginLine beginLine = new BeginLine();
        beginLine.instance.transform.idt().translate(-5f,0,0).rotate(Vector3.Y, 90);
        beginLine.updateBodyFromInstance();
        final EndLine endLine = new EndLine();
        endLine.instance.transform.idt().translate(-15f,0,0).rotate(Vector3.Y, 90);
        endLine.updateBodyFromInstance();
        final Asteroid asteroid = new Asteroid();
        asteroid.instance.transform.idt().translate(-20,0,10);
        asteroid.updateBodyFromInstance();
        final Array<Entity> entities = new Array<Entity>();
        entities.add(beginLine);
        entities.add(endLine);
        entities.add(asteroid);

        final Array<RenderableProvider> additionals = new Array<RenderableProvider>();
        final Model hBarModel = MODEL_L.get(MID_ROAD_VERTICAL_BAR);
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,-5)));
        additionals.add(new ModelInstance(hBarModel, new Matrix4().translate(45,-1,+5)));

        return new LevelConstructor(roadBuilder.floorConstructors, roadBuilder.barrierConstructors, additionals, entities);
    }


    final Array<Road.FloorConstructor> floorConstructors;
    final Array<Road.BarrierConstructor> barrierConstructors;
    final Array<RenderableProvider> additionals;
    final @Nullable Array<Entity> entities;

    private LevelConstructor(Array<Road.FloorConstructor> floorConstructors, Array<Road.BarrierConstructor> barrierConstructors,
                             @Nullable Array<RenderableProvider> additionals, @Nullable Array<Entity> entities) {
        this.floorConstructors = floorConstructors;
        this.barrierConstructors = barrierConstructors;
        this.additionals = additionals;
        this.entities = entities;
    }

}
