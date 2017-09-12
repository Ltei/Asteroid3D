package com.lteii.asteroid3d.loaders;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CylinderShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import static com.lteii.asteroid3d.Globals.TEXTURE_L;
import static com.lteii.asteroid3d.utils.Math.color255;

public class ModelLoader extends Loader<ModelLoader.ModelID, Model> {


    public static class ModelID extends Loader.Key {
        private final boolean isFilePath;
        private final String id;
        private ModelID(boolean isFilePath, String id) {
            this.isFilePath = isFilePath;
            this.id = id;
        }
    }


    public static final ModelID MID_PLAYER_CAR = new ModelID(true, "object/playerCar5.obj");
    public static final ModelID MID_SKY_SPHERE = new ModelID(false, "skySphere");
    public static final ModelID MID_ROAD_FLOOR = new ModelID(false, "roadFloor");
    public static final ModelID MID_ROAD_HORIZONTAL_BAR = new ModelID(false, "roadHorizontalBar");
    public static final ModelID MID_ROAD_VERTICAL_BAR = new ModelID(false, "roadVerticalBar");
    public static final ModelID MID_ROAD_BAR_BALL = new ModelID(false, "roadBarBall");
    public static final ModelID MID_ASTEROID = new ModelID(false, "asteroid");


    private final ObjLoader objLoader = new ObjLoader();
    private final ModelBuilder modelBuilder = new ModelBuilder();


    public Model get(ModelID id) {
        return loaded.get(id);
    }


    @Override
    protected void load(ModelID id) {
        if (isLoaded(id)) throw new IllegalArgumentException();
        if (id.isFilePath) {
            loaded.put(id, objLoader.loadModel(Gdx.files.internal(id.id)));
        } else {
            loadCustomModel(id);
        }
    }
    @Override
    protected void unload(ModelID id) {
        if (!isLoaded(id)) throw new IllegalArgumentException();
        loaded.remove(id).dispose();
    }

    private void loadCustomModel(ModelID id) {
        if (id == MID_SKY_SPHERE) {
            loadSkySphere();
        } else if (id == MID_ROAD_FLOOR) {
            loadRoadFloor();
        } else if (id == MID_ROAD_HORIZONTAL_BAR) {
            loadRoadHorizontalBar();
        }  else if (id == MID_ROAD_VERTICAL_BAR) {
            loadRoadVerticalBar();
        } else if (id == MID_ROAD_BAR_BALL) {
            loadRoadBarBall();
        } else if (id == MID_ASTEROID) {
            loadAsteroid();
        } else {
            throw new IllegalStateException();
        }
    }
    private void loadSkySphere() {
        final Model model = modelBuilder.createSphere(500, 500, 500, 30, 30,
                new Material(TextureAttribute.createDiffuse(TEXTURE_L.get(TextureLoader.TID_SKY))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

        // Invert the indices so that the Sphere's inside is rendered instead of it's outside
        final Mesh mesh = model.meshes.get(0);
        final int indicesArraySize = mesh.getNumIndices();
        final short[] indices = new short[indicesArraySize];
        mesh.getIndices(indices);
        final short[] invertedIndices = new short[indicesArraySize];
        for (int i=0; i<indicesArraySize; i++) {
            invertedIndices[i] = indices[indicesArraySize-i-1];
        }
        mesh.setIndices(invertedIndices);
        // Can also force the Sphere's inside rendering like that
        //model.materials.get(0).set(new IntAttribute(IntAttribute.CullFace, 0));

        loaded.put(MID_SKY_SPHERE, model);
    }
    private void loadRoadFloor() {
        final float floorSize = 10;
        final float floorHalfSize = floorSize/2;
        final Model floorPlateModel = modelBuilder.createRect(
                -floorHalfSize,0,-floorHalfSize,
                -floorHalfSize,0,floorHalfSize,
                floorHalfSize,0,floorHalfSize,
                floorHalfSize,0,-floorHalfSize,
                0,1,0,
                new Material(TextureAttribute.createDiffuse(TEXTURE_L.get(TextureLoader.TID_FLOOR)), new BlendingAttribute()),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        loaded.put(MID_ROAD_FLOOR, floorPlateModel);
    }
    private void loadRoadHorizontalBar() {
        modelBuilder.begin();
        final MeshPartBuilder part = modelBuilder.part("p1", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                new Material(ColorAttribute.createDiffuse(color255(100))));
        part.setVertexTransform(new Matrix4().rotate(Vector3.Z, 90));
        CylinderShapeBuilder.build(part, .5f, 10, .5f, 10);
        loaded.put(MID_ROAD_HORIZONTAL_BAR, modelBuilder.end());
    }
    private void loadRoadVerticalBar() {
        loaded.put(MID_ROAD_VERTICAL_BAR, modelBuilder.createCylinder(.5f, 2, .5f, 10,
                new Material(ColorAttribute.createDiffuse(color255(100))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }
    private void loadRoadBarBall() {
        loaded.put(MID_ROAD_BAR_BALL, modelBuilder.createSphere(.5f, .5f, .5f, 10, 10,
                new Material(ColorAttribute.createDiffuse(color255(100))),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }
    private void loadAsteroid() {
        loaded.put(MID_ASTEROID, modelBuilder.createBox(1, 1, 1, new Material(ColorAttribute.createDiffuse(Color.DARK_GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

    @Override
    public void dispose() {
        for (Model model : loaded.values())
            model.dispose();
        loaded.clear();
    }

}
