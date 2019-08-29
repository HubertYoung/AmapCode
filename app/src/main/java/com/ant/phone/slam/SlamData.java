package com.ant.phone.slam;

public class SlamData {
    public byte[] a;
    public float[] b;

    public SlamData(float[] matrix, byte[] data) {
        this.a = data;
        this.b = matrix;
    }
}
