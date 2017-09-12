package com.lteii.asteroid3d.gameBase.collision3d;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

public class CompoundShape3D extends Shape3D {

    public static class TestModel implements RenderableProvider, Disposable {

        public final Model model;
        public final ModelInstance instance;

        public TestModel(Model model, ModelInstance instance) {
            this.model = model;
            this.instance = instance;
        }

        @Override
        public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
            instance.getRenderables(renderables, pool);
        }

        @Override
        public void dispose() {
            model.dispose();
        }
    }
    public TestModel createTestModel() {
        final ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        for (Shape3D s : shapes) {
            if (s instanceof Box3D) {
                final Box3D shape = (Box3D)s;
                final MeshPartBuilder part = modelBuilder.part("", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal,
                        new Material(ColorAttribute.createDiffuse(Color.GREEN), new BlendingAttribute(.3f)));
                part.setVertexTransform(tmpTransform.set(shape.transform).mul(this.transform));
                BoxShapeBuilder.build(part, shape.halfSize.x*2, shape.halfSize.y*2, shape.halfSize.z*2);
            } else {
                throw new IllegalStateException();
            }
        }
        final Model model = modelBuilder.end();
        return new TestModel(model, new ModelInstance(model));
    }


    public final Array<Shape3D> shapes = new Array<Shape3D>();

    public CompoundShape3D(Shape3D...shapes) {
        for (Shape3D shape : shapes) {
            this.shapes.add(shape);
        }
    }


    @Override
    public boolean collideWith(Shape3D shape) {
        for (Shape3D thisShape : shapes) {
            if (thisShape.collideWith(shape)) return true;
        }
        return false;
    }

    private final Matrix4 tmpTransform = new Matrix4();
    @Override
    public void set(Matrix4 transform) {
        tmpTransform.set(transform).mul(this.transform);
        for (Shape3D shape : shapes) {
            shape.set(tmpTransform);
        }
        setBoundingBox();
    }

    private void setBoundingBox() {
        final Shape3D first = shapes.first();
        boundingBox.min.set(first.boundingBox.min);
        boundingBox.max.set(first.boundingBox.max);
        for (Shape3D shape : shapes) {
            boundingBox.composeMinMax(shape.boundingBox.min);
            boundingBox.composeMinMax(shape.boundingBox.max);
        }
    }

}
