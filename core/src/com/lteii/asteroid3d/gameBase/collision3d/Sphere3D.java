package com.lteii.asteroid3d.gameBase.collision3d;


import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Sphere3D extends Shape3D {


    final Vector3 center;
    final float radius;
    final float radius2;

    public Sphere3D(float radius) {
        this.center = new Vector3();
        this.radius = radius;
        this.radius2 = radius*radius;
    }


    @Override
    public boolean collideWith(Shape3D shape) {
        if (!boundingBox.intersects(shape.boundingBox)) return false;

        if (shape instanceof Box3D) {
            return Utils.collisionBoxSphere((Box3D)shape, this);
        } else if (shape instanceof Sphere3D) {
            return Utils.collisionSphereSphere(this, (Sphere3D)shape);
        } else if (shape instanceof CompoundShape3D) {
            return shape.collideWith(this);
        } else {
            throw new IllegalStateException();
        }
    }


    private static final Matrix4 tmpTransform = new Matrix4();
    @Override
    public void set(Matrix4 transform) {
        tmpTransform.set(transform).mul(this.transform);

        tmpTransform.getTranslation(center);
        setBoundingBox();
    }

    private void setBoundingBox() {
        boundingBox.min.set(center).sub(radius);
        boundingBox.max.set(center).add(radius);
        boundingBox.checkValidity();
    }

}
