package com.lteii.asteroid3d.gameBase.game3D;


import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;
import com.lteii.asteroid3d.gameBase.collision3d.Shape3D;

public class InstanceShapeEntity extends InstanceEntity implements EntityCollidable {


    protected final Shape3D shape;
    public final Array<Class<? extends EntityCollidable>> collisionFilter = new Array<Class<? extends EntityCollidable>>();

    public InstanceShapeEntity(Model model, Shape3D shape) {
        super(model);
        this.shape = shape;
        shape.set(instance.transform);
    }


    public void updateBodyFromInstance() {
        shape.set(instance.transform);
    }



    @Override
    public boolean canCollideWith(EntityCollidable entity) {
        return collisionFilter.size == 0 || collisionFilter.contains(entity.getClass(), true);
    }

    @Override
    public boolean intersects(Shape3D shape) {
        return this.shape.collideWith(shape);
    }

    @Override
    public boolean intersects(EntityCollidable entity) {
        return entity.intersects(shape);
    }
}
