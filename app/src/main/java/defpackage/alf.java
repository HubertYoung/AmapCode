package defpackage;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.view.MotionEvent;

/* renamed from: alf reason: default package */
/* compiled from: BaseGestureDetector */
public abstract class alf {
    protected final Context a;
    protected boolean b;
    protected MotionEvent c;
    protected MotionEvent d;
    protected float e;
    protected float f;
    protected long g;

    /* access modifiers changed from: protected */
    public abstract void a(int i, MotionEvent motionEvent);

    /* access modifiers changed from: protected */
    public abstract void b(int i, MotionEvent motionEvent);

    public alf(Context context) {
        this.a = context;
    }

    public final boolean a(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (!this.b) {
            a(action, motionEvent);
        } else {
            b(action, motionEvent);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void b(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = this.c;
        if (this.d != null) {
            this.d.recycle();
            this.d = null;
        }
        this.d = MotionEvent.obtain(motionEvent);
        this.g = motionEvent.getEventTime() - motionEvent2.getEventTime();
        if (VERSION.SDK_INT >= 8) {
            this.e = motionEvent.getPressure(motionEvent.getActionIndex());
            this.f = motionEvent2.getPressure(motionEvent2.getActionIndex());
            return;
        }
        this.e = motionEvent.getPressure(0);
        this.f = motionEvent2.getPressure(0);
    }

    /* access modifiers changed from: protected */
    public void a() {
        if (this.c != null) {
            this.c.recycle();
            this.c = null;
        }
        if (this.d != null) {
            this.d.recycle();
            this.d = null;
        }
        this.b = false;
    }

    public final long b() {
        return this.g;
    }

    public final MotionEvent c() {
        return this.d;
    }

    public static PointF c(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (int i = 0; i < pointerCount; i++) {
            f2 += motionEvent.getX(i);
            f3 += motionEvent.getY(i);
        }
        float f4 = (float) pointerCount;
        return new PointF(f2 / f4, f3 / f4);
    }
}
