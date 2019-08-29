package defpackage;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* renamed from: alk reason: default package */
/* compiled from: ScaleGestureDetector */
public final class alk {
    private float A;
    private float B;
    public final Context a;
    public final a b;
    public boolean c;
    public MotionEvent d;
    public MotionEvent e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float k;
    public float l;
    public long m;
    public final float n;
    public float o;
    public float p;
    public boolean q;
    public boolean r;
    public int s;
    public int t;
    public boolean u;
    private float v;
    private float w;
    private float x;
    private float y;
    private float z;

    /* renamed from: alk$a */
    /* compiled from: ScaleGestureDetector */
    public interface a {
        boolean a(alk alk);

        boolean b(alk alk);

        void c(alk alk);
    }

    /* renamed from: alk$b */
    /* compiled from: ScaleGestureDetector */
    public static class b implements a {
        public boolean a(alk alk) {
            return false;
        }

        public boolean b(alk alk) {
            return true;
        }

        public void c(alk alk) {
        }
    }

    public alk(Context context, a aVar) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.a = context;
        this.b = aVar;
        this.n = (float) viewConfiguration.getScaledEdgeSlop();
    }

    public final int a(MotionEvent motionEvent, int i2, int i3) {
        int pointerCount = motionEvent.getPointerCount();
        int findPointerIndex = motionEvent.findPointerIndex(i2);
        for (int i4 = 0; i4 < pointerCount; i4++) {
            if (!(i4 == i3 || i4 == findPointerIndex)) {
                float f2 = this.n;
                float f3 = this.o;
                float f4 = this.p;
                float a2 = a(motionEvent, i4);
                float b2 = b(motionEvent, i4);
                if (a2 >= f2 && b2 >= f2 && a2 <= f3 && b2 <= f4) {
                    return i4;
                }
            }
        }
        return -1;
    }

    public static float a(MotionEvent motionEvent, int i2) {
        if (i2 < 0) {
            return Float.MIN_VALUE;
        }
        if (i2 == 0) {
            return motionEvent.getRawX();
        }
        return motionEvent.getX(i2) + (motionEvent.getRawX() - motionEvent.getX());
    }

    public static float b(MotionEvent motionEvent, int i2) {
        if (i2 < 0) {
            return Float.MIN_VALUE;
        }
        if (i2 == 0) {
            return motionEvent.getRawY();
        }
        return motionEvent.getY(i2) + (motionEvent.getRawY() - motionEvent.getY());
    }

    public final void a(MotionEvent motionEvent) {
        if (this.e != null) {
            this.e.recycle();
        }
        this.e = MotionEvent.obtain(motionEvent);
        this.z = -1.0f;
        this.A = -1.0f;
        this.B = -1.0f;
        MotionEvent motionEvent2 = this.d;
        int findPointerIndex = motionEvent2.findPointerIndex(this.s);
        int findPointerIndex2 = motionEvent2.findPointerIndex(this.t);
        int findPointerIndex3 = motionEvent.findPointerIndex(this.s);
        int findPointerIndex4 = motionEvent.findPointerIndex(this.t);
        if (findPointerIndex < 0 || findPointerIndex2 < 0 || findPointerIndex3 < 0 || findPointerIndex4 < 0) {
            this.r = true;
            if (this.c) {
                this.b.c(this);
            }
            return;
        }
        float x2 = motionEvent2.getX(findPointerIndex);
        float y2 = motionEvent2.getY(findPointerIndex);
        float x3 = motionEvent2.getX(findPointerIndex2);
        float y3 = motionEvent2.getY(findPointerIndex2);
        float x4 = motionEvent.getX(findPointerIndex3);
        float y4 = motionEvent.getY(findPointerIndex3);
        float x5 = motionEvent.getX(findPointerIndex4) - x4;
        float y5 = motionEvent.getY(findPointerIndex4) - y4;
        this.v = x3 - x2;
        this.w = y3 - y2;
        this.x = x5;
        this.y = y5;
        this.f = x4 + (x5 * 0.5f);
        this.g = y4 + (y5 * 0.5f);
        this.m = motionEvent.getEventTime() - motionEvent2.getEventTime();
        this.k = motionEvent.getPressure(findPointerIndex3) + motionEvent.getPressure(findPointerIndex4);
        this.l = motionEvent2.getPressure(findPointerIndex) + motionEvent2.getPressure(findPointerIndex2);
    }

    public final void a() {
        if (this.d != null) {
            this.d.recycle();
            this.d = null;
        }
        if (this.e != null) {
            this.e.recycle();
            this.e = null;
        }
        this.q = false;
        this.c = false;
        this.s = -1;
        this.t = -1;
        this.r = false;
    }

    private float c() {
        if (this.z == -1.0f) {
            float f2 = this.x;
            float f3 = this.y;
            this.z = (float) Math.sqrt((double) ((f2 * f2) + (f3 * f3)));
        }
        return this.z;
    }

    public final float b() {
        if (this.B == -1.0f) {
            this.B = c() / this.j;
        }
        return this.B;
    }
}
