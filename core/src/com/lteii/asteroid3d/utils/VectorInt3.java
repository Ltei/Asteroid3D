package com.lteii.asteroid3d.utils;



public class VectorInt3 {


    public int x;
    public int y;
    public int z;

    public VectorInt3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public VectorInt3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public boolean equals(Object object) {
        if (object instanceof VectorInt3) {
            final VectorInt3 vec = (VectorInt3)object;
            return x == vec.x && y == vec.y && z == vec.z;
        }
        return false;
    }

    public boolean equals(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
    }

}
