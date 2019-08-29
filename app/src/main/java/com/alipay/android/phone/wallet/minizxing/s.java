package com.alipay.android.phone.wallet.minizxing;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public abstract class s extends Drawable {
    public static int g = -1;
    public static int h = -16777216;
    /* access modifiers changed from: private */
    public int a;
    /* access modifiers changed from: private */
    public int b;
    protected BitMatrix i;
    protected int j;
    protected int k;
    protected int l;
    protected Paint m = new Paint();
    protected Paint n;
    protected float o;
    protected float p;
    protected float q;
    protected float r;
    protected int s;
    protected int t;
    protected int u;
    protected int v;
    a w;

    interface a {
        void a(Canvas canvas);
    }

    class b implements a {
        private Bitmap b;
        private Bitmap c = Bitmap.createBitmap(new int[]{s.g}, 1, 1, Config.ARGB_8888);
        private Paint d = new Paint();
        private Rect e = new Rect(0, 0, 1, 1);
        private Rect f;
        private RectF g;
        private RectF h;

        public b() {
            this.g = new RectF(s.this.q - ((float) s.this.u), s.this.r - ((float) s.this.v), s.this.q + (s.this.o * ((float) s.this.s)) + ((float) s.this.u), s.this.r + (s.this.p * ((float) s.this.t)) + ((float) s.this.v));
            int[] src = new int[(s.this.s * s.this.t)];
            for (int i = 0; i < s.this.t; i++) {
                for (int j = 0; j < s.this.s; j++) {
                    src[(s.this.s * i) + j] = s.this.i.get(j, i) ? s.h : s.g;
                }
            }
            this.b = Bitmap.createBitmap(src, s.this.s, s.this.t, Config.ARGB_8888);
            this.f = new Rect(0, 0, s.this.s, s.this.t);
            this.h = new RectF(s.this.q, s.this.r, s.this.q + (s.this.o * ((float) s.this.s)), s.this.r + (s.this.p * ((float) s.this.t)));
            this.d.setAntiAlias(false);
            this.d.setDither(false);
            this.d.setFilterBitmap(false);
        }

        public final void a(Canvas canvas) {
            canvas.setDrawFilter(null);
            canvas.drawBitmap(this.c, this.e, this.g, this.d);
            canvas.drawBitmap(this.b, this.f, this.h, this.d);
        }
    }

    class c implements a {
        c() {
        }

        public final void a(Canvas canvas) {
            canvas.drawRect(s.this.q - ((float) s.this.u), s.this.r - ((float) s.this.v), (s.this.o * ((float) s.this.s)) + s.this.q + ((float) s.this.u), (s.this.p * ((float) s.this.t)) + s.this.r + ((float) s.this.v), s.this.m);
            s.this.b = 0;
            while (s.this.b < s.this.t) {
                s.this.a = 0;
                while (s.this.a < s.this.s) {
                    if (s.this.i.get(s.this.a, s.this.b)) {
                        canvas.drawRect((((float) s.this.a) * s.this.o) + s.this.q, (((float) s.this.b) * s.this.p) + s.this.r, (((float) (s.this.a + 1)) * s.this.o) + s.this.q, (((float) (s.this.b + 1)) * s.this.p) + s.this.r, s.this.n);
                    }
                    s.f(s.this);
                }
                s.c(s.this);
            }
        }
    }

    static /* synthetic */ int c(s x0) {
        int i2 = x0.b + 1;
        x0.b = i2;
        return i2;
    }

    static /* synthetic */ int f(s x0) {
        int i2 = x0.a + 1;
        x0.a = i2;
        return i2;
    }

    public s(BitMatrix _bitMatrix, int _width, int _height, int _rotate) {
        this.i = _bitMatrix;
        this.j = _width;
        this.k = _height;
        this.l = _rotate % 360;
        this.m.setColor(g);
        this.n = new Paint();
        this.n.setColor(h);
    }

    public void draw(Canvas canvas) {
        if (this.w != null) {
            this.w.a(canvas);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public final void a(DrawCoreTypes s2) {
        switch (s2) {
            case DrawCoreType_Drawable:
                this.w = new c();
                return;
            default:
                this.w = new b();
                return;
        }
    }
}
