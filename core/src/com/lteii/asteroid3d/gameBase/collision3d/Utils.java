package com.lteii.asteroid3d.gameBase.collision3d;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.lteii.asteroid3d.utils.annotations.Outptr;

import static com.badlogic.gdx.math.MathUtils.clamp;

class Utils {

    private static class BoxInfos {

        private final Vector3 center;
        private final Vector3[] edges = new Vector3[8];

        private BoxInfos(Box3D box) {
            center = new Vector3(box.center);

            final Vector3 axis = new Vector3();
            final float angle = box.rotation.getAxisAngle(axis);

            edges[0] = new Vector3(box.halfSize).scl(+1,+1,+1).rotate(axis, angle);
            edges[1] = new Vector3(box.halfSize).scl(+1,+1,-1).rotate(axis, angle);
            edges[2] = new Vector3(box.halfSize).scl(+1,-1,+1).rotate(axis, angle);
            edges[3] = new Vector3(box.halfSize).scl(+1,-1,-1).rotate(axis, angle);
            edges[4] = new Vector3(box.halfSize).scl(-1,+1,+1).rotate(axis, angle);
            edges[5] = new Vector3(box.halfSize).scl(-1,+1,-1).rotate(axis, angle);
            edges[6] = new Vector3(box.halfSize).scl(-1,-1,+1).rotate(axis, angle);
            edges[7] = new Vector3(box.halfSize).scl(-1,-1,-1).rotate(axis, angle);
        }

    }

    private static final Array<Vector3> VECTOR3_POOL = new Array<Vector3>();
    private static final Array<Vector2> VECTOR2_POOL = new Array<Vector2>();
    static {
        for (int i=0; i<3; i++)
            VECTOR3_POOL.add(new Vector3());
        for (int i=0; i<2; i++)
            VECTOR2_POOL.add(new Vector2());
    }


    static boolean collisionBoxBox(Box3D box0, Box3D box1) {
        final BoxInfos boxInfos0 = new BoxInfos(box0);
        final BoxInfos boxInfos1 = new BoxInfos(box1);
        if (isSeparatingAxis(boxInfos0, boxInfos1, box0.axisX)) return false;
        if (isSeparatingAxis(boxInfos0, boxInfos1, box0.axisY)) return false;
        if (isSeparatingAxis(boxInfos0, boxInfos1, box0.axisZ)) return false;
        if (isSeparatingAxis(boxInfos0, boxInfos1, box1.axisX)) return false;
        if (isSeparatingAxis(boxInfos0, boxInfos1, box1.axisY)) return false;
        if (isSeparatingAxis(boxInfos0, boxInfos1, box1.axisZ)) return false;
        return true;
    }
    static boolean collisionBoxSphere(Box3D box, Sphere3D sphere) {
        final Vector3 tmpClosestPoint = VECTOR3_POOL.pop();

        closestPointInOBB(sphere.center, box, tmpClosestPoint);
        final float distance2 = tmpClosestPoint.sub(sphere.center).len2();

        VECTOR3_POOL.add(tmpClosestPoint);
        return distance2 <= sphere.radius2;
    }
    static boolean collisionSphereSphere(Sphere3D sphere0, Sphere3D sphere1) {
        final Vector3 tmpVector = VECTOR3_POOL.pop();

        final boolean collide = tmpVector.set(sphere0.center).sub(sphere1.center).len2() <= sphere0.radius2+sphere1.radius2;

        VECTOR3_POOL.add(tmpVector);
        return collide;
    }

    // Utils for collisionBoxBox
    private static boolean isSeparatingAxis(BoxInfos box0, BoxInfos box1, Vector3 axis) {
        final Vector3 tmpOffset = VECTOR3_POOL.pop();
        final Vector2 tmpRange0 = VECTOR2_POOL.pop();
        final Vector2 tmpRange1 = VECTOR2_POOL.pop();

        tmpOffset.set(box1.center).sub(box0.center);
        final float projectedOffset = tmpOffset.dot(axis);

        flattenPoints(box0, axis, tmpRange0);
        flattenPoints(box1, axis, tmpRange1);

        tmpRange1.add(projectedOffset, projectedOffset);

        VECTOR3_POOL.add(tmpOffset);
        VECTOR2_POOL.add(tmpRange0);
        VECTOR2_POOL.add(tmpRange1);
        return tmpRange0.x > tmpRange1.y || tmpRange1.x > tmpRange0.y;
    }
    private static void flattenPoints(BoxInfos box, Vector3 axis, @Outptr Vector2 output) {
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;

        for (Vector3 edge : box.edges) {
            final float dot = axis.dot(edge);
            if (dot < min) min = dot;
            if (dot > max) max = dot;
        }

        output.set(min, max);
    }

    // Utils for collisionBoxSphere
    private static void closestPointInOBB(Vector3 point, Box3D box, @Outptr Vector3 output) {
        final Vector3 tmpDeltaPos = VECTOR3_POOL.pop();

        tmpDeltaPos.set(point).sub(box.center);
        output.set(box.center);

        final Vector3 tmpVector = new Vector3();
        output.add(tmpVector.set(box.axisX).scl(clamp(tmpDeltaPos.dot(box.axisX), -box.halfSize.x, box.halfSize.x)));
        output.add(tmpVector.set(box.axisY).scl(clamp(tmpDeltaPos.dot(box.axisY), -box.halfSize.y, box.halfSize.y)));
        output.add(tmpVector.set(box.axisZ).scl(clamp(tmpDeltaPos.dot(box.axisZ), -box.halfSize.z, box.halfSize.z)));

        VECTOR3_POOL.add(tmpDeltaPos);
    }

}
