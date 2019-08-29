package com.alipay.zoloz.hardware.camera;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

/* compiled from: CameraData */
public final class a {
    public ByteBuffer a;
    public int b;
    public int c;
    public int d;
    public ShortBuffer e;
    public int f;
    public int g;
    public int h;
    public int i;
    boolean j;

    public a() {
    }

    public a(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5) {
        this(byteBuffer, i2, i3, i4, i5, 0);
    }

    private a(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5, byte b2) {
        this.a = byteBuffer;
        this.b = i2;
        this.c = i3;
        this.d = 0;
        this.e = null;
        this.f = 0;
        this.g = 0;
        this.h = i4;
        this.i = i5;
        this.j = false;
    }
}
