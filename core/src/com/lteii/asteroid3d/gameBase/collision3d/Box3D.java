package com.lteii.asteroid3d.gameBase.collision3d;


import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Box3D extends Shape3D {


    final Vector3 halfSize;

    final Vector3 center;
    final Quaternion rotation;
    final Vector3 axisX, axisY, axisZ;

    public Box3D(float sizeX, float sizeY, float sizeZ) {
        this.halfSize = new Vector3(sizeX/2, sizeY/2, sizeZ/2);

        this.center = new Vector3();
        this.rotation = new Quaternion();
        this.axisX = new Vector3(1,0,0);
        this.axisY = new Vector3(0,1,0);
        this.axisZ = new Vector3(0,0,1);
    }


    @Override
    public boolean collideWith(Shape3D shape) {
        if (!boundingBox.intersects(shape.boundingBox)) return false;

        if (shape instanceof Box3D) {
            return Utils.collisionBoxBox(this, (Box3D)shape);
        } else if (shape instanceof Sphere3D) {
            return Utils.collisionBoxSphere(this, (Sphere3D)shape);
        } else if (shape instanceof CompoundShape3D) {
            return shape.collideWith(this);
        } else {
            throw new IllegalStateException();
        }
    }


    private static final Matrix4 tmpTransform = new Matrix4();
    private static final Vector3 tmpRotationAxis = new Vector3();
    @Override
    public void set(Matrix4 transform) {
        tmpTransform.set(transform).mul(this.transform);

        tmpTransform.getTranslation(center);
        tmpTransform.getRotation(rotation);

        // Set axis XYZ
        final float rotationAngle = rotation.getAxisAngle(tmpRotationAxis);
        axisX.set(1,0,0).rotate(tmpRotationAxis, rotationAngle).nor();
        axisY.set(0,1,0).rotate(tmpRotationAxis, rotationAngle).nor();
        axisZ.set(0,0,1).rotate(tmpRotationAxis, rotationAngle).nor();

        // Set bounding box from this
        setBoundingBox(rotationAngle, tmpRotationAxis);
    }

    private static final Vector3 tmpTop = new Vector3();
    private void setBoundingBox(float rotationAngle, Vector3 rotationAxis) {
        // First top
        tmpTop.set(halfSize).rotate(rotationAxis, rotationAngle);
        boundingBox.min.set(tmpTop);
        boundingBox.max.set(tmpTop);

        boundingBox.composeMinMax(tmpTop.set(halfSize).scl(1,1,-1).rotate(rotationAxis, rotationAngle));
        boundingBox.composeMinMax(tmpTop.set(halfSize).scl(1,-1,1).rotate(rotationAxis, rotationAngle));
        boundingBox.composeMinMax(tmpTop.set(halfSize).scl(1,-1,-1).rotate(rotationAxis, rotationAngle));
        boundingBox.composeMinMax(tmpTop.set(halfSize).scl(-1,1,1).rotate(rotationAxis, rotationAngle));
        boundingBox.composeMinMax(tmpTop.set(halfSize).scl(-1,1,-1).rotate(rotationAxis, rotationAngle));
        boundingBox.composeMinMax(tmpTop.set(halfSize).scl(-1,-1,1).rotate(rotationAxis, rotationAngle));
        boundingBox.composeMinMax(tmpTop.set(halfSize).scl(-1).rotate(rotationAxis, rotationAngle));

        // Add center
        boundingBox.min.add(center);
        boundingBox.max.add(center);

        boundingBox.checkValidity();
    }

}
