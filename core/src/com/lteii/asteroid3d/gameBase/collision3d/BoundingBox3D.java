package com.lteii.asteroid3d.gameBase.collision3d;


import com.badlogic.gdx.math.Vector3;

public class BoundingBox3D {


    public final Vector3 min, max;

    public BoundingBox3D() {
        this.min = new Vector3();
        this.max = new Vector3();
    }


    public void composeMinMax(Vector3 vector) {
        if (min.x > vector.x) min.x = vector.x;
        if (min.y > vector.y) min.y = vector.y;
        if (min.z > vector.z) min.z = vector.z;
        if (max.x < vector.x) max.x = vector.x;
        if (max.y < vector.y) max.y = vector.y;
        if (max.z < vector.z) max.z = vector.z;
    }
    public void checkValidity() {
        if (min.x <= max.x && min.y <= max.y && min.z <= max.z) return;
        throw new IllegalStateException();
    }

    public boolean contains(BoundingBox3D aabb) {
        return min.x <= aabb.min.x && min.y <= aabb.min.y && min.z <= aabb.min.z
                && max.x >= aabb.max.x && max.y >= aabb.max.y && max.z >= aabb.max.z;
    }
    public boolean intersects(BoundingBox3D aabb) {
        if ((aabb.min.x >= max.x) || (aabb.max.x <= min.x)
                || (aabb.min.y >= max.y) || (aabb.max.y <= min.y)
                || (aabb.min.z >= max.z) || (aabb.max.z <= min.z)) {
            return false;
        }
        return true;
    }

}
