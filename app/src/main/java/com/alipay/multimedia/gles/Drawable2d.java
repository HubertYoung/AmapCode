package com.alipay.multimedia.gles;

import java.nio.FloatBuffer;

public class Drawable2d {
    public static final int SIZEOF_FLOAT = 4;
    private static final float[] a = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] b = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    private static final FloatBuffer c = GlUtil.createFloatBuffer(a);
    private static final FloatBuffer d = GlUtil.createFloatBuffer(b);
    private FloatBuffer e = c;
    private FloatBuffer f = d;
    private int g = (a.length / this.h);
    private int h = 2;
    private int i = (this.h * 4);
    private int j = 8;

    public FloatBuffer getVertexArray() {
        return this.e;
    }

    public FloatBuffer getTexCoordArray() {
        return this.f;
    }

    public int getVertexCount() {
        return this.g;
    }

    public int getVertexStride() {
        return this.i;
    }

    public int getTexCoordStride() {
        return this.j;
    }

    public int getCoordsPerVertex() {
        return this.h;
    }
}
