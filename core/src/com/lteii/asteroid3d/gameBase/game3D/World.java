package com.lteii.asteroid3d.gameBase.game3D;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.lteii.asteroid3d.WorldRenderer;
import com.lteii.asteroid3d.utils.annotations.Nullable;

public abstract class World implements Disposable {


    private final Array<EntityCollidable> collidableEntities = new Array<EntityCollidable>();
    private final Array<EntityRenderable> renderableEntities = new Array<EntityRenderable>();
    private final Array<EntityUpdatable> updatableEntities = new Array<EntityUpdatable>();

    public @Nullable CollisionListener collisionListener;
    protected boolean playing = true;


    protected void update(float deltaS) {
        // Update entities
        for (EntityUpdatable updatableEntity : updatableEntities)
            updatableEntity.update(deltaS);

        // Check collisions
        for (int i = 0; i< collidableEntities.size-1; i++) {
            final EntityCollidable object0 = collidableEntities.get(i);
            for (int j = i+1; j< collidableEntities.size; j++) {
                final EntityCollidable object1 = collidableEntities.get(j);
                if (object0.canCollideWith(object1) && object1.canCollideWith(object0)) {
                    if (object0.intersects(object1)) {
                        collisionListener.onCollision(object0, object1);
                        if (playing = false) return;
                    }
                }
            }
        }
    }
    public void render(WorldRenderer renderer) {
        for (EntityRenderable entity : renderableEntities)
            entity.render(renderer);
    }


    public final void add(Entity entity) {
        if (entity instanceof EntityCollidable) {
            if (collidableEntities.contains((EntityCollidable)entity, true)) throw new IllegalStateException();
            collidableEntities.add((EntityCollidable)entity);
        }
        if (entity instanceof EntityRenderable) {
            if (renderableEntities.contains((EntityRenderable)entity, true)) throw new IllegalStateException();
            renderableEntities.add((EntityRenderable)entity);
        }
        if (entity instanceof EntityUpdatable) {
            if (updatableEntities.contains((EntityUpdatable)entity, true)) throw new IllegalStateException();
            updatableEntities.add((EntityUpdatable)entity);
        }
    }
    public final void remove(Entity entity) {
        if (entity instanceof EntityCollidable) {
            if (!collidableEntities.contains((EntityCollidable)entity, true)) throw new IllegalStateException();
            collidableEntities.removeValue((EntityCollidable)entity, true);
        }
        if (entity instanceof EntityRenderable) {
            if (!renderableEntities.contains((EntityRenderable)entity, true)) throw new IllegalStateException();
            renderableEntities.removeValue((EntityRenderable)entity, true);
        }
        if (entity instanceof EntityUpdatable) {
            if (!updatableEntities.contains((EntityUpdatable) entity, true)) throw new IllegalStateException();
            updatableEntities.removeValue((EntityUpdatable) entity, true);
        }
    }

}
