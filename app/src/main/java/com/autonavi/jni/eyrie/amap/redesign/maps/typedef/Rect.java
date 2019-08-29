package com.autonavi.jni.eyrie.amap.redesign.maps.typedef;

public class Rect {
    public float bottom;
    public float left;
    public float right;
    public float top;

    public Rect() {
        this.left = 0.0f;
        this.right = 0.0f;
        this.top = 0.0f;
        this.bottom = 0.0f;
    }

    public Rect(float f, float f2, float f3, float f4) {
        this.left = f;
        this.right = f2;
        this.top = f3;
        this.bottom = f4;
    }

    public float width() {
        return this.right - this.left;
    }

    public float height() {
        return this.bottom - this.top;
    }
}
