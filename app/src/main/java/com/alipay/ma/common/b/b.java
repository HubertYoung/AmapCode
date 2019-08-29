package com.alipay.ma.common.b;

/* compiled from: MaQrResult */
public final class b extends c {
    public int[] a;
    public int[] b;
    public int c;
    public int d;
    public int e;
    public int f;

    private b(e type, String text) {
        super(type, text);
    }

    public b(e type, String text, int[] xs, int[] ys, int x, int y, int width, int height, char ecLevel, int bitErrors, int version, int strategy) {
        this(type, text);
        this.a = xs;
        this.b = ys;
        this.c = x;
        this.d = y;
        this.e = width;
        this.f = height;
        this.g = ecLevel;
        this.h = bitErrors;
        this.i = version;
        this.j = strategy;
    }
}
