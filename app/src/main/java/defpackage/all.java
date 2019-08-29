package defpackage;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* renamed from: all reason: default package */
/* compiled from: TwoFingerGestureDetector */
public abstract class all extends alf {
    private final float h;
    protected float i;
    protected float j;
    protected float k;
    protected float l;
    private float m;
    private float n;
    private float o;
    private float p;
    private float q = 0.0f;
    private float r = 0.0f;
    private float s = 0.0f;
    private float t = 0.0f;

    /* access modifiers changed from: protected */
    public abstract void a(int i2, MotionEvent motionEvent);

    /* access modifiers changed from: protected */
    public abstract void b(int i2, MotionEvent motionEvent);

    public all(Context context) {
        super(context);
        this.h = (float) ViewConfiguration.get(context).getScaledEdgeSlop();
    }

    /* access modifiers changed from: protected */
    public void b(MotionEvent motionEvent) {
        super.b(motionEvent);
        MotionEvent motionEvent2 = this.c;
        int pointerCount = this.c.getPointerCount();
        int pointerCount2 = motionEvent.getPointerCount();
        if (pointerCount2 == 2 && pointerCount2 == pointerCount) {
            this.o = -1.0f;
            this.p = -1.0f;
            float x = motionEvent2.getX(0);
            float y = motionEvent2.getY(0);
            float x2 = motionEvent2.getX(1);
            float y2 = motionEvent2.getY(1);
            this.i = x2 - x;
            this.j = y2 - y;
            float x3 = motionEvent.getX(0);
            float y3 = motionEvent.getY(0);
            float x4 = motionEvent.getX(1);
            float y4 = motionEvent.getY(1);
            this.k = x4 - x3;
            this.l = y4 - y3;
            this.q = x3 - x;
            this.r = y3 - y;
            this.s = x4 - x2;
            this.t = y4 - y2;
        }
    }

    public final PointF a(int i2) {
        if (i2 == 0) {
            return new PointF(this.q, this.r);
        }
        return new PointF(this.s, this.t);
    }

    /* access modifiers changed from: protected */
    public final boolean d(MotionEvent motionEvent) {
        DisplayMetrics displayMetrics = this.a.getResources().getDisplayMetrics();
        this.m = ((float) displayMetrics.widthPixels) - this.h;
        this.n = ((float) displayMetrics.heightPixels) - this.h;
        float f = this.h;
        float f2 = this.m;
        float f3 = this.n;
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        float f4 = 0.0f;
        float x = 1 < motionEvent.getPointerCount() ? (motionEvent.getX() - motionEvent.getRawX()) + motionEvent.getX(1) : 0.0f;
        float y = motionEvent.getY() - motionEvent.getRawY();
        if (1 < motionEvent.getPointerCount()) {
            f4 = motionEvent.getY(1) + y;
        }
        boolean z = rawX < f || rawY < f || rawX > f2 || rawY > f3;
        boolean z2 = x < f || f4 < f || x > f2 || f4 > f3;
        if ((!z || !z2) && !z && !z2) {
            return false;
        }
        return true;
    }
}
