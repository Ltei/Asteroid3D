package com.lteii.asteroid3d.utils;


import static com.lteii.asteroid3d.utils.Math.mod;

public class Direction {

    public static final byte UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;


    protected byte direction;

    protected Direction(byte direction) {
        this.direction = direction;
    }

    public static Direction newUp() { return new Direction(UP); }
    public static Direction newLeft() { return new Direction(LEFT); }
    public static Direction newDown() { return new Direction(DOWN); }
    public static Direction newRight() { return new Direction(RIGHT); }


    public VectorInt2 getVector() {
        if (direction == UP) {
            return new VectorInt2(0,1);
        } else if (direction == LEFT) {
            return new VectorInt2(-1,0);
        } else if (direction == DOWN) {
            return new VectorInt2(0,-1);
        } else if (direction == RIGHT) {
            return new VectorInt2(1,0);
        }
        throw  new IllegalStateException();
    }
    public VectorInt2 getRightVector() {
        if (direction == UP) {
            return new VectorInt2(1,0);
        } else if (direction == LEFT) {
            return new VectorInt2(0,1);
        } else if (direction == DOWN) {
            return new VectorInt2(-1,0);
        } else if (direction == RIGHT) {
            return new VectorInt2(0,-1);
        }
        throw  new IllegalStateException();
    }

    public boolean isUp() { return direction == UP; }
    public boolean isLeft() { return direction == LEFT; }
    public boolean isDown() { return direction == DOWN; }
    public boolean isRight() { return direction == RIGHT; }


    public static Direction opposite(Direction direction) {
        return new Direction( (byte)mod(direction.direction+2, 4) );
    }

    public static Direction relativeDirection(Direction direction, Direction viewedBy) {
        if (direction.direction == viewedBy.direction) return new Direction(UP);
        if (direction.direction == mod(viewedBy.direction+1, 4)) return new Direction(LEFT);
        if (direction.direction == mod(viewedBy.direction+2, 4)) return new Direction(DOWN);
        if (direction.direction == mod(viewedBy.direction-1, 4)) return new Direction(RIGHT);
        throw  new IllegalStateException();
    }


    @Override
    public String toString() {
        if (direction == UP) {
            return "Up";
        } else if (direction == LEFT) {
            return "Left";
        } else if (direction == DOWN) {
            return "Down";
        } else if (direction == RIGHT) {
            return "Right";
        }
        throw  new IllegalStateException();
    }

}
