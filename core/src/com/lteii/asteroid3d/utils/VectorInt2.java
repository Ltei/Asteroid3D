package com.lteii.asteroid3d.utils;


public class VectorInt2 {

    public int x;
    public int y;

    public VectorInt2() {
        this.x = 0;
        this.y = 0;
    }
    public VectorInt2(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

}
