package com.lteii.asteroid3d.utils;


public interface Computation {

    Computation IDENTITY = new Computation() {
        @Override
        public float compute(float x) {
            return x;
        }
    };
    Computation SQUARE = new Computation() {
        @Override
        public float compute(float x) {
            return x*x;
        }
    };
    Computation CUBE = new Computation() {
        @Override
        public float compute(float x) {
            return x*x*x;
        }
    };


    float compute(float x);
}
